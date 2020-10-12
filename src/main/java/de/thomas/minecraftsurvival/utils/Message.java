package de.thomas.minecraftsurvival.utils;

public class Message {

    public static String PREFIX = "§7[§6Mc§aS§7]§r ";
    private final ErrorMessageType errorMessageType;
    private String message = PREFIX;

    public Message(ErrorMessageType errorMessageType) {
        this.errorMessageType = errorMessageType;
    }

    public Message() {
        this(ErrorMessageType.NULL);
    }

    public String getMessage() {
        switch (errorMessageType) {
            case NOT_A_PLAYER:
                message += "§cDu musst ein Spieler sein um diesen Befehl ausführen zu können!";
                break;
            case NOT_ENOUGH_PERMISSION:
                message += "§cDu hast nicht genügen Berechtigungen um diesen Befehl ausführen zu können!";
                break;
            case NOT_EXIST:
                message += "§cDieser Befehl exsistiert nicht!";
                break;
            default:
                message += "§cEin Fehler ist aufgetreten!";
        }
        return message;
    }

    public ErrorMessageType getErrorMessageType() {
        return errorMessageType;
    }
}
