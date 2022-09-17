package de.thomas.utils.resourcepack;

import de.thomas.utils.message.Message;
import de.thomas.utils.resourcepack.base.HashingUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResourcePack {

    protected final String url;
    protected final String hash;
    protected final int size;

    public ResourcePack(String url, String hash, int size) {
        this.url = url;
        this.hash = hash;
        this.size = size;
    }

    public String getServer() {
        return Bukkit.getServer().getName();
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }

    public int getSize() {
        return size;
    }

    private byte[] getHashSum() {
        return HashingUtil.toByteArray(hash);
    }

    public void setResourcePack(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;

        try {
            player.setResourcePack(url, getHashSum(), new Message("Bitte benutze das BaguettePack", false).getMessage(), true);
        } catch (NoSuchMethodError ignored) {
            player.setResourcePack(url, getHashSum());
        }
    }
}
