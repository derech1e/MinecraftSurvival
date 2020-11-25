package de.thomas.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OnlinePlayerCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String prefix = "!";
        if (!event.getMessage().getContentRaw().contains(prefix))
            return;
        String msg = event.getMessage().getContentRaw().substring(prefix.length());

        if (Objects.requireNonNull(event.getMember()).getUser().isBot())
            return;

        if (getAlias().contains(msg)) {
            event.getMessage().delete().queue();
            this.sendMessage(event.getChannel());
        }
    }


    public List<String> getAlias() {
        return Arrays.asList("mc", "minecraft", "modpack", "avatar", "online");
    }

    public void sendMessage(TextChannel channel) {
        channel.sendMessage("Suche...").complete().delete().queueAfter(1L, TimeUnit.SECONDS);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(new Color((int) (Math.random() * 1.6777216E7D)));
        if(Bukkit.getOnlinePlayers().size() > 0) {
            builder.addField("Spieler:", String.valueOf(Bukkit.getOnlinePlayers().size()), true);
            builder.addField("Spielerliste:", Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining("\n")), true);
        } else {
            builder.addField("Achtung!", "Es sind keine Spieler Online!", true);
        }
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15L, TimeUnit.SECONDS);
    }
}
