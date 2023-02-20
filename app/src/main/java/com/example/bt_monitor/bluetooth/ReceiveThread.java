package com.example.bt_monitor.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceiveThread extends Thread{
    private BluetoothSocket socket;
    private InputStream inputS;
    private OutputStream outputS;
    private byte[] rBuffer;


    public ReceiveThread(BluetoothSocket socket){
        this.socket = socket;
        try {
            inputS = socket.getInputStream();
        }catch (IOException e){
        }
        try {
            outputS = socket.getOutputStream();
        }catch (IOException e){
        }

    }

    /*@Override
    public void run() {
        rBuffer = new byte[50];
        while(true){
            try {
                int size = inputS.read(rBuffer);
                String message = new String(rBuffer, 0, size);
                Log.d("MyLog",  message );
            }catch (IOException e){
                break;
            }
        }
    }*/
    @Override
    public void run() {
        reseiveStr();
    }


    public void reseiveStr() {
        rBuffer = new byte[50];
        while(true){
            try {
                int size = inputS.read(rBuffer);
                String message = new String(rBuffer, 0, size);
                Log.d("MyLog",  message );
            }catch (IOException e){
                break;
            }
        }
    }




    public void sendMessage(byte[] buteArray){
        try {
            outputS.write(buteArray);

        }catch (IOException e){


        }


    }
}
