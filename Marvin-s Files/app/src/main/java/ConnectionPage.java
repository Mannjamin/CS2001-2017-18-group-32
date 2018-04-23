package com.marvin.lightr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class ConnectionPage extends AppCompatActivity {

    private TextView PleaseEnter;
    private EditText DeviceServerAddress;
    private Button CheckConnection;
    private Button ContinueToApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_page);

        PleaseEnter = (TextView) findViewById(R.id.tvPleaseEnter);
        DeviceServerAddress = (EditText) findViewById(R.id.etDeviceServerAddress);
        CheckConnection = (Button) findViewById(R.id.btnCheckConnection);
        ContinueToApp = (Button) findViewById(R.id.btnContinuetoApp);

        ContinueToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
    }


    public void openMainMenu() {
        Intent intent6 = new Intent(this, MainMenu.class);
        startActivity(intent6);
    }
}
