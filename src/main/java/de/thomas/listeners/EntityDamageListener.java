package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.animation.TitleAnimation;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamageEvent(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (Variables.frozenPlayers.contains(player.getUniqueId()) || ConfigCache.spawnProtection) event.setCancelled(true);
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            if (Variables.protectedPlayers.contains(player) || TitleAnimation.getPlayerInAnimation().contains(player)) {
                event.setCancelled(true);
            }
    }
}
