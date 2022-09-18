package de.thomas.utils.resourcepack.verification;

import de.thomas.utils.resourcepack.ResourcePackURLData;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

public class HashingUtil {

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String toHexString(byte[] array) {
        StringBuilder r = new StringBuilder(array.length * 2);
        for (byte b : array) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static byte[] toByteArray(String s) {
        final int len = s.length();

        // "111" is not a valid hex encoding.
        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
        }

        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(s.charAt(i));
            int l = hexToBin(s.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
            }

            out[i / 2] = (byte) (h * 16 + l);
        }

        return out;
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }

    public static Pair<String, Integer> getDataFromUrl(String url) throws Exception {
        // This is not done async on purpose. We don't want the server to start without having checked this first.
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        final URLConnection urlConnection = new URL(url).openConnection();

        // Notify size of file
        final int sizeInMB = urlConnection.getContentLength() / 1024 / 1024;

        final InputStream fis = urlConnection.getInputStream();
        int n = 0;
        byte[] buffer = new byte[8192];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        fis.close();
        final byte[] urlBytes = digest.digest();
        return Pair.of(toHexString(urlBytes), sizeInMB);
    }

    public static ResourcePackURLData performPackCheck(String url, String configHash) throws Exception {
        // This is not done async on purpose. We don't want the server to start without having checked this first.
        final Pair<String, Integer> data = getDataFromUrl(url);
        return new ResourcePackURLData(data.getFirst(), configHash, data.getSecond());
    }
}