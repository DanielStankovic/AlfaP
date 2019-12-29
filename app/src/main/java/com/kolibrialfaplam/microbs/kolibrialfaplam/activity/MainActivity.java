package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;





import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kolibrialfaplam.microbs.kolibrialfaplam.BuildConfig;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.ApiClient;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.DialogBuilder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.SendMail;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NetworkClass.increaseDialogProgressInterface {

    private ImageView sendErrorImg;
    private TextView appVersionTv;
    private EditText userNameEt;
    public static String ERROR_PATH = "";
    public static String ERROR_FILE = "errorLog.txt";

    public static int employeeID = 0;
    public static String deviceNo = "";

    /**
     * SYNC PROCES
     *
     * MainActivity ime
     * Model klasa (serialized names)
     * Network klasa syncMetoda
     * getModifiedDate(imeTabele)
     * Call -> kreiranje metode za poziv na server (ime i body tag)
     * Odgovor, provera dogovora.
     * pravljenje tabele
     * Brisanje, provera isActive, insert
     *
     */

    public static Integer nivoSinhronizacije = 0;
    public final static String[] imeNivoaSinhronizacije = {
            "Korisnik",
            "Prijava korisnika",
            "Provera verzije",
            "Brendovi artikla",
            "Kategorije artikla",
            "Artikli",
            "Kvarovi",
            "Uzroci kvarova",
            "Usluge",
            "Norme usluga",
            "Cenovnici",
            "Partneri",
            "Regioni",
            "Mesta po regionima",
            "Materijali",
            "Magacini",
            "Tipovi prioriteta",
            "Stanje magacina",
            "Komentari za CheckOut",
            "Radni nalozi",
            "Tipovi radnih naloga",
            "Tipovi slika",
            "Start-Stop status",
            "Tip Pitanja",
            "Pitanja",
            "Potpis - komentari",
            "Artikli-Materijal veza",
            "Artikli-Usluge veza",
            "Artikli-Kvarovi veza",
            "Dodate stavke za otvoreni radni nalog",
            "Boja materijala proizvoda",
            "Uspešno logovanje"};

    public static Integer nivoSlanja = 0;
    public final static String[] sendError = {
            "Slanje CheckIn-a",
            "Slanje Start/Stop statusa",
            "Slanje slika potpisa",
            "Slanje slika po radnom nalogu",
            "Slanje izveštaja urađenih radnih naloga"
    };

    private String unsentData = "";


    //NOTE: password field uses TextInputEditText because of the show/hide password button in the field
    private TextInputEditText passwordEt;
    private Button loginBtn;


    private AlertDialog progressDialog;
    private ProgressBar progressBar;
    private TextView progressBarTv;

    private TelephonyManager telephonyManager;
    private String IMEI = "";

    public int progress = 0;


    public static int newVersionCode = 0;
    public static int versionCode = BuildConfig.VERSION_CODE;
    private String versionName = BuildConfig.VERSION_NAME;

    public static String newVersionName = "";
    public static String newVersionDownloadLink = "";

    BrokerSQLite db = new BrokerSQLite(MainActivity.this);
    NetworkClass nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getErrorLogFile();

        //Ovde je moguce proveriti sve dozvole RUNTIME metodom. Za sada se proverava samo dozvola za dobijanje IMEIa.
        // To se radi preko metode getPhoneIMEI. Za ostale nema potrebe, ako treba otkomentarisace se ovaj deo.
        //verifyPermissions();

        getPhoneIMEI();

        //views initialization
        init();
    }

    private void init() {

        //Buttons
        sendErrorImg = findViewById(R.id.sendErrorImg);
        loginBtn = findViewById(R.id.loginBtn);

        //TextViews
        appVersionTv = findViewById(R.id.appVersionTv);

        //EditTexts
        userNameEt = findViewById(R.id.userNameEtLogin);
        passwordEt = findViewById(R.id.passwordEtLogin);


        //onClickListener assignment
        sendErrorImg.setOnClickListener(this);
        loginBtn.setOnClickListener(this);


        //Setting version name, username and pass if the address is using local MBS address
        if (ApiClient.SERVICE_ADDRESS.equalsIgnoreCase("http://89.216.113.44:8222/")) {
            versionName = versionName + " - LOCAL";
            userNameEt.setText("dace");
            passwordEt.setText("dace");
        }
        appVersionTv.setText(getResources().getString(R.string.version, versionName));


        //finrst initialisaion of db.
        db.firstInit();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //salje gresku na server sa bazom
            case R.id.sendErrorImg: {
                DialogBuilder.showInfoDialog(this, "Obaveštenje", "Da li želite da pošaljete grešku?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ToDo Ovde implementirati slanje greske
                        if(Utilities.isOnline(MainActivity.this)){
                            db.open();
                            String employeeName = "";
                            int employeeID = db.getEmployeeID();
                            if(employeeID != -1){
                                employeeName = db.getEmployeeNameByID(employeeID);
                            }
                            db.close();
                            StringBuilder sb = new StringBuilder();
                            sb.append("App Version: ").append(BuildConfig.VERSION_NAME);
                            sb.append("\n");
                            sb.append("EmployeeID: ").append(employeeID);
                            sb.append("\n");
                            sb.append("Employee Name: ").append(employeeName);

                            SendMail mail = new SendMail();
                            mail.setAddAttachment(true);
                            mail.setMailContentType(SendMail.MailContentType.MAIL_TEXT_TYPE);
                            mail.setSubject("GREŠKA SA ALFAPLAM ANDROID-A");
                            mail.setMailTo("kolibri@micro-bs.com");
                            mail.setBody(sb.toString());
                            mail.execute(MainActivity.this);

                        }else{
                            Utilities.showToast("Nije vam aktivna internet konekcija", MainActivity.this);
                        }

                    }
                });

                break;
            }

            case R.id.loginBtn: {

                String userName = userNameEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (TextUtils.isEmpty(userName))
                    userNameEt.setError(getString(R.string.error_message));
                else if (TextUtils.isEmpty(password))
                    passwordEt.setError(getString(R.string.error_message));
                else
                    try {
                        SyncAs syncAS = new SyncAs();
                        db.open();
                        employeeID = db.getEmployeeID(userName, password);
                        if (employeeID == 0) {
                            syncAS.execute();
                            showProgressDialog(5);
                        } else {
                            syncAS.execute();
                            showProgressDialog(5);
                        }


                    } catch (Exception ex) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }

                        ErrorClass.handle(ex, MainActivity.this);

                        DialogBuilder.showOkDialog(this, "Prijava", "Greška prilikom prijave!");

                    }
                // loginUser(userName, password);
                break;
            }
            default:
                break;
        }
    }


    private void loginUser(String userName, String password) {

        //ToDo Ovde ide logika za sinhronizaciju
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
        Utilities.showToast("Uspešna prijava", this);


    }


    @Override
    public void onBackPressed() {

        DialogBuilder.showInfoDialog(this, "Obaveštenje", "Da li želite da napustite aplikaciju?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }


    private void getPhoneIMEI() {
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        verifyPermissions();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//
//        } else {
//            IMEI = telephonyManager.getDeviceId();
//        }


    }


    private void showProgressDialog(int progressMax) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.sync_dialog_layout, null);
        builder.setView(view);

        progressBar = view.findViewById(R.id.progressBar);
        progressBarTv = view.findViewById(R.id.progressBarTv);

        progressDialog = builder.create();
        progressDialog.setCancelable(false);

        progressBar.setMax(imeNivoaSinhronizacije.length * 5 + sendError.length * 5 + progressMax);

        progressBar.setProgress(0);
        progressDialog.show();


    }

    private void verifyPermissions() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
            ) {
                IMEI = telephonyManager.getDeviceId();

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 101:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//                        return;
//                    }
//                    IMEI = telephonyManager.getDeviceId();
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Dozvola je potrebna za normalno funkcionisanje aplikacije.", Toast.LENGTH_LONG).show();
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//
//                    }
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }

        try {
            verifyPermissions();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorClass.handle(e, MainActivity.this);
        }
    }

    private void getErrorLogFile() {

        String root = Environment.getExternalStorageDirectory().toString();
        root = root + "/ErrorLog/";
        File myDir = new File(root);
        myDir.mkdirs();
        ERROR_PATH = root;
    }

    public void incProgress(final int inc, final String string) {

        progress = progress + inc;

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressBar.setProgress(progress);
                if (string != null && !string.isEmpty()) {
                    progressBarTv.setText(string);
                }
            }
        });
    }

    private void setProgress(final int set, final String string) {

        progress = set;

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressBar.setProgress(progress);
                if (string != null && !string.isEmpty()) {
                    progressBarTv.setText(string);
                }
            }
        });
    }

    @Override
    public void increaseProgress(int progress, String title) {
        incProgress(progress, title);
    }


    private class SyncAs extends AsyncTask<Void, Void, Void> {

        Boolean errorHappened = false;

        @Override
        protected void onPreExecute() {
            unsentData = "";

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                if (employeeID == 0) {

                    nc = new NetworkClass(MainActivity.this, true, userNameEt.getText().toString(), passwordEt.getText()
                            .toString(), IMEI, progressBarTv, progressBar, MainActivity.this);

                    setProgress(5, "Brisanje baze");

                    //TODO ovde obrisati bazu
                    db.sentCheckIn_D();
                    db.sentSignatureImage_D();
                    db.sentWorkOrderImage_D();
                    db.sentStartStop_D();
                    db.deleteFromAddedTablesForSentWO();
                    db.sentWorkOrderResult_D();

                    nc.syncData(progress);

                    incProgress(5, "Završena Sinhronizacija");

                } else {
                    nc = new NetworkClass(MainActivity.this, false, userNameEt.getText().toString(), passwordEt.getText()
                            .toString(), IMEI, progressBarTv, progressBar, MainActivity.this);

                    setProgress(5, "Brisanje baze");

                    //TODO ovde obrisati bazu
                    db.sentCheckIn_D();
                    db.sentSignatureImage_D();
                    db.sentWorkOrderImage_D();
                    db.sentStartStop_D();
                    db.deleteFromAddedTablesForSentWO();
                    db.deleteUnassignedAddedTables();
                    db.sentWorkOrderResult_D();



                    nc.syncData(progress);
                    sendDataToService();

                    employeeID = db.getEmployeeID(userNameEt.getText().toString(), passwordEt.getText()
                            .toString());

                    incProgress(5, "Završena Sinhronizacija");
                }

            } catch (Exception ex) {

                Utilities.writeErrorToFile(ex);
                errorHappened = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                        if (employeeID != 0) {
                            StringBuilder dialogMessage = new StringBuilder();
                            if (nivoSinhronizacije == -1) // sinhronizacija je prosla, puklo je slanje
                            {

                                // Prikaz liste gresaka na osnovu nivoa tabele
                                dialogMessage.append("Nisu poslati sledeći podaci: \n");
                                // ukoliko je MP (greske)

                                for (int i = nivoSlanja; i < sendError.length; i++) {

                                    dialogMessage.append("\t - ").append(sendError[i]).append("\n");
                                }

                            } else {

                                // Prikaz liste gresaka na osnovu nivoa tabele
                                dialogMessage.append("Nisu sinhronizovani podaci: \n");
                                // Sinhronizacija za MP (greske)

                                for (int i = nivoSinhronizacije; i < imeNivoaSinhronizacije.length; i++) {
                                    dialogMessage.append("\t - ").append(imeNivoaSinhronizacije[i]).append("\n");
                                }

                            }
                            dialogMessage.append("\n Da li želite opet da pokušate sinhronizaciju podataka?");

                            DialogBuilder.showYesNoDialog(MainActivity.this, "Greška pri sinhronizaciji! Sinhronizacija je obavezna!",
                                    dialogMessage.toString(), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MainActivity.nivoSinhronizacije = 0;
                                            SyncAs ws = new SyncAs();
                                            if (employeeID == 0) {
                                                ws.execute();
                                                showProgressDialog(5);
                                            } else {
                                                ws.execute();
                                                showProgressDialog(5);
                                            }

                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nc.isLogin = false;
                                        }
                                    });

                        } else {
                            Toast.makeText(MainActivity.this, "Neuspešna prijava, pokušajte ponovo! ",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            if (!errorHappened) {
                if (employeeID > 0) {
                    if (newVersionCode != versionCode) {
                        DialogBuilder.showOkDialogWithCallback(MainActivity.this, "Na serveru postoji nova verzija aplikacije!", "Preuzmi novu verziju?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = newVersionDownloadLink;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                finish();
                            }
                        });
                    } else {
                        // ukoliko nisu svi podaci posalti kako treba ispisi
                        // samo da nisu i nastavi
                        if (unsentData.trim().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Provera završena! ", Toast.LENGTH_LONG).show();
                            Intent i = new Intent();
                            i.setClass(MainActivity.this, OptionsActivity.class);
                            startActivity(i);
                            setLoginDate();
                            nc.isLogin = false;
                        } else {
                            DialogBuilder.showOkDialogWithCallback(MainActivity.this, "Nisu poslati svi podaci na server!", unsentData, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Provera zavrsena! ", Toast.LENGTH_LONG)
                                            .show();
                                    Intent i = new Intent();
                                    i.setClass(MainActivity.this, OptionsActivity.class);
                                    startActivity(i);
                                    setLoginDate();
                                    nc.isLogin = false;
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Neuspešna prijava, pokušajte ponovo! ", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }


    public void sendDataToService() throws IOException {


        StringBuilder unsentDataSb = new StringBuilder();

        MainActivity.nivoSlanja = 0;
        incProgress(5, sendError[nivoSlanja]);
        if(!nc.sendCheckInToServerAll())
        unsentDataSb.append(" CHECKIN \n");

        MainActivity.nivoSlanja++;
        incProgress(5, sendError[nivoSlanja]);
        if(!nc.sendStartStopToServerAll())
        unsentDataSb.append(" START-STOP  \n");

        MainActivity.nivoSlanja++;
        incProgress(5, sendError[nivoSlanja]);
        if(!nc.sendSignatureToServerAll())
            unsentDataSb.append(" SLIKE POTPISA  \n");

        MainActivity.nivoSlanja++;
        incProgress(5, sendError[nivoSlanja]);
        if(!nc.sendWorkOrderImageAll())
            unsentDataSb.append(" SLIKE PO RADNOM NALOGU  \n");

        MainActivity.nivoSlanja++;
        incProgress(5, sendError[nivoSlanja]);
        if(!nc.sendWorkOrderResultAll())
            unsentDataSb.append(" IZVEŠTAJ ODRAĐENIH RADNIH NALOGA  \n");

        unsentData = unsentDataSb.toString();

    }
    private void setLoginDate() {

        try {
            String date = Utilities.getTomorrowDateAndTimeForLogin();

            String dateFromDb = db.getLoginDate();
            if (dateFromDb.isEmpty()) {
                db.loginDate_I(date);
            } else {
                db.loginDate_U(date);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
