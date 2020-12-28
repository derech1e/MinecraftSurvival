package de.thomas.utils.message;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Message {

    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "BM" + ChatColor.GREEN + "311" +ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    private final ErrorMessageType errorMessageType;
    private @NotNull String message = PREFIX;

    public Message(ErrorMessageType errorMessageType) {
        this.errorMessageType = errorMessageType;
    }

    public Message(String message) {
        this.message = PREFIX + message;
        errorMessageType = ErrorMessageType.EMPTY;
    }

    public @NotNull String getMessage() {
        switch (errorMessageType) {
            case NOT_A_PLAYER:
                message += ChatColor.RED + "Du musst ein Spieler sein um diesen Befehl ausführen zu können!";
                break;
            case NOT_ENOUGH_PERMISSION:
                message += ChatColor.RED + "Du hast nicht genügen Berechtigungen um diesen Befehl ausführen zu können!";
                break;
            case NOT_EXIST:
                message += ChatColor.RED + "Dieser Befehl existiert nicht!";
                break;
            case NULL:
                message += ChatColor.RED + "Ein Nicht bekannter Fehler ist aufgetreten!";
            case FALSE_PARM:
                message += ChatColor.RED + "Ein Nicht bekannter Fehler ist aufgetreten!";
        }
        return message;
    }
}
