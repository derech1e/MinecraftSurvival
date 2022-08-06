package de.thomas.utils.builder.impl;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.InventoryBuilder;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.config.context.WayPoint;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class DefaultInventories {

    private static final Configuration configuration = MinecraftSurvival.getINSTANCE().configuration;

    public static Inventory getWayPointsOverview() {
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_WAYPOINTS, 9);
        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), true);

        inventoryBuilder.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAxYWZlOTczYzU0ODJmZGM3MWU2YWExMDY5ODgzM2M3OWM0MzdmMjEzMDhlYTlhMWEwOTU3NDZlYzI3NGEwZiJ9fX0=").setName(ChatColor.GOLD + "Auswählen").toItemStack());
        inventoryBuilder.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19").setName(ChatColor.GREEN + "Hinzufügen").toItemStack());
        inventoryBuilder.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=").setName(ChatColor.RED + "Entfernen").toItemStack());

        return inventoryBuilder.build();
    }

    public static @Nullable Inventory getWaypoints(Player player, Component name) {
        if (configuration.getWayPoints(player).isEmpty()) {
            player.closeInventory();
            return null;
        }
        InventoryBuilder inventoryBuilder = new InventoryBuilder(name, Variables.calculateInventorySize((int) configuration.getWayPoints(player).stream().map(WayPoint::location).map(Location::getWorld).filter(world -> world.getName().equals(player.getWorld().getName())).count()));

        configuration.getWayPoints(player).forEach((wayPoint) -> {
            if (wayPoint.location().getWorld().getName().equals(player.getWorld().getName())) {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(wayPoint.name());
                itemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=");
                inventoryBuilder.addItem(itemBuilder.toItemStack());
            }
        });

        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
        return inventoryBuilder.build();
    }

    public static Inventory getSettings(Player player) {
        InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_SETTINGS, 9);
        //Placeholder
        inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
        //Content
        inventoryBuilder.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=").setName(ChatColor.GOLD + "Wegpunkte").toItemStack());
        boolean clockState = configuration.getClockStateByPlayer(player);
        inventoryBuilder.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0=").setName(ChatColor.GOLD + "Uhrzeit: " + (clockState ? ChatColor.GREEN + "AN" : ChatColor.RED + "AUS")).toItemStack());

        return inventoryBuilder.build();
    }

}
