package com.marvin.lightr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private TextView New;
    private Button Login;
    private Button Register;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        New = (TextView)findViewById(R.id.tvNew);
        Login = (Button)findViewById(R.id.btnLogin);
        Register = (Button)findViewById(R.id.btnRegister);



        Info.setText("Number of attempts left: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
                openStartupSettings();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationPage();
            }
        });
    }

    public void openStartupSettings() {
        Intent intent2 = new Intent(this, StartupSettings.class);
        startActivity(intent2);
    }

    public void openRegistrationPage() {
        Intent intent3 = new Intent(this, RegistrationPage.class);
        startActivity(intent3);
    }

    private void validate(String userName, String userPassword) {
        if((userName == "Admin") && (userPassword == "1234")) {
            Intent intent = new Intent(LoginPage.this, StartupSettings.class);
            startActivity(intent);
        }else{
            counter--;

            Info.setText("Number of attempts left: " + String.valueOf(counter));

            if(counter == 0) {
                Login.setEnabled(false);
            }

        }
    }
}
