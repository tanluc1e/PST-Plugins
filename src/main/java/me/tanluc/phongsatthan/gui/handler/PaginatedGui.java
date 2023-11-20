package me.tanluc.phongsatthan.gui.handler;

import me.tanluc.phongsatthan.MobContracts;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedGui extends Gui {
    private final MobContracts plugin;

    protected int page = 0;
    protected int maxItemsPerPage = 45;

    public PaginatedGui(MobContracts plugin, GuiUtil guiUtil) {
        super(guiUtil);
        this.plugin = plugin;
    }
    // 46 - 54
    // 51 49

    public void addBottomRow() {

        // Buttons
        ItemStack next = new ItemStack(Material.PAPER, 1);
        ItemMeta nextmeta = next.getItemMeta();
        nextmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.button.next")));
        next.setItemMeta(nextmeta);
        inventory.setItem(50, next);

        ItemStack back = new ItemStack(Material.PAPER, 1);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.button.previous")));
        List<String> lore = new ArrayList<>();
        if (page == 0) {
            backmeta.setLore(lore);
        }
        back.setItemMeta(backmeta);
        inventory.setItem(48, back);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closemeta = close.getItemMeta();
        closemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.button.close")));
        close.setItemMeta(closemeta);
        inventory.setItem(49, close);

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8"));
        filler.setItemMeta(meta);

        // add back to main menu button

        // Bottom row fillers
        for (int i = 45; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, filler);
            }
        }
    }
}
