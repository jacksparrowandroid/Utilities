package com.usb.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientActivity extends AppCompatActivity {
    private static final String TAG = "Main";

    public static final int TIMEOUT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(initializeConnection).start();
            }
        });
    }
    private String connectionStatus = null;
    ServerSocket server = null;
    private Runnable initializeConnection = new Thread() {
        public void run() {

            Socket client = null;
            try {
                client = new Socket("localhost",3832);
                DataOutputStream dOut = new DataOutputStream(client.getOutputStream());
                dOut.writeByte(1);
                dOut.writeUTF("This is the first type of message.");
                dOut.flush(); // Send off the data
            } catch (SocketTimeoutException e) {
                connectionStatus = "Connection has timed out! Please try again";
            } catch (IOException e) {
                Log.e(TAG, "" + e);
            } finally {
                // close the server socket
                try {
                    if (server != null)
                        server.close();
                } catch (IOException ec) {
                    Log.e(TAG, "Cannot close server socket" + ec);
                }
            }

            if (client != null) {
                Log.d(TAG, "connected!");
            }
        }
    };
}
