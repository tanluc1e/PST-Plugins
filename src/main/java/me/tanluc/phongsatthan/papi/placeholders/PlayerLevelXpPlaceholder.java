package me.tanluc.phongsatthan.papi.placeholders;

import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.database.DatabaseManager;
import org.bukkit.entity.Player;

public class PlayerLevelXpPlaceholder implements Placeholder {
    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public PlayerLevelXpPlaceholder(MobContracts plugin, DatabaseManager databaseManager) {
        this.plugin = plugin; // Initialize the plugin instance
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getCurrentLevel() * plugin.getConfig().getInt("settings.levels.xp-multi"));
    }

    @Override
    public String getName() {
        return "player_level_experience";
    }
}
