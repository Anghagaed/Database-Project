package net.codejava.crypto;


import java.io.*;
 
/**
 * A tester for the CryptoUtils class.
 * @author www.codejava.net
 *
 */
public class Test {
    public static void main(String[] args) {
        String key = "Mary has one cat";
      
        //code below is original code
       File inputFile = new File("~/Desktop/DATABASE/test.db");
        File encryptedFile = new File("document.encrypted");
        File decryptedFile = new File("document.decrypted");
        
        //code below is fatima's attempt
       /* FileInputStream inputFile = new FileInputStream("document.txt");
        File encryptedFile = new File("document.encrypted");
        FileOutputStream decryptedFile = new FileOutputStream("document.decrypted");*/
        
         
        try {
            fileAES.encrypt(key, inputFile, encryptedFile);
            fileAES.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}