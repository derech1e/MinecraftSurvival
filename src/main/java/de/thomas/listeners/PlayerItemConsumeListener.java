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

        if (item.getType().equals(Material.BREAD) && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() >= 1) {
//            player.setFoodLevel(10);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 3, 1, true, true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 30, 0, true, true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 20 * 60, 50, true, true, true));
        }
    }
}
