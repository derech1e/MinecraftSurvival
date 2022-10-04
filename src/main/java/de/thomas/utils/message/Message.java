package de.thomas.utils.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;

public class Message {

    private final ErrorMessageType errorMessageType;
    public String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "M" + ChatColor.GREEN + "S" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    private String message = PREFIX;
    private boolean prefix = true;

    public Message(ErrorMessageType errorMessageType) {
        this.errorMessageType = errorMessageType;
    }

    public Message(String message) {
        this.message = message;
        this.prefix = false;
        this.errorMessageType = ErrorMessageType.EMPTY;
    }
    @Deprecated
    public Message(String message, boolean prefix) {
        this.prefix = prefix;
        this.message = prefix ? PREFIX + message : message;
        this.errorMessageType = ErrorMessageType.EMPTY;
    }

    public String getRawMessageString() {
        switch (errorMessageType) {
            case NOT_A_PLAYER ->
                    message += ChatColor.RED + "Du musst ein Spieler sein um diesen Befehl ausführen zu können!";
            case NOT_ENOUGH_PERMISSION ->
                    message += ChatColor.RED + "Du hast nicht genügen Berechtigungen um diesen Befehl ausführen zu können!";
            case NOT_EXIST -> message += ChatColor.RED + "Dieser Befehl existiert nicht!";
            case NULL, FALSE_PARAM -> message += ChatColor.RED + "Ein Nicht bekannter Fehler ist aufgetreten!";
        }
        return message;
    }

    public Component getMessage() {
        return switch (errorMessageType) {
            case NOT_A_PLAYER ->
                    Component.text("Du musst ein Spieler sein um diesen Befehl ausführen zu können!", NamedTextColor.RED);
            case NOT_ENOUGH_PERMISSION ->
                    Component.text("Du hast nicht genügen Berechtigungen um diesen Befehl ausführen zu können!", NamedTextColor.RED);
            case NOT_EXIST ->
                    Component.text("Dieser Befehl existiert nicht!", NamedTextColor.RED);
            case NULL, FALSE_PARAM ->
                    Component.text("Ein Nicht bekannter Fehler ist aufgetreten!", NamedTextColor.RED);
            default -> Component.text(message);
        };
    }

    public Component getPrefixedMessage() {
        if (!this.prefix)
            this.PREFIX = "";

        return switch (errorMessageType) {
            case NOT_A_PLAYER ->
                    Component.text(PREFIX).append(Component.text("Du musst ein Spieler sein um diesen Befehl ausführen zu können!", NamedTextColor.RED));
            case NOT_ENOUGH_PERMISSION ->
                    Component.text(PREFIX).append(Component.text("Du hast nicht genügen Berechtigungen um diesen Befehl ausführen zu können!", NamedTextColor.RED));
            case NOT_EXIST ->
                    Component.text(PREFIX).append(Component.text("Dieser Befehl existiert nicht!", NamedTextColor.RED));
            case NULL, FALSE_PARAM ->
                    Component.text(PREFIX).append(Component.text("Ein Nicht bekannter Fehler ist aufgetreten!", NamedTextColor.RED));
            default -> Component.text(PREFIX).append(Component.text(message));
        };
    }
}
