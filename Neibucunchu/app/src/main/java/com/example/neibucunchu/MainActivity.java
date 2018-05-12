package com.example.neibucunchu;



import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnwrite=(Button)findViewById(R.id.btnwrite);
        Button btnread=(Button)findViewById(R.id.btnread);
        final TextView text2=(TextView)findViewById(R.id.text1);
        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outputStream=openFileOutput("linshi.text",MODE_PRIVATE);
                    String text="Name jiangbinghua  StudentId 2015011434";
                    try{
                        outputStream.write(text.getBytes(StandardCharsets.UTF_8));
                    }
                    finally {
                        if(outputStream!=null){
                            outputStream.close();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream inputStream = openFileInput("linshi.text");
                    int i;
                    StringBuilder stringBuilder=new StringBuilder("");
                    try {
                        while ((i=inputStream.read())!=-1){
                            stringBuilder.append((char)i);
                        }
                        text2.setText(stringBuilder.toString());
                    }
                    finally {
                        if(inputStream!=null){
                            inputStream.close();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
