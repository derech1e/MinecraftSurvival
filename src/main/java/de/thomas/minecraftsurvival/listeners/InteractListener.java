package de.thomas.minecraftsurvival.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Comparator;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();

        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
            player.setCompassTarget(getClosestPlayerToPlayer(player).getLocation());
        }
    }

    private Player getClosestPlayerToPlayer(Player player) {
        return (Player) player.getWorld().getEntitiesByClasses(Player.class).stream().min(Comparator.comparingInt(p -> (int) p.getLocation().distance(player.getLocation()))).orElse(player);
    }
}
