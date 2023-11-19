package me.tanluc.phongsatthan.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.gui.MainMenu;
import me.tanluc.phongsatthan.utils.CreateCustomGuiItem;

import java.util.List;

public class LeaderboardsCommand extends ChildCommand {

    private final MobContracts plugin;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final DatabaseManager databaseManager;

    public LeaderboardsCommand(String command, MobContracts plugin, CreateCustomGuiItem createCustomGuiItem, DatabaseManager databaseManager) {
        super(command);
        this.plugin = plugin;
        this.createCustomGuiItem = createCustomGuiItem;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.leaderboard.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.leaderboard.usage");
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        new MainMenu(plugin.getMenuUtil(player), createCustomGuiItem, plugin, databaseManager).open();
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
