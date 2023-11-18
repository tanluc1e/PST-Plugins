package me.tanluc.phongsatthan.papi.placeholders;

import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.database.DatabaseManager;

public class PlayerCurrentXpPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public PlayerCurrentXpPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getCurrentXp());
    }

    @Override
    public String getName() {
        return "player_experience";
    }
}
