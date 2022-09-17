package de.thomas.utils.resourcepack;

import de.thomas.utils.message.Message;
import de.thomas.utils.resourcepack.verification.HashingUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public record ResourcePack(String url, String hash, int size) {

    private byte[] getHashSum() {
        return HashingUtil.toByteArray(hash);
    }

    public void setResourcePack(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;

        try {
            player.setResourcePack(url, getHashSum(), new Message("Das BaguettePack ist zwingend notwendig!", false).getMessage(), true);
        } catch (NoSuchMethodError ignored) {
            player.setResourcePack(url, getHashSum());
        }
    }
}
