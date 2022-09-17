package de.thomas.utils.resourcepack;

public record ResourcePackURLData(String urlHash, String configHash, int size) {

    public boolean match() {
        return urlHash.equalsIgnoreCase(configHash);
    }
}