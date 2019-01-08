package com.example.prabalkar.encryptanddecryptapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt extends AppCompatActivity {

    EditText editText, inputpassword;
    TextView textView;
    Button encbtn, decbtn;
    String outputstring;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        editText = (EditText) findViewById(R.id.edittext);
        inputpassword= (EditText) findViewById(R.id.inputpassword);
        textView = (TextView) findViewById(R.id.textview);
        encbtn =(Button) findViewById(R.id.encbtn);
        decbtn =(Button) findViewById(R.id.decbtn);

        encbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputstring = encrypt(editText.getText().toString(), inputpassword.getText().toString());
                    textView.setText(outputstring);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        decbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputstring = decrypt(outputstring, inputpassword.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                textView.setText(outputstring);
            }
        });




    }


    private String decrypt(String outputstring, String password)throws Exception{

        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeValue = android.util.Base64.decode(outputstring, android.util.Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;


    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = android.util.Base64.encodeToString(encVal, android.util.Base64.DEFAULT);
        return  encryptedValue;


    }

    private SecretKeySpec generateKey(String password) throws  Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes =password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}



