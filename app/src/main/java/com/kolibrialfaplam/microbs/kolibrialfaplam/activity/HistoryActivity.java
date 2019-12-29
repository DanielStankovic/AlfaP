package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.HistoryAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.History;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.DialogBuilder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private TextView historyTitleTv;
    private RecyclerView historyRv;
    private String serialNumber = "";
    private String datePeriod = "";

    private List<History> workOrderHistoryList;
    private HistoryAdapter adapter;

    private AlertDialog loadingDialog;
    private NetworkClass nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        serialNumber = Objects.requireNonNull(getIntent().getExtras()).getString("SerialNumber", "");
        datePeriod = Objects.requireNonNull(getIntent().getExtras()).getString("DatePeriod", "");
        if (serialNumber.isEmpty() && datePeriod.isEmpty()) {
            Utilities.showToast("Došlo je do problema pri očitavanju podataka. Pokušajte ponovo", HistoryActivity.this);
            finish();
        } else {

            //Znaci da se proverava istorija po serijskom broju
            if(datePeriod.isEmpty()){
            historyTitleTv.setText(getResources().getString(R.string.serial_number, serialNumber));
            new GetWorkOrderHistory().execute(serialNumber);
            }else{
                //znaci da se proverava istorija po datumu
                if(datePeriod.contains(",")){
                    String dates[] = datePeriod.split(",");
                    historyTitleTv.setText(getResources().getString(R.string.period_date, Utilities.changeDateFormatToLocalFormat(dates[0]), Utilities.changeDateFormatToLocalFormat(dates[1])));
                }else{
                    historyTitleTv.setText(getResources().getString(R.string.date_lbl_param, Utilities.changeDateFormatToLocalFormat(datePeriod)));
                }
                new GetWorkOrderHistory().execute(datePeriod);
            }
        }

    }

    private void init() {

        nc = new NetworkClass(this);
        historyTitleTv = findViewById(R.id.historyTitleTv);
        historyRv = findViewById(R.id.historyRv);
        historyRv.setHasFixedSize(true);
        historyRv.setLayoutManager(new LinearLayoutManager(this));

    }

    private class GetWorkOrderHistory extends AsyncTask<String, Void, String> {
        private boolean isOk = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                workOrderHistoryList = nc.getWorkOrdersHistory(params[0]);
                isOk = true;
            } catch (Exception e) {
                isOk = false;
                Utilities.writeErrorToFile(e);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String params) {

            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();

            if (isOk) {
                if (workOrderHistoryList.isEmpty()) {
                    if(TextUtils.equals(params, serialNumber)) {
                        DialogBuilder.showOkDialogWithCallback(HistoryActivity.this, "PAŽNJA", "Ne postoje radni nalozi po ovom serijskom broju.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }else{
                        if(params.contains(",")){
                            DialogBuilder.showOkDialogWithCallback(HistoryActivity.this, "PAŽNJA", "Ne postoje radni nalozi za ovaj period.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }else{
                            DialogBuilder.showOkDialogWithCallback(HistoryActivity.this, "PAŽNJA", "Ne postoje radni nalozi za ovaj datum.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }

                    }
                } else {
                    adapter = new HistoryAdapter(workOrderHistoryList, HistoryActivity.this);
                    historyRv.setAdapter(adapter);
                }
            } else {
                Utilities.showToast("Došlo je do problema pri preuzimanju istorije po serijskom broju", HistoryActivity.this);
                finish();
            }


            super.onPostExecute(params);
        }
    }


    private void showLoadingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }
}
