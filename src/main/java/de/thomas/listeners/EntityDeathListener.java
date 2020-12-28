package de.thomas.listeners;

import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(@NotNull EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            if (event.getDrops().size() != 0) {
                event.getDrops().clear();
                ArmorStand armorStand = (ArmorStand) event.getEntity();
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), new ItemBuilder(Material.ARMOR_STAND).setArmorStandSmall(armorStand.isSmall()).showArmorStandArms(armorStand.hasArms()).setName("§f" + generateArmorStandName(armorStand.isSmall(), armorStand.hasArms())).toItemStack());
            }
        }
    }

    private @NotNull String generateArmorStandName(boolean isSmall, boolean hasArms) {
        return isSmall & hasArms ? "Small Armor Stand with Arms" : isSmall & !hasArms ? "Small Armor Stand" : hasArms ? "Armor Stand with Arms" : "Armor Stand";
    }
}
