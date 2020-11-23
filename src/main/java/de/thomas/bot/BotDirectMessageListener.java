package de.thomas.bot;

import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BotDirectMessageListener extends ListenerAdapter {

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if (event instanceof PrivateMessageReceivedEvent) {
            PrivateMessageReceivedEvent receivedEvent = (PrivateMessageReceivedEvent) event;
            String message = receivedEvent.getMessage().getContentRaw();
            short code = 0;
            try {
                code = Short.parseShort(message);
            } catch (NumberFormatException ignored) {
                try {
                    receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Dies ist kein Gültiger Code. Tut mir leid :(").queue());
                    return;
                } catch (Exception ignored1) {}
            }

            if (Variables.verifyCodes.containsKey(code)) {
                short finalCode = code;
                if(ConfigCache.verifiedPlayers.containsValue(receivedEvent.getAuthor().getId())) {
                    receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Fehler! Du hast bereits einen Account mit " + receivedEvent.getAuthor().getName() + " Verknüpft!").queue());
                    return;
                }
                ConfigCache.verifiedPlayers.put(Variables.verifyCodes.get(code), receivedEvent.getAuthor().getId());
                receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Du hast erfolgreich deinen Account mit " + Bukkit.getOfflinePlayer(Variables.verifyCodes.get(finalCode)).getName() + " verknüpft!").queue());

                Variables.verifyCodes.remove(code);
                    return;
            } else {
                try {
                    receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Dies ist kein Gültiger Code. Tut mir leid :(").queue());
                } catch (Exception ignored) {}
            }
        }
        super.onGenericEvent(event);
    }
}
