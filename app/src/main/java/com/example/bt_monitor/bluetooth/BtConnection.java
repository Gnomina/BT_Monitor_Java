package com.example.bt_monitor.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bt_monitor.adapter.BtConsts;

public class BtConnection {
    private Context context;
    private SharedPreferences pref;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice device;
    private ConnectThread connectThread;


    public BtConnection(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void connect(){//проверки перед подключением
        String mac = pref.getString(BtConsts.MAC_KEY, "");
        if(!btAdapter.isEnabled() || mac.isEmpty()) return;
        device = btAdapter.getRemoteDevice(mac);
        if(device == null) return;
        connectThread = new ConnectThread(context, btAdapter, device);
        connectThread.start();
    }

    public void sendMessage(String message){
        connectThread.getRThread().sendMessage(message.getBytes());

    }

//Пишем тут -> пытаемся передать в майн активити
   public void addto() {
        connectThread.getRThread().reseiveStr();
    }


   // public void readMessage(String Message){



}


