package me.tanluc.phongsatthan.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.utils.CurrentContracts;

public class PlayerLeaveListener implements Listener {

    private final CurrentContracts currentContracts;
    private final MobContracts plugin;

    public PlayerLeaveListener(CurrentContracts currentContracts, MobContracts plugin) {
        this.currentContracts = currentContracts;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("settings.general.kill-contract-on-leave")) {
            if (currentContracts.inContract(event.getPlayer())) {
                currentContracts.removePlayerContract(event.getPlayer());
            }
        }
    }
}