package ru.n08i40k.freezeplayer.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.n08i40k.freezeplayer.FreezePlayer;

public class Freeze implements CommandExecutor {
    FreezePlayer plugin;
    public Freeze(FreezePlayer instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        Player p = ((sender instanceof Player) ? (Player) sender : null);
        if (p == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can use this command only in game!"));
            return false;
        }

        if (!sender.hasPermission("freeze.freeze")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-args").replace("{example}", "/freeze N08I40K")));
            return false;
        }
        if (plugin.FreezedPlayers.contains(args[0])) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.already-frozen").replace("{nick}", args[0])));
            return false;
        }

        plugin.FreezedPlayers.add(args[0]);
        plugin.database.FreezePlayer(args[0]);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.freeze-success").replace("{nick}", args[0])));

        Player freezed = plugin.getServer().getPlayer(args[0]);
        if (freezed != null)
            freezed.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.frozen-by").replace("{nick}", p.getName())));
        return true;
    }
}
