package me.tanluc.phongsatthan.papi.placeholders;

import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.database.DatabaseManager;

public class CommonSlainPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public CommonSlainPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getCommonSlain());
    }

    @Override
    public String getName() {
        return "common_slain";
    }
}
