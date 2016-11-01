package com.jni.anto.kalip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.telephony.TelephonyManager;
import java.io.File;
import android.util.Log;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;
import java.security.*;

public class MainActivity extends AppCompatActivity {


    static String source="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static String target="Q9A8ZWS7XEDC6RFVT5GBY4HNU3J2MI1KO0LP";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button device_id = (Button) findViewById(R.id.button);
        device_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                Toast.makeText(getApplicationContext(), tm.getDeviceId(), Toast.LENGTH_SHORT).show();
                //get The Phone Number
                //String phone = tm.getLine1Number();
            }

        });

        Button filecheck = (Button) findViewById(R.id.button2);
        filecheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                File file = new File("/sdcard/test");
                if (file.exists()) {
                    Toast.makeText(getApplicationContext(), "File Exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "File Doesnt Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button rootdetect = (Button) findViewById(R.id.button3);
        rootdetect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isPhoneRooted()) {
                    Toast.makeText(getApplicationContext(), "Phone Rooted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Phone Not Rooted",Toast.LENGTH_SHORT).show();
                }

            }

        });


        Button stringDude = (Button) findViewById(R.id.button4);
        stringDude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dude("yaay from string args !");

            }
        });


        Button charDude = (Button) findViewById(R.id.button5);
        charDude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                char[] array = new char[26];
                int index = 0;
                for (char c = 'a'; c <= 'z'; c++) {
                    array[index++] = c;
                }
                dude(array);

            }
        });



        Button bttnobfuscate = (Button) findViewById(R.id.button7);
        bttnobfuscate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = "123456";
                    String result = obfuscate(password);
                Toast.makeText(getApplicationContext(),"Obfuscating : "+password + "Result : "+result,Toast.LENGTH_LONG).show();

            }
        });



        Button encrypt = (Button) findViewById(R.id.button6);
        encrypt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            try{


                byte[] b = "hahaha".getBytes();

                byte[] keyStart = "this is a key".getBytes();
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                sr.setSeed(keyStart);
                kgen.init(128, sr); // 192 and 256 bits may not be available
                SecretKey skey = kgen.generateKey();
                byte[] key = skey.getEncoded();

// encrypt
                encrypt(key,b);


            }catch (Exception e){
                Log.d("BUGG",e.toString());
            }

            }
        });
}

    public static boolean isPhoneRooted() {

        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Throwable e1) {
            // ignore
        }

        return false;
    }


    public void dude(String a){
        Toast.makeText(getApplicationContext(),a.toString(),Toast.LENGTH_LONG).show();
    }
    public void dude(char[] a){

        Toast.makeText(getApplicationContext(),new String(a),Toast.LENGTH_LONG).show();
    }

    public  void encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        Toast.makeText(getApplicationContext(),new String(encrypted),Toast.LENGTH_SHORT).show();
    }

    public  byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }



    public static String obfuscate(String s) {
        char[] result= new char[10];
        for (int i=0;i<s.length();i++) {
            char c=s.charAt(i);
            int index=source.indexOf(c);
            result[i]=target.charAt(index);
        }

        return new String(result);
    }

    public static String unobfuscate(String s) {
        char[] result= new char[10];
        for (int i=0;i<s.length();i++) {
            char c=s.charAt(i);
            int index=target.indexOf(c);
            result[i]=source.charAt(index);
        }

        return new String(result);
    }

}
