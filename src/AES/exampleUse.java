package AES;
import java.util.Scanner;

public class exampleUse {

	public static void main(String[] args) 
    {
        final String secretKey = "123456789";
         
        //String originalString = "insertStringHere!";
        Scanner scanner  = new Scanner (System.in);
        String originalString = scanner.nextLine();
        if(scanner != null) { scanner.close(); }
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;
         
        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }

}
