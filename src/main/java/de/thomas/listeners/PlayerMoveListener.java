package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.animation.SpawnLevitationAnimation;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class PlayerMoveListener implements Listener {

    @Deprecated
    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (Variables.frozenPlayers.contains(player.getUniqueId()))
            event.setTo(event.getFrom());

        Block carpet = player.getLocation().subtract(0, 0.06250, 0).getBlock();
        Block bedrock = player.getLocation().subtract(0, 1.06250, 0).getBlock();
        Block lantern = player.getLocation().subtract(0, 2.06250, 0).getBlock();

        Material[] carpets = new Material[]{Material.RED_CARPET, Material.ORANGE_CARPET, Material.YELLOW_CARPET};

        if (Arrays.stream(carpets).anyMatch(type -> type.equals(carpet.getType())) && bedrock.getType().equals(Material.BEDROCK) && lantern.getType().equals(Material.SEA_LANTERN)) {
            new SpawnLevitationAnimation(player).start();
        }

        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            boolean isPlayerInSpawnArea = player.getLocation().distance(Objects.requireNonNull(ConfigCache.glideAreaLocation)) <= ConfigCache.glideAreaRadius;

            if (player.getInventory().getArmorContents() != null)
                if (Arrays.stream(player.getInventory().getArmorContents()).anyMatch(itemStack -> itemStack.getType().equals(Material.ELYTRA)))
                    return;

            if (!Variables.glidingPlayers.contains(player))
                if (isPlayerInSpawnArea)
                    if (player.getLocation().subtract(0, 2, 0).getBlock().getType().isAir() && player.getFallDistance() >= 3) {
                        Variables.glidingPlayers.add(player);
                        Variables.protectedPlayers.add(player);
                        if (ConfigCache.glideBoots)
                            player.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                    }

            if (Variables.glidingPlayers.contains(player))
                if (player.isOnGround() || player.isInWater() || player.isInLava() || !player.getLocation().subtract(0, 2, 0).getBlock().getType().isAir()) {
                    Variables.glidingPlayers.remove(player);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftSurvival.getINSTANCE(), () -> Variables.protectedPlayers.remove(player), 20 * 2);
                }

            player.setGliding(Variables.glidingPlayers.contains(player));
        }
    }
}
