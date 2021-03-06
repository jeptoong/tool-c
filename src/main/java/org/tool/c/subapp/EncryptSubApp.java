package org.tool.c.subapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.c.services.crypto.Encrypt;
import org.tool.c.services.crypto.SymmetricKey;
import org.tool.c.utils.constants.Constants;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Encrypt data.
 */
public class EncryptSubApp {

    private static Logger LOG = LoggerFactory.getLogger(EncryptSubApp.class);

    public static void main(String[] args) {
        try {
            String data[] = {""}; // Define value to encrypt here.

            ResourceBundle bundle = ResourceBundle.getBundle(Constants.BUNDLE_APPLICATION);
            String algorithm = bundle.getString("crypto.algorithm");
            int length = Integer.parseInt(bundle.getString("crypto.length"));
            String secretFilePath = bundle.getString("crypto.secret-file");

            // New Symmetric key
            SymmetricKey sk = new SymmetricKey(length, algorithm);
            // Encrypt
            Encrypt encrypt = new Encrypt(sk.getKey(), algorithm);
            for (String value : data) {
                String encryptValue = encrypt.encryptString(value);

                // Print out result
                LOG.info("Encrypt value of \"" + value + "\" is: " + encryptValue);
            }

            // Save symmetric key
            sk.writeToFile(secretFilePath);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
