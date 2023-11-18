package me.tanluc.phongsatthan.papi.placeholders;

import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.database.DatabaseManager;

public class LegendarySlainPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public LegendarySlainPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getLegendarySlain());
    }

    @Override
    public String getName() {
        return "legendary_slain";
    }
}
