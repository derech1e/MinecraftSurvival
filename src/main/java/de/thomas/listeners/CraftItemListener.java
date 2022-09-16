package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.config.context.BaguetteContext;
import de.thomas.utils.message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class CraftItemListener implements Listener {

    private final Configuration configuration = MinecraftSurvival.getINSTANCE().configuration;

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if(event.getRecipe().getResult().getType() == Material.BREAD && event.getRecipe().getResult().getItemMeta().getCustomModelData() >= 1) {
            // Load baguette counter
            BaguetteContext context = configuration.get(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME, BaguetteContext.class);
            if(context == null) {
                context = new BaguetteContext();
                System.out.println("AAAA");
            }

            configuration.addBaguetteCounter((Player) event.getWhoClicked());
            context = new BaguetteContext(context.totalBaguetteCounter() + 1);

            ItemMeta itemMeta = Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
            itemMeta.displayName(new Message(String.format("§fBaguette #%s", context.totalBaguetteCounter()), false).getMessage());
            event.getCurrentItem().setItemMeta(itemMeta);

            MinecraftSurvival.getINSTANCE().configuration.set(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME, context);
            MinecraftSurvival.getINSTANCE().configuration.save();
        }
    }
}
