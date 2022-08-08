package de.thomas.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Material prev = event.getFrom().clone().subtract(0, 1, 0).getBlock().getType();
        Material next = event.getTo().clone().subtract(0, 1, 0).getBlock().getType();

        if (!event.hasExplicitlyChangedPosition() || prev == next || next.isAir()) {
            return;
        }

        switch (event.getTo().clone().subtract(0, 0.9, 0).getBlock().getType()) {
            case GRAY_CONCRETE, LIGHT_GRAY_CONCRETE -> event.getPlayer().setWalkSpeed(1F);
            default -> event.getPlayer().setWalkSpeed(0.2F);
        }
    }
}
