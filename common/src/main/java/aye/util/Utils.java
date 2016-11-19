package aye.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import aye.content.Contexts;

/**
 * Created by reid on 2016/11/20.
 */

public class Utils {

    public static int getScreenWidth(){
        return Contexts.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return Contexts.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static String getMD5(String message) {
        if (message == null)
            return null;

        return getMD5(message.getBytes());
    }

    public static String getMD5(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        String digest = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            digest = toHexString(algorithm.digest());
        } catch (Exception e) {
        }
        return digest;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String str = Integer.toHexString(0xFF & b);
            while (str.length() < 2) {
                str = "0" + str;
            }
            hexString.append(str);
        }
        return hexString.toString();
    }

    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return byte2HexStr(rawHmac);
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = null;
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        return sb.toString().toLowerCase().trim();
    }
}
