package de.thomas.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OnlinePlayerCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String prefix = "!";
        if (!event.getMessage().getContentRaw().contains(prefix))
            return;
        String msg = event.getMessage().getContentRaw().substring(prefix.length());

        if (event.getMember() != null && event.getMember().getUser().isBot())
            return;

        if (getAlias().contains(msg)) {
            event.getMessage().delete().queue();
            this.sendMessage(event.getChannel());
        }
    }


    public @NotNull List<String> getAlias() {
        return Arrays.asList("mc", "minecraft", "modpack", "avatar", "online");
    }

    public void sendMessage(@NotNull TextChannel channel) {
        channel.sendMessage("Suche...").complete().delete().queueAfter(1L, TimeUnit.SECONDS);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(new Color((int) (Math.random() * 1.6777216E7D)));
        if (Bukkit.getOnlinePlayers().size() > 0) {
            builder.addField("Spieler:", String.valueOf(Bukkit.getOnlinePlayers().size()), false);
            builder.addField("Spielerliste:", Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining("\n")), false);
        } else {
            builder.addField("Achtung!", "Es sind keine Spieler Online!", false);
        }
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15L, TimeUnit.SECONDS);
    }
}
