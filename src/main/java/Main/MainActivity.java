package Main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.encryptapp.R;

import Encryption.EncryptionMain;
import Hash.HashMain;

import About.AboutMain;



public class MainActivity extends AppCompatActivity {
    EncryptionMain encryptionMain;
    HashMain hashMain;

    AboutMain aboutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void goToEncryption(View view) {
        encryptionMain = new EncryptionMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, encryptionMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToAbout(View view) {
        aboutMain = new AboutMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, aboutMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToHash(View view) {
        hashMain = new HashMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, hashMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void encryptionButtonClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.Switch:
                    encryptionMain.switchAlgho(view);
                    break;
                case R.id.Encrypt_Button:
                    encryptionMain.encrypt(view);
                    break;
                case R.id.Decrypt_Button:
                    encryptionMain.decrypt(view);
                    break;
                case R.id.copy_button:
                    encryptionMain.copyToClipboard(view);
                    break;
                case R.id.reset_button:
                    encryptionMain.reset(view);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
    }

    }

    public void HashButtonClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.Switch:
                    hashMain.switchAlgho(view);
                    break;
                case R.id.hash_Button:
                    hashMain.hash(view);
                    break;
                case R.id.copy_button:
                    hashMain.copyToClipboard(view);
                    break;
                case R.id.reset_button:
                    hashMain.reset(view);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
