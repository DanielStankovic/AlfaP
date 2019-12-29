package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.WarehouseStateAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Warehouse;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.List;
import java.util.Objects;

public class WarehouseStateActivity extends AppCompatActivity {

    private RecyclerView warehouseStateRv;
    private TextInputEditText warehouseStateSearchMat;
    private ImageButton warehouseStateRefreshBtn;
    private AlertDialog loadingDialog;

    private BrokerSQLite db;
    private NetworkClass nc;

    private List<Material> materialList;
    private WarehouseStateAdapter adapter;
    private TextView warehouseNameTv, warehouseCodeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_state);

        init();
        setAdapters();
        new GetMaterialsAsync().execute();
    }

    private void init() {

        db = new BrokerSQLite(this);
        nc = new NetworkClass(this);
        warehouseNameTv = findViewById(R.id.warehouseNameTv);
        warehouseCodeTv = findViewById(R.id.warehouseCodeTv);
        warehouseStateSearchMat = findViewById(R.id.warehouseStateSearchMat);
        warehouseStateRefreshBtn = findViewById(R.id.warehouseStateRefreshBtn);
        warehouseStateRv = findViewById(R.id.warehouseStateRv);

        warehouseStateRv.setHasFixedSize(false);
        warehouseStateRv.setLayoutManager(Utilities.getLinearLayoutManager(WarehouseStateActivity.this));
        warehouseStateRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WarehouseStateActivity.this), WarehouseStateActivity.this, warehouseStateRv));


    }

    private void setAdapters() {
        warehouseStateRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new RefreshWarehouseState().execute();
            }
        });

        warehouseStateSearchMat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class GetMaterialsAsync extends AsyncTask<Void, Void, Void> {
        private boolean isOk = false;
        private Warehouse warehouse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                db.open();
                materialList = db.getWarehouseStateMaterials(MainActivity.employeeID);
                warehouse = db.getEmployeeWarehouse(MainActivity.employeeID);
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

                warehouseNameTv.setText(warehouse.getWarehouseName());
                warehouseCodeTv.setText(warehouse.getWarehouseCode());

                if (!materialList.isEmpty()) {
                    adapter = new WarehouseStateAdapter(WarehouseStateActivity.this, materialList);
                    warehouseStateRv.setAdapter(adapter);
                }
            } else {
                Utilities.showToast("Došlo je do problema pri preuzimanju podataka iz baze.", WarehouseStateActivity.this);
            }

            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();

            super.onPostExecute(aVoid);
        }
    }

    private class RefreshWarehouseState extends AsyncTask<Void, Void, Void> {
        private boolean isOk = false;

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                nc.syncWarehouseState();
                db.open();
                materialList = db.getWarehouseStateMaterials(MainActivity.employeeID);
                db.close();
                isOk = true;
            } catch (Exception e) {
                isOk = false;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isOk) {
                if (!materialList.isEmpty()) {
                    adapter = new WarehouseStateAdapter(WarehouseStateActivity.this, materialList);
                    warehouseStateRv.setAdapter(adapter);
                    Utilities.showToast("Preuzeti sveži podaci.", WarehouseStateActivity.this);
                }
            } else {
                Utilities.showToast("Došlo je do greške prilikom sinhronizacije stanja magacina!", WarehouseStateActivity.this);
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
