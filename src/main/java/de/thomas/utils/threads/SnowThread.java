package de.thomas.utils.threads;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.threads.base.IThreadBase;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
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
            int maxDistance = 25;

            if (player.isUnderWater())
                return;

            for (int i = 0; i < 250; i++) {
                Location particleLocation = player.getLocation().clone().add(new Vector(
                        random.nextInt((2 * maxDistance) + 1) - maxDistance,
                        random.nextInt((2 * maxDistance) + 1) - maxDistance,
                        random.nextInt((2 * maxDistance) + 1) - maxDistance)
                );

                if (random.nextInt() % 2 == 0) {
                    float xAdditive = (random.nextFloat() - 0.5F) * maxDistance * 2.0F;
                    float max = (float) Math.sqrt((maxDistance * maxDistance - xAdditive * xAdditive)) * 2.0F;
                    float yAdditive = (random.nextFloat() - 0.5F) * max;
                    float zAdditive = (random.nextFloat() - 0.5F) * max;
                    particleLocation = player.getLocation().clone().add(new Vector(
                            xAdditive,
                            yAdditive,
                            zAdditive)
                    );
                }
                if (particleLocation.getBlock().getType() == Material.WATER || particleLocation.getBlock().getType() == Material.LAVA)
                    continue;
                if (particleLocation.getY() < particleLocation.getWorld().getHighestBlockYAt(particleLocation))
                    continue;

                if(particleLocation.getBlock().getType() != Material.SNOW && particleLocation.getBlock().getType() != Material.SNOW_BLOCK) {
                    if(particleLocation.getBlock().isSolid())
                        continue;
                }

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

                if (player.isInsideVehicle())
                    continue;

                // Spawn snow layer
                Block block = particleLocation.getBlock();
                Material material = block.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
                if ((material.isSolid() && material.isOccluding() && material != Material.PACKED_ICE) || block.getType() == Material.SNOW) {

                    if (random.nextInt(50) != 1) {
                        continue;
                    }

                    if(block.getType() != Material.SNOW) {
                        block.setType(Material.SNOW);
                    } else {
                        Snow snowLevel = (Snow) block.getBlockData();
                        snowLevel.setLayers(snowLevel.getLayers() == snowLevel.getMaximumLayers() ? snowLevel.getMaximumLayers() : snowLevel.getLayers() + 1);
                        block.setBlockData(snowLevel);
                    }
                }
            }
        }), 0, 5);
    }

    @Override
    public void stopThread() {
        super.stopThread();
    }
}
