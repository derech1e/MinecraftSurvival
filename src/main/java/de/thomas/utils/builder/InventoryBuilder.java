package de.thomas.utils.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    final Inventory inventory;
    final Component inventoryTitle;
    final int inventorySize;

    public InventoryBuilder(Component inventoryTitle, int inventorySize) {
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

    public Inventory build() {
        return inventory;
    }
}
