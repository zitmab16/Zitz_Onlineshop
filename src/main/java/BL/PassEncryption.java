/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;


   import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;

public class PassEncryption {

    public static void main(String[] args) throws SQLException {

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
              "pass".getBytes(StandardCharsets.UTF_8));
            System.out.println(bytesToHex(encodedhash));
            System.out.println(bytesToHex(encodedhash).length());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    public String encryptString(String pass){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
              pass.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
      return null;
    }
    private static String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
    String hex = Integer.toHexString(0xff & hash[i]);
    if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}
} 

