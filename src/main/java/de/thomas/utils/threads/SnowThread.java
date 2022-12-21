package de.thomas.utils.threads;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.threads.base.IThreadBase;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SnowThread extends IThreadBase {
    private final List<Particle> particleList = Arrays.asList(Particle.WAX_OFF, Particle.SNOWFLAKE, Particle.END_ROD);

    @Override
    public void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().getEnvironment() == World.Environment.NORMAL).forEach(player -> {
            Random random = new Random();

            if (player.isUnderWater())
                return;

            for (int i = 0; i < 600; i++) {
                Location particleLocation = getCloseParticleLocation(player, 25);

                if (!canSpawnParticle(particleLocation)) continue;

                spawnParticle(particleLocation);

                if (player.isInsideVehicle() || particleLocation == player.getLocation())
                    continue;

                // Spawn snow layer
                if (random.nextInt(20) != 1) {
                    continue;
                }
                spawnSnowLayer(particleLocation);
            }
            // Further away
            for (int i = 0; i < 2500; i++) {
                Location particleLocation = getFurtherParticleLocation(player, 75);

                if (!canSpawnParticle(particleLocation)) continue;

                spawnParticle(particleLocation);

                if (player.isInsideVehicle() || particleLocation == player.getLocation())
                    continue;

                // Spawn snow layer
                if (random.nextInt(10) != 1) {
                    continue;
                }
                spawnSnowLayer(particleLocation);
            }
        }), 0, 5);
    }

    @Override
    public void stopThread() {
        super.stopThread();
    }

    private boolean canSpawnParticle(Location particleLocation) {
        if (particleLocation.getBlock().getType() == Material.WATER || particleLocation.getBlock().getType() == Material.LAVA)
            return false;
        if (particleLocation.getY() < particleLocation.getWorld().getHighestBlockYAt(particleLocation))
            return false;

        if (particleLocation.getBlock().getType() != Material.SNOW && particleLocation.getBlock().getType() != Material.SNOW_BLOCK) {
            return !particleLocation.getBlock().isSolid();
        }
        return true;
    }

    private void spawnParticle(Location particleLocation) {
        Random random = new Random();
        Particle particle = particleList.get(random.nextInt(particleList.size()));
        ParticleBuilder particleBuilder = new ParticleBuilder(particle).count(0).offset(0, -1.8, 0).extra(0.05).location(particleLocation);
        if (particle.getDataType().equals(Particle.DustOptions.class)) {
            particleBuilder.data(new Particle.DustOptions(Color.WHITE, 1));
        } else if (particle.getDataType().equals(Particle.DustTransition.class)) {
            particleBuilder.data(new Particle.DustTransition(Color.WHITE, Color.SILVER, 1));
        } else if (particle.getDataType().equals(BlockData.class)) {
            particleBuilder.data(Material.SNOW_BLOCK.createBlockData());
        } else if (particle.getDataType().equals(ItemStack.class)) {
            particleBuilder.data(new ItemStack(Material.SNOWBALL));
        }
        particleBuilder.spawn();
    }

    private void spawnSnowLayer(Location particleLocation) {
        Block block = particleLocation.getBlock();
        Material material = block.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
        if ((material.isSolid() && material.isOccluding() && material != Material.PACKED_ICE) || block.getType() == Material.SNOW) {
            if (block.getType() != Material.SNOW) {
                block.setType(Material.SNOW);
                return;
            }

            Snow snowLevel = (Snow) block.getBlockData();
            snowLevel.setLayers(snowLevel.getLayers() == snowLevel.getMaximumLayers() ? snowLevel.getMaximumLayers() : snowLevel.getLayers() + 1);
            block.setBlockData(snowLevel);
        }
    }

    private Location getCloseParticleLocation(Player player, int maxDistance) {
        Random random = new Random();
        return player.getLocation().clone().add(new Vector(
            random.nextInt((2 * maxDistance) + 1) - maxDistance,
            random.nextInt((2 * maxDistance) + 1) - maxDistance,
            random.nextInt((2 * maxDistance) + 1) - maxDistance)
        );
    }

    private Location getFurtherParticleLocation(Player player, int maxDistance) {
        Random random = new Random();
        float xAdditive = (random.nextFloat() - 0.5F) * maxDistance * 2.0F;
        float max = (float) Math.sqrt((maxDistance * maxDistance - xAdditive * xAdditive)) * 2.0F;
        float yAdditive = (random.nextFloat() - 0.5F) * max;
        float zAdditive = (random.nextFloat() - 0.5F) * max;
        return player.getLocation().clone().add(new Vector(
            xAdditive,
            yAdditive,
            zAdditive)
        );
    }
}
