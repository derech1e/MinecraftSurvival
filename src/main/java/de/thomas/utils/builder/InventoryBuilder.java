package de.thomas.utils.builder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryBuilder {

    final @NotNull Inventory inventory;
    final String inventoryTitle;
    final int inventorySize;

    public InventoryBuilder(@NotNull String inventoryTitle, int inventorySize) {
        this.inventoryTitle = inventoryTitle;
        this.inventorySize = inventorySize;
        this.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
    }

    public void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    public void setPlaceHolder(ItemStack itemStack, boolean overwrite) {
        for (int i = 0; i < inventorySize; i++) {
            if (overwrite)
                inventory.setItem(i, itemStack);
            else {
                if (inventory.getItem(i) == null)
                    inventory.setItem(i, itemStack);
            }
        }
    }

    public void addItem(ItemStack... itemStack) {
        inventory.addItem(itemStack);
    }

    public @NotNull Inventory build() {
        return inventory;
    }
}
