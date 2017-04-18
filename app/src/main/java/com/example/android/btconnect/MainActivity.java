package com.example.android.btconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

//import static com.example.android.btconnect.R.id.already_paired_devices_list;
//import static com.example.android.btconnect.R.id.newly_discovered_devices;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button pushCommandButton;

    static final int REQUEST_CODE = 1;
    String editTextString;
    Intent sendIntent;

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }


    Button searchButton;
    TextView alreadyPairedDevicesList,
            newlyDiscoveredDevicesList,
            newlyDiscoveredDevices;
    BluetoothAdapter mBluetoothAdapter;
    int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.v("Bluetooth check:", "not supported");
        } else {
            Log.v("Bluetooth check: ", "suppoert");
            if (!mBluetoothAdapter.isEnabled()) { //checks if BT is enabled
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT); // request to enable BT
            }
        }


        // query the set of paired devices to see if the desired device is already known
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);


        editText = (EditText) findViewById(R.id.editText);

        pushCommandButton = (Button) findViewById(R.id.push_command_button);

        pushCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextString = String.valueOf(editText.getText());
                editText.setText(editTextString);

//                sendIntent.setFlags(0);

//                sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, editTextString);
//                sendIntent.setType("text/plain");
//
//                startActivityForResult(sendIntent, REQUEST_CODE);
//                Log.v("startActivityForResult", "Attempted");

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                Uri screenshotUri = Uri.parse(picURI);

                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.android.bluetooth");
//                sendIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sendIntent, "Share fbs Command."));

            }// onClick
        });// setOnClickListener

//        searchButton = (Button) findViewById(R.id.search_button);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                newlyDiscoveredDevices.setVisibility(View.VISIBLE);
//
//                mBluetoothAdapter.startDiscovery();
//                if (mBluetoothAdapter.startDiscovery() == true) {
//                    Log.v("Discovery started? ", "yes");
//                }
//
////                mBluetoothAdapter.cancelDiscovery();
////                if (mBluetoothAdapter.cancelDiscovery() == true) {
////                    Log.v("Discovery canceled? ", "yes");
////                }
//
//            } // onClick
//
//        }); // setOnClickListener

    } //onCreate



}
