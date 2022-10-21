package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerMoveListener implements Listener {

    private final List<Material> materials = Arrays.asList(Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE);
    private final List<Material> surfaceMaterials = Arrays.asList(Material.DIRT_PATH);
    private final HashMap<UUID, Integer> accelerateIDs = new HashMap<>();
    private final HashMap<UUID, Integer> slowDownIDs = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getName().equals("TheChamp_")) return;

        Material prev = event.getFrom().clone().subtract(0, 1, 0).getBlock().getType();
        Material next = event.getTo().clone().subtract(0, 1, 0).getBlock().getType();

        if (!event.hasExplicitlyChangedPosition()) {
            return;
        }
        if (next.isAir() || prev.isAir()) {
            return;
        }
        // Note: prev == next check does not work. Server cant process the high speed of the player

        if (canSpeed(event.getTo())) {
            removeTask(player, slowDownIDs);
            if (!accelerateIDs.containsKey(player.getUniqueId()) && player.getWalkSpeed() != 1) {
                accelerateIDs.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
                    float newWalkSpeed = player.getWalkSpeed() < 0.9F ? player.getWalkSpeed() + 0.1F : 1F;
                    player.setWalkSpeed(newWalkSpeed);
                    if (player.getWalkSpeed() == 1F) {
                        removeTask(player, accelerateIDs);
                    }
                }, 5, 1));
            }
        } else {
            removeTask(player, accelerateIDs);
            if (player.getWalkSpeed() != 0.2F && !slowDownIDs.containsKey(player.getUniqueId())) {
                slowDownIDs.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
                    float newWalkSpeed = player.getWalkSpeed() > 0.2F ? player.getWalkSpeed() - 0.1F : 0.2F;
                    player.setWalkSpeed(newWalkSpeed);
                    if (player.getWalkSpeed() == 0.2F) {
                        removeTask(player, slowDownIDs);
                    }
                }, 5, 3));
            }
        }
    }

    private void removeTask(Player player, HashMap<UUID, Integer> taskIdMap) {
        Bukkit.getScheduler().cancelTask(taskIdMap.getOrDefault(player.getUniqueId(), -1));
        taskIdMap.remove(player.getUniqueId());
    }

    private boolean canSpeed(Location location) {
        return (isStandingOnBlock(location.clone().subtract(0, 0.9, 0), materials)) || (isStandingOnBlock(location.clone().subtract(0, 0.8, 0), surfaceMaterials) && isStandingOnBlock(location.clone().subtract(0, 1.8, 0), materials));
    }

    private boolean isStandingOnBlock(Location location, List<Material> materials) {
        double boundXZ = 0.6000000238418579;
        return materials.contains(location.getBlock().getType())
                || materials.contains(location.clone().add(boundXZ, 0, 0).getBlock().getType())
                || materials.contains(location.clone().add(0, 0, boundXZ).getBlock().getType())
                || materials.contains(location.clone().add(-boundXZ, 0, 0).getBlock().getType());
    }
}
