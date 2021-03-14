package de.thomas.bot.log;

import de.thomas.bot.BotMain;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author derech1e
 * @since 14.03.2021
 **/
public class UserChannelListener extends ListenerAdapter {


    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        String liftedUser = event.getMember().getUser().getName();
        List<String> currentUserInChannel = event.getChannelLeft().getMembers().stream().map(Member::getUser).map(User::getName).collect(Collectors.toList());
        MinecraftSurvival.logger.info(String.format("'%s' << '%s'. Aktuell: %s", liftedUser, event.getChannelLeft().getName(), currentUserInChannel));
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        String joinedUser = event.getMember().getUser().getName();
        List<String> currentUserInChannel = event.getChannelJoined().getMembers().stream().map(Member::getUser).map(User::getName).collect(Collectors.toList());
        MinecraftSurvival.logger.info(String.format("'%s' >> '%s'. Aktuell: %s", joinedUser, event.getChannelJoined().getName(), currentUserInChannel));
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        String user = event.getMember().getUser().getName();
        List<String> currentUserInChannel = event.getChannelJoined().getMembers().stream().map(Member::getUser).map(User::getName).collect(Collectors.toList());
        MinecraftSurvival.logger.info(String.format("'%s' bewegt von '%s' in '%s'. Aktuell: %s", user, event.getChannelLeft().getName(), event.getChannelJoined().getName(), currentUserInChannel));
    }
}
