package de.thomas.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType().equals(Material.BREAD) && item.getItemMeta().getCustomModelData() >= 1) {
            player.setFoodLevel(20);
            player.setHealth(player.getHealth() + 2);
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30, 0, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 15, 10, false, false, false));
        }
    }
}
