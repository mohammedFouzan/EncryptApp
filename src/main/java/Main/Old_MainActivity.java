package Main;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.encryptapp.R;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import Encryption.Algorithms.AES;
import Encryption.Algorithms.Caesarcipher;
import Encryption.Algorithms.PlayFair;
import Encryption.Algorithms.Vigenere;

public class Old_MainActivity extends AppCompatActivity {
    private String message;
    private String key;
    private Button Switch;
    private Button Encrypt_Button;
    private Button Decrypt_Button;
    private PlayFair p;
    private TextView Answer;
    private EditText Textfield_Text;
    private EditText Textfield_Key;
    private TextView Matrix_value;
    private TextView Play_Fair_VALUE;
    private ConstraintLayout ConstraintLayout;
    private ConstraintSet ConstraintSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.encryption_main);
        Switch = findViewById(R.id.Switch);
        Encrypt_Button = findViewById(R.id.Encrypt_Button);
        Decrypt_Button = findViewById(R.id.Decrypt_Button);
        Answer = findViewById(R.id.Answer);
        Textfield_Text = findViewById(R.id.TextArea);
        Textfield_Key = findViewById(R.id.Key);
        Matrix_value = findViewById(R.id.Matrix);
        Play_Fair_VALUE = findViewById(R.id.Play_Fair_VALUE);
        //Switch.setText("Advanced Encryption Standard");
        ConstraintLayout = findViewById(R.id.ConstraintLayout);
        ConstraintSet = new ConstraintSet();
        ConstraintSet.clone(ConstraintLayout);
    }

    public void Encrypt(View view) throws Exception {


        if (Textfield_Text.length() == 0) {
            Toast.makeText(this, "Enter a message to Encrypt", Toast.LENGTH_SHORT).show();
            return;
        }
        message = String.valueOf(Textfield_Text.getText());
        key = String.valueOf(Textfield_Key.getText());
        String Algorithm = String.valueOf(Switch.getText());
        switch (Algorithm) {
            case "Advanced Encryption Standard": {
                AES aes = new AES();
                String encData = aes.AESencrypt(key.getBytes("UTF-16LE"), message.getBytes("UTF-16LE"));
                Answer.setText(encData);
                break;
            }
            case "Caesar Cipher": {
                if (key.isEmpty()) {
                    Toast.makeText(this, "Enter a key to Encrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(key) > 26) {
                    Toast.makeText(this, "The Key must be 26 or under", Toast.LENGTH_SHORT).show();
                    return;
                }
                Caesarcipher c = new Caesarcipher();
                Answer.setText(c.caesarcipherEnc(message, Integer.parseInt(key)));
                break;

            }
            case "Vigenere Cipher": {
                if (Textfield_Key.length() == 0) {
                    Toast.makeText(this, "Enter a key to Encrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (char i : message.toUpperCase().toCharArray()) {
                    if (i < 'A' || i > 'Z') {
                        Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for (char i : key.toUpperCase().toCharArray()) {
                    if (i < 'A' || i > 'Z') {
                        Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                Vigenere v = new Vigenere();
                Answer.setText(v.Vigenereencrypt(message, key));
                break;
            }


            case "Play Fair": {
                try {
                    p = new PlayFair("");
                    Play_Fair_VALUE.setText(p.Encrypt(message, key));
                    Matrix_value.setText(p.getT1());
                } catch (Exception e) {
                    Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "SHA-256": {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
                String encoded = Base64.encodeToString(hash, 0);
                Answer.setText(encoded);

            }
            //            case "RSA": {
//                try {
//
//                    keyPair = buildKeyPair();
//                    PrivateKey privateKey = keyPair.getPrivate();
//                    pubKey = keyPair.getPublic();
//                    byte[] signed = encryptRSA(privateKey, message);
//                    String stringToStore = new String(Base64.encode(signed, 0));
//                    answer.setText(stringToStore);
//
//                } catch (Exception e) {
//                    Toast.makeText(this, "Your message is to long", Toast.LENGTH_SHORT).show();
//
//                }
//                break;
//            }

        }
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    private static PublicKey pubKey;

    public void Decrypt(View view) throws Exception {
        if (Textfield_Text.length() == 0) {
            Toast.makeText(this, "Enter a message to Decrypt", Toast.LENGTH_SHORT).show();
            return;
        }

        message = String.valueOf(Textfield_Text.getText());
        key = String.valueOf(Textfield_Key.getText());
        String SwitchValue = Switch.getText().toString();
        switch (SwitchValue) {
            case "Advanced Encryption Standard":
                AES aes = new AES();
                try {
                    String decData = aes.AESdecrypt(key, Base64.decode(message.getBytes("UTF-16LE"), Base64.DEFAULT));
                    Answer.setText(decData);
                } catch (Exception e) {
                    Toast.makeText(this, "Your key is wrong", Toast.LENGTH_SHORT).show();
                }
                break;
            case "Caesar Cipher":
                if (Textfield_Key.length() == 0) {
                    Toast.makeText(this, "Enter a key", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(key) >= 26) {
                    Toast.makeText(this, "The Key must be 26 or under", Toast.LENGTH_SHORT).show();
                    return;
                }
                Caesarcipher c = new Caesarcipher();
                Answer.setText(c.caesarcipherDec(message, Integer.parseInt(key)));
                break;
            case "Vigenere Cipher":
                if (Textfield_Key.length() == 0) {
                    Toast.makeText(this, "Enter a key to Decrypt", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (char i : message.toUpperCase().toCharArray()) {
                    if (i < 'A' || i > 'Z') {
                        Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for (char i : key.toUpperCase().toCharArray()) {
                    if (i < 'A' || i > 'Z') {
                        Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Vigenere v = new Vigenere();
                Answer.setText(v.Vigeneredecrypt(message, key));
                break;

            case "Play Fair":
                try {
                    Play_Fair_VALUE.setText(p.Decrypt(message, key));
                    Matrix_value.setText(p.getT1());
                } catch (Exception e) {

                    Toast.makeText(this, "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
                }
                break;
            //            case    "RSA" :
//                try
//                {
//                    byte[] restoredBytes = Base64.decode(message.getBytes(), 0);
//                    byte[] verified = decryptRSA(pubKey, restoredBytes);
//                    answer.setText(new String(verified));
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(this, "Your key is worng", Toast.LENGTH_SHORT).show();
//
//                }
//                break;

        }

    }


    public void switchAlgho(View view) {
        RESET(null);
        String SwitchValue = Switch.getText().toString();
        switch (SwitchValue) {
            case "Advanced Encryption Standard":
                Textfield_Key.setInputType(InputType.TYPE_CLASS_NUMBER);
                Switch.setText("Caesar Cipher");
                break;
            case "Caesar Cipher":
                Textfield_Key.setInputType(InputType.TYPE_CLASS_TEXT);
                Switch.setText("Vigenere Cipher");
                break;
            case "Vigenere Cipher":
                Textfield_Key.setVisibility(View.VISIBLE);
                Answer.setVisibility(View.GONE);
                Matrix_value.setVisibility(View.VISIBLE);
                Play_Fair_VALUE.setVisibility(View.VISIBLE);
                Switch.setText("Play Fair");
                break;
            case "Play Fair":
                Answer.setVisibility(View.VISIBLE);
                Matrix_value.setVisibility(View.GONE);
                Play_Fair_VALUE.setVisibility(View.GONE);
                ConstraintSet.setHorizontalBias(R.id.Encrypt_Button, (float) 0.50);
                ConstraintSet.applyTo(ConstraintLayout);
                Decrypt_Button.setVisibility(View.GONE);
                Textfield_Key.setVisibility(View.GONE);
                Encrypt_Button.setText("Hash");
                Switch.setText("SHA-256");
                break;
            case "SHA-256":
                Textfield_Key.setVisibility(View.VISIBLE);
                Decrypt_Button.setVisibility(View.VISIBLE);
                ConstraintSet.setHorizontalBias(R.id.Encrypt_Button, (float) 0.25);
                ConstraintSet.applyTo(ConstraintLayout);
                Encrypt_Button.setText("Encrypt");
                Switch.setText("Advanced Encryption Standard");
                break;

        }

    }

    //        } else if (Switch.getText().equals("RSA")) {
//            ekey.setVisibility(View.VISIBLE);
//            answer.setVisibility(View.GONE);
//            emat.setVisibility(View.VISIBLE);
//            emessage2.setVisibility(View.VISIBLE);
//            Switch.setText("Play Fair");
//}



    public void RESET(View view) {
        Textfield_Text.setText("");
        Textfield_Key.setText("");
        Answer.setText("");
        Play_Fair_VALUE.setText("");
        Matrix_value.setText("");
        if(view!=null)
        Toast.makeText(this, "All data has been deleted", Toast.LENGTH_SHORT).show();
    }



    public void copyToClipboard(View view) {
        if (Play_Fair_VALUE.length() == 0) {
            String copyText = String.valueOf(Answer.getText());
            if (Answer.length() == 0) {
                Toast.makeText(this, "There is no message to copy", Toast.LENGTH_SHORT).show();
                return;
            }

            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(copyText);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText("Your message :", copyText);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(this,
                    "Your message has be copied", Toast.LENGTH_SHORT).show();
        } else {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(Play_Fair_VALUE.getText().toString());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText("Your message :", Play_Fair_VALUE.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(this, "Your message has be copied", Toast.LENGTH_SHORT).show();
        }
    }
}





















