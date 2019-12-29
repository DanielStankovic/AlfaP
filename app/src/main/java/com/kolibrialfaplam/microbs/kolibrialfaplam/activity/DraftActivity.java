package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.DraftAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Draft;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.List;

public class DraftActivity extends AppCompatActivity implements DraftAdapter.DraftAdapterInterface, NetworkClass.SendDataInterface {


    private RecyclerView draftRv;
    private DraftAdapter draftAdapter;
    private List<Draft> draftItemsList;
    private AlertDialog loadingDialog;
    private BrokerSQLite db;
    private NetworkClass nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        //views initialization
        init();
        new GetDraftItemsAsync().execute();

    }

    private void init(){

        db = new BrokerSQLite(this);
        nc = new NetworkClass(DraftActivity.this, DraftActivity.this);
        draftRv = findViewById(R.id.draftRv);
        draftRv.setHasFixedSize(true);
        draftRv.setLayoutManager(new LinearLayoutManager(this));
        draftRv.setAdapter(draftAdapter);

    }

    @Override
    public void onSendButtonClicked(Draft draftItem) {
        if(TextUtils.equals(draftItem.getHeaderTitle(),"Izveštaj o radnom nalogu")){
            nc.sendWorkOrderResult();
        }else if(TextUtils.equals(draftItem.getHeaderTitle(),"Slike - radni nalog")){
            nc.sendWorkOrderImage();
        }else if(TextUtils.equals(draftItem.getHeaderTitle(),"Slike - potpis")){
            nc.sendSignatureToServer();
        }else if(TextUtils.equals(draftItem.getHeaderTitle(),"Check In")){
            nc.sendCheckInToServer();
        }else if(TextUtils.equals(draftItem.getHeaderTitle(),"Start-Stop")){
            nc.sendStartStopToServer();
        }
    }

    @Override
    public void onStartSendingData() {
        showLoadingDialog();
    }

    @Override
    public void onSuccessSendingData(int dataType) {
        loadingDialog.cancel();
        new GetDraftItemsAsync().execute();
    }

    @Override
    public void onErrorSendingData(String errorMessage, int dataType) {
    Utilities.showToast("Došlo je do greške prilikom slanja podataka. Proverite internet konekciju i pokušajte ponovo. GREŠKA: " + errorMessage, DraftActivity.this);
        loadingDialog.cancel();
    }


    private class GetDraftItemsAsync extends AsyncTask<Void, Void, Void> {
        private boolean isOk = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                db.open();
                draftItemsList = db.getDraftItems();
                isOk = true;
                db.close();
            } catch (SQLException e) {
                isOk = false;
                Utilities.writeErrorToFile(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            if (isOk) {

                if (!draftItemsList.isEmpty()) {
                    draftAdapter = new DraftAdapter(DraftActivity.this, draftItemsList, DraftActivity.this);
                    draftRv.setAdapter(draftAdapter);
                }
            } else {
                Utilities.showToast("Došlo je do problema pri preuzimanju podataka iz baze.", DraftActivity.this);
            }

            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();

            super.onPostExecute(aVoid);
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
