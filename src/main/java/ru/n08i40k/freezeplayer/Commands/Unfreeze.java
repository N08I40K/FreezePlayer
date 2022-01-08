package ru.n08i40k.freezeplayer.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.n08i40k.freezeplayer.FreezePlayer;

public class Unfreeze implements CommandExecutor {
    FreezePlayer plugin;
    public Unfreeze(FreezePlayer instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        Player p = ((sender instanceof Player) ? (Player) sender : null);
        if (p == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can use this command only in game!"));
            return false;
        }

        if (!sender.hasPermission("freeze.unfreeze")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-args").replace("{example}", "/unfreeze N08I40K")));
            return false;
        }
        if (!plugin.FreezedPlayers.contains(args[0])) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-frozen").replace("{nick}", args[0])));
            return false;
        }

        plugin.FreezedPlayers.remove(args[0]);
        plugin.database.UnFreezePlayer(args[0]);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.unfreeze-success").replace("{nick}", args[0])));

        Player freezed = plugin.getServer().getPlayer(args[0]);
        if (freezed != null)
            freezed.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.unfrozen-by").replace("{nick}", p.getName())));
        return true;
    }
}
