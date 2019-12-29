package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;


import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.RouteAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.ArrayList;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RouteActivity extends AppCompatActivity {

    private RecyclerView routeRv;
    private ArrayList<Route> routeArrayList;
    private RouteAdapter adapter;
    public static int selectedRouteID;
    private BrokerSQLite db;
    private AlertDialog loadingDialog;
    private AppCompatSpinner workOrderTypeSpinner, partnerSpinner, regionSpinner, regionPlaceSpinner;
    private AppCompatCheckBox criticalWorkOrderCb;
    private TextView routeTotalNumWorkOrder;

    private SparseArray<String> workOrderTypeArray;
    private SparseArray<String> partnerArray;
    private SparseArray<String> regionArray;
    private SparseArray<String> regionPlaceArray;
    private int workOrderTypeID = -1;
    private int partnerID = -1;
    private int regionID = -1;
    private int regionPlaceID = -1;

    private boolean showCriticalWorkOrders = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        //views initialization
        init();
    }

    private void init(){

        db = new BrokerSQLite(this);
        workOrderTypeSpinner = findViewById(R.id.workOrderTypeSpinner);
        partnerSpinner = findViewById(R.id.partnerSpinner);
        regionSpinner = findViewById(R.id.regionSpinner);
        regionPlaceSpinner = findViewById(R.id.regionPlaceSpinner);
        routeTotalNumWorkOrder = findViewById(R.id.routeTotalNumWorkOrder);
        criticalWorkOrderCb = findViewById(R.id.criticalWorkOrderCb);
        routeRv = findViewById(R.id.routeRv);
        routeRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        routeRv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(routeRv.getContext(),linearLayoutManager.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        routeRv.addItemDecoration(decoration);


        criticalWorkOrderCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showCriticalWorkOrders = isChecked;
                setArrayListAndAdapter();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetRoutesAsync().execute();
    }

    private class GetRoutesAsync extends AsyncTask<Void, Void, Void>{
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
                routeArrayList = db.getRouteList(workOrderTypeID, partnerID, regionID, regionPlaceID, showCriticalWorkOrders);
                workOrderTypeArray = db.getWorkOrderTypeArray();
                partnerArray = db.getPartnerArray();
                regionArray = db.getRegionArray();
                regionPlaceArray = db.getRegionPlaceArray(-1);
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
                routeTotalNumWorkOrder.setText(getResources().getString(R.string.total_num_work_order, routeArrayList.size()));
                if(!routeArrayList.isEmpty()) {
                    adapter = new RouteAdapter(RouteActivity.this, routeArrayList);
                    routeRv.setAdapter(adapter);
                }

                setupSpinners();
            } else {
                Utilities.showToast("Došlo je do problema pri preuzimanju podataka iz baze.", RouteActivity.this);
            }

            if( loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();


            super.onPostExecute(aVoid);

        }
    }


    private void setupSpinners(){

        workOrderTypeSpinner.setAdapter(new SparseStringsAdapter(this, workOrderTypeArray));
        partnerSpinner.setAdapter(new SparseStringsAdapter(this, partnerArray));
        regionSpinner.setAdapter(new SparseStringsAdapter(this, regionArray));
        regionPlaceSpinner.setAdapter(new SparseStringsAdapter(this, regionPlaceArray));


        //Ova selekcija je potrebo da se postavi pre dodeljivanja listenera. U ovom slucaju ce se izvrsiti selekcija
        //a nece imati listeneri i nece se pozivati baza 4 puta.
        workOrderTypeSpinner.setSelection(0,false);
        partnerSpinner.setSelection(0,false);
        regionSpinner.setSelection(0,false);
        regionPlaceSpinner.setSelection(0,false);

        workOrderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workOrderTypeID = workOrderTypeArray.keyAt(position);
                setArrayListAndAdapter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        partnerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                partnerID = partnerArray.keyAt(position);
                setArrayListAndAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                db.open();
                regionID = regionArray.keyAt(position);
                regionPlaceArray = db.getRegionPlaceArray(regionID);
                regionPlaceSpinner.setAdapter(new SparseStringsAdapter(RouteActivity.this, regionPlaceArray));
                db.close();
                setArrayListAndAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        regionPlaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    regionPlaceID = regionPlaceArray.keyAt(position);

                    setArrayListAndAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }

    private void setArrayListAndAdapter(){
        db.open();
        routeArrayList = db.getRouteList(workOrderTypeID, partnerID, regionID, regionPlaceID, showCriticalWorkOrders);
        adapter = new RouteAdapter(RouteActivity.this, routeArrayList);
        routeRv.setAdapter(adapter);
        db.close();
    }


    private abstract class  SparseArrayAdapter<E> extends BaseAdapter {
        private SparseArray<E> mData;
        public void setData(SparseArray<E> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public E getItem(int position) {
            return mData.valueAt(position);
        }

        @Override
        public long getItemId(int position) {
            return mData.keyAt(position);
        }
    }

    private class SparseStringsAdapter extends SparseArrayAdapter<String> {
        private final LayoutInflater mInflater;
        public SparseStringsAdapter(Context context, SparseArray<String> data) {
            mInflater = LayoutInflater.from(context);
            setData(data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView result = (TextView) convertView;
            if (result == null) {
                result = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            result.setText(getItem(position));
            result.setTextSize(TypedValue.COMPLEX_UNIT_SP,10 );
            return result;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            return textView;
        }


    }
}
