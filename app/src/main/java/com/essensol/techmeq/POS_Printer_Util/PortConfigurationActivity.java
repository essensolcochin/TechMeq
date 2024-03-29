package com.essensol.techmeq.POS_Printer_Util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.R;
import com.printer.io.PortParameters;
import com.printer.service.PrinterPrintService;

public class PortConfigurationActivity extends AppCompatActivity {


    private static final String DEBUG_TAG = "PortConfigurationActivity";

    private RadioButton rbUSB, rbBluetooth, rbEhternet;
    private TextView tvPortInfo;
    private Button btConnect;
    private LinearLayout llEthernet;
    private PortParameters mPortParam;
    private EditText etIpAddress, etPortNum;

    // Return Intent extra

    public static       String EXTRA_DEVICE_ADDRESS   = "device_address";
    public static final int    REQUEST_ENABLE_BT      = 2;
    public static final int    REQUEST_CONNECT_DEVICE = 3;
    public static final int    REQUEST_USB_DEVICE     = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_configuration);
        mPortParam = new PortParameters();
        initView();
    }


    private void initView() {
        llEthernet = (LinearLayout) findViewById(R.id.llEthernet);
        tvPortInfo = (TextView) findViewById(R.id.tvPortInfo);
        btConnect = (Button) findViewById(R.id.btOk);
        rbUSB = (RadioButton) findViewById(R.id.rbUsb);
        rbBluetooth = (RadioButton) findViewById(R.id.rbBluetooth);
        rbEhternet = (RadioButton) findViewById(R.id.rbEthernet);
        etIpAddress = (EditText) findViewById(R.id.etIpAddress);
        etPortNum = (EditText) findViewById(R.id.etPortNumber);
//        rbUSB.setOnClickListener(new USBRaidoOnClickListener());
        rbBluetooth.setOnClickListener(new BluetoothRaidoOnClickListener());
//        rbEhternet.setOnClickListener(new EthernetRaidoOnClickListener());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void messageBox(String err) {
        Toast.makeText(getApplicationContext(),
                err, Toast.LENGTH_SHORT).show();
    }

    class USBRaidoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            btConnect.setVisibility(View.GONE);
            llEthernet.setVisibility(View.GONE);
            tvPortInfo.setVisibility(View.GONE);
            mPortParam.setPortType(PortParameters.USB);
//            getUsbDevice();
        }
    }

    class BluetoothRaidoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            btConnect.setVisibility(View.GONE);
            llEthernet.setVisibility(View.GONE);
            tvPortInfo.setVisibility(View.GONE);
            mPortParam.setPortType(PortParameters.BLUETOOTH);
            getBluetoothDevice();
        }
    }

    class EthernetRaidoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            tvPortInfo.setVisibility(View.GONE);
            llEthernet.setVisibility(View.VISIBLE);
            btConnect.setVisibility(View.VISIBLE);
            mPortParam.setPortType(PortParameters.ETHERNET);
        }
    }

//    protected void getUsbDevice() {
//        Intent intent = new Intent(PortConfigurationActivity.this,
//                UsbDeviceList.class);
//        startActivityForResult(intent, REQUEST_USB_DEVICE);
//    }

    public void getBluetoothDevice() {
        // Get local Bluetooth adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            messageBox("Bluetooth is not supported by the device");
        } else {
            // If BT is not on, request that it be enabled.
            // setupChat() will then be called during onActivityResult
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,
                        REQUEST_ENABLE_BT);
            } else {

                Log.e("getBT device","Success"+REQUEST_CONNECT_DEVICE);
                Intent intent = new Intent(PortConfigurationActivity.this,
                        BluetoothDeviceList.class);
                startActivityForResult(intent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
//    Log.d(DEBUG_TAG, "requestCode" + requestCode + "=>" + "resultCode"
//        + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // bluetooth is opened
                // select bluetooth device fome list
                Intent intent = new Intent(PortConfigurationActivity.this,
                        BluetoothDeviceList.class);
                startActivityForResult(intent,
                        REQUEST_CONNECT_DEVICE);
            } else {
                // bluetooth is not open
                Toast.makeText(this, "Bluetooth Enabled",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CONNECT_DEVICE) {
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras().getString(
                        EXTRA_DEVICE_ADDRESS);
                // fill in some parameters
                tvPortInfo.setVisibility(View.VISIBLE);
                tvPortInfo.setText(getString(R.string.bluetooth_address) + address);
                btConnect.setVisibility(View.VISIBLE);
                mPortParam.setBluetoothAddr(address);
            }
        }
//        else if (requestCode == REQUEST_USB_DEVICE) {
//            // When DeviceListActivity returns with a device to connect
//            if (resultCode == Activity.RESULT_OK) {
//                // Get the device MAC address
//                String address = data.getExtras().getString(
//                        EXTRA_DEVICE_ADDRESS);
//                // fill in some parameters
//                tvPortInfo.setVisibility(View.VISIBLE);
//                tvPortInfo.setText(getString(R.string.usb_address) + address);
//                btConnect.setVisibility(View.VISIBLE);
//                mPortParam.setUsbDeviceName(address);
//            }
//        }
    }

    public void okButtonClicked(View view) {
        String ipAddress = etIpAddress.getText().toString();
        String portNum = etPortNum.getText().toString();
        mPortParam.setIpAddr(ipAddress);
        mPortParam.setPortNumber(Integer.valueOf(portNum));
        Intent intent = new Intent(this, PrinterConnectDialog.class);
        Bundle bundle = new Bundle();
        bundle.putInt(PrinterPrintService.PORT_TYPE, mPortParam.getPortType());
        bundle.putString(PrinterPrintService.IP_ADDR, mPortParam.getIpAddr());
        bundle.putInt(PrinterPrintService.PORT_NUMBER, mPortParam.getPortNumber());
        bundle.putString(PrinterPrintService.BLUETOOT_ADDR, mPortParam.getBluetoothAddr());
        bundle.putString(PrinterPrintService.USB_DEVICE_NAME, mPortParam.getUsbDeviceName());
        intent.putExtras(bundle);
        this.setResult(Activity.RESULT_OK, intent);
        this.finish();
    }


}
