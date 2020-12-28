package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.builder.defaults.DefaultInventorys;
import de.thomas.utils.config.ConfigCache;
import de.thomas.utils.message.Message;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class InventoryClickListener implements Listener {


    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null)
            return;

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        String viewTitle = event.getView().getTitle();

        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;

        if (viewTitle.equals(Variables.INVENTORY_NAME_COMPASS)) {
            event.setCancelled(true);

            Location spawnLocation = player.getWorld().getName().equals("world_nether") ? new Location(Bukkit.getWorld("world_nether"), -131, 104, 8) : (player.getBedSpawnLocation() == null ? player.getWorld().getSpawnLocation() : player.getBedSpawnLocation());

            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Spawnpunkt")) {
                replaceOrAdd(player.getUniqueId(), null);
                setNewCompassTarget(player, spawnLocation, "den" + ChatColor.GOLD + " Spawnpunkt ", null);
            } else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Einstellungen")) {
                player.openInventory(DefaultInventorys.getSettings(player));
            } else if (currentItem.getType().equals(Material.PLAYER_HEAD)) {
                Player targetPlayer = Bukkit.getPlayer(currentItem.getItemMeta().getDisplayName().replace(String.valueOf(ChatColor.WHITE), ""));
                replaceOrAdd(player.getUniqueId(), targetPlayer);
                setNewCompassTarget(player, targetPlayer.getLocation(), ChatColor.AQUA + targetPlayer.getName(), targetPlayer);
            }


        } else if (viewTitle.equals(Variables.INVENTORY_NAME_SETTINGS)) {
            event.setCancelled(true);
            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Wegpunkte")) {
                player.openInventory(DefaultInventorys.getWayPointsOverview());
            } else if (currentItem.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD + "Uhr")) {
                ConfigCache.clockTime.replace(player.getUniqueId(), !ConfigCache.clockTime.get(player.getUniqueId()));
                boolean clockState = ConfigCache.clockTime.getOrDefault(player.getUniqueId(), true);
                event.getClickedInventory().setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0=").setName(ChatColor.GOLD + "Uhrzeit: " + (clockState ? ChatColor.GREEN + "AN" : ChatColor.RED + "AUS")).toItemStack());
            }
        } else if (viewTitle.equals(Variables.INVENTORY_NAME_WAYPOINTS)) {
            event.setCancelled(true);
            if (currentItem.getItemMeta().getDisplayName().equals(Variables.INVENTORY_NAME_WAYPOINT_ADD)) {
                new AnvilGUI.Builder()
                        .onComplete((anvilPlayer, text) -> {
                            HashMap<String, Location> locations = ConfigCache.playerWaypoints.containsKey(anvilPlayer.getUniqueId()) ? ConfigCache.playerWaypoints.get(anvilPlayer.getUniqueId()) : new HashMap<>();
                            //locations.entrySet().stream().noneMatch(stringLocationEntry -> stringLocationEntry.getKey().equals(text) && stringLocationEntry.getValue().getWorld().getName().equals(anvilPlayer.getWorld().getName()))
                            if (locations.keySet().stream().noneMatch(s -> Objects.equals(s, text))) {
                                locations.put(text, anvilPlayer.getLocation());
                                if (ConfigCache.playerWaypoints.containsKey(anvilPlayer.getUniqueId()))
                                    ConfigCache.playerWaypoints.replace(anvilPlayer.getUniqueId(), locations);
                                else
                                    ConfigCache.playerWaypoints.put(anvilPlayer.getUniqueId(), locations);
                                anvilPlayer.sendMessage(new Message("Du hast den Wegpunkt §6" + text + ChatColor.WHITE + " auf §6X: " + Math.round(anvilPlayer.getLocation().getX()) + " Y: " + Math.round(anvilPlayer.getLocation().getY()) + " Z: " + Math.round(anvilPlayer.getLocation().getZ()) + " §r gesetzt!").getMessage());
                            } else {
                                anvilPlayer.sendMessage(new Message(ChatColor.RED + "Dieser Name ist bereits vergeben!").getMessage());
                                return AnvilGUI.Response.text("Bereits vergeben!");
                            }
                            return AnvilGUI.Response.close();
                        })
                        .text("Name hier...")
                        .itemLeft(new ItemStack(Material.PAPER))
                        .title("Wegpunktname!")
                        .plugin(MinecraftSurvival.getINSTANCE())
                        .open(player);
            } else if (currentItem.getItemMeta().getDisplayName().equals(Variables.INVENTORY_NAME_WAYPOINT_DELETE)) {
                Inventory inventory = DefaultInventorys.getWayPointsDelete(player);
                if (inventory == null) {
                    player.sendMessage(new Message(ChatColor.RED + "Es gibt keine Wegpunkte zu entfernen!").getMessage());
                    return;
                }
                player.openInventory(inventory);
            } else if (currentItem.getItemMeta().getDisplayName().equals(Variables.INVENTORY_NAME_WAYPOINT_SELECT)) {
                Inventory inventory = DefaultInventorys.getWayPointsSelect(player);
                if (inventory == null) {
                    player.sendMessage(new Message(ChatColor.RED + "Es gibt keine Wegpunkte zum Auswählen!").getMessage());
                    return;
                }
                player.openInventory(inventory);
            }
        } else if (viewTitle.equals(Variables.INVENTORY_NAME_WAYPOINT_SELECT)) {
            ConfigCache.playerWaypoints.get(player.getUniqueId()).forEach((name, location) -> {
                if (currentItem.getItemMeta().getDisplayName().equals(name)) {
                    setNewCompassTarget(player, location, "den Wegpunkt " + ChatColor.GOLD + name, null);
                }
            });
        } else if (viewTitle.equals(Variables.INVENTORY_NAME_WAYPOINT_DELETE)) {
            event.setCancelled(true);
            ItemStack placeHolder = new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack();
            if (!currentItem.getItemMeta().getDisplayName().equals(placeHolder.getItemMeta().getDisplayName()) && currentItem.getType() != placeHolder.getType()) {
                ConfigCache.playerWaypoints.get(player.getUniqueId()).forEach((name, location) -> {
                    if (currentItem.getItemMeta().getDisplayName().equals(name)) {
                        ConfigCache.playerWaypoints.remove(player.getUniqueId());
                    }
                });
                event.setCurrentItem(placeHolder);
            }
        }
    }

    private void setNewCompassTarget(@NotNull Player player, @Nullable Location location, String message, @Nullable Player targetPlayer) {
        if (location != null) {
            int distance = (int) Math.round(player.getLocation().distance(location));
            player.setCompassTarget(location);
            player.sendMessage(new Message("Du hast " + message + ChatColor.WHITE + " als dein neues Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt" + ChatColor.WHITE + ")").getMessage());
            player.closeInventory();

            if (targetPlayer != null)
                targetPlayer.sendMessage(new Message(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " hat dich als sein neues Compass-Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt " + ChatColor.WHITE + ")").getMessage());
        }
    }

    private void replaceOrAdd(UUID uuid, Player value) {
        if (Variables.targetCompassPlayers.containsKey(uuid))
            Variables.targetCompassPlayers.replace(uuid, value);
        else Variables.targetCompassPlayers.put(uuid, value);
    }
}
