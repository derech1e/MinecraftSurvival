package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.config.context.BaguetteContext;
import de.thomas.utils.message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class CraftItemListener implements Listener {

    private final Configuration configuration = MinecraftSurvival.getINSTANCE().configuration;
    private BaguetteContext baguetteContext = null;

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult() == null)
            return;

        if (event.getInventory().containsAtLeast(new ItemBuilder(Material.BREAD).toItemStack(), 4)) {
            event.getInventory().setResult(new ItemBuilder(Material.AIR).toItemStack());
            return;
        }

        if (event.getInventory().getResult().getType() == Material.BREAD && event.getInventory().getResult().getItemMeta().getCustomModelData() >= 1) {
            initBaguetteContext();

            ItemMeta itemMeta = Objects.requireNonNull(event.getInventory().getResult()).getItemMeta();
            itemMeta.displayName(new Message(String.format("§fBaguette #%s", baguetteContext.totalBaguetteCounter() + 1), false).getMessage());
            event.getInventory().getResult().setItemMeta(itemMeta);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (Objects.requireNonNull(event.getInventory().getResult()).getType() == Material.BREAD && event.getInventory().getResult().getItemMeta().getCustomModelData() >= 1) {
            // Load baguette counter
            initBaguetteContext();

            configuration.addBaguetteCounter((Player) event.getWhoClicked());
            baguetteContext = new BaguetteContext(baguetteContext.totalBaguetteCounter() + 1);

            ItemMeta itemMeta = Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
            itemMeta.displayName(new Message(String.format("§fBaguette #%s", baguetteContext.totalBaguetteCounter()), false).getMessage());
            event.getCurrentItem().setItemMeta(itemMeta);

            MinecraftSurvival.getINSTANCE().configuration.set(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME, baguetteContext);
            MinecraftSurvival.getINSTANCE().configuration.save();
        }

    }

    private void initBaguetteContext() {
        if (baguetteContext != null) return;
        baguetteContext = configuration.get(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME, BaguetteContext.class);
        if (baguetteContext == null) {
            baguetteContext = new BaguetteContext();
        }
    }
}
