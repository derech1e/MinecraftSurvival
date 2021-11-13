package de.thomas.listeners;

import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            if (event.getDrops().size() != 0) {
                event.getDrops().removeIf(itemStack -> itemStack.getType().equals(Material.ARMOR_STAND));
                ArmorStand armorStand = (ArmorStand) event.getEntity();
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), new ItemBuilder(Material.ARMOR_STAND).addItemFlag(ItemFlag.HIDE_ENCHANTS).addEnchantment(Enchantment.DURABILITY, 1, true).setArmorStandSmall(armorStand.isSmall()).showArmorStandArms(armorStand.hasArms()).setName("§f" + generateArmorStandName(armorStand.isSmall(), armorStand.hasArms())).toItemStack());
            }
        }
    }

    private String generateArmorStandName(boolean isSmall, boolean hasArms) {
        return isSmall & hasArms ? "Small Armor Stand with Arms" : isSmall & !hasArms ? "Small Armor Stand" : hasArms ? "Armor Stand with Arms" : "Armor Stand";
    }
}
