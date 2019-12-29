package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.SleeperClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PrintReportActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothConnection printerConnection;
    private ZebraPrinter printer;

    private int workOrderID;
    private int workOrderResultID;
    private boolean inWarranty;

    private BrokerSQLite db;
    private Route route;
    private List<Service> serviceList;
    private List<Material> materialList;
    private List<AddedFailuresAndCauses> failuresAndCausesList;
    private String employeeName;


    private int labelLength = 100;
    private String header = "! 0 200 200 " + labelLength + " 1\r\n";


    private TextView lblPrinterName;

    //Sifre Stampaca
    private ArrayList<String> listOfPrinterMacAddresses = new ArrayList<>(Arrays.asList(
            "AC:3F:A4:A1:F3:54",
            "AC:3F:A4:A1:F3:57",
            "AC:3F:A4:A1:F3:80",
            "AC:3F:A4:A1:F3:56",
            "AC:3F:A4:A2:74:D9",
            "AC:3F:A4:A1:CD:C9",
            "AC:3F:A4:A2:3B:03",
            "AC:3F:A4:A2:3B:53",
            "AC:3F:A4:A2:75:50",
            "AC:3F:A4:A1:F3:7F",
            "AC:3F:A4:A1:CE:E4"
    ));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_report);


        workOrderID = getIntent().getIntExtra("WorkOrderID", -1);
        workOrderResultID = getIntent().getIntExtra("WorkOrderResultID", -1);
        inWarranty = getIntent().getBooleanExtra("InWarranty", false);
        db = new BrokerSQLite(this);
        getDataForPrinting();
        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnPrint = findViewById(R.id.btnPrint);

        lblPrinterName = findViewById(R.id.lblPrinterName);


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FindBluetoothDevice();

                } catch (Exception ex) {
                    ErrorClass.handle(ex, PrintReportActivity.this);
                }
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//
                    if (bluetoothDevice == null) {
                        Utilities.showToast("Niste povezali štampač. Proverite Bluetooth i kliknite na poveži štamapč dugme", PrintReportActivity.this);
                        return;
                    }
                    new Thread(new Runnable() {
                        public void run() {
                            Looper.prepare();
                            doConnectionTest();
                            Looper.loop();
                            Looper.myLooper().quit();

                        }
                    }).start();

                } catch (Exception ex) {
                    ErrorClass.handle(ex, PrintReportActivity.this);
                    disconnect();
                }
            }
        });

    }


    public ZebraPrinter connect() {

        printerConnection = null;
        printerConnection = new BluetoothConnection(bluetoothDevice.getAddress());


        try {
            printerConnection.open();

        } catch (ConnectionException e) {

            Utilities.showToast("Niste upalili štampač. Ukoliko postoji problem sa štampanjem, napustite aplikaciju i restartujte tablet.", PrintReportActivity.this);
            SleeperClass.sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
            } catch (ConnectionException e) {
                printer = null;
                SleeperClass.sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                printer = null;
                SleeperClass.sleep(1000);
                disconnect();
            }
        }else{
            Utilities.showToast("Niste upalili štampač. Ukoliko postoji problem sa štampanjem, napustite aplikaciju i restartujte tablet.", PrintReportActivity.this);
        }

        return printer;
    }


    private void doConnectionTest() {
        printer = connect();

        if (printer != null) {
            if(sendTestLabel()){

                try {
                    db.open();
                    db.workOrderPrintInfo_I(workOrderID, workOrderResultID);
                    db.close();
                } catch (SQLException e) {
                    Utilities.writeErrorToFile(e);
                }
            }
        } else {
            disconnect();
        }
    }

    private boolean sendTestLabel() {
        boolean isOk = false;
        try {
            byte[] configLabel = getConfigLabel();
            //PRVO STAMANJE
            printerConnection.write(configLabel);
            SleeperClass.sleep(6000);
//            //DRUGO STAMAPANJE
            printerConnection.write(configLabel);
            SleeperClass.sleep(1500);
            isOk = true;
        } catch (ConnectionException e) {
            Utilities.writeErrorToFile(e);
        } finally {
            disconnect();
        }
        return isOk;
    }

    private byte[] getConfigLabel() {

        byte[] configLabel = null;

//            String cpclConfigLabel = "! 0 200 200 297 1\r\n" +
//                    "ON-FEED IGNORE\r\n" +
//                    "T 4 1 1 25 Veliki naslov aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\r\n" +
//
//                    "PRINT\r\n";
//
//            String s2 = "! 0 200 200 141 1\r\n" +
//                    "ON-FEED IGNORE\r\n" +
//                    "T 7 0 1 69 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaam\r\n" +
//                    "PRINT\r\n";
//            configLabel = s2.getBytes();
        StringBuilder sb = new StringBuilder();
        sb.append("! U1 SETBOLD 0");
        sb.append("Br. nal: ").append(Utilities.formatLabelLines(route.getWorkOrderCode(), 38));
        increaseLabelLength();
        sb.append("Kupac: ").append(Utilities.formatLabelLines(route.getCustomerName(), 40));
        increaseLabelLength();
        sb.append("Adresa: ").append(Utilities.formatLabelLines(route.getCustomerAddress() + " " + route.getCustomerAddressNo(), 39));
        increaseLabelLength();
        sb.append("Grad: ").append(Utilities.formatLabelLines(route.getCustomerCity(), 41));
        increaseLabelLength();
        sb.append("Proizvod: ").append(Utilities.formatLabelLines(route.getProductName(), 37));
        increaseLabelLength();
        sb.append("Opis:\r\n ");
        increaseLabelLength();
        sb.append(Utilities.formatLabelLinesForDesc(route.getWorkOrderDescription()));
        increaseLabelLength();

        if (!failuresAndCausesList.isEmpty()) {
            sb.append("! U1 SETBOLD 2");
            sb.append("Kvarovi:\r\n");
            increaseLabelLength();
            sb.append("! U1 SETBOLD 0");
            sb.append("! U1 SETLP 7 0 24");
            sb.append("Naziv\r\n");
            increaseLabelLength();
            for (AddedFailuresAndCauses failuresAndCauses : failuresAndCausesList) {
                sb.append(Utilities.formatLabelLinesForFailuresAndCauses(failuresAndCauses.getFailureCauseName().trim(), 47));
                increaseLabelLength();
            }
        }

        if (!route.getFailureCauseNote().isEmpty()) {
            sb.append("! U1 SETBOLD 2");
            sb.append("Kvar ostalo:\r\n");
            increaseLabelLength();
            sb.append("! U1 SETBOLD 0");
            sb.append("! U1 SETLP 7 0 24");
            sb.append(Utilities.formatLabelLinesForDesc(route.getFailureCauseNote()));
            increaseLabelLength();
        }


        double serviceTotal = 0;

        if (!serviceList.isEmpty()) {
            sb.append("! U1 SETBOLD 2");
            sb.append("Usluge:\r\n");
            increaseLabelLength();
            sb.append("! U1 SETBOLD 0");
            sb.append("! U1 SETLP 7 0 24");
            sb.append("Naziv                         Jed. Kol.  Cena  \r\n");
            double price = 0;
            for (Service service : serviceList) {
                if(!inWarranty) price = service.getPrice();
                serviceTotal = serviceTotal + price;
                sb.append(Utilities.formatLabelLinesForService(service.getServiceName().trim(), 29)).append(service.getUnitOfMeasure().trim()).append("  ").append(service.getUnitSpent()).append("  ").append(getResources().getString(R.string.price, price)).append("\r\n");
                increaseLabelLength();
            }
            sb.append("! U1 SETBOLD 2");
            sb.append("Usluge ukupno:").append(StringUtils.leftPad(getResources().getString(R.string.price,serviceTotal), 34, " "));

        }



        double materialTotal = 0;
        if (!materialList.isEmpty()) {
            sb.append("! U1 SETBOLD 2");
            sb.append("Materijali:\r\n");
            increaseLabelLength();
            sb.append("! U1 SETBOLD 0");
            sb.append("! U1 SETLP 7 0 24");
            sb.append("Naziv                          Jed. Kol. Cena  \r\n");
            double price = 0;

            for (Material material : materialList) {
                if(!inWarranty) price = material.getPrice();
                materialTotal = materialTotal + price * material.getQuantitySpent();
                sb.append(Utilities.formatLabelLinesForMaterial(material.getMaterialName().trim(), 30)).append(material.getUnitOfMeasure().trim().substring(0,3)).append("   ").append(material.getQuantitySpent()).append("   ").append(getResources().getString(R.string.price, price)).append("\r\n");
                increaseLabelLength();
            }
            sb.append("! U1 SETBOLD 2");
            sb.append("Materijali ukupno:").append(StringUtils.leftPad(getResources().getString(R.string.price,materialTotal), 30, " "));
        }


        sb.append("\r\n");
        sb.append(StringUtils.rightPad("_", 48, "_")).append("\r\n");
        sb.append("UKUPNO:").append(StringUtils.leftPad(getResources().getString(R.string.price,materialTotal+serviceTotal), 41, " "));
        sb.append(StringUtils.rightPad("_", 48, "_")).append("\r\n");
        sb.append("! U1 SETBOLD 0");
        increaseLabelLength();
        sb.append("Serviser: ").append(Utilities.formatLabelLines(employeeName, 30));
        increaseLabelLength();
        sb.append("Datum: ");
        String date = Utilities.getCurrentDateTime();
        sb.append(Utilities.changeDateTimeFormatToLocalFormat(date)).append("\r\n");
        increaseLabelLength();
        sb.append("\r\n");
        increaseLabelLength();
        sb.append("Korisnik: ").append(Utilities.formatLabelLines(route.getCustomerName(), 37));
        increaseLabelLength();
        sb.append("\r\n");
        increaseLabelLength();
        sb.append("\r\n");
        sb.append("\r\n");
        String signature = StringUtils.rightPad("Potpis:     ", 47, "_");
        sb.append(signature);
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\r\n");

        increaseLabelLength();

        String stringWithoutSerbChars = Utilities.removeSerbianChars(sb.toString());

        StringBuilder sbFinal = new StringBuilder();
        sbFinal.append(header);
        sbFinal.append("ON-FEED IGNORE\r\n");
        sbFinal.append(sb.toString());
        // sb.append("PRINT\r\n");
        configLabel = stringWithoutSerbChars.getBytes();
        return configLabel;
    }


    private void increaseLabelLength() {
        labelLength = labelLength + 22;
    }

    private void FindBluetoothDevice() {

        try {

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.enable();
            if (bluetoothAdapter == null) {
                lblPrinterName.setText("Uređaj ne podržava Bluetooth");
            }
            if (bluetoothAdapter.isEnabled()) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT, 0);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if (pairedDevice.size() > 0) {
                for (BluetoothDevice pairedDev : pairedDevice) {


                    if(listOfPrinterMacAddresses.contains(pairedDev.getAddress())){
//                    if (pairedDev.getName().equals("Zebra3")) {
                        bluetoothDevice = pairedDev;
                        lblPrinterName.setText("Bluetooth stampač povezan: " + bluetoothDevice.getName());
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }


    private void getDataForPrinting() {

        db.open();
        route = db.getDraftRoute(workOrderResultID, workOrderID, false);
        serviceList = db.getAddedServicesForPrinting(workOrderID, route.getProductID());
        materialList = db.getAddedMaterialsForPrinting(workOrderID);
        failuresAndCausesList = db.getAddedFailuresAndCausesForPrinting(workOrderID);
        employeeName = db.getEmployeeNameByID(MainActivity.employeeID);
        db.close();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (printerConnection != null && printerConnection.isConnected()) {
            disconnect();
        }
    }

    public void disconnect() {
        try {
            if (printerConnection != null) {
                printerConnection.close();
            }
        } catch (ConnectionException e) {
            Utilities.writeErrorToFile(e);
        }
    }

}
