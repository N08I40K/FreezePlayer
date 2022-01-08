package ru.n08i40k.freezeplayer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import java.util.logging.Logger;

public class EventsListener implements Listener {
    FreezePlayer plugin;
    Logger logger;

    public EventsListener(FreezePlayer instance) {
        plugin = instance;
        logger = plugin.getLogger();
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onDamageSelf(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player && plugin.FreezedPlayers.contains(ev.getEntity().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onDamageOther(EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Player && plugin.FreezedPlayers.contains(ev.getDamager().getName())) ev.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCmd(PlayerCommandPreprocessEvent ev) {
        if (!plugin.allowed_cmds.contains(ev.getMessage().split(" ")[0].substring(1)) && plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onBedEnter(PlayerBedEnterEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getEntity().getName())) ev.getEntity().setHealth(20);
    }
    @EventHandler(ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent ev) {
        if (ev.getWhoClicked() instanceof Player && plugin.FreezedPlayers.contains(ev.getWhoClicked().getName())) ev.setCancelled(true);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        if (plugin.FreezedPlayers.contains(ev.getPlayer().getName())) ev.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.join-if-frozen")));
    }

}
