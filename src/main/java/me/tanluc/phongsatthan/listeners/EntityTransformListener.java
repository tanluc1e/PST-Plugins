package me.tanluc.phongsatthan.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityTransformListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getScoreboardTags().contains("Contract")) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
