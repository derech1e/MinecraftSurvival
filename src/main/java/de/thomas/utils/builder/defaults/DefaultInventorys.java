package de.thomas.utils.builder.defaults;

import de.thomas.utils.Variables;
import de.thomas.utils.builder.InventoryBuilder;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

public class DefaultInventorys {

    public static Inventory getWayPointsOverview() {
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_WAYPOINTS, 9);
        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), true);

        inventoryBuilder.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAxYWZlOTczYzU0ODJmZGM3MWU2YWExMDY5ODgzM2M3OWM0MzdmMjEzMDhlYTlhMWEwOTU3NDZlYzI3NGEwZiJ9fX0=").setName(ChatColor.GOLD + "Auswählen").toItemStack());
        inventoryBuilder.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19").setName(ChatColor.GREEN + "Hinzufügen").toItemStack());
        inventoryBuilder.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=").setName(ChatColor.RED + "Entfernen").toItemStack());

        return inventoryBuilder.build();
    }

    public static Inventory getWayPointsSelect(Player player) {
        if (!ConfigCache.playerWaypoints.containsKey(player.getUniqueId())) {
            player.closeInventory();
            return null;
        }
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_WAYPOINT_SELECT, Variables.calculateInventorySize(ConfigCache.playerWaypoints.get(player.getUniqueId()).entrySet().size()));

        ConfigCache.playerWaypoints.get(player.getUniqueId()).forEach((name, location) -> {
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            itemBuilder.setName(name);
            itemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=");
            inventoryBuilder.addItem(itemBuilder.toItemStack());
        });

        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
        return inventoryBuilder.build();
    }

    public static Inventory getWayPointsDelete(Player player) {
        if (!ConfigCache.playerWaypoints.containsKey(player.getUniqueId())) {
            player.closeInventory();
            return null;
        }
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_WAYPOINT_DELETE, Variables.calculateInventorySize(ConfigCache.playerWaypoints.get(player.getUniqueId()).entrySet().size()));

        ConfigCache.playerWaypoints.get(player.getUniqueId()).forEach((name, location) -> {
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            itemBuilder.setName(name);
            itemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=");
            inventoryBuilder.addItem(itemBuilder.toItemStack());
        });

        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
        return inventoryBuilder.build();
    }

    public static AnvilInventory addWayPoint(Player player) {
        AnvilInventory inventory = (AnvilInventory) Bukkit.createInventory(player, InventoryType.ANVIL);
        inventory.setFirstItem(new ItemBuilder(Material.PAPER).setName("Gib einen Namen an").toItemStack());
        inventory.setMaximumRepairCost(0);
        inventory.setRepairCost(0);
        return inventory;
    }

    public static Inventory getSettings(Player player) {
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_SETTINGS, 9);
        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
        //Content
        inventoryBuilder.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=").setName(ChatColor.GOLD + "Wegpunkte").toItemStack());
        boolean clockState = ConfigCache.clockTime.getOrDefault(player.getUniqueId(), true);
        inventoryBuilder.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0=").setName(ChatColor.GOLD + "Uhrzeit: " + (clockState ? ChatColor.GREEN + "AN" : ChatColor.RED + "AUS")).toItemStack());

        return inventoryBuilder.build();
    }

}
