package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.builder.impl.DefaultInventories;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.config.context.WayPoint;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class InventoryClickListener implements Listener {
    private final Configuration configuration = MinecraftSurvival.getINSTANCE().configuration;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null)
            return;

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        Component viewTitle = event.getView().title();
        String displayName = event.getCurrentItem().getItemMeta().hasDisplayName() ? PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(event.getCurrentItem().getItemMeta().displayName())) : "";
        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER))
            return;

        // Inventory Compass
        if (viewTitle.equals(Variables.INVENTORY_NAME_COMPASS)) {
            event.setCancelled(true);

            if (currentItem.getItemMeta() == null || currentItem.getItemMeta().displayName() == null) return;

            Location spawnLocation =
                    player.getWorld().getName().equals("world_nether") ? Variables.playerPortalLocationSpawnMap.getOrDefault(player.getUniqueId(), configuration.getNetherPortalLocationByPlayer(player)) :
                            player.getBedSpawnLocation() == null ? player.getWorld().getSpawnLocation() : player.getBedSpawnLocation();

            if (displayName.equals("§6Spawnpunkt")) {
                setNewCompassTarget(player, spawnLocation, "den" + ChatColor.GOLD + " Spawnpunkt", null);
            } else if (displayName.equals("§fEinstellungen")) {
                player.openInventory(DefaultInventories.getSettings(player));
            } else if (currentItem.getType().equals(Material.PLAYER_HEAD)) {
                Player targetPlayer = Bukkit.getPlayer(displayName.replace(ChatColor.RESET.toString(), ""));
                if (targetPlayer == null) {
                    player.sendMessage(new Message(ErrorMessageType.NULL).getMessage());
                    return;
                }
                setNewCompassTarget(player, targetPlayer.getLocation(), ChatColor.AQUA + targetPlayer.getName(), targetPlayer);
            }

            // Inventory Settings
        } else if (viewTitle.toString().equals(Variables.INVENTORY_NAME_SETTINGS.toString())) {
            event.setCancelled(true);
            if (displayName.equals("§6Wegpunkte")) {
                player.openInventory(DefaultInventories.getWayPointsOverview());
            } else if(displayName.contains("Autobahn")) {
                boolean speedBlockState = configuration.updateSpeedBlockStateByPlayer(player, !configuration.getSpeedBlockStateByPlayer(player));
                event.getClickedInventory().setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzA2ZDdiZWZjODJmMjAxZjgzZTE5Mzc2N2U2M2Y4MTAzNzIxNWFmZDQ4M2EzOGQzNjk2NTk4MmNhNmQwIn19fQ==").setName(ChatColor.GOLD + "Autobahn: " + (speedBlockState ? ChatColor.GREEN + "AN" : ChatColor.RED + "AUS")).toItemStack());
            } else if (displayName.contains("Uhr")) {
                boolean clockState = configuration.updateClockStateByPlayer(player, !configuration.getClockStateByPlayer(player));
                event.getClickedInventory().setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0=").setName(ChatColor.GOLD + "Uhrzeit: " + (clockState ? ChatColor.GREEN + "AN" : ChatColor.RED + "AUS")).toItemStack());
            }

            // Inventory Waypoints
        } else if (viewTitle.toString().equals(Variables.INVENTORY_NAME_WAYPOINTS.toString())) {
            event.setCancelled(true);
            if (displayName.equals("§aHinzufügen")) {
                new AnvilGUI.Builder()
                        .onComplete((anvilPlayer, text) -> {
                            configuration.addWayPoint(player, text);
                            anvilPlayer.sendMessage(new Message("Du hast den Wegpunkt §6" + text + ChatColor.WHITE + " auf §6X: " + Math.round(anvilPlayer.getLocation().getX()) + " Y: " + Math.round(anvilPlayer.getLocation().getY()) + " Z: " + Math.round(anvilPlayer.getLocation().getZ()) + " §rgesetzt!", true).getRawMessageString());
                            return AnvilGUI.Response.close();
                        })
                        .text("Name hier...")
                        .itemLeft(new ItemStack(Material.PAPER))
                        .title("Wegpunktname!")
                        .plugin(MinecraftSurvival.getINSTANCE())
                        .open(player);
            } else if (displayName.equals("§cEntfernen")) {
                Inventory inventory = DefaultInventories.getWaypoints(player, Variables.INVENTORY_NAME_WAYPOINT_DELETE);
                if (inventory == null) {
                    player.sendMessage(new Message(ChatColor.RED + "Es gibt keine Wegpunkte zu entfernen!", true).getRawMessageString());
                    return;
                }
                player.openInventory(inventory);
            } else if (displayName.equals("§6Auswählen")) {
                Inventory inventory = DefaultInventories.getWaypoints(player, Variables.INVENTORY_NAME_WAYPOINT_SELECT);
                if (inventory == null) {
                    player.sendMessage(new Message(ChatColor.RED + "Es gibt keine Wegpunkte zum Auswählen!", true).getRawMessageString());
                    return;
                }
                player.openInventory(inventory);
            }


            // Inventory Waypoints Select
        } else if (viewTitle.toString().equals(Variables.INVENTORY_NAME_WAYPOINT_SELECT.toString())) {
            WayPoint wayPoint = configuration.getWorldWayPoints(player).get(event.getSlot());
            if (displayName.equals(wayPoint.name())) {
                setNewCompassTarget(player, wayPoint.location(), "den Wegpunkt " + ChatColor.GOLD + wayPoint.name(), null);
            }

            // Inventory Waypoints Delete
        } else if (viewTitle.toString().equals(Variables.INVENTORY_NAME_WAYPOINT_DELETE.toString())) {
            event.setCancelled(true);
            ItemStack placeHolder = new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack();
            if (currentItem.getType().equals(Material.PLAYER_HEAD)) {
                configuration.removeWayPoint(player, event.getSlot(), event.getClickedInventory());
                event.setCurrentItem(placeHolder);
            }
        }
    }

    private void setNewCompassTarget(Player player, @Nullable Location location, String message, @Nullable Player targetPlayer) {
        if (location != null) {
            Variables.targetCompassPlayers.put(player.getUniqueId(), targetPlayer);
            int distance = (int) Math.round(player.getLocation().distance(location));
            player.setCompassTarget(location);
            player.sendMessage(new Message("Du hast " + message + ChatColor.WHITE + " als dein neues Ziel gesetzt. (" + ChatColor.GOLD + distance + (distance > 1 ? " Blöcke entfernt" : " Block entfernt") + ChatColor.WHITE + ")", true).getRawMessageString());
            player.closeInventory();

            if (targetPlayer != null)
                targetPlayer.sendMessage(new Message(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " hat dich als sein neues Compass-Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt " + ChatColor.WHITE + ")", true).getRawMessageString());
        }
    }
}
