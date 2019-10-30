package com.essensol.techmeq.UI;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.essensol.techmeq.Adapters.ProductsAdapter;
import com.essensol.techmeq.DialogFragments.AddCategoryFragment;
import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Adapters.HomeTabAdapter_;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.CategoryModel;
import com.essensol.techmeq.Model.CustomerSpinnerModel;
import com.essensol.techmeq.Model.PurchaseModel;

import com.essensol.techmeq.POS_Printer_Util.PrinterConnectDialog;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.FinancialYear_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sale_Item_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import com.printer.aidl.PService;
import com.printer.command.EscCommand;
import com.printer.command.LabelCommand;
import com.printer.command.PrinterCom;
import com.printer.command.PrinterUtils;
import com.printer.io.PrinterDevice;
import com.printer.service.PrinterPrintService;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;



public class MainActivity extends Toolbar implements _AddProductDetailsDailog.OnCompleteListener  {

    public static final String CONNECT_STATUS = "connect.status";

    private static final int INTENT_PORT_SETTINGS = 0;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    ProductsAdapter adapter;
    List<CategoryModel> items = new ArrayList<>();
//    RecyclerView purchase;
//    Realm mRealm;
    private ProductViewModel model;

    LinearLayout add;

    ArrayAdapter<CustomerSpinnerModel> CustomerAdapter;

    List<CustomerSpinnerModel>customerSpinnerList=new ArrayList<>();



    GridLayoutManager layoutManager;
    RecyclerView products,purchase;

    PurchaseListAdapter purchaseListAdapter;
//    ArrayList<String> items = new ArrayList<>();

    CardView bottom;


    LinearLayout pay,credit;

    private  static int FinYearId;

    private static   int SaleId;

    TextView tot,vat,taxable;

     ArrayList<PurchaseModel> puchase = new ArrayList<>();
    List<SalesItem> addSaleList =new ArrayList<>();


    int CustId;

    HomeTabAdapter_ adapter_;
    String mName,mQty,mPrice;

    private  ProgressDialog dialog;

    private SearchableSpinner CustomerName;


    /**
     * Printer Classes
     *
     */
    private PService mPService = null;
    private PrinterServiceConnection conn = null;
    private int mPrinterIndex = 0;
    private int mTotalCopies = 0;
    private static final int MAIN_QUERY_PRINTER_STATUS = 0xfe;
    private static final int REQUEST_PRINT_LABEL = 0xfd;
    private static final int REQUEST_PRINT_RECEIPT = 0xfc;

    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ServiceConnection", "onServiceDisconnected() called");
            mPService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPService = PService.Stub.asInterface(service);
            Log.e("ServiceConnection", "onServiceDisconnected() called"+mPService);
        }
    }






    /**
     *
     * Getting Bluetooth Devices
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("TAG", action);
            // PrinterCom.ACTION_DEVICE_REAL_STATUS 为广播的IntentFilter
            if (action.equals(PrinterCom.ACTION_DEVICE_REAL_STATUS)) {

                int requestCode = intent.getIntExtra(PrinterCom.EXTRA_PRINTER_REQUEST_CODE, -1);

                if (requestCode == MAIN_QUERY_PRINTER_STATUS) {

                    int status = intent.getIntExtra(PrinterCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    String str;
                    if (status == PrinterCom.STATE_NO_ERR) {
                        str = "打印机正常";
                    } else {
                        str = "打印机 ";
                        if ((byte) (status & PrinterCom.STATE_OFFLINE) > 0) {
                            str += "脱机";
                        }
                        if ((byte) (status & PrinterCom.STATE_PAPER_ERR) > 0) {
                            str += "缺纸";
                        }
                        if ((byte) (status & PrinterCom.STATE_COVER_OPEN) > 0) {
                            str += "打印机开盖";
                        }
                        if ((byte) (status & PrinterCom.STATE_ERR_OCCURS) > 0) {
                            str += "打印机出错";
                        }
                        if ((byte) (status & PrinterCom.STATE_TIMES_OUT) > 0) {
                            str += "查询超时";
                        }
                    }

                    Toast.makeText(getApplicationContext(), "打印机：" + mPrinterIndex + " 状态：" + str, Toast.LENGTH_SHORT)
                            .show();
                } else if (requestCode == REQUEST_PRINT_LABEL) {
                    int status = intent.getIntExtra(PrinterCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    if (status == PrinterCom.STATE_NO_ERR) {
                        sendLabel();
                    } else {
                        Toast.makeText(MainActivity.this, "query printer status error", Toast.LENGTH_SHORT).show();
                    }
                } else if (requestCode == REQUEST_PRINT_RECEIPT) {
                    int status = intent.getIntExtra(PrinterCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    if (status == PrinterCom.STATE_NO_ERR) {
                        sendReceipt();
                    } else {
                        Toast.makeText(MainActivity.this, "query printer status error", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (action.equals(PrinterCom.ACTION_RECEIPT_RESPONSE)) {
                if (--mTotalCopies > 0) {
                    sendReceiptWithResponse();
                }
            } else if (action.equals(PrinterCom.ACTION_LABEL_RESPONSE)) {
                byte[] data = intent.getByteArrayExtra(PrinterCom.EXTRA_PRINTER_LABEL_RESPONSE);
                int cnt = intent.getIntExtra(PrinterCom.EXTRA_PRINTER_LABEL_RESPONSE_CNT, 1);
                String d = new String(data, 0, cnt);


                Log.d("LABEL RESPONSE", d);

                if (--mTotalCopies > 0 && d.charAt(1) == 0x00) {
                    sendLabelWithResponse();
                }
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        purchase =  findViewById(R.id.purchase);


        CustomerName= findViewById(R.id.CustomerName);


        bottom =findViewById(R.id.frame);

        pay =findViewById(R.id.pay);

        vat=findViewById(R.id.vat);

        tot=findViewById(R.id.tot);

        taxable=findViewById(R.id.taxable);
        credit=findViewById(R.id.credit);
        connection();


        registerReceiver(mBroadcastReceiver, new IntentFilter(PrinterCom.ACTION_DEVICE_REAL_STATUS));

        registerReceiver(mBroadcastReceiver, new IntentFilter(PrinterCom.ACTION_RECEIPT_RESPONSE));

        registerReceiver(mBroadcastReceiver, new IntentFilter(PrinterCom.ACTION_LABEL_RESPONSE));

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPortDialogueClicked();

                printReceiptClicked(v);

//                Intent intent = new Intent(MainActivity.this, PortConfigurationActivity.class);
//                startActivityForResult(intent, INTENT_PORT_SETTINGS);
            }
        });



////from tab
        products = findViewById(R.id.products);

        add = findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm =getSupportFragmentManager();


                final AddCategoryFragment dialog= new AddCategoryFragment();

                dialog.show(fm,"TAG");
            }
        });

        layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.number_of_grid_items));
        products.setLayoutManager(layoutManager);
        products.setItemViewCacheSize(6);
        products.setDrawingCacheEnabled(true);
        products.setHasFixedSize(true);
        products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


//

        adapter = new ProductsAdapter(items,this);

        products.setAdapter(adapter);




        model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {
                if(sales_categories !=null) {
                    items.clear();
                    for (int i=0;i<sales_categories.size();i++)
                    {
                        if(sales_categories.get(i).isStatus())
                        {

                            CategoryModel item =new CategoryModel(sales_categories.get(i).getProductCatId(),
                                    sales_categories.get(i).getProductCategory(),
                                    sales_categories.get(i).getImage(),
                                    sales_categories.get(i).isStatus());

                            items.add(item);
                        }
                    }


                    adapter.SetProducts(items);
                }

            }
        });

        model.GetCustNameAndId().observe(this, new Observer<List<CustomerSpinnerModel>>() {
            @Override
            public void onChanged(@Nullable List<CustomerSpinnerModel> customerSpinnerModels) {

                Log.e("Size"," "+customerSpinnerModels.size());
                customerSpinnerList =customerSpinnerModels;

                CustomerAdapter = new ArrayAdapter<CustomerSpinnerModel>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,customerSpinnerList);
                CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CustomerName.setAdapter(CustomerAdapter);


            }
        });



        CustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustId =customerSpinnerList.get(position).getCustId();
                Log.e("CustId Iddddd"," "+CustId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//////////////////

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        int devicewidth = displaymetrics.widthPixels/2;

        int deviceheight = displaymetrics.heightPixels /4;




        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(puchase.size()>0)
                {
                    AddSale();
                }

            }
        });


        //Registering Broadcast receiver






        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        purchase.setLayoutManager(linearLayoutManager);


//        new GetCustIdAsync(OfflineDb.getInstance(this).customer_dao()).execute();


    }






    private  void AddSale() {


        dialog =new ProgressDialog(this);
        dialog.setTitle("Adding Sale Record.");
        dialog.setMessage("Saving....");
        dialog.setCancelable(false);
        dialog.show();


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        new GetFinancialAsync(OfflineDb.getInstance(this).financialYear_dao()).execute();

        SalesHeader header =new SalesHeader(1,FinYearId,Integer.toString(SaleId+1),c,CustId
                ,Double.parseDouble(tot.getText().toString())
                ,Double.parseDouble(vat.getText().toString()),0,Double.parseDouble(tot.getText().toString()),0);

        new  AddSalesHeaderAsync(OfflineDb.getInstance(this).sales_header_dao()).execute(header);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                AddSaleItem();

            }
        }, 1000);


    }

    private  void AddSaleItem() {

        for (int i=0;i<puchase.size();i++)
        {

//            SaleId = saleId;
//            ProductId = productId;
//            Qty = qty;
//            Price = price;
//            Total = total;
//            TaxPercent = taxPercent;
//            TaxAmt = taxAmt;
//            LineTotal = lineTotal;



            PurchaseModel items =puchase.get(i);

            Log.e("Mainnnn"," "+items.getProductId());

            SalesItem model=new SalesItem(SaleId,items.getProductId(),items.getQty(),items.getRate(),Double.parseDouble(tot.getText().toString())
                                            ,5.0,Double.parseDouble(vat.getText().toString()),items.getLinetot());

            addSaleList.add(model);

        }

        try {
            String result = new AddSaleItemAsync(OfflineDb.getInstance(this).sale_item_dao()).execute(addSaleList).get();

            if(result.equalsIgnoreCase("Completed"))
            {
                dialog.dismiss();
//                Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT).show();
//                puchase.clear();
//                purchaseListAdapter.notifyDataSetChanged();
//                taxable.setText("");
//                vat.setText("");
//                tot.setText("");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getProductListItem(int Qty, int Product_Id, int ProductCatId, double TaxPercent, String ProductName, double Sales_Price, double rate) {


        double lineTot=((Sales_Price/100.0f)*5)+Qty;

       PurchaseModel model =new PurchaseModel(ProductName,Product_Id,Qty,rate,rate,lineTot);

       puchase.add(model);

        purchaseListAdapter = new PurchaseListAdapter(puchase, this);

        purchase.setAdapter(purchaseListAdapter);
        purchaseListAdapter.notifyDataSetChanged();





        double  total = 0;
        double netAmnt = 0;
        double  percentage=0;
        double  _taxable =0;

        for(int i=0;i<puchase.size();i++)
        {

            netAmnt=puchase.get(i).getNetAmount();

            _taxable =_taxable+netAmnt;

            taxable.setText(Double.toString(_taxable));

            percentage = percentage+(netAmnt/100.0f)*5 ;

            vat.setText(String.valueOf(percentage));

            Log.e("percentage()","percentage "+percentage);



            total = total+ puchase.get(i).getNetAmount()+percentage;
            tot.setText(Double.toString(total));


        }


    }


    private  static class AddSalesHeaderAsync extends AsyncTask<SalesHeader,Void,Void> {

        private Sales_Header_DAO header_dao;


        public AddSalesHeaderAsync(Sales_Header_DAO header_dao) {
            this.header_dao = header_dao;
        }

        @Override
        protected Void doInBackground(SalesHeader... salesHeaders) {


//            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//            String formattedDate = df.format(c);



            header_dao.AddSalesHeader(salesHeaders[0]);

            SaleId = header_dao.getId();

            Log.e("LastAdded","Id _--> "+SaleId );







            return null;
        }
    }


    private  static class AddSaleItemAsync extends AsyncTask<List<SalesItem>,Void,String> {

        private Sale_Item_DAO item_dao;

        public AddSaleItemAsync(Sale_Item_DAO Saleitemdao) {
            this.item_dao = Saleitemdao;
        }

        @Override
        protected String doInBackground(List<SalesItem>... items) {




           item_dao.AddSalesItem(items[0]);



//            Log.e("GetAllSales","Id _--> "+item_dao.GetAllSales().size() );







            String result="Completed";

            return result;
        }
    }



    private  static  class GetFinancialAsync extends AsyncTask<Void,Void,Void> {

        private FinancialYear_DAO dao;



        public GetFinancialAsync(FinancialYear_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            FinYearId=dao.GetMaxFinId();

            Log.e("GetFinancialAsync","Id --> "+FinYearId);

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            //do stuff

        }

    }







    /**
     * Device Click Listeners
     *
     */

    private void connection() {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(this, PrinterPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }

    public boolean[] getConnectState() {
        boolean[] state = new boolean[PrinterPrintService.MAX_PRINTER_CNT];
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            state[i] = false;
        }
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            try {
                if (mPService.getPrinterConnectStatus(i) == PrinterDevice.STATE_CONNECTED) {
                    state[i] = true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return state;
    }

    public void openPortDialogueClicked() {

        Log.e("ServiceConnection", "openPortDialogueClicked() called"+mPService);

        if (mPService == null) {
            Toast.makeText(this, "Print Service is not start, please check it", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("Test", "openPortConfigurationDialog ");
        Intent intent = new Intent(this, PrinterConnectDialog.class);
        boolean[] state = getConnectState();
        intent.putExtra(CONNECT_STATUS, state);
        this.startActivity(intent);
    }

    public void printTestPageClicked(View view) {
        try {
            int rel = mPService.printeTestPage(mPrinterIndex); //
            Log.i("ServiceConnection", "rel " + rel);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rel];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    public void getPrinterStatusClicked(View view) {
        try {
            mTotalCopies = 0;
            mPService.queryPrinterStatus(mPrinterIndex, 500, MAIN_QUERY_PRINTER_STATUS);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void getPrinterCommandTypeClicked(View view) {
        try {
            int type = mPService.getPrinterCommandType(mPrinterIndex);
            if (type == PrinterCom.ESC_COMMAND) {
                Toast.makeText(getApplicationContext(), "打印机使用ESC命令", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "打印机使用TSC命令", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void printArabicReceiptClicked(View view) {
        EscCommand esc = new EscCommand();
        // init printer
        esc.addInitializePrinter();
        // cancel Kanji
        esc.addCancelKanjiMode();
        // set paper width
        PrinterUtils.setPaperWidth(PrinterUtils.PAPER_58_WIDTH);
        // select codepage which is arabic
        esc.addSelectCodePage(EscCommand.CODEPAGE.ARABIC);
        esc.addArabicText("الهاتف المحمول معطوب و يحتاج إلى إصلاح");
        esc.addPrintAndFeedLines((byte) 5);
        Vector<Byte> datas = esc.getCommand(); // send data
        byte[] bytes = PrinterUtils.ByteTo_byte(datas);
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rs;
        try {
            rs = mPService.sendEscCommand(mPrinterIndex, sss);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rs];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void sendReceipt() {

        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        esc.addPrintAndFeedLines((byte) 3);
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);// 设置为倍高倍宽
        esc.addText("***** TecMeq ******\n");
        esc.addTurnUnderlineModeOnOrOff(EscCommand.UNDERLINE_MODE.UNDERLINE_1DOT);
//        esc.addSetKanjiUnderLine(EscCommand.UNDERLINE_MODE.UNDERLINE_1DOT);
        esc.addPrintAndLineFeed();




        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addText("Print text\n");
//        esc.addText("Welcome to use  printer!\n");
//
//
//        esc.addPrintAndLineFeed();

//
//        esc.addText("Printer");
//        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//        esc.addSetAbsolutePrintPosition((short) 6);
//        esc.addText("Printer");
//        esc.addSetAbsolutePrintPosition((short) 10);
//        esc.addText("Printer");
//        esc.addPrintAndLineFeed();

        esc.addText("Product     ");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short) 6);
        esc.addText("Quantity     ");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);
        esc.addText("Rate\n");
        esc.addPrintAndLineFeed();


        for (int i=0;i<puchase.size();i++)
        {

            esc.addText(puchase.get(i).getName()+"     ");
            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
            esc.addSetAbsolutePrintPosition((short) 6);
            esc.addText(puchase.get(i).getQty()+"     ");
            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
            esc.addSetAbsolutePrintPosition((short)25);
            esc.addText(puchase.get(i).getRate()+"\n");
            esc.addPrintAndLineFeed();

//            esc.addText(puchase.get(i).getName());
//            esc.addSetAbsolutePrintPosition((short) 10);
//            esc.addText(Integer.toString(puchase.get(i).getQty()));
//            esc.addSetAbsolutePrintPosition((short) 10);
//            esc.addText(Double.toString(puchase.get(i).getRate())+"\n");
//            esc.addSetAbsolutePrintPosition((short) 10);
        }

        esc.addText("-----------------------------");
        esc.addPrintAndLineFeed();

        esc.addText("SubTotal       :");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);


        esc.addText(taxable.getText().toString()+"\n");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);

        esc.addText("Tax            :");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);


        esc.addText(vat.getText().toString()+"\n");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);

        esc.addText("-----------------------------");
        esc.addPrintAndLineFeed();

        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);// 设置为倍高倍宽


        esc.addText("Grand Total :");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);


        esc.addText(tot.getText().toString()+"\n");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short)25);

//        /* 打印图片 */
//        esc.addText("Print bitmap!\n"); // 打印文字
////        Bitmap b = BitmapFactory.decodeResource(getResources(), R.raw.hani);
////        esc.addRastBitImage(b, 384, 0); // 打印图片
//
//        /* 打印一维条码 */
//        esc.addText("Print code128\n"); // 打印文字
//        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);//
//        // 设置条码可识别字符位置在条码下方
//        esc.addSetBarcodeHeight((byte) 60); // 设置条码高度为60点
//        esc.addSetBarcodeWidth((byte) 1); // 设置条码单元宽度为1
//        esc.addCODE128(esc.genCodeB("Printer")); // 打印Code128码
//        esc.addPrintAndLineFeed();

//        /*
//         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
//         */
//        esc.addText("Print QRcode\n"); // 打印文字
//        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31); // 设置纠错等级
//        esc.addSelectSizeOfModuleForQRCode((byte) 3);// 设置qrcode模块大小
//        esc.addStoreQRCodeData("www.printer.cc");// 设置qrcode内容
//        esc.addPrintQRCode();// 打印QRCode
//        esc.addPrintAndLineFeed();

        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);// 取消倍高倍宽

        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        esc.addText("--- Thank you for shopping ---\r\n");

        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addPrintAndFeedLines((byte) 8);

        Vector<Byte> datas = esc.getCommand();
        byte[] bytes = PrinterUtils.ByteTo_byte(datas);
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rs;
        try {
            rs = mPService.sendEscCommand(mPrinterIndex, sss);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rs];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void sendReceiptWithResponse() {
        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        esc.addPrintAndFeedLines((byte) 3);
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);// 设置为倍高倍宽
        esc.addText("Sample\n"); // 打印文字
        esc.addPrintAndLineFeed();

        /* 打印文字 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addText("Print text\n"); // 打印文字
        esc.addText("Welcome to use printer!\n"); // 打印文字

        /* 打印繁体中文 需要打印机支持繁体字库 */
        String message = "票據打印機\n";
        // esc.addText(message,"BIG5");
        esc.addText(message, "GB2312");
        esc.addPrintAndLineFeed();

        /* 绝对位置 具体详细信息请查看GP58编程手册 */
        esc.addText("Printer");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short) 6);
        esc.addText("Printer");
        esc.addSetAbsolutePrintPosition((short) 10);
        esc.addText("Printer");
        esc.addPrintAndLineFeed();

        /* 打印图片 */
        // esc.addText("Print bitmap!\n"); // 打印文字
        // Bitmap b = BitmapFactory.decodeResource(getResources(),
        // R.drawable.gprinter);
        // esc.addRastBitImage(b, 384, 0); // 打印图片

        /* 打印一维条码 */
        esc.addText("Print code128\n"); // 打印文字
        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);//

        esc.addSetBarcodeHeight((byte) 60); // 设置条码高度为60点
        esc.addSetBarcodeWidth((byte) 1); // 设置条码单元宽度为1
        esc.addCODE128(esc.genCodeB("Printer")); // 打印Code128码
        esc.addPrintAndLineFeed();


        esc.addText("Print QRcode\n"); // 打印文字
        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31); // 设置纠错等级
        esc.addSelectSizeOfModuleForQRCode((byte) 3);// 设置qrcode模块大小
        esc.addStoreQRCodeData("www.Printer.cc");// 设置qrcode内容
        esc.addPrintQRCode();// 打印QRCode
        esc.addPrintAndLineFeed();

        /* 打印文字 */
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印左对齐
        esc.addText("Completed!\r\n"); // 打印结束
        // 开钱箱
        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addPrintAndFeedLines((byte) 8);

        // 加入查询打印机状态，打印完成后，此时会接收到PrinterCom.ACTION_DEVICE_STATUS广播
        esc.addQueryPrinterStatus();

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = PrinterUtils.ByteTo_byte(datas);
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rs;
        try {
            rs = mPService.sendEscCommand(mPrinterIndex, sss);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rs];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void sendLabel() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(60, 60);
        tsc.addGap(0);
        tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);
        tsc.addReference(0, 0);
        tsc.addTear(EscCommand.ENABLE.ON);
        tsc.addCls();

        tsc.addText(20, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "Welcome to use printer!");

//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.raw.hani);
//        tsc.addBitmap(20, 50, LabelCommand.BITMAP_MODE.OVERWRITE, b.getWidth(), b);

        tsc.addQRCode(250, 80, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, " www.Printer.cc");
        // 绘制一维条码
        tsc.add1DBarcode(20, 250, LabelCommand.BARCODETYPE.CODE128, 100, LabelCommand.READABEL.EANBEL, LabelCommand.ROTATION.ROTATION_0, "SMARNET");
        tsc.addPrint(1, 1); // 打印标签
        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);
        Vector<Byte> datas = tsc.getCommand(); // 发送数据
        byte[] bytes = PrinterUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mPService.sendLabelCommand(mPrinterIndex, str);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rel];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void sendLabelWithResponse() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(60, 60); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(0); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        tsc.addReference(0, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区
        // 绘制简体中文
        tsc.addText(20, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "Welcome to use printer!");
        // 绘制图片
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.raw.hani);
//        tsc.addBitmap(20, 50, LabelCommand.BITMAP_MODE.OVERWRITE, b.getWidth(), b);

        tsc.addQRCode(250, 80, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, " www.Printer.cc");
        // 绘制一维条码
        tsc.add1DBarcode(20, 250, LabelCommand.BARCODETYPE.CODE128, 100, LabelCommand.READABEL.EANBEL, LabelCommand.ROTATION.ROTATION_0, "SMARNET");
        tsc.addPrint(1, 1); // 打印标签
        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);
        // 开启带Response的打印，用于连续打印
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);

        Vector<Byte> datas = tsc.getCommand(); // 发送数据
        byte[] bytes = PrinterUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mPService.sendLabelCommand(mPrinterIndex, str);
            PrinterCom.ERROR_CODE r = PrinterCom.ERROR_CODE.values()[rel];
            if (r != PrinterCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), PrinterCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printReceiptClicked(View view) {
        try {
            int type = mPService.getPrinterCommandType(mPrinterIndex);
            if (type == PrinterCom.ESC_COMMAND) {
                mPService.queryPrinterStatus(mPrinterIndex, 1000, REQUEST_PRINT_RECEIPT);
            } else {
                Toast.makeText(this, "Printer is not receipt mode", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    public void printLabelClicked(View view) {
        try {
            int type = mPService.getPrinterCommandType(mPrinterIndex);
            if (type == PrinterCom.LABEL_COMMAND) {
                mPService.queryPrinterStatus(mPrinterIndex, 1000, REQUEST_PRINT_LABEL);
            } else {
                Toast.makeText(this, "Printer is not label mode", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

//    public void printTestClicked(View view) {
//        try {
//            int type = mPService.getPrinterCommandType(mPrinterIndex);
//            if (type == PrinterCom.ESC_COMMAND) {
//                EditText etCopies = (EditText) findViewById(R.id.etPrintCopies);
//                String str = etCopies.getText().toString();
//                int copies = 0;
//                if (!str.equals("")) {
//                    copies = Integer.parseInt(str);
//                    mTotalCopies = copies;
//                }
//                sendReceiptWithResponse();
//
//            } else if (type == PrinterCom.LABEL_COMMAND) {
//                EditText etCopies = (EditText) findViewById(R.id.etPrintCopies);
//                String str = etCopies.getText().toString();
//                int copies = 0;
//                if (!str.equals("")) {
//                    copies = Integer.parseInt(str);
//                    mTotalCopies = copies;
//                }
//                sendLabelWithResponse();
//            } else {
//                Toast.makeText(this, "Printer is not receipt mode", Toast.LENGTH_SHORT).show();
//            }
//        } catch (RemoteException e1) {
//            e1.printStackTrace();
//        }
//    }

    public void customerDisplayerClicked(View view) {
//        Intent intent = new Intent(this, CustomerDiaplayActivity.class);
//        startActivity(intent);
    }


}

