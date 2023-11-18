package me.tanluc.phongsatthan.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.events.ContractSummonEvent;
import me.tanluc.phongsatthan.utils.CurrentContracts;

public class ContractSummonListener implements Listener {

    private final CurrentContracts currentContracts;
    private final DatabaseManager databaseManager;

    public ContractSummonListener(CurrentContracts currentContracts, DatabaseManager databaseManager) {
        this.currentContracts = currentContracts;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onContractSpawn(ContractSummonEvent event) {
        currentContracts.addEntity(event.getPlayer(), event.getEntity());

        databaseManager.addToContractsMap(event.getPlayer().getUniqueId(), event.getPlayer().getName(), event.getUuid(), event.getName(),
                event.getTier(), event.getEntity().getType(), (int) Math.round(event.getHealth()), (int) Math.round(event.getDamage()));

    }
}
