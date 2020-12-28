package de.thomas.bot;

import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BotDirectMessageListener extends ListenerAdapter {

    public static String getName(@NotNull String uuid) {
        String url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names";
        try {
            String nameJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size() - 1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            return nameObject.get("name").toString();
        } catch (@NotNull IOException | ParseException e) {
            return "error";
        }
    }

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
                } catch (Exception ignored1) {
                }
            }

            if (Variables.verifyCodes.containsKey(code)) {
                short finalCode = code;
                if (ConfigCache.verifiedPlayers.containsValue(receivedEvent.getAuthor().getId())) {
                    receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Fehler! Du hast bereits einen Account mit " + receivedEvent.getAuthor().getName() + " Verknüpft!").queue());
                    return;
                }
                ConfigCache.verifiedPlayers.put(Variables.verifyCodes.get(code), receivedEvent.getAuthor().getId());
                receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                        channel.sendMessage("Du hast erfolgreich deinen Account mit " + getName(Variables.verifyCodes.get(finalCode).toString()) + " verknüpft!").queue());

                Variables.verifyCodes.remove(code);
                return;
            } else {
                try {
                    receivedEvent.getAuthor().openPrivateChannel().queue((channel) ->
                            channel.sendMessage("Dies ist kein Gültiger Code. Tut mir leid :(").queue());
                } catch (Exception ignored) {
                }
            }
        }
        super.onGenericEvent(event);
    }
}
