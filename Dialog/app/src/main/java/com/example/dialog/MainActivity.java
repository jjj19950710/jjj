package com.example.dialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_Toast,btn_Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Toast = findViewById(R.id.btn_Toast);
        btn_Dialog = findViewById(R.id.btn_Dialog);

        btn_Toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示")
                       .setMessage("请输入用户名和密码")
                       .setCancelable(false)
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "用户按下了确认按钮", Toast.LENGTH_SHORT).show();
                    }
                })
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "用户按下了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                })
                        .setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "用户按下了忽略按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        btn_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 获取 layout inflater对象
                LayoutInflater inflater = getLayoutInflater();
                //建立对话框视图
               final View dialogView = inflater.inflate(R.layout.login, null);
                builder.setView(dialogView)
                       .setTitle("Login")
                       .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText username =  dialogView.findViewById(R.id.UserId);
                                EditText password =  dialogView.findViewById(R.id.Password);
                                if (username.getText().toString().equals("abc") && password.getText().toString().equals("123"))
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(MainActivity.this, "用户名与密码不匹配", Toast.LENGTH_SHORT).show();

                            }
                        })

                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });
    }
}