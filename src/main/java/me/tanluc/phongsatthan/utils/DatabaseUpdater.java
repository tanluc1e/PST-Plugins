package me.tanluc.phongsatthan.utils;

import me.tanluc.phongsatthan.database.DatabaseManager;
import org.bukkit.scheduler.BukkitRunnable;
import me.tanluc.phongsatthan.MobContracts;

public class DatabaseUpdater {

    private final DatabaseManager databaseManager;
    private final MobContracts plugin;

    public DatabaseUpdater(DatabaseManager databaseManager, MobContracts plugin) {
        this.databaseManager = databaseManager;
        this.plugin = plugin;
    }

    public void updateDatabaseTimer(){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(plugin.isEnabled){
                    databaseManager.updateDatabase();
                    databaseManager.saveContracts();
                }
            }
        }.runTaskTimer(plugin, 6000, 6000);
    }
}