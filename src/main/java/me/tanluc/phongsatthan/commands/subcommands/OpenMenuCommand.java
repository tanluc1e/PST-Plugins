package me.tanluc.phongsatthan.commands.subcommands;

import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.gui.generic.AllContractsGui;
import me.tanluc.phongsatthan.gui.generic.ProfilesGui;
import me.tanluc.phongsatthan.gui.slain.CommonContractsGui;
import me.tanluc.phongsatthan.gui.slain.EpicContractsGui;
import me.tanluc.phongsatthan.gui.slain.LegendaryContractsGui;
import me.tanluc.phongsatthan.gui.stats.ContractsKilledGui;
import me.tanluc.phongsatthan.gui.stats.PlayerLevelGui;
import me.tanluc.phongsatthan.gui.stats.ServerStatsGui;
import me.tanluc.phongsatthan.gui.stats.TotalExperienceGui;
import me.tanluc.phongsatthan.utils.CreateCustomGuiItem;
import me.tanluc.phongsatthan.utils.GenericUseMethods;
import me.tanluc.phongsatthan.utils.PlayerObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OpenMenuCommand extends ChildCommand {

    private final MobContracts plugin;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final DatabaseManager databaseManager;
    private final GenericUseMethods genericUseMethods;

    public OpenMenuCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, DatabaseManager databaseManager, CreateCustomGuiItem createCustomGuiItem) {
        super(command);
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.databaseManager = databaseManager;
        this.createCustomGuiItem = createCustomGuiItem;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.menu.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.menu.usage");
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();

        if (args.length < 2 || args.length > 3) {
            genericUseMethods.sendVariedSenderMessage(sender, "&e" + this.getSyntax());
            return;
        }

        Player targetPlayer;
        if (args.length == 2) {
            if (!(sender instanceof Player)) {
                genericUseMethods.sendVariedSenderMessage(sender, "&cError: You must be a player to use this command without specifying a player name.");
                return;
            }
            targetPlayer = (Player) sender;
        } else {
            targetPlayer = plugin.getServer().getPlayer(args[2]);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online or does not exist!");
                return;
            }
        }

        if (args[1].equalsIgnoreCase("all")) {
            new AllContractsGui(plugin.getMenuUtil((Player) sender),  databaseManager, createCustomGuiItem, plugin).open();
        } else if (args[1].equalsIgnoreCase("common")) {
            new CommonContractsGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("epic")) {
            new EpicContractsGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("legendary")) {
            new LegendaryContractsGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("profile")) {
            new ProfilesGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("server")) {
            new ServerStatsGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("kill")) {
            new ContractsKilledGui(plugin.getMenuUtil((Player) sender), databaseManager, createCustomGuiItem, plugin).open();
        } else if (args[1].equalsIgnoreCase("exp")) {
            new TotalExperienceGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        } else if (args[1].equalsIgnoreCase("level")) {
            new PlayerLevelGui(plugin.getMenuUtil((Player) sender), plugin, databaseManager, createCustomGuiItem).open();
        }
    }


    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2) {
            return Arrays.asList("all", "common", "epic", "legendary", "profile", "server", "kill", "exp", "level");
        }
        return null;
    }
}
