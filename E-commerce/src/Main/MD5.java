package Main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static MD5 instance = null;
    private MD5(){}
    public static MD5 getInstance(){
        if (instance == null){
            instance = new MD5();
        }
        return instance;
    }

    public String encrypt(String word) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(word.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
