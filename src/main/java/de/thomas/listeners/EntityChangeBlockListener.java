package de.thomas.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EntityChangeBlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockChange(final EntityChangeBlockEvent event) {
        final Entity fallingBlock = event.getEntity();
        final Block block = event.getBlock();

        if (!(fallingBlock instanceof FallingBlock) || event.getTo() == Material.AIR) {
            return;
        }

        final double vX = fallingBlock.getVelocity().getX();
        final double vZ = fallingBlock.getVelocity().getZ();
        final Block bN = block.getRelative(BlockFace.NORTH);
        final Block bE = block.getRelative(BlockFace.EAST);
        final Block bS = block.getRelative(BlockFace.SOUTH);
        final Block bW = block.getRelative(BlockFace.WEST);

        if (bN.getType() == Material.END_PORTAL) {
            if (vZ >= 0.0) {
                return;
            }
            final Entity item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.6, -0.5), new ItemStack(event.getTo()));
            item.setVelocity(new Vector(0.0, -0.1, -0.5));
        }
        if (bE.getType() == Material.END_PORTAL) {
            if (vX <= 0.0) {
                return;
            }
            final Entity item = block.getWorld().dropItem(block.getLocation().add(1.5, 0.6, 0.5), new ItemStack(event.getTo()));
            item.setVelocity(new Vector(0.5, -0.1, 0.0));
        }
        if (bS.getType() == Material.END_PORTAL) {
            if (vZ <= 0.0) {
                return;
            }
            final Entity item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.6, 1.5), new ItemStack(event.getTo()));
            item.setVelocity(new Vector(0.0, -0.1, 0.5));
        }
        if (bW.getType() == Material.END_PORTAL) {
            if (vX >= 0.0) {
                return;
            }
            final Entity item = block.getWorld().dropItem(block.getLocation().add(-0.5, 0.6, 0.5), new ItemStack(event.getTo()));
            item.setVelocity(new Vector(-0.5, -0.1, 0.0));
        }
    }
}
