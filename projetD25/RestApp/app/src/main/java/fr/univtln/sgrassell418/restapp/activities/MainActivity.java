package fr.univtln.sgrassell418.restapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


import fr.univtln.sgrassell418.restapp.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        connectWS();
    }




    // UI BINDING

    public void callGetActivity(View view){
        Intent intent = new Intent(this, GetActivity.class);
        this.startActivity(intent);
    }

    public void callPutActivity(View view){
        Intent intent = new Intent(this, PutActivity.class);
        this.startActivity(intent);
    }

    public void callCalendarActivity(View view){
        Toast.makeText(this, "Not yet avaiable...", Toast.LENGTH_LONG).show();
    }

    // WEBSOCKET

    private void connectWS(){
        try {
            WebSocketClient client = new WebSocketClient(new URI("http://10.42.4.118:8080/")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.i("WS", "opened");
                }

                @Override
                public void onMessage(String message) {
                    Log.i("WS", "NOTIF");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.i("WS", "closed");
                }

                @Override
                public void onError(Exception e) {
                    Log.e("WS", e.toString());
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}


