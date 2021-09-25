package ifeanyi.opara.treasureapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    KeyGenerator keyGenerator;
    SecretKey secretKey;

    byte[] encrypt;

    Drawable drawable;
    Bitmap bitmap1, bitmap2;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;
    byte[] IV;

    public Encrypt(){
        bytearrayoutputstream = new ByteArrayOutputStream();

        IV = new byte[12];
        SecureRandom random;
        random = new SecureRandom();
        random.nextBytes(IV);

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String encryptButtonClicked(ImageView imageView) throws Exception {
        String byteString = convertImageToBitmap(imageView);
        encrypt = encrypt(byteString.getBytes(), secretKey, IV);
        return new String(encrypt, "UTF-8");
    }

    public String decryptButtonClicked() throws Exception {
        return decrypt(encrypt, secretKey, IV);
    }

    public Bitmap convertByteArrayToBitmap() throws Exception {
        String decrypt = decryptButtonClicked();
        byte[] decryptByte = decrypt.getBytes();
        bitmap2 = BitmapFactory.decodeByteArray(decryptByte, 0, decrypt.length());
        return bitmap2;
    }

    //returns string
    public String convertImageToBitmap(ImageView imageView1){
        Drawable drawable = imageView1.getDrawable();
        bitmap1 = ((BitmapDrawable) drawable).getBitmap();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 70, bytearrayoutputstream);
        BYTE = bytearrayoutputstream.toByteArray();
        return BYTE.toString();
    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
