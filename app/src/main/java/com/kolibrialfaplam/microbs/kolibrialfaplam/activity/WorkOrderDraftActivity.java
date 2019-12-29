package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedFailuresAndCausesAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedMaterialAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedServiceAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.PollQuestionDraftAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderDraftActivity extends AppCompatActivity implements AddedFailuresAndCausesAdapter.AddedFailuresAndCausesAdapterListener, AddedMaterialAdapter.AddedMaterialAdapterListener, AddedServiceAdapter.AddedServiceAdapterListener {

    private int workOrderID;
    private int workOrderResultID;
    private int workOrderDraftType;
    private AlertDialog loadingDialog;
    private BrokerSQLite db;
    private Route route;
    private NetworkClass nc;


    //WORK ORDER
    private TextInputEditText draftWorkOrderType, draftWorkOrderCode, draftWorkOrderDate;
    private AppCompatCheckBox draftWarrantyCb, draftCustomerRejectedCb;
    private TextInputEditText draftWorkOrderNote, draftWorkOrderDescription;
    private AppCompatRadioButton draftRadioBtnYes, draftRadioBtnNo;
    private TextView draftHasSignatureLabel;

    //CLIENT
    private TextInputEditText draftWorkOrderCustomerName, draftWorkOrderCustomerAddress,
            draftWorkOrderCustomerAddressNo, draftWorkOrderCustomerCity, draftWorkOrderCustomerPhone;
    private TextView draftWorkOrderClientSubtitle;
    private AppCompatCheckBox draftEscalatedCb, draftIsLegalPersonCb;

    //PRODUCT
    private TextInputEditText draftWorkOrderProductName, draftWorkOrderProductCode,
            draftWorkOrderProductSrNum, draftWorkOrderProductProductionDate, draftWorkOrderProductPurchaseDate,draftWorkOrderProductColor, draftWorkOrderProductMatType;
    private Product selectedProduct;

    //PARTNER
    private TextInputEditText draftWorkOrderPartnerName, draftWorkOrderPartnerCode, draftWorkOrderPartnerAddress,
            draftWorkOrderPartnerContact;
    private LinearLayout draftPartnerContainer;


    //SERVICES

    private TextView draftAddedServicesLabel;
    private RecyclerView draftAddedServicesRv;
    private AddedServiceAdapter draftAddedServiceAdapter;
    private List<Service> draftAddedServicesList;


    //MATERIALS

    private TextView draftAddedMaterialsLabel;
    private RecyclerView draftAddedMaterialsRv;
    private AddedMaterialAdapter draftAddedMaterialAdapter;
    private List<Material> draftAddedMaterialsList;


    //FAILURES AND CAUSES

    private TextView draftAddedFailuresLabel;
    private RecyclerView draftAddedFailuresRv;
    private List<AddedFailuresAndCauses> draftAddedFailuresAndCausesList;
    private AddedFailuresAndCausesAdapter draftAddedFailuresAndCausesAdapter;
    private TextView draftFailureCauseLbl;
    private LinearLayout draftFailureCauseOtherLayout;
    private TextView draftFailCauseOtherText;


    //QUESTIONS
    private ExpandableListView draftExpandableListView;
    private List<PollQuestion> pollQuestionArrayList;
    private PollQuestionDraftAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_draft);

        workOrderID = getIntent().getIntExtra("WorkOrderID", -1);
        workOrderResultID = getIntent().getIntExtra("WorkOrderResultID", -1);
        workOrderDraftType = getIntent().getIntExtra("WorkOrderDraftType", -1);

        //View initialisation
        init();

        if (workOrderResultID != -1 && workOrderID != -1 && workOrderDraftType != -1) {
            if (workOrderDraftType == Constants.WORK_ORDER_DRAFT_TAG)
                new GetWorkOrderDraftAsync().execute();
            else {
                if (Utilities.isOnline(WorkOrderDraftActivity.this))
                    new GetWorkOrderDetailsByResultID().execute();
                else {
                    Utilities.showToast("Nije vam aktivna internet konekcija", WorkOrderDraftActivity.this);
                    finish();
                }
            }

        } else {
            Utilities.showToast("Došlo je do greške pri očitavanju podataka o radnom nalogu. Pokušajte ponovo.", WorkOrderDraftActivity.this);
            finish();
        }


    }

    private void init() {

        db = new BrokerSQLite(this);
        nc = new NetworkClass(this);

        //WORK ORDER
        draftWorkOrderType = findViewById(R.id.draftWorkOrderType);
        draftWorkOrderCode = findViewById(R.id.draftWorkOrderCode);
        draftWorkOrderDate = findViewById(R.id.draftWorkOrderDate);
        draftWarrantyCb = findViewById(R.id.draftWarrantyCb);
        draftWorkOrderNote = findViewById(R.id.draftWorkOrderNote);
        draftWorkOrderDescription = findViewById(R.id.draftWorkOrderDescription);
        draftRadioBtnYes = findViewById(R.id.draftRadioBtnYes);
        draftRadioBtnNo = findViewById(R.id.draftRadioBtnNo);
        draftHasSignatureLabel = findViewById(R.id.draftHasSignatureLabel);
        draftExpandableListView = findViewById(R.id.draftExpandableListView);
        draftFailureCauseLbl = findViewById(R.id.draftFailureCauseLbl);
        draftFailureCauseOtherLayout = findViewById(R.id.draftFailureCauseOtherLayout);
        draftFailCauseOtherText = findViewById(R.id.draftFailCauseOtherText);
        draftCustomerRejectedCb = findViewById(R.id.draftCustomerRejectedCb);
        draftWorkOrderProductColor = findViewById(R.id.draftWorkOrderProductColor);
        draftWorkOrderProductMatType = findViewById(R.id.draftWorkOrderProductMatType);

        //CLIENT
        draftWorkOrderCustomerName = findViewById(R.id.draftWorkOrderCustomerName);
        draftWorkOrderCustomerAddress = findViewById(R.id.draftWorkOrderCustomerAddress);
        draftWorkOrderCustomerAddressNo = findViewById(R.id.draftWorkOrderCustomerAddressNo);
        draftWorkOrderCustomerCity = findViewById(R.id.draftWorkOrderCustomerCity);
        draftWorkOrderCustomerPhone = findViewById(R.id.draftWorkOrderCustomerPhone);
        draftWorkOrderClientSubtitle = findViewById(R.id.draftWorkOrderClientSubtitle);
        draftEscalatedCb = findViewById(R.id.draftEscalatedCb);
        draftIsLegalPersonCb = findViewById(R.id.draftIsLegalPersonCb);

        //PRODUCT
        draftWorkOrderProductName = findViewById(R.id.draftWorkOrderProductName);
        draftWorkOrderProductCode = findViewById(R.id.draftWorkOrderProductCode);
        draftWorkOrderProductSrNum = findViewById(R.id.draftWorkOrderProductSerNum);
        draftWorkOrderProductProductionDate = findViewById(R.id.draftWorkOrderProductProductionDate);
        draftWorkOrderProductPurchaseDate = findViewById(R.id.draftWorkOrderProductPurchaseDate);

        //PARTNER
        draftWorkOrderPartnerName = findViewById(R.id.draftWorkOrderPartnerName);
        draftWorkOrderPartnerCode = findViewById(R.id.draftWorkOrderPartnerCode);
        draftWorkOrderPartnerAddress = findViewById(R.id.draftWorkOrderPartnerAddress);
        draftWorkOrderPartnerContact = findViewById(R.id.draftWorkOrderPartnerContact);
        draftPartnerContainer = findViewById(R.id.draftPartnerContainer);


    }

    @Override
    public void onFailureAndCauseDeleted(AddedFailuresAndCauses addedFailuresAndCauses, int listSize) {
        //U ovoj klasi se nista ne radi ovde. Sluzi za brisanje pri kreiranju radnog naloga
    }

    @Override
    public void onMaterialDeleted(Material material, int listSize) {
        //U ovoj klasi se nista ne radi ovde. Sluzi za brisanje pri kreiranju radnog naloga

    }

    @Override
    public void onServiceDeleted(Service service, int listSize) {
        //U ovoj klasi se nista ne radi ovde. Sluzi za brisanje pri kreiranju radnog naloga

    }

    private class GetWorkOrderDraftAsync extends AsyncTask<Void, Void, Void> {

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
                route = db.getDraftRoute(workOrderResultID, workOrderID, true);

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
                fillViewsWithData(Constants.WORK_ORDER_DRAFT_TAG);
            } else {
                Utilities.showToast("Došlo je do greške pri očitavanju podataka o radnom nalogu. Pokušajte ponovo.", WorkOrderDraftActivity.this);
                finish();
            }
            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }

    private class GetWorkOrderDetailsByResultID extends AsyncTask<Void, Void, Void> {

        private boolean isOk = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                route = nc.getWorkOrderDetailsByWorkOrderID(workOrderID);
                isOk = true;
            } catch (Exception e) {
                isOk = false;
                Utilities.writeErrorToFile(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isOk) {
                fillViewsWithData(Constants.WORK_ORDER_HISTORY_TAG);
            } else {
                Utilities.showToast("Došlo je do greške pri očitavanju podataka o radnom nalogu. Pokušajte ponovo.", WorkOrderDraftActivity.this);
                finish();
            }
            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }


    private void fillViewsWithData(int workOrderDraftType) {
        if (route != null) {

            //WORK ORDER
            draftWorkOrderType.setText(route.getWorkOrderType());
            draftWorkOrderCode.setText(route.getWorkOrderCode());
            draftWorkOrderDate.setText(Utilities.changeDateFormatToLocalFormat(route.getPlannedDate()));
            draftWarrantyCb.setChecked(route.isInWarranty());
            draftCustomerRejectedCb.setChecked(route.isCustomerRejected());
            draftWorkOrderDescription.setText(route.getWorkOrderDescription());
            draftWorkOrderNote.setText(route.getWorkOrderNote());
            db.open();
            selectedProduct = db.getProductByProductID(route.getProductID());
            draftWorkOrderProductColor.setText(db.getColorNameByColorID(route.getProductColorID()));
            draftWorkOrderProductMatType.setText(getMaterialTypeNameByID(route.getMaterialTypeID()));
            db.close();

            if (route.getWorkOrderCode().startsWith("522")) {

                if (workOrderDraftType == Constants.WORK_ORDER_HISTORY_TAG)
                    pollQuestionArrayList = route.getWorkOrderPollList();
                else {
                    db.open();
                    pollQuestionArrayList = db.getAddedPollQuestionsDraft(workOrderResultID, workOrderID);
                    db.close();
                }
                draftWarrantyCb.setVisibility(View.GONE);
                draftExpandableListView.setVisibility(View.VISIBLE);
                setWorkOrderPollQuestions(pollQuestionArrayList);


            }
            setWorkOrderSignedLabel(route.isHasSignature());

            if (route.getClosedStatus() == 1)
                draftRadioBtnYes.setChecked(true);
            else
                draftRadioBtnNo.setChecked(true);

            //CLIENT
            draftWorkOrderCustomerName.setText(route.getCustomerName());
            draftWorkOrderCustomerAddress.setText(route.getCustomerAddress());
            draftWorkOrderCustomerAddressNo.setText(route.getCustomerAddressNo());
            draftWorkOrderCustomerCity.setText(route.getCustomerCity());
            draftWorkOrderCustomerPhone.setText(route.getCustomerPhone());
            draftIsLegalPersonCb.setChecked(route.isLegalPerson());
            if (route.isCustomerWarning()) {
                draftWorkOrderClientSubtitle.setTextColor(ContextCompat.getColorStateList(WorkOrderDraftActivity.this, R.color.customer_warning_text_color));
                draftWorkOrderCustomerName.setTextColor(ContextCompat.getColorStateList(WorkOrderDraftActivity.this, R.color.customer_warning_text_color));
            }
            draftEscalatedCb.setChecked(route.isEscalated());


            //PRODUCT

            draftWorkOrderProductName.setText(route.getProductName());
            draftWorkOrderProductCode.setText(route.getProductCode());
            draftWorkOrderProductSrNum.setText(route.getSerialNumber());
            draftWorkOrderProductPurchaseDate.setText(Utilities.changeDateFormatToLocalFormat(route.getProductPurchaseDate()));
            draftWorkOrderProductProductionDate.setText(Utilities.changeDateFormatToLocalFormat(route.getProductProductionDate()));

            //PARTNER

            if (TextUtils.equals("FIZIČKO LICE", route.getPartnerName().trim())) {
                draftPartnerContainer.setVisibility(View.INVISIBLE);
            } else {
                draftPartnerContainer.setVisibility(View.VISIBLE);
                draftWorkOrderPartnerName.setText(route.getPartnerName());
                draftWorkOrderPartnerCode.setText(route.getPartnerCode());
                draftWorkOrderPartnerAddress.setText(route.getPartnerAddress());
                draftWorkOrderPartnerContact.setText(route.getPartnerContact());
            }


            initAddedServices(workOrderDraftType, route);
            initAddedMaterials(workOrderDraftType, route);
            initAddedFailures(workOrderDraftType, route);

            if (!draftAddedServicesList.isEmpty())
                showUIElements(Constants.SHOW_UI_SERVICE_TAG);

            if (!draftAddedMaterialsList.isEmpty())
                showUIElements(Constants.SHOW_UI_MATERIAL_TAG);

            if (!draftAddedFailuresAndCausesList.isEmpty())
                showUIElements(Constants.SHOW_UI_FAILURE_TAG);

            if (!route.getFailureCauseNote().isEmpty()) {
                draftFailureCauseLbl.setVisibility(View.VISIBLE);
                draftFailureCauseOtherLayout.setVisibility(View.VISIBLE);
                draftFailCauseOtherText.setText(route.getFailureCauseNote());
            }

        } else {
            Utilities.showToast("Došlo je do greške pri očitavanju podataka o radnom nalogu. Pokušajte ponovo.", WorkOrderDraftActivity.this);
            finish();
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

    private void setWorkOrderSignedLabel(boolean isWorkOrderSigned) {
        if (isWorkOrderSigned) {
            String text = getString(R.string.hasSignature, "jeste");
            Spanned styledText = Html.fromHtml(text);
            draftHasSignatureLabel.setText(styledText);
        } else {
            String text = getString(R.string.hasSignature, "nije");
            Spanned styledText = Html.fromHtml(text);
            draftHasSignatureLabel.setText(styledText);
        }
    }

    private void initAddedServices(int workOrderDraftType, Route route) {

        draftAddedServicesLabel = findViewById(R.id.draftAddedServicesLabel);
        draftAddedServicesList = new ArrayList<>();
        draftAddedServicesRv = findViewById(R.id.draftAddedServicesRv);
        draftAddedServicesRv.setHasFixedSize(false);
        draftAddedServicesRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this));
        draftAddedServicesRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this), WorkOrderDraftActivity.this, draftAddedServicesRv));
        if (workOrderDraftType == Constants.WORK_ORDER_HISTORY_TAG) {
            draftAddedServicesList = route.getWorkOrderServiceList();

            for (Service service : draftAddedServicesList) {
                db.open();
                service = db.historyServiceUpdate(service, selectedProduct.getProductID());
                db.close();
            }

        } else {
            db.open();
            draftAddedServicesList = db.getAddedServices(workOrderResultID, RouteActivity.selectedRouteID, selectedProduct.getProductID());
            db.close();
        }
        draftAddedServiceAdapter = new AddedServiceAdapter(WorkOrderDraftActivity.this, draftAddedServicesList, this, Constants.ADDED_TABLES_DRAFT_TAG);
        draftAddedServicesRv.setAdapter(draftAddedServiceAdapter);

    }

    private void initAddedMaterials(int workOrderDraftType, Route route) {

        draftAddedMaterialsLabel = findViewById(R.id.draftAddedMaterialsLabel);
        draftAddedMaterialsList = new ArrayList<>();
        draftAddedMaterialsRv = findViewById(R.id.draftAddedMaterialsRv);
        draftAddedMaterialsRv.setHasFixedSize(false);
        draftAddedMaterialsRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this));
        draftAddedMaterialsRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this), WorkOrderDraftActivity.this, draftAddedMaterialsRv));
        if (workOrderDraftType == Constants.WORK_ORDER_HISTORY_TAG) {
            draftAddedMaterialsList = route.getWorkOrderMaterialList();
            for (Material material : draftAddedMaterialsList) {
                db.open();
                material = db.historyMaterialUpdate(material);
                db.close();
            }
        } else {
            db.open();
            draftAddedMaterialsList = db.getAddedMaterials(workOrderResultID, RouteActivity.selectedRouteID);
            db.close();
        }
        draftAddedMaterialAdapter = new AddedMaterialAdapter(WorkOrderDraftActivity.this, draftAddedMaterialsList, this, Constants.ADDED_TABLES_DRAFT_TAG);
        draftAddedMaterialsRv.setAdapter(draftAddedMaterialAdapter);


    }

    private void initAddedFailures(int workOrderDraftType, Route route) {

        draftAddedFailuresLabel = findViewById(R.id.draftAddedFailuresLabel);
        draftAddedFailuresRv = findViewById(R.id.draftAddedFailuresRv);
        draftAddedFailuresAndCausesList = new ArrayList<>();
        draftAddedFailuresRv.setHasFixedSize(false);
        draftAddedFailuresRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this));
        draftAddedFailuresRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderDraftActivity.this), WorkOrderDraftActivity.this, draftAddedFailuresRv));
        if (workOrderDraftType == Constants.WORK_ORDER_HISTORY_TAG)
            draftAddedFailuresAndCausesList = route.getWorkOrderFailureList();
        else {
            db.open();
            draftAddedFailuresAndCausesList = db.getAddedFailuresAndCauses(workOrderResultID, RouteActivity.selectedRouteID);
            db.close();
        }
        draftAddedFailuresAndCausesAdapter = new AddedFailuresAndCausesAdapter(WorkOrderDraftActivity.this, draftAddedFailuresAndCausesList, this, Constants.ADDED_TABLES_DRAFT_TAG);
        draftAddedFailuresRv.setAdapter(draftAddedFailuresAndCausesAdapter);


    }

    private void showUIElements(int tag) {
        if (tag == Constants.SHOW_UI_SERVICE_TAG) {
            draftAddedServicesLabel.setVisibility(View.VISIBLE);
            draftAddedServicesRv.setVisibility(View.VISIBLE);
        } else if (tag == Constants.SHOW_UI_MATERIAL_TAG) {
            draftAddedMaterialsLabel.setVisibility(View.VISIBLE);
            draftAddedMaterialsRv.setVisibility(View.VISIBLE);
        } else if (tag == Constants.SHOW_UI_FAILURE_TAG) {
            draftAddedFailuresLabel.setVisibility(View.VISIBLE);
            draftAddedFailuresRv.setVisibility(View.VISIBLE);
        }
    }

    private void setWorkOrderPollQuestions(List<PollQuestion> pollQuestionArrayList) {

        ArrayList<String> groupTitle = new ArrayList<>();
        groupTitle.add("Pitanja");

        questionAdapter = new PollQuestionDraftAdapter(this, groupTitle, pollQuestionArrayList);
        draftExpandableListView.setAdapter(questionAdapter);

        setExpandableListViewHeight(draftExpandableListView, -1);
        draftExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setExpandableListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }

    private void setExpandableListViewHeight(ExpandableListView listView, int group) {
        PollQuestionDraftAdapter listAdapter = (PollQuestionDraftAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private String getMaterialTypeNameByID(int materialTypeID){
        if(materialTypeID == 1)
            return "Levo";
        else if (materialTypeID == 2)
            return "Desno";
        else
            return "Nepoznato";
    }


}
