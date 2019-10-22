package katie.marvel.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashBuilder {

    /**
     * md5(ts+privateKey+publicKey)
     * @return
     */
    public static String getHash(String ts, String privateKey, String publicKey) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not find MD5 algorithm");
        }
        byte[] bytesOfMessage = (ts + privateKey + publicKey).getBytes(StandardCharsets.UTF_8);
        byte[] digested = md.digest(bytesOfMessage);
        return toHexString(digested);
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
