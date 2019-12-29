package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.ApiClient;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.StartStop;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.DialogBuilder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.IOException;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener, NetworkClass.increaseDialogProgressInterface, NetworkClass.SendDataInterface {

    //Data Buttons
    private Button sendDataBtn, getDataBtn;

    //Options Buttons
    private Button routeBtn, draftBtn, lastWorkOrdersBtn, warehouseStateBtn;

    //Start Stop Button
    private Button startStopBtn;
    private boolean isStarted;
    private StartStop startStop;
    private TextView startDateTimeLbl;
    private String startDateTime = "";
    private AlertDialog loadingDialog;
    private int nivoSlanja = 0;

    private boolean gettingData = true; // true for GETTING data, false for SENDING data

    public int progress = 0;
    private String unsentData = "";

    private AlertDialog progressDialog;
    ProgressBar progressBar;
    TextView progressBarTv;

    BrokerSQLite db = new BrokerSQLite(OptionsActivity.this);
    private NetworkClass nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //views initialization
        init();

        if (TextUtils.equals(ApiClient.SERVICE_ADDRESS, "http://89.216.113.44:8222/")) {

            if(Utilities.exportDB(OptionsActivity.this))
                Utilities.showToast("Baza eksportovana", OptionsActivity.this);
            else
                Utilities.showToast("Došlo je do greške pri eksportu baze", OptionsActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupStartStopStatus();
    }

    private void init() {

        //TextView
        startDateTimeLbl = findViewById(R.id.startDateTimeLbl);

        //Buttons
        routeBtn = findViewById(R.id.routeBtn);
        draftBtn = findViewById(R.id.draftBtn);
        warehouseStateBtn = findViewById(R.id.warehouseStateBtn);
        lastWorkOrdersBtn = findViewById(R.id.lastWorkOrdersBtn);
        getDataBtn = findViewById(R.id.getDataBtn);
        sendDataBtn = findViewById(R.id.sendDataBtn);
        startStopBtn = findViewById(R.id.startStopBtn);

        //onClickListener assignment
        routeBtn.setOnClickListener(this);
        draftBtn.setOnClickListener(this);
        warehouseStateBtn.setOnClickListener(this);
        lastWorkOrdersBtn.setOnClickListener(this);
        getDataBtn.setOnClickListener(this);
        sendDataBtn.setOnClickListener(this);
        startStopBtn.setOnClickListener(this);

        nc = new NetworkClass(OptionsActivity.this, OptionsActivity.this, OptionsActivity.this);


    }

    private void setupStartStopStatus() {
        db.open();
        startStop = db.getStartStop(MainActivity.employeeID);
        if (startStop == null) {
            isStarted = false;
            startStop = new StartStop();
        } else {
            isStarted = startStop.isStarted();
        }
        changeUIElements(isStarted);
        db.close();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.routeBtn: {
                if (checkIfStartForTodayExist()) {
                    Intent intent = new Intent(this, RouteActivity.class);
                    startActivity(intent);
                } else {
                    Utilities.showToast("Niste odradili START za današnji dan!", OptionsActivity.this);
                }
                break;
            }
            case R.id.draftBtn: {
                //ToDo implementirati logiku za route draftove

                Intent intent = new Intent(this, DraftActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.warehouseStateBtn: {
                //ToDo implementirati logiku za pregled stanja magacina

                Intent intent = new Intent(this, WarehouseStateActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lastWorkOrdersBtn: {
                //ToDo implementirati logiku za poslednje radne naloge
                showDatePickerDialogForHistory();

                break;
            }
            case R.id.getDataBtn: {
                DialogBuilder.showInfoDialog(OptionsActivity.this,
                        "Preuzimanje novih šifarnika", "Da li želite da preuzmete nove podatke sa servera?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    gettingData = true; //GETTING DATA, not sending
                                    new SyncAS().execute();

                                } catch (Exception ex) {
                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        progressDialog.cancel();
                                    }

                                    ErrorClass.handle(ex, OptionsActivity.this);

                                    DialogBuilder.showOkDialog(OptionsActivity.this, "Prijava", "Greška prilikom prijave!");

                                }
                            }
                        });
                break;
            }
            case R.id.sendDataBtn: {
                //ToDo implementirati logiku za slanje podataka na server
                DialogBuilder.showInfoDialog(OptionsActivity.this, "Slanje podataka na server", "Da li želite da pošaljete podatke na server?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    gettingData = false;
                                    new SyncAS().execute();
                                } catch (Exception ex) {
                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        progressDialog.cancel();
                                    }

                                    ErrorClass.handle(ex, OptionsActivity.this);

                                    DialogBuilder.showOkDialog(OptionsActivity.this, "Prijava", "Greška prilikom slanja podataka na server!");

                                }
                            }
                        });
                break;
            }

            case R.id.startStopBtn: {
                isStarted = !isStarted;
                startStop.setStarted(isStarted);
                startStop.setTime(Utilities.getCurrentDateTime());
                changeUIElements(isStarted);
                db.open();
                db.startStop_I(MainActivity.employeeID, isStarted);
                nc.sendStartStopToServer();
                db.close();
                break;
            }

            default:
                break;

        }
    }

    private void showDatePickerDialogForHistory() {


        final AlertDialog datePickerDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.date_period_dialog, null);
        builder.setView(view);
        final AppCompatRadioButton datePickerDateRb = view.findViewById(R.id.datePickerDateRb);
        datePickerDateRb.setChecked(true);

        final TextView fromLblDateDialog = view.findViewById(R.id.fromLblDateDialog);
        final DatePicker datePickerFrom = view.findViewById(R.id.datePickerFrom);

        final LinearLayout datePickerLinLay2 = view.findViewById(R.id.datePickerLinLay2);
        final DatePicker datePickerTo = view.findViewById(R.id.datePickerTo);

        Button datePickerCancelBtn = view.findViewById(R.id.datePickerCancelBtn);
        Button datePickerChooseBtn = view.findViewById(R.id.datePickerChooseBtn);



        datePickerFrom.setMaxDate(new Date().getTime());
        datePickerTo.setMaxDate(new Date().getTime());

        datePickerDialog = builder.create();
        datePickerDialog.show();

        datePickerCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.dismiss();

            }
        });

        datePickerDateRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    datePickerLinLay2.setVisibility(View.GONE);
                    fromLblDateDialog.setText(getResources().getString(R.string.date_lbl));
                }else{
                    datePickerLinLay2.setVisibility(View.VISIBLE);
                    fromLblDateDialog.setText(getResources().getString(R.string.from));
                }
            }
        });

        datePickerChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isOnline(OptionsActivity.this)) {
                    String date = "";

                    if (datePickerDateRb.isChecked()) {
                        int month = datePickerFrom.getMonth() + 1;
                        date = datePickerFrom.getYear() + "-" + month + "-" + datePickerFrom.getDayOfMonth();
                    } else {
                        int monthFrom = datePickerFrom.getMonth() + 1;
                        int monthTo = datePickerTo.getMonth() + 1;
                        date = datePickerFrom.getYear() + "-" + monthFrom + "-" + datePickerFrom.getDayOfMonth() + "," +
                                datePickerTo.getYear() + "-" + monthTo + "-" + datePickerTo.getDayOfMonth();
                    }

                    Intent intentHistory = new Intent(OptionsActivity.this, HistoryActivity.class);
                    intentHistory.putExtra("DatePeriod", date);
                    startActivity(intentHistory);

                }else{
                    Utilities.showToast("Nije vam aktivna internet konekcija", OptionsActivity.this);
                }

                datePickerDialog.dismiss();
            }
        });


    }

    private void changeUIElements(boolean isStarted) {


        routeBtn.setEnabled(isStarted);
        draftBtn.setEnabled(isStarted);
        lastWorkOrdersBtn.setEnabled(isStarted);
        warehouseStateBtn.setEnabled(isStarted);

        if (isStarted) {
            startStopBtn.setBackgroundResource(R.drawable.stop_btn_bg);
            startStopBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
            startStopBtn.setText(getResources().getString(R.string.stop));
            startDateTime = getResources().getString(R.string.start_time, Utilities.changeDateTimeFormatToLocalFormat(startStop.getTime()));
        } else {
            startStopBtn.setBackgroundResource(R.drawable.start_btn_bg);
            startStopBtn.setTextColor(ContextCompat.getColor(this, R.color.gradient_end));
            startStopBtn.setText(getResources().getString(R.string.start));
            startDateTime = "";
        }

        startDateTimeLbl.setText(startDateTime);
    }

    private boolean checkIfStartForTodayExist() {

        String todayDate = Utilities.getCurrentDateTime().split(" ")[0];
        String startDate = startStop.getTime().split(" ")[0];
        return TextUtils.equals(startDate, todayDate);
    }

    @Override
    public void increaseProgress(int progress, String title) {
        incProgress(progress, title);
    }

    public void incProgress(final int inc, final String string) {

        progress = progress + inc;

        OptionsActivity.this.runOnUiThread(new Runnable() {

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

        OptionsActivity.this.runOnUiThread(new Runnable() {

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
    public void onStartSendingData() {
        showLoadingDialog();
    }

    @Override
    public void onSuccessSendingData(int dataType) {
        loadingDialog.cancel();
        Utilities.showToast("Start-Stop status poslat na server", OptionsActivity.this);
    }

    @Override
    public void onErrorSendingData(String errorMessage, int dataType) {
        loadingDialog.cancel();
        Utilities.writeErrorToFile(new Exception(errorMessage));
        Utilities.showToast("Došlo je do greške prilikom slanja Start-Stop statusa na server. Proverite internet konekciju i pokušajte opet kasnije", OptionsActivity.this);
    }


    private class SyncAS extends AsyncTask<Void, Void, Void> {
        boolean errorHappened = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(gettingData);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (gettingData) {
                    setProgress(5, "");
                    nc.syncData(progress);
                } else {
                    sendDataToService();
                }
            } catch (Exception ex) {
                Utilities.writeErrorToFile(ex);
                errorHappened = true;

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            if (!errorHappened) {
                if (MainActivity.employeeID > 0 && gettingData) {
                    Utilities.showToast("Podaci uspešno preuzeti!", OptionsActivity.this);
                } else if (MainActivity.employeeID > 0 && !gettingData) {
                    if (unsentData.length() == 0) {
                        Utilities.showToast("Podaci uspešno poslati!", OptionsActivity.this);

                    } else {
                        DialogBuilder.showOkDialog(OptionsActivity.this, "Došlo je do greške", "Nisu poslati sledeći podaci: \n" + unsentData);
                    }
                } else {
                    Utilities.showToast("Došlo je do greške! Pokušajte ponovo!", OptionsActivity.this);

                }
            } else {
                Utilities.showToast("Došlo je do greške! Pokušajte ponovo!", OptionsActivity.this);

            }

            //resetuje se na 0 u slucaju da se klikne opet na dugme
            nivoSlanja = 0;
            unsentData = "";
        }
    }

    private void showProgressDialog(boolean gettingData) {

        String title = gettingData ? "Sinhronizacija podataka..." : "Slanje podataka u toku...";
        int progressValue = gettingData ? MainActivity.imeNivoaSinhronizacije.length * 5 + 5 : MainActivity.sendError.length * 5 + 5;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.sync_dialog_layout, null);
        builder.setView(view);

        TextView syncTitle = view.findViewById(R.id.syncTitle);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarTv = view.findViewById(R.id.progressBarTv);
        syncTitle.setText(title);

        progressDialog = builder.create();
        progressDialog.setCancelable(false);

        progressBar.setMax(progressValue);

        progressBar.setProgress(0);
        progressDialog.show();


    }

    public void sendDataToService() throws IOException {


        StringBuilder unsentDataSb = new StringBuilder();


        incProgress(5, MainActivity.sendError[nivoSlanja]);
        if (!nc.sendCheckInToServerAll())
            unsentDataSb.append(" CHECKIN \n");

        nivoSlanja++;
        incProgress(5, MainActivity.sendError[nivoSlanja]);
        if (!nc.sendStartStopToServerAll())
            unsentDataSb.append(" START-STOP  \n");

        nivoSlanja++;
        incProgress(5, MainActivity.sendError[nivoSlanja]);
        if (!nc.sendSignatureToServerAll())
            unsentDataSb.append(" SLIKE POTPISA  \n");

        nivoSlanja++;
        incProgress(5, MainActivity.sendError[nivoSlanja]);
        if (!nc.sendWorkOrderImageAll())
            unsentDataSb.append(" SLIKE PO RADNOM NALOGU  \n");

        nivoSlanja++;
        incProgress(5, MainActivity.sendError[nivoSlanja]);
        if (!nc.sendWorkOrderResultAll())
            unsentDataSb.append(" IZVEŠTAJ ODRAĐENIH RADNIH NALOGA  \n");

        unsentData = unsentDataSb.toString();

    }

    private void showLoadingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }

    @Override
    public void onBackPressed() {
        DialogBuilder.showInfoDialog(OptionsActivity.this, "Obaveštenje", "Da li ste sigurni da želite da se odjavite?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }


}
