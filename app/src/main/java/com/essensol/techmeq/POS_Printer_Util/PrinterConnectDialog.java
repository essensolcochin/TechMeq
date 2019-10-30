package com.essensol.techmeq.POS_Printer_Util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.essensol.techmeq.R;
import com.essensol.techmeq.UI.MainActivity;

import com.printer.aidl.PService;
import com.printer.command.PrinterCom;
import com.printer.io.PortParameters;
import com.printer.io.PrinterDevice;
import com.printer.save.PortParamDataBase;
import com.printer.service.PrinterPrintService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrinterConnectDialog extends AppCompatActivity {


    private final static String DEBUG_TAG = "SamleApp";
    private static final int INTENT_PORT_SETTINGS = 0;
    private ListViewAdapter mListViewAdapter = null;
    private List<Map<String, Object>> mList = null;
    private PortParameters mPortParam[] = new PortParameters[PrinterPrintService.MAX_PRINTER_CNT];
    private int mPrinterId = 0;
    private PService mPService;
    private PrinterServiceConnection conn = null;

    public class PrinterSeial {
        static final int GPIRNTER001 = 0;
        static final int GPIRNTER002 = 1;
        static final int GPIRNTER003 = 2;
        static final int GPIRNTER004 = 3;
    }



   class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.i(DEBUG_TAG, "onServiceDisconnected() called");
            mPService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPService = PService.Stub.asInterface(service);

            Log.e(DEBUG_TAG, "onServiceDisconnected() called"+mPService);
        }
    }


    private void connection() {
        conn = new PrinterServiceConnection();
        Log.i(DEBUG_TAG, "connection");
        Intent intent = new Intent(this, PrinterPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }


    private void initPortParam() {
        Intent intent = getIntent();
        boolean[] state = intent.getBooleanArrayExtra(MainActivity.CONNECT_STATUS);
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            PortParamDataBase database = new PortParamDataBase(this);
            mPortParam[i] = new PortParameters();
            mPortParam[i] = database.queryPortParamDataBase("" + i);
            mPortParam[i].setPortOpenState(state[i]);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_printer_connect_dialog);

        Log.e(DEBUG_TAG, "onCreate ");
        initPortParam();
        initView();
        registerBroadcast();
        connection();
    }




    private void initView() {
        ListView list = (ListView) findViewById(R.id.lvOperateList);
        mList = getOperateItemData();
        mListViewAdapter = new ListViewAdapter(this, mList, mHandler);
        list.setAdapter(mListViewAdapter);
        list.setOnItemClickListener(new TitelItemOnClickLisener());
        list.setOnItemLongClickListener(new TitelItemOnLongClickLisener());
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PrinterCom.ACTION_CONNECT_STATUS);
        this.registerReceiver(PrinterStatusBroadcastReceiver, filter);
    }

    private BroadcastReceiver PrinterStatusBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (PrinterCom.ACTION_CONNECT_STATUS.equals(intent.getAction())) {
                int type = intent.getIntExtra(PrinterPrintService.CONNECT_STATUS, 0);
                int id = intent.getIntExtra(PrinterPrintService.PRINTER_ID, 0);
                Log.d(DEBUG_TAG, "connect status " + type);
                if (type == PrinterDevice.STATE_CONNECTING) {
                    setProgressBarIndeterminateVisibility(true);
//                    SetLinkButtonEnable(ListViewAdapter.DISABLE);
                    mPortParam[id].setPortOpenState(false);
//                    Map<String, Object> map;
//                    map = mList.get(id);
//                    map.put(ListViewAdapter.STATUS, getString(R.string.connecting));
//                    mList.set(id, map);
//                    mListViewAdapter.notifyDataSetChanged();

                } else if (type == PrinterDevice.STATE_NONE) {
                    setProgressBarIndeterminateVisibility(false);
//                    SetLinkButtonEnable(ListViewAdapter.ENABLE);
                    mPortParam[id].setPortOpenState(false);
                    Map<String, Object> map;
                    map = mList.get(id);
                    map.put(ListViewAdapter.STATUS, getString(R.string.connect));
                    mList.set(id, map);
//                    mListViewAdapter.notifyDataSetChanged();
                } else if (type == PrinterDevice.STATE_VALID_PRINTER) {
                    setProgressBarIndeterminateVisibility(false);
//                    SetLinkButtonEnable(ListViewAdapter.ENABLE);
                    mPortParam[id].setPortOpenState(true);
                    Map<String, Object> map;
                    map = mList.get(id);
                    map.put(ListViewAdapter.STATUS, getString(R.string.cut));
                    mList.set(id, map);
                    mListViewAdapter.notifyDataSetChanged();
                } else if (type == PrinterDevice.STATE_INVALID_PRINTER) {
                    setProgressBarIndeterminateVisibility(false);
                    SetLinkButtonEnable(ListViewAdapter.ENABLE);
                    messageBox("Please use Gprinter!");
                }
            }
        }
    };

    private String getPortParamInfoString(PortParameters Param) {
        String info = new String();
        info = getString(R.string.port);
        int type = Param.getPortType();
        Log.d(DEBUG_TAG, "Param.getPortType() " + type);
        if (type == PortParameters.BLUETOOTH) {
            info += getString(R.string.bluetooth);
            info += "  " + getString(R.string.address);
            info += Param.getBluetoothAddr();

        } else {
            info = getString(R.string.init_port_info);
        }

        return info;
    }

    void SetPortParamToView(PortParameters Param) {
        Map<String, Object> map;
        map = mList.get(mPrinterId);
        String info = getPortParamInfoString(Param);
        map.put(ListViewAdapter.INFO, info);
        mList.set(mPrinterId, map);
        mListViewAdapter.notifyDataSetChanged();
    }

    void SetLinkButtonEnable(String s) {
        Map<String, Object> map;
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            map = mList.get(i);
            map.put(ListViewAdapter.BT_ENABLE, s);
            mList.set(i, map);
        }
        mListViewAdapter.notifyDataSetChanged();
    }

    private List<Map<String, Object>> getOperateItemData() {
        int[] PrinterID = new int[] { R.string.gprinter001, R.string.gprinter002, R.string.gprinter003,
                R.string.gprinter004, R.string.gprinter005, R.string.gprinter006, R.string.gprinter007,
                R.string.gprinter008, R.string.gprinter009, R.string.gprinter010, R.string.gprinter011,
                R.string.gprinter012, R.string.gprinter013, R.string.gprinter014, R.string.gprinter015,
                R.string.gprinter016, R.string.gprinter017, R.string.gprinter018, R.string.gprinter019,
                R.string.gprinter020 };
        int[] PrinterImage = new int[] { R.drawable.plus, R.drawable.plus, R.drawable.plus,
                R.drawable.plus, R.drawable.plus, R.drawable.plus, R.drawable.plus,
                R.drawable.plus, R.drawable.plus, R.drawable.plus, R.drawable.plus,
                R.drawable.plus, R.drawable.plus, R.drawable.plus, R.drawable.plus,
                R.drawable.plus, R.drawable.plus, R.drawable.plus, R.drawable.plus,
                R.drawable.plus };
        Map<String, Object> map;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            map = new HashMap<String, Object>();
            map.put(ListViewAdapter.IMG, PrinterImage[i]);
            map.put(ListViewAdapter.TITEL, getString(PrinterID[i]));
            if (mPortParam[i].getPortOpenState() == false)
                map.put(ListViewAdapter.STATUS, getString(R.string.connect));
            else
                map.put(ListViewAdapter.STATUS, getString(R.string.cut));
            String str = getPortParamInfoString(mPortParam[i]);
            map.put(ListViewAdapter.INFO, str);
            map.put(ListViewAdapter.BT_ENABLE, "enable");
            list.add(map);
        }
        return list;
    }

    class TitelItemOnLongClickLisener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            Log.d(DEBUG_TAG, "TitelItemOnLongClickLisener " + arg2);
            Intent intent = new Intent(PrinterPrintService.ACTION_PRINT_TESTPAGE);
            intent.putExtra(PrinterPrintService.PRINTER_ID, arg2);
            sendBroadcast(intent);
            return true;
        }
    }

    class TitelItemOnClickLisener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub]
            mPrinterId = arg2;
            Intent intent = new Intent(PrinterConnectDialog.this, PortConfigurationActivity.class);
            startActivityForResult(intent, INTENT_PORT_SETTINGS);
        }
    }

    void connectOrDisConnectToDevice(int PrinterId) {
        mPrinterId = PrinterId;
        int rel = 0;
        Log.e(DEBUG_TAG, String.valueOf(mPortParam[PrinterId].getPortOpenState()));
        if (mPortParam[PrinterId].getPortOpenState() == false) {
            if (CheckPortParamters(mPortParam[PrinterId])) {
                try {
                    mPService.closePort(mPrinterId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                switch (mPortParam[PrinterId].getPortType()) {
                    case PortParameters.USB:
                        try {

                            rel = mPService.openPort(PrinterId, mPortParam[PrinterId].getPortType(),
                                    mPortParam[PrinterId].getUsbDeviceName(), 0);
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case PortParameters.ETHERNET:
                        try {
                            rel = mPService.openPort(PrinterId, mPortParam[PrinterId].getPortType(),
                                    mPortParam[PrinterId].getIpAddr(), mPortParam[PrinterId].getPortNumber());
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case PortParameters.BLUETOOTH:
                        try {
                            rel = mPService.openPort(PrinterId, mPortParam[PrinterId].getPortType(),
                                    mPortParam[PrinterId].getBluetoothAddr(), 0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rel];
                Log.e(DEBUG_TAG, "result :" + String.valueOf(r));
                if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                    if (r == PrinterCom.ERROR_CODE.DEVICE_ALREADY_OPEN) {
                        mPortParam[PrinterId].setPortOpenState(true);
                        Map<String, Object> map = mList.get(PrinterId);
                        map.put(ListViewAdapter.STATUS, getString(R.string.cut));
                        mList.set(PrinterId, map);
                        mListViewAdapter.notifyDataSetChanged();
                    } else {
                        messageBox(PrinterCom.getErrorText(r));
                    }
                }
            } else {
                messageBox("Wrong Port Params ");
            }
        } else {
            Log.d(DEBUG_TAG, "DisconnectToDevice ");
            setProgressBarIndeterminateVisibility(true);
            SetLinkButtonEnable(ListViewAdapter.DISABLE);
            Map<String, Object> map = mList.get(PrinterId);
            map.put(ListViewAdapter.STATUS, getString(R.string.cutting));
            mList.set(PrinterId, map);
            mListViewAdapter.notifyDataSetChanged();
            try {
                mPService.closePort(PrinterId);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case ListViewAdapter.MESSAGE_CONNECT:
                    connectOrDisConnectToDevice(message.arg1);
            }
            return false;
        }
    });

    Boolean CheckPortParamters(PortParameters param) {
        boolean rel = false;
        int type = param.getPortType();
        if (type == PortParameters.BLUETOOTH) {
            if (!param.getBluetoothAddr().equals("")) {
                rel = true;
            }
        } else if (type == PortParameters.ETHERNET) {
            if ((!param.getIpAddr().equals("")) && (param.getPortNumber() != 0)) {
                rel = true;
            }
        } else if (type == PortParameters.USB) {
            if (!param.getUsbDeviceName().equals("")) {
                rel = true;
            }
        }
        return rel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.d(DEBUG_TAG, "requestCode" + requestCode + '\n' + "resultCode" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PORT_SETTINGS) {
            // getIP settings info from IP settings dialog
            if (resultCode == RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle = data.getExtras();
                Log.d(DEBUG_TAG, "PrinterId " + mPrinterId);
                int param = bundle.getInt(PrinterPrintService.PORT_TYPE);
                mPortParam[mPrinterId].setPortType(param);
                Log.d(DEBUG_TAG, "PortType " + param);
                String str = bundle.getString(PrinterPrintService.IP_ADDR);
                mPortParam[mPrinterId].setIpAddr(str);
                Log.d(DEBUG_TAG, "IP addr " + str);
                param = bundle.getInt(PrinterPrintService.PORT_NUMBER);
                mPortParam[mPrinterId].setPortNumber(param);
                Log.d(DEBUG_TAG, "PortNumber " + param);
                str = bundle.getString(PrinterPrintService.BLUETOOT_ADDR);
                mPortParam[mPrinterId].setBluetoothAddr(str);
                Log.e(DEBUG_TAG, "BluetoothAddr " + str);
                str = bundle.getString(PrinterPrintService.USB_DEVICE_NAME);
                mPortParam[mPrinterId].setUsbDeviceName(str);
                Log.d(DEBUG_TAG, "USBDeviceName " + str);
//                SetPortParamToView(mPortParam[mPrinterId]);
                if (CheckPortParamters(mPortParam[mPrinterId])) {
                    PortParamDataBase database = new PortParamDataBase(this);
                    database.deleteDataBase("" + mPrinterId);
                    database.insertPortParam(mPrinterId, mPortParam[mPrinterId]);
                } else {
                    messageBox("Wrong Port Params ");
                }

            } else {
                messageBox("Port Not Saved");
            }
        }
    }

    private void messageBox(String err) {
        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
    }



}
