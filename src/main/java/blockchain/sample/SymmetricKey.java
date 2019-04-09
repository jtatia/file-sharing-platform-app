package blockchain.sample;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmetricKey {

    private SecretKeySpec secretKey;
    private Cipher cipher;

    public SymmetricKey(String secret, int length, String algorithm)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] key = new byte[length];
        key = fixSecret(secret, length);
        this.secretKey = new SecretKeySpec(key, algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
        if (s.length() < length) {
            int missingLength = length - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, length).getBytes("UTF-8");
    }

    public String encryptFile(File f)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Encrypting file: " + f.getName());
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        this.writeToFile(f);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public void decryptFile(File f, byte[] lk)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Decrypting file: " + f.getName());
       // byte lk[] = key.getBytes();
        SecretKeySpec spec = new SecretKeySpec(lk,"AES");
        this.cipher.init(Cipher.DECRYPT_MODE, spec);
        this.writeToFile(f);
    }

    public void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream in = new FileInputStream(f);
        byte[] input = new byte[(int) f.length()];
        in.read(input);

        FileOutputStream out = new FileOutputStream(f);
        byte[] output = this.cipher.doFinal(input);
        out.write(output);

        out.flush();
        out.close();
        in.close();
    }

}
