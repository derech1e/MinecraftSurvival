package de.thomas.bot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HalloCommand extends ListenerAdapter {

    private final List<String> keyWords = Arrays.asList("Hallo", "Hey", "Hey Ho", "Hello", "Hi", "Tag", "HuHu", "Tach", "Morgen", "Abend", "Guten Abend", "Guten Morgen", "Guten Tag", "Guten", "Wie gehts");

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            return;
        }

        if (keyWords.stream().anyMatch(args[0]::equalsIgnoreCase)) {
            Random random = new Random();
            event.getChannel().sendMessage(keyWords.get(random.nextInt(keyWords.size())) + " " + (event.getMember().getNickname() != null ? event.getMember().getNickname() : event.getMember().getUser().getName()) + "!").queue();
        }
    }
}
