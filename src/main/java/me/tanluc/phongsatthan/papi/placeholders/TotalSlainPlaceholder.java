package me.tanluc.phongsatthan.papi.placeholders;

import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.database.DatabaseManager;

public class TotalSlainPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public TotalSlainPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getTotalSlain());
    }

    @Override
    public String getName() {
        return "total_slain";
    }
}
