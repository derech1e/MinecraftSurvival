package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.animation.TitleAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (Variables.freezedPlayers.contains(player.getUniqueId())) event.setCancelled(true);
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            if (Variables.glidingPlayers.contains(player) || TitleAnimation.getPlayerInAnimation().contains(player))
                event.setCancelled(true);
    }
}
