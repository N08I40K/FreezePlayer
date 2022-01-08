package ru.n08i40k.freezeplayer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.n08i40k.freezeplayer.Classes.Database;
import ru.n08i40k.freezeplayer.Commands.Freeze;
import ru.n08i40k.freezeplayer.Commands.Unfreeze;

import java.util.ArrayList;
import java.util.List;

public final class FreezePlayer extends JavaPlugin {
    public List<String> FreezedPlayers = new ArrayList<>();
    public Database database;
    public FileConfiguration config;
    public List<String> allowed_cmds;

    @Override
    public void onEnable() {
        // Plugin startup logic
        database = new Database(this);

        // Config
        config = this.getConfig();
        config.addDefault("settings.allowed-cmds", new String[]{"m", "msg"});

        config.addDefault("messages.no-permission", "&4You do not have permission to use this command!");
        config.addDefault("messages.no-args", "&eYou have not provided a nickname!\n&e{example}");

        config.addDefault("messages.freeze-success", "&2You have successfully frozen &e{nick}&2!");
        config.addDefault("messages.unfreeze-success", "&2You have successfully unfrozen &e{nick}&2!");

        config.addDefault("messages.already-frozen", "&6Player {nick} is already frozen!");
        config.addDefault("messages.not-frozen", "&6Player {nick} was not frozen!");

        config.addDefault("messages.frozen-by", "&6You have been frozen by {nick}!");
        config.addDefault("messages.join-if-frozen", "&6You are still frozen!");
        config.addDefault("messages.unfrozen-by", "&6You have been unfrozen by {nick}!");

        config.options().copyDefaults(true);
        this.saveConfig();
        allowed_cmds = config.getStringList("settings.allowed-cmds");

        // EventsListener
        this.getServer().getPluginManager().registerEvents(new EventsListener(this), this);

        // Commands
        getServer().getPluginCommand("freeze").setExecutor(new Freeze(this));
        getServer().getPluginCommand("unfreeze").setExecutor(new Unfreeze(this));

        this.getLogger().info("Plugin successfully loaded!");
    }

    @Override
    public void onDisable() {
        database.closeConnection();
        // Plugin shutdown logic
    }
    public void Stop() {
        if (this.isEnabled()) {
            this.getLogger().warning("Plugin is shutting down!");
            this.setEnabled(false);
        }
    }
}
