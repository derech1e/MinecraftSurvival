package de.thomas.utils.builder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    String inventoryTitle;
    int inventorySize;
    Inventory inventory;

    public InventoryBuilder(String inventoryTitle) {
        this.inventoryTitle = inventoryTitle;
        this.inventorySize = 9;
        this.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
    }

    public InventoryBuilder(String inventoryTitle, int inventorySize) {
        this.inventoryTitle = inventoryTitle;
        this.inventorySize = inventorySize;
        this.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
    }

    public String getInventoryTitle() {
        return inventoryTitle;
    }

    public InventoryBuilder setInventoryTitle(String inventoryTitle) {
        this.inventoryTitle = inventoryTitle;
        return this;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public InventoryBuilder setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
        return this;
    }

    public InventoryBuilder setContent(ItemStack[] content) {
        inventory.setContents(content);
        return this;
    }

    public InventoryBuilder setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
        return this;
    }

    public InventoryBuilder addItem(ItemStack... itemStack) {
        inventory.addItem(itemStack);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
