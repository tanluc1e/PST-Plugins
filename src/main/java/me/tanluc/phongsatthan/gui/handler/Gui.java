package me.tanluc.phongsatthan.gui.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Gui implements InventoryHolder {

    protected Inventory inventory;
    protected GuiUtil menuUtil;
    public static String guiName;

    public Gui(GuiUtil menuUtil) {
        this.menuUtil = menuUtil;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setItems();

    public void open() {
        guiName = getMenuName();
        inventory = Bukkit.createInventory(this, getSlots(), guiName);

        this.setItems();

        menuUtil.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
