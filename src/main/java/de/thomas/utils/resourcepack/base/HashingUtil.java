package de.thomas.utils.resourcepack.base;
import de.thomas.utils.resourcepack.base.verification.ResourcePackURLData;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashingUtil {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static String toHexString(byte[] array) {
        return bytesToHex(array);
    }

    public static byte[] toByteArray(String s) {
        return decodeUsingBigInteger(s);
    }

    public static byte[] decodeUsingBigInteger(String hexString) {
        byte[] byteArray = new BigInteger(hexString, 16)
                .toByteArray();
        if (byteArray[0] == 0) {
            byte[] output = new byte[byteArray.length - 1];
            System.arraycopy(
                    byteArray, 1, output,
                    0, output.length);
            return output;
        }
        return byteArray;
    }

    public static String getHashFromUrl(String url) throws Exception {
        return getDataFromUrl(url).getFirst();
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