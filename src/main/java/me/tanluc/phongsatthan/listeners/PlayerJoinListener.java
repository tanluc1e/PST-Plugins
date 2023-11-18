package me.tanluc.phongsatthan.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.database.DatabaseManager;

public class PlayerJoinListener implements Listener {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public PlayerJoinListener(MobContracts plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        databaseManager.newPlayer(event.getPlayer());
    }
}
