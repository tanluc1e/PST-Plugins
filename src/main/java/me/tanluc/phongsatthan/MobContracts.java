package me.tanluc.phongsatthan;

import me.tanluc.phongsatthan.commands.CommandManager;
import me.tanluc.phongsatthan.contracts.CommonContract;
import me.tanluc.phongsatthan.contracts.EpicContract;
import me.tanluc.phongsatthan.contracts.LegendaryContract;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.effects.CommonEffects;
import me.tanluc.phongsatthan.effects.EpicEffects;
import me.tanluc.phongsatthan.effects.LegendaryEffects;
import me.tanluc.phongsatthan.gui.handler.GuiUtil;
import me.tanluc.phongsatthan.level.LevellingSystem;
import me.tanluc.phongsatthan.listeners.*;
import me.tanluc.phongsatthan.mobs.MobFeatures;
import me.tanluc.phongsatthan.papi.MobContractsPlaceholderExpansion;
import me.tanluc.phongsatthan.papi.placeholders.*;
import me.tanluc.phongsatthan.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.tanluc.phongsatthan.database.*;
import me.tanluc.phongsatthan.listeners.*;
import me.tanluc.phongsatthan.papi.placeholders.*;
import me.tanluc.phongsatthan.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MobContracts extends JavaPlugin {

    private CommandManager commandManager;
    private GenericUseMethods genericUseMethods;
    private LegendaryContract legendaryContract;
    private CommonContract commonContract;
    private EpicContract epicContract;
    private MobFeatures mobFeatures;
    private LegendaryEffects legendaryEffects;
    private EpicEffects epicEffects;
    private CurrentContracts currentContracts;
    private ContractType contractType;
    private LevellingSystem levellingSystem;
    private ContractStats contractStats;
    private CreateCustomGuiItem createCustomGuiItem;
    private MobContractsPlaceholderExpansion expansion;
    private CommonEffects commonEffects;
    private DatabaseManager databaseManager;
    private DatabaseUpdater databaseUpdater;

    public Boolean isEnabled = false;

    private static final Map<Player, GuiUtil> playerMap = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getLogger().info("Preparing to enable...");
        getLogger().info("Loading classes...");
        databaseManager = new DatabaseManager(this);
        contractType = new ContractType();
        currentContracts = new CurrentContracts();
        databaseUpdater = new DatabaseUpdater(databaseManager, this);

        genericUseMethods = new GenericUseMethods(this);
        levellingSystem = new LevellingSystem(this, genericUseMethods, databaseManager);
        commonEffects = new CommonEffects(this);
        legendaryEffects = new LegendaryEffects(this, genericUseMethods, currentContracts);
        epicEffects = new EpicEffects(this, currentContracts);
        createCustomGuiItem = new CreateCustomGuiItem(this);
        mobFeatures = new MobFeatures(this, genericUseMethods);
        legendaryContract = new LegendaryContract(this, mobFeatures, legendaryEffects, contractType);
        commonContract = new CommonContract(this, mobFeatures, contractType, commonEffects);
        epicContract = new EpicContract(this, mobFeatures, epicEffects, contractType);
        commandManager = new CommandManager(this,
                genericUseMethods,
                legendaryContract,
                epicContract,
                commonContract,
                currentContracts,
                contractType,
                createCustomGuiItem,
                databaseManager);

        getLogger().info("Classes loaded!");

        // database stuffz
        getLogger().info("Connecting to database.");

        // Player database creation
        File file = new File(this.getDataFolder(), "PlayerDatabase.db");
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Contracts database creation
        File contractFile = new File(this.getDataFolder(), "ContractsDatabase.db");
        if(!contractFile.exists()){
            try{
                contractFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create the tables in the stuffs
        getLogger().info("Starting database checking...");
        databaseManager.createPlayerDatabase();
        databaseManager.createContractsDatabase();
        getLogger().info("Databases okay!");
        getLogger().info("Loading leaderboard dependants...");

        // Load all data back up boii
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().info("Loading leaderboard dependants");
                databaseManager.loadAllPlayers();
                databaseManager.loadAllContracts();
                getLogger().info("Leaderboard dependants loaded");
            }
        }.runTaskLater(this, 100);

        getLogger().info("Database checks complete! SQLite ready!");

        // register events
        getLogger().info("Registering events...");
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(this, databaseManager), this);
        pluginManager.registerEvents(new PlayerLeaveListener(currentContracts, this), this);
        pluginManager.registerEvents(new ContractSummonListener(currentContracts, databaseManager), this);
        pluginManager.registerEvents(new EntityDeathListener(this, contractType, currentContracts, databaseManager, genericUseMethods, levellingSystem, mobFeatures), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityTransformListener(), this);
        pluginManager.registerEvents(new GuiClickListener(), this);
        getLogger().info("Events registered");

        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Registering placeholders...");
            expansion = new MobContractsPlaceholderExpansion(this);

            // %mobcontracts_player_level%
            expansion.registerPlaceholders(new PlayerLevelPlaceholder(databaseManager));
            // %mobcontracts_player_level_experience%
            expansion.registerPlaceholders(new PlayerLevelXpPlaceholder(this, databaseManager));
            // %mobcontracts_player_experience%
            expansion.registerPlaceholders(new PlayerCurrentXpPlaceholder(databaseManager));
            // %mobcontracts_player_total_experience%
            expansion.registerPlaceholders(new PlayerTotalXpPlaceholder(databaseManager));
            // %mobcontracts_common_owned%
            expansion.registerPlaceholders(new CommonOwnedPlaceholder(databaseManager));
            // %mobcontracts_epic_owned%
            expansion.registerPlaceholders(new EpicOwnedPlaceholder(databaseManager));
            // %mobcontracts_legendary_owned%
            expansion.registerPlaceholders(new LegendaryOwnedPlaceholder(databaseManager));
            // %mobcontracts_total_owned%
            expansion.registerPlaceholders(new TotalOwnedPlaceholder(databaseManager));
            // %mobcontracts_common_slain%
            expansion.registerPlaceholders(new CommonSlainPlaceholder(databaseManager));
            // %mobcontracts_epic_slain%
            expansion.registerPlaceholders(new EpicSlainPlaceholder(databaseManager));
            // %mobcontracts_legendary_slain%
            expansion.registerPlaceholders(new LegendarySlainPlaceholder(databaseManager));
            // %mobcontracts_total_slain%
            expansion.registerPlaceholders(new TotalSlainPlaceholder(databaseManager));

            getLogger().info("Placeholders registered!");
            getLogger().info("Placeholders ready to use!");
        } else {
            getLogger().warning("To use my placeholders, please install PlaceholderAPI and restart the server! :)");
        }

        this.isEnabled = true;

        getLogger().info("Initialising Player saving...");
        this.databaseUpdater.updateDatabaseTimer();
        getLogger().info("Initialised player saving!");

        getLogger().info("Everything is now enabled. Hopefully we don't explode! ~Minty");
    }

    public void onDisable() {
        this.isEnabled = false;
        currentContracts.removeAllContracts();
        getLogger().info("Contracts removed.");
        databaseManager.savePlayers();
        databaseManager.saveContractsOnDisable();
        reloadConfig();
        getLogger().info("Config.yml saved");
        getLogger().info("Disabled");
    }

    public void sendConsoleError(String message) {
        getLogger().severe(message);
    }

    public void sendConsoleMessage(String message) {
        getLogger().info(message);
    }

    public GuiUtil getMenuUtil(Player p) {
        GuiUtil util;

        if (playerMap.containsKey(p))
            return playerMap.get(p);
        else {
            util = new GuiUtil(p);
            playerMap.put(p, util);

            return util;
        }
    }
}
