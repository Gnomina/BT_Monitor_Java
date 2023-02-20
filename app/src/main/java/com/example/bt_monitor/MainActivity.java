package com.example.bt_monitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bt_monitor.adapter.BtConsts;
import com.example.bt_monitor.bluetooth.BtConnection;


public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;//елемент класса onCreateOptionsMenu
    private BluetoothAdapter btAdapter; //переменная БТ адаптера
    private final int ENABLE_REQUSTS = 15;
    private SharedPreferences pref;
    private BtConnection btConnection;
    //------------------------------------
    TextView tvMessage;
    //------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---------------------------------------------
        tvMessage = findViewById(R.id.tvRecMessage);
        tvMessage.setText("svdfmblkdg kfmnkof");
        //--------------------------------------------
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_bt_button) {
            if (!btAdapter.isEnabled()) {
                enableBt();
            } else {
                btAdapter.disable();
                menuItem.setIcon(R.drawable.ic_bt_enable);
            }
        }else if(item.getItemId() == R.id.id_menu){
            if(btAdapter.isEnabled()){
            Intent i = new Intent(MainActivity.this, BtListActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "BT is off!.Turn on BT!!", Toast.LENGTH_SHORT).show();
            }


        }else if(item.getItemId() == R.id.id_connect){
            btConnection.connect();
            Toast.makeText(this, "BT IS Connected", Toast.LENGTH_LONG).show();

        }




        return super.onOptionsItemSelected(item);
    }

    @Override // создали кнопку блютус в верхней части меню
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override //запрос разрешения включения БТ
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == ENABLE_REQUSTS) {
            if (resultCode == RESULT_OK) {
                setBtIcon();

            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBtIcon() {
        //меняет значек БТ включен, выключеню
        if (btAdapter.isEnabled()) {
            menuItem.setIcon(R.drawable.ic_bt_disable);
        } else {
            menuItem.setIcon(R.drawable.ic_bt_enable);
        }

    }

    private void init() {
        //создали переменную доступ к реальному БТ адаперу
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);


    }

    private void enableBt() {

        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, ENABLE_REQUSTS);




    }






}