package de.thomas.utils.builder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryBuilder {

    final @NotNull Inventory inventory;
    String inventoryTitle;
    int inventorySize;

    public InventoryBuilder(@NotNull String inventoryTitle) {
        this.inventoryTitle = inventoryTitle;
        this.inventorySize = 9;
        this.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
    }

    public InventoryBuilder(@NotNull String inventoryTitle, int inventorySize) {
        this.inventoryTitle = inventoryTitle;
        this.inventorySize = inventorySize;
        this.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
    }

    public String getInventoryTitle() {
        return inventoryTitle;
    }

    public @NotNull InventoryBuilder setInventoryTitle(String inventoryTitle) {
        this.inventoryTitle = inventoryTitle;
        return this;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public @NotNull InventoryBuilder setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
        return this;
    }

    public @NotNull InventoryBuilder setContent(ItemStack[] content) {
        inventory.setContents(content);
        return this;
    }

    public @NotNull InventoryBuilder setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
        return this;
    }

    public @NotNull InventoryBuilder setPlaceHolder(ItemStack itemStack, boolean overwrite) {
        for (int i = 0; i < inventorySize; i++) {
            if (overwrite)
                inventory.setItem(i, itemStack);
            else {
                if(inventory.getItem(i) == null)
                    inventory.setItem(i, itemStack);
            }
        }
        return this;
    }

    public void addItem(ItemStack... itemStack) {
        inventory.addItem(itemStack);
    }

    public @NotNull Inventory build() {
        return inventory;
    }
}
