package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerMoveListener implements Listener {

    private final List<Material> materials = Arrays.asList(Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE);
    private final List<Material> surfaceMaterials = List.of(Material.DIRT_PATH);
    private final HashMap<UUID, Integer> accelerateIDs = new HashMap<>();
    private final HashMap<UUID, Integer> slowDownIDs = new HashMap<>();

    private final Configuration configuration = MinecraftSurvival.getINSTANCE().configuration;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Material prev = event.getFrom().clone().subtract(0, 1, 0).getBlock().getType();
        Material next = event.getTo().clone().subtract(0, 1, 0).getBlock().getType();

        if (!event.hasExplicitlyChangedPosition() || (!configuration.getClockStateByPlayer(player) && player.getWalkSpeed() == 0.2F)) {
            return;
        }
        if (next.isAir() || prev.isAir()) {
            return;
        }
        // Note: prev == next check does not work. Server cant process the high speed of the player

        if (canSpeed(event.getTo()) && configuration.getSpeedBlockStateByPlayer(player)) {
            removeTask(player, slowDownIDs);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 5, 50, false, false, false));
            try {
                player.setExp(getSubtractionExp(player));
            } catch (IllegalArgumentException exception) {
                player.setExp(player.getLevel() == 0 ? 0F : 1F);
                player.setLevel(player.getLevel() == 0 ? 0 : player.getLevel() - 1);
            }
            if (!accelerateIDs.containsKey(player.getUniqueId()) && player.getWalkSpeed() != 1) {
                accelerateIDs.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
                    float newWalkSpeed = player.getWalkSpeed() < 0.5F ? player.getWalkSpeed() + 0.1F : 0.6F;
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

    private float getSubtractionExp(Player player) {
        switch (player.getLevel()) {
            case 30, 29, 28, 27, 26, 25 -> {
                return player.getExp() - 0.0005F;
            }
            case 24, 23, 22, 21, 20, 19 -> {
                return player.getExp() - 0.0009F;
            }
            case 18, 17, 16, 15, 14 -> {
                return player.getExp() - 0.001F;
            }
            case 13, 12, 11, 10, 9, 8, 7, 6 -> {
                return player.getExp() - 0.0025F;
            }
            case 5, 4, 3, 2, 1, 0 -> {
                return player.getExp() - 0.005F;
            }
            default -> {
                return player.getExp() - 0.0001F;
            }
        }
    }
}
