package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.message.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackListener implements Listener {

    @EventHandler
    public void onStatus(PlayerResourcePackStatusEvent event) {
        switch (event.getStatus()) {
            case DECLINED, FAILED_DOWNLOAD -> {
                Variables.resourcePack.setResourcePack(event.getPlayer().getUniqueId());
                event.getPlayer().kick(new Message("Das BaguettePack ist zwingend notwendig!", false).getMessage());
            }
        }
    }
}
