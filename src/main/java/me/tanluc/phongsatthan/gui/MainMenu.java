package me.tanluc.phongsatthan.gui;

import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.gui.generic.AllContractsGui;
import me.tanluc.phongsatthan.gui.generic.ProfilesGui;
import me.tanluc.phongsatthan.gui.handler.Gui;
import me.tanluc.phongsatthan.gui.handler.GuiUtil;
import me.tanluc.phongsatthan.gui.slain.CommonContractsGui;
import me.tanluc.phongsatthan.gui.slain.EpicContractsGui;
import me.tanluc.phongsatthan.gui.slain.LegendaryContractsGui;
import me.tanluc.phongsatthan.gui.stats.ContractsKilledGui;
import me.tanluc.phongsatthan.gui.stats.PlayerLevelGui;
import me.tanluc.phongsatthan.gui.stats.ServerStatsGui;
import me.tanluc.phongsatthan.gui.stats.TotalExperienceGui;
import me.tanluc.phongsatthan.utils.CreateCustomGuiItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import me.tanluc.phongsatthan.MobContracts;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainMenu extends Gui {

    private final CreateCustomGuiItem createCustomGuiItem;
    private final MobContracts plugin;
    private final FileConfiguration config;
    private final DatabaseManager databaseManager;

    public MainMenu(GuiUtil menuUtil, CreateCustomGuiItem createCustomGuiItem, MobContracts plugin, DatabaseManager databaseManager) {
        super(menuUtil);
        this.createCustomGuiItem = createCustomGuiItem;
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.databaseManager = databaseManager;
    }

    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.title.main-menu"));
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 3:
                new AllContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()),  databaseManager, createCustomGuiItem, plugin).open();
                break;
            case 4:
                new ProfilesGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
            case 5:
                new ServerStatsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager, createCustomGuiItem).open();
                break;
            case 12:// "&e&lCONTRACTS KILLED":
                new ContractsKilledGui(plugin.getMenuUtil((Player) e.getWhoClicked()), databaseManager, createCustomGuiItem, plugin).open();
                break;
            case 13:
                new TotalExperienceGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
            case 14:
                new PlayerLevelGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
            case 21:
                new CommonContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
            case 22:
                new EpicContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
            case 23:
                new LegendaryContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, databaseManager,  createCustomGuiItem).open();
                break;
        }
    }

    @Override
    public void setItems() {
        for (int i = 0; i < getSlots(); i++) {

            if (i == 3) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.all-contracts.material"),
                        config.getString("gui.main-menu.all-contracts.name"),
                        config.getStringList("gui.main-menu.all-contracts.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 4) {

                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta meta = (SkullMeta) skull.getItemMeta();

                meta.setOwningPlayer(menuUtil.getOwner());
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("gui.main-menu.profiles.name")));
                String[] lore = config.getStringList("gui.main-menu.profiles.lore").stream().toArray(String[]::new);
                meta.setLore(Arrays.stream(lore).map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
                skull.setItemMeta(meta);
                inventory.setItem(i, skull);
                continue;
            }

            if (i == 5) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.server-stats.material"),
                        config.getString("gui.main-menu.server-stats.name"),
                        config.getStringList("gui.main-menu.server-stats.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 12) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.contracts-killed.material"),
                        config.getString("gui.main-menu.contracts-killed.name"),
                        config.getStringList("gui.main-menu.contracts-killed.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 13) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.total-experience.material"),
                        config.getString("gui.main-menu.total-experience.name"), config.getStringList("gui.main-menu.total-experience.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 14) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.player-level.material"),
                        config.getString("gui.main-menu.player-level.name"), config.getStringList("gui.main-menu.player-level.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 21) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.common-contracts.material"),
                        config.getString("gui.main-menu.common-contracts.name"), config.getStringList("gui.main-menu.common-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if (i == 22) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.epic-contracts.material"),
                        config.getString("gui.main-menu.epic-contracts.name"), config.getStringList("gui.main-menu.epic-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if (i == 23) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.legendary-contracts.material"),
                        config.getString("gui.main-menu.legendary-contracts.name"), config.getStringList("gui.main-menu.legendary-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if ((i == 0) || (i == 8) || (i == 26) || (i == 18)) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.accent-item"), "&8", null);
                inventory.setItem(i, item);
                continue;
            }

            ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.filler-item"), "&8", null);
            inventory.setItem(i, item);
        }
    }
}
