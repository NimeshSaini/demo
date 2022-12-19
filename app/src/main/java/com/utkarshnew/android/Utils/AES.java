package com.utkarshnew.android.Utils;


import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.utkarshnew.android.Utils.Network.API;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static String CIPHER_NAME = "AES/CBC/NoPadding";
    public static String strArrayKey = "MTA5MCMj1090##JSFGKiZeJClfKiUzZiZCKw==XWc7dnMnMmFs";
    public static String strArrayvector = "MTA5MCMj1090##IyokREp2eXcydyUhXy0kQA==XWc7dnMnMmFs";
    private static int CIPHER_KEY_LEN = 16; //128 bits
    public static String strArrayKeyDownloadV2 = "MTA5MDkwMTAjIw==c2w7NDYkJzsq10909010##ZyYnOy5Ic2Y0N15AO31bMCg4N2onNyY4XmpJJzooOCwuS2h0JTY0R2Y=MTA5MDkwMTAjIw==";
    //public static String strArrayKeyDownloadV2 = "MTA5MCMj1090##c1sncjU0aydzLiwyW3MnZjBhL3c0NTs=XWc7dnMnMmFs";
    public static String strArrayKeyDownload = "MTA5MCMj1090##YUh9NXNbezsnPiwkMiZ2Ow==XWc7dnMnMmFs";
    public static String strArrayvectorDownload = "MTA5MCMj1090##aTtHfSdodCNkKm86Jy8lZg==XWc7dnMnMmFs";


    public static String encrypt(String data) {
        String key = AES.generatekeyAPI();
        String iv = AES.generateVectorAPI();
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec secretKey = new SecretKeySpec(fixKey(key).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encryptedData = cipher.doFinal((data.getBytes("UTF-8")));
            String encryptedDataInBase64 = Base64.encodeToString(encryptedData, Base64.DEFAULT);
            return encryptedDataInBase64 + ":";

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*public static String encrypt(String key, String iv, String plain) {
        try {
            //byte[] iv = new byte[16];
            //new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //Cipher cipher = Cipher.getInstance(AES.CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"), new IvParameterSpec(iv.getBytes("utf-8")));
            byte[] cipherText = cipher.doFinal(plain.getBytes("utf-8"));
            byte[] ivAndCipherText = getCombinedArray(iv.getBytes("utf-8"), cipherText);
            return Base64.encodeToString(ivAndCipherText, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }*/

    /*public static String decrypt(String encoded) {
        try {
            byte[] ivAndCipherText = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] iv = Arrays.copyOfRange(ivAndCipherText, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(ivAndCipherText, 16, ivAndCipherText.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(TOKEN_KEY.getBytes("utf-8"), "AES"), new IvParameterSpec(iv));
            return new String(cipher.doFinal(cipherText), "utf-8");
        } catch (Exception e) {
            Ln.e(e);
            return null;
        }
    }*/

    /*private static byte[] getCombinedArray(byte[] one, byte[] two) {
        byte[] combined = new byte[one.length + two.length];
        for (int i = 0; i < combined.length; ++i) {
            combined[i] = i < one.length ? one[i] : two[i - one.length];
        }
        return combined;
    }*/

    private static String fixKey(String key) {

        if (key.length() < AES.CIPHER_KEY_LEN) {
            int numPad = AES.CIPHER_KEY_LEN - key.length();

            for (int i = 0; i < numPad; i++) {
                key += "0"; //0 pad to len 16 bytes
            }

            return key;

        }

        if (key.length() > AES.CIPHER_KEY_LEN) {
            return key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
        }

        return key;
    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */

    public static String decrypt(String data, String key, String ivParameter) {

        try {
            if (data.contains(":")) {
                String[] parts = data.split(":");

                IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance(AES.CIPHER_NAME);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

                byte[] decodedEncryptedData = Base64.decode(parts[0], Base64.NO_PADDING);

                byte[] original = cipher.doFinal(decodedEncryptedData);

                return new String(original);
            } else {
                IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance(AES.CIPHER_NAME);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

                byte[] decodedEncryptedData = Base64.decode(data, Base64.NO_PADDING);

                byte[] original = cipher.doFinal(decodedEncryptedData);

                return new String(original);
            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String generatekeyAPI() {
        String finalKey = "";
        String parts;
        if (SharedPreference.getInstance() != null && SharedPreference.getInstance().getLoggedInUser() != null && !TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
            parts = (SharedPreference.getInstance().getLoggedInUser().getId() + API.APITOKEN).substring(0, 16);
        } else {
            parts = ("0" + API.APITOKEN).substring(0, 16);
        }
        for (char c : parts.toCharArray()) {
            finalKey = finalKey + AES.encryptPassword(AES.strArrayKey).toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        Log.d("princekey",""+finalKey);
        return finalKey;
    }



    public static String generatekey1(String token) {
        String ky ="7fF*L^5L_*N";
        String finalKey = "";
        for (char c : token.toCharArray()) {
            finalKey = finalKey + ky.toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }


    public static String generateVector1(String parts) {
        String finalKey = "";
        String vec ="K2PDJvyw2w";

        for (char c : parts.toCharArray()) {
            finalKey = finalKey + vec.toCharArray()[Integer.parseInt(String.valueOf(c))];
        }

        return finalKey;
    }



    public static String generateVectorAPI() {
        String finalKey = "";
        String parts;
        if (SharedPreference.getInstance() != null && SharedPreference.getInstance().getLoggedInUser() != null && !TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
            parts = (SharedPreference.getInstance().getLoggedInUser().getId() + API.APITOKEN).substring(0, 16);
        } else {
            parts = ("0" + API.APITOKEN).substring(0, 16);
        }
        for (char c : parts.toCharArray()) {
            finalKey = finalKey + AES.encryptPassword(AES.strArrayvector).toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }




    public static String generatekey(String token) {
        String finalKey = "";
        String[] parts = token.split("_");
        for (char c : parts[2].toCharArray()) {
            finalKey = finalKey + AES.encryptPassword(AES.strArrayKey).toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }

    public static String generateVector(String token) {
        String finalKey = "";
        String[] parts = token.split("_");
        for (char c : parts[2].toCharArray()) {
            finalKey = finalKey + AES.encryptPassword(AES.strArrayvector).toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }

    public static String encryptPassword(String key) {
        String password = "";
        String[] base = key.split("1090##", 2);
        String sub1 = base[0];
        String sub2 = base[1];
        String[] subBase = sub2.split("==", 2);
        String s1 = subBase[0];
        String finalEncodeStart = s1 + "==";
        String endEncode = subBase[1];

        byte[] decodeStart = Base64.decode(finalEncodeStart, Base64.DEFAULT);
        String finalDecodeStart = new String(decodeStart, StandardCharsets.UTF_8);

        byte[] decodeEnd = Base64.decode(endEncode, Base64.DEFAULT);
        String finalDecodeEnd = new String(decodeEnd, StandardCharsets.UTF_8);
        //password = finalDecodeStart + finalDecodeEnd;
        password = finalDecodeStart;

        return password;
    }

    /*public static String encryptPassword() {
        String password = "";
        String[] base = AES.strArrayKeyDownloadV2.split("1090##", 2);
        String sub1 = base[0];
        String sub2 = base[1];
        String[] subBase = sub2.split("=", 2);
        String s1 = subBase[0];
        String finalEncodeStart = s1 + "=";
        String endEncode = subBase[1];

        byte[] decodeStart = Base64.decode(finalEncodeStart, Base64.DEFAULT);
        String finalDecodeStart = new String(decodeStart, StandardCharsets.UTF_8);

        byte[] decodeEnd = Base64.decode(endEncode, Base64.DEFAULT);
        String finalDecodeEnd = new String(decodeEnd, StandardCharsets.UTF_8);
        password = finalDecodeStart + finalDecodeEnd;

        return password;
    }*/

    public static String encryptPassword() {
        String password = "";
        String[] base = AES.strArrayKeyDownloadV2.split("10909010##", 2);
        String sub1 = base[0];
        String sub2 = base[1];
        String[] subBase = sub1.split("==", 2);
        String finalEncodeStart = subBase[1];
        String[] subBaseEnd = sub2.split("=", 2);
        String finalEncodeEnd = subBaseEnd[0];

        byte[] decodeStart = Base64.decode(finalEncodeStart, Base64.DEFAULT);
        String finalDecodeStart = new String(decodeStart, StandardCharsets.UTF_8);

        byte[] decodeEnd = Base64.decode(finalEncodeEnd, Base64.DEFAULT);
        String finalDecodeEnd = new String(decodeEnd, StandardCharsets.UTF_8);
        password = finalDecodeStart + finalDecodeEnd;

        return password;
    }
    public static String strArrayKeyLib = "7fF*L^5L_*N";
    public static String strArrayvectorLib = "K2PDJvyw2w";

    public static String generateLibkeyAPI(String token) {
        String finalKey = "";
        String parts;
        parts = token;
        for (char c : parts.toCharArray()) {
            finalKey = finalKey + AES.strArrayKeyLib.toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }

    public static String generateLibVectorAPI(String token) {
        String finalKey = "";
        String parts;
        parts = token;
        for (char c : parts.toCharArray()) {
            finalKey = finalKey + AES.strArrayvectorLib.toCharArray()[Integer.parseInt(String.valueOf(c))];
        }
        return finalKey;
    }
}

