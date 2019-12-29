package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedFailuresAndCausesAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedMaterialAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.AddedServiceAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.FailureCauseAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.FailureForWorkOrderAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.MaterialsAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.PollQuestionAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.ProductsAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.ServicesAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Failure;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.FailureCause;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ServiceNorm;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderResult;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.DialogBuilder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkOrderActivity extends AppCompatActivity implements View.OnClickListener, NetworkClass.SendDataInterface,
        ServicesAdapter.ServiceAdapterListener, AddedServiceAdapter.AddedServiceAdapterListener,
        MaterialsAdapter.MaterialAdapterListener, AddedMaterialAdapter.AddedMaterialAdapterListener, ProductsAdapter.ProductAdapterListener,
        FailureCauseAdapter.FailureCauseAdapterListener, AddedFailuresAndCausesAdapter.AddedFailuresAndCausesAdapterListener {

    private final int REQUEST_PERMISSION_CODE = 102;
    private final String CHECK_IN_STATUS = "check_in_status";
    private final String CHECK_IN_ID = "check_in_id";

    private final int SIGNATURE_ACTIVITY_CODE = 100;

    private final int DRAFT_TAG = 10;
    private final int ROUTE_TAG = 11;

    private RelativeLayout basicContainerRelativeLay;


    private Route route;

    //WORK ORDER
    private TextInputEditText workOrderType, workOrderCode, workOrderDate;
    private AppCompatCheckBox warrantyCb, customerRejectedCb;


    //CLIENT
    private TextInputEditText workOrderCustomerName, workOrderCustomerAddress, workOrderCustomerAddressNo, workOrderCustomerCity, workOrderCustomerPhone;
    private TextView workOrderClientSubtitle;
    private AppCompatCheckBox escalatedCb;
    private boolean isEscaleted = false;
    private AppCompatCheckBox isLegalPersonCb;

    //PRODUCT
    private TextInputEditText workOrderProductName, workOrderProductCode,
            workOrderProductSrNum,  workOrderProductPurchaseDate, workOrderProductColor,workOrderProductMatType; //workOrderProductProductionDate,
    //workOrderProductPower - Receno je da se skloni ovo polje
    private Button workOrderProdDocBtn;
    private Button workOrderProdHistBtn;

    //PARTNER
    private TextInputEditText workOrderPartnerName, workOrderPartnerCode, workOrderPartnerAddress, workOrderPartnerContact;
    private LinearLayout partnerContainer;

    //POLL QUESTIONS
    private ArrayList<PollQuestion> pollQuestionArrayList;
    private PollQuestionAdapter questionAdapter;
    private ArrayList<PollQuestion> completedPollQuestionList;

    private Button checkInBtn;
    private Button scanBarcodeBtn;
    private Button takePictureBtn;
    private Button checkFailureForWO;
    private Button servicesBtn;
    private Button materialsBtn;
    private Button failuresBtn;
    private Button sendDataToServerBtn;
    private ExpandableListView expandableListView;

    private TextInputEditText workOrderNote, workOrderDescription;


    private boolean isCheckedIn = false;
    private boolean isInRoute = true;

    //SERVICES
    private TextView addedServicesLabel, workOrderChangeProductBtn;
    private RecyclerView addedServicesRv;
    private Service selectedService = null;
    private List<Service> serviceList;
    private ServicesAdapter servicesAdapter;
    private TextInputEditText unitSpentEt;
    private AddedServiceAdapter addedServiceAdapter;
    private List<Service> addedServicesList;


    //MATERIALS
    private TextView addedMaterialsLabel;
    private RecyclerView addedMaterialsRv;
    private Material selectedMaterial = null;
    private List<Material> materialList;
    private MaterialsAdapter materialsAdapter;
    private AddedMaterialAdapter addedMaterialAdapter;
    private List<Material> addedMaterialsList;

    //FAILURES
    private TextView addedFailuresLabel;
    private RecyclerView addedFailuresRv;
    private int selectedFailureID;
    private List<Failure> failureList;
    private FailureCause selectedFailureCause;
    private FailureCauseAdapter failureCauseAdapter;
    private RecyclerView failureCauseRv;
    private List<FailureCause> failureCauseList;
    private List<AddedFailuresAndCauses> addedFailuresAndCausesList;
    private AddedFailuresAndCausesAdapter addedFailuresAndCausesAdapter;
    private TextInputLayout failureCauseEtLay;
    private TextInputLayout failureCauseOtherCommLay;
    private TextInputEditText failureCauseOtherComm;
    private String failureCauseNote = "";
    private LinearLayout failureCauseOtherLayout;
    private TextView failureCauseLbl;
    private ImageView failureCauseOtherDelete;
    private TextView failureCauseOtherText;


    private TextView hasSignatureLabel;
    private TextView isReceiptPrintedLabel;
    private Button getCustomerSignatureBtn;
    private Button printReceiptBtn;
    private boolean isBarcodeScanned = false;
    private boolean isWorkOrderSigned = false;


    //PRODUCT
    private Product selectedProduct = null;
    private String productionDate = "";
    private String purchaseDate = "";

    private List<Product> productList;
    private ProductsAdapter productsAdapter;


    TextInputLayout textInputLayoutForHint;

    private String checkInID = "";

    int workOrderResultID;

    private BrokerSQLite db = new BrokerSQLite(this);
    private NetworkClass nc;

    private AlertDialog checkOutDialog;
    private AlertDialog loadingDialog;

    private RadioGroup isWorkOrderFinishedRg;
    private AppCompatRadioButton radioBtnYes, radioBtnNo;

    private WorkOrderResult workOrderResult;
    private String signatureNote;

    private boolean isReceiptPrinted;
    private boolean isDone = false;
    private int closedStatus = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        //views initialization
        init();
        checkIfDraftExists();
        initRoute(ROUTE_TAG);
        initAddedServices(ROUTE_TAG);
        initAddedMaterials(ROUTE_TAG);
        initAddedFailures(ROUTE_TAG);
        hideUIElementsOnStart();
        setListeners();

        //TODO Ovde dobiti workOrder iz baze po IDju koji se dobija klikom n aitem iz RVa.


        //TODO Ovde srediti pitanja za ExpadableListView

    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            db.open();

            isWorkOrderSigned = db.checkIfWorkOrderSigned(RouteActivity.selectedRouteID);
            isReceiptPrinted = db.checkIfWorkOrderPrintInfoExists(RouteActivity.selectedRouteID, workOrderResultID);


            setWorkOrderSignedLabel(isWorkOrderSigned);
            setWorkOrderPrintedLabel(isReceiptPrinted);


            db.close();
        } catch (SQLException e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(CHECK_IN_STATUS, isCheckedIn);
        outState.putString(CHECK_IN_ID, checkInID);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isCheckedIn = savedInstanceState.getBoolean(CHECK_IN_STATUS);
        checkInID = savedInstanceState.getString(CHECK_IN_ID);
        changeUIOnCheckIn();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            verifyPermissions();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorClass.handle(e, WorkOrderActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Utilities.showToast("Skeniranje prekinuto", WorkOrderActivity.this);
            } else {
                workOrderProductSrNum.setText(result.getContents());
                isBarcodeScanned = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (isCheckedIn) {
            showCheckOutDialog();
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.checkInBtn:
                try {
                    verifyPermissions();
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorClass.handle(e, WorkOrderActivity.this);
                }
                break;

            case R.id.scanBarcodeBtn:
                startBarcodeScanning();
                break;

            case R.id.takePictureBtn:
                startActivity(new Intent(WorkOrderActivity.this, TakePictureActivity.class));
                break;

            case R.id.servicesBtn:
                showSelectServiceDialog();
                break;
            case R.id.materialsBtn:
                showSelectMaterialDialog();
                break;
            case R.id.failuresBtn:
                showSelectFailureDialog();
                break;
            case R.id.getCustomerSignatureBtn:
                Intent intent = new Intent(WorkOrderActivity.this, SignatureActivity.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY_CODE);
                break;

            case R.id.workOrderChangeProductBtn:
                showChangeProductDialog();
                break;

            case R.id.sendDataToServerBtn:
                validateDataBeforeSending();
                break;

            case R.id.workOrderProdDocBtn:

                if (selectedProduct != null) {
                    String selProd = new Gson().toJson(selectedProduct);
                    Intent intent1 = new Intent(WorkOrderActivity.this, ProductDocumentationActivity.class);
                    intent1.putExtra("selectedProduct", selProd);
                    startActivity(intent1);
                }

                break;

            case R.id.workOrderProdHistBtn:
                if (TextUtils.isEmpty(workOrderProductSrNum.getText().toString().trim()) || workOrderProductSrNum.getText().toString().trim().length() != 17) {
                    Utilities.showToast("Serijski broj artikla nije unet pravilno", WorkOrderActivity.this);
                } else{
                    if(Utilities.isOnline(WorkOrderActivity.this)) {
                        Intent intentHistory = new Intent(WorkOrderActivity.this, HistoryActivity.class);
                        intentHistory.putExtra("SerialNumber", workOrderProductSrNum.getText().toString().trim());
                        startActivity(intentHistory);
                    }else{
                        Utilities.showToast("Nije vam aktivna internet konekcija", WorkOrderActivity.this);
                    }
                }

                    break;

            case R.id.printReceiptBtn:
                Intent intent2 = new Intent(WorkOrderActivity.this, PrintReportActivity.class);
                intent2.putExtra("WorkOrderID", RouteActivity.selectedRouteID);
                intent2.putExtra("WorkOrderResultID", workOrderResultID);
                boolean inWarr = workOrderCode.getText().toString().trim().startsWith("521");
                intent2.putExtra("InWarranty",inWarr);
                startActivity(intent2);

                break;

            case R.id.checkFailureForWO:

                showFailureFowWorkOrderDialog(route.getWorkOrderCode(), route.getWorkOrderID(), route.getMaterialTypeID(), route.getProductColorID());

                break;
            default:
                break;
        }
    }

    private void init() {


        basicContainerRelativeLay = findViewById(R.id.basicContainerRelativeLay);

        //TextView fields
        hasSignatureLabel = findViewById(R.id.hasSignatureLabel);
        isReceiptPrintedLabel = findViewById(R.id.isReceiptPrintedLabel);
        workOrderChangeProductBtn = findViewById(R.id.workOrderChangeProductBtn);


        //EditText fields

        //WORKORDER
        workOrderType = findViewById(R.id.workOrderType);
        workOrderCode = findViewById(R.id.workOrderCode);
        workOrderDate = findViewById(R.id.workOrderDate);
        warrantyCb = findViewById(R.id.warrantyCb);
        customerRejectedCb = findViewById(R.id.customerRejectedCb);
        //PRODUCT
        workOrderProductName = findViewById(R.id.workOrderProductName);
        workOrderProductCode = findViewById(R.id.workOrderProductCode);
        workOrderProductSrNum = findViewById(R.id.workOrderProductSerNum);
        //  workOrderProductPower = findViewById(R.id.workOrderProductPower);
        //workOrderProductProductionDate = findViewById(R.id.workOrderProductProductionDate);
        workOrderProductPurchaseDate = findViewById(R.id.workOrderProductPurchaseDate);
        workOrderProdDocBtn = findViewById(R.id.workOrderProdDocBtn);
        workOrderProdHistBtn = findViewById(R.id.workOrderProdHistBtn);
        workOrderProductColor = findViewById(R.id.workOrderProductColor);
        workOrderProductMatType = findViewById(R.id.workOrderProductMatType);

        //CLIENT
        workOrderCustomerName = findViewById(R.id.workOrderCustomerName);
        workOrderCustomerAddress = findViewById(R.id.workOrderCustomerAddress);
        workOrderCustomerAddressNo = findViewById(R.id.workOrderCustomerAddressNo);
        workOrderCustomerName.setFilters(getCustomerNameInputFilters());
        workOrderCustomerAddress.setFilters(getCustomerAddressInputFilters());
        //    workOrderCustomerAddressNo.setFilters(getCustomerAddressNoInputFilters());
        workOrderCustomerCity = findViewById(R.id.workOrderCustomerCity);
        workOrderCustomerPhone = findViewById(R.id.workOrderCustomerPhone);
        workOrderClientSubtitle = findViewById(R.id.workOrderClientSubtitle);
        escalatedCb = findViewById(R.id.escalatedCb);
        isLegalPersonCb = findViewById(R.id.isLegalPersonCb);
        //PARTNER
        workOrderPartnerName = findViewById(R.id.workOrderPartnerName);
        workOrderPartnerCode = findViewById(R.id.workOrderPartnerCode);
        workOrderPartnerAddress = findViewById(R.id.workOrderPartnerAddress);
        workOrderPartnerContact = findViewById(R.id.workOrderPartnerContact);
        partnerContainer = findViewById(R.id.partnerContainer);
        workOrderNote = findViewById(R.id.workOrderNote);
        workOrderDescription = findViewById(R.id.workOrderDescription);

        //Buttons
        checkInBtn = findViewById(R.id.checkInBtn);
        scanBarcodeBtn = findViewById(R.id.scanBarcodeBtn);
        takePictureBtn = findViewById(R.id.takePictureBtn);
        servicesBtn = findViewById(R.id.servicesBtn);
        materialsBtn = findViewById(R.id.materialsBtn);
        failuresBtn = findViewById(R.id.failuresBtn);
        getCustomerSignatureBtn = findViewById(R.id.getCustomerSignatureBtn);
        printReceiptBtn = findViewById(R.id.printReceiptBtn);
        sendDataToServerBtn = findViewById(R.id.sendDataToServerBtn);
        checkFailureForWO = findViewById(R.id.checkFailureForWO);

        //ExpandableListView
        expandableListView = findViewById(R.id.expandableListView);

        //Radio Group
        isWorkOrderFinishedRg = findViewById(R.id.isWorkOrderFinishedRg);
        radioBtnYes = findViewById(R.id.radioBtnYes);
        radioBtnNo = findViewById(R.id.radioBtnNo);

        //FailureCauseOther
        failureCauseOtherLayout = findViewById(R.id.failureCauseOtherLayout);
        failureCauseLbl = findViewById(R.id.failureCauseLbl);
        failureCauseOtherDelete = findViewById(R.id.failCauseOtherDelete);
        failureCauseOtherText = findViewById(R.id.failCauseOtherText);


        workOrderResult = new WorkOrderResult();


    }

    private void initRoute(int typeID) {
        db.open();

        if(typeID == ROUTE_TAG)
            route = db.getRouteByRouteID(RouteActivity.selectedRouteID);
        else if (typeID == DRAFT_TAG){
            //OVO JE ROUTE IZ DRAFTA ZATO SE POSTAVLJAJU SVA OVA POLJA
            route = db.getDraftRoute(workOrderResultID, RouteActivity.selectedRouteID, false);
            workOrderResultID = route.getWorkOrderResultID();
            workOrderProductSrNum.setText(route.getSerialNumber());
            purchaseDate = route.getProductPurchaseDate();
            productionDate = route.getProductProductionDate();
            selectedProduct = db.getProductByProductID(route.getProductID());
            workOrderProductPurchaseDate.setText(Utilities.changeDateFormatToLocalFormat(purchaseDate));
           // workOrderProductProductionDate.setText(Utilities.changeDateFormatToLocalFormat(productionDate));
            isWorkOrderSigned = route.isHasSignature();
            isReceiptPrinted = db.checkIfWorkOrderPrintInfoExists(route.getWorkOrderID(), route.getWorkOrderResultID());
            isEscaleted = route.isEscalated();
            isBarcodeScanned = route.isSerialNoScanned();
            closedStatus = route.getClosedStatus();
            failureCauseNote = route.getFailureCauseNote();
            customerRejectedCb.setChecked(route.isCustomerRejected());
            initAddedServices(typeID);
            initAddedMaterials(typeID);
            initAddedFailures(typeID);

            if(!addedServicesList.isEmpty())
                showUIElements(Constants.SHOW_UI_SERVICE_TAG);

            if(!addedMaterialsList.isEmpty())
                showUIElements(Constants.SHOW_UI_MATERIAL_TAG);

            if(!addedFailuresAndCausesList.isEmpty())
                showUIElements(Constants.SHOW_UI_FAILURE_TAG);

            if(!failureCauseNote.isEmpty()){
                failureCauseLbl.setVisibility(View.VISIBLE);
                failureCauseOtherLayout.setVisibility(View.VISIBLE);
                failureCauseOtherText.setText(failureCauseNote);
            }

            //todo srediti labelu za potpis, cb za problem, radio button za isClosed
            setWorkOrderSignedLabel(isWorkOrderSigned);
            setWorkOrderPrintedLabel(isReceiptPrinted);
            escalatedCb.setChecked(isEscaleted);
//            if(closedStatus == -1){
//                radioBtnYes.setChecked(false);
//                radioBtnNo.setChecked(false);
//            }else if(closedStatus == 1){
//                radioBtnYes.setChecked(true);
//                radioBtnNo.setChecked(false);
//            }else{
//                radioBtnYes.setChecked(false);
//                radioBtnNo.setChecked(true);
//            }

        }

        completedPollQuestionList = new ArrayList<>();
        if (route != null) {
            db.open();
            selectedProduct = db.getProductByProductID(route.getProductID());
            workOrderProductColor.setText(db.getColorNameByColorID(route.getProductColorID()));
            workOrderProductMatType.setText(getMaterialTypeNameByID(route.getMaterialTypeID()));
            db.close();

            if (route.isCustomerWarning()) {
                workOrderClientSubtitle.setTextColor(ContextCompat.getColorStateList(WorkOrderActivity.this, R.color.customer_warning_text_color));
                workOrderCustomerName.setTextColor(ContextCompat.getColorStateList(WorkOrderActivity.this, R.color.customer_warning_text_color));
            }

            isLegalPersonCb.setChecked(route.isLegalPerson());
            workOrderType.setText(route.getWorkOrderType());
            workOrderCode.setText(route.getWorkOrderCode());
            workOrderDate.setText(Utilities.changeDateFormatToLocalFormat(route.getPlannedDate()));
            if (route.getWorkOrderCode().startsWith("522")) {
                db.open();
                pollQuestionArrayList = db.getWorkOrderPollQuestions(route.getWorkOrderPollID());
                warrantyCb.setVisibility(View.GONE);
                setWorkOrderPollQuestions(pollQuestionArrayList);
                db.close();
            }
            warrantyCb.setChecked(route.isInWarranty());

            if(!route.isInWarranty())
                workOrderProductSrNum.setText(getResources().getString(R.string.customer_sr_num));

            workOrderProductName.setText(route.getProductName());
            workOrderProductCode.setText(route.getProductCode());

         //   workOrderProductModel.setText(route.getProductModel());
            //PITAJFilipa da li se ovo bese unosi rucno
            //  workOrderProductSrNum.setText();
            // workOrderProductPower.setText();

            workOrderCustomerName.setText(route.getCustomerName());
            workOrderCustomerAddress.setText(route.getCustomerAddress().toUpperCase());
            workOrderCustomerAddressNo.setText(route.getCustomerAddressNo());
            //Postavio sam filter ovde zato sto ako ga stavim u Init metodi, onda se broj ne upisuje. Mora prvo da se upise broj pa se onda stavlja filter.
            workOrderCustomerAddressNo.setFilters(getCustomerAddressNoInputFilters());
            workOrderCustomerCity.setText(route.getCustomerCity());
            workOrderCustomerPhone.setText(route.getCustomerPhone());

            //OVO SE POSTAVLJA KADA JE FIZICKO LICE PARTNER..TADA NEMA POTREBE DA SE VIDI PARTNER POSTO JE FIZICKO LICE ALFA PLAM. IDE PREKO IMENA POSTO KOD MOZDA MENJAJU JER CE DA MENJAJU BAZU PARTNERA
            if (TextUtils.equals("FIZIČKO LICE", route.getPartnerName().trim())) {
                partnerContainer.setVisibility(View.INVISIBLE);
            } else {
                partnerContainer.setVisibility(View.VISIBLE);
                workOrderPartnerName.setText(route.getPartnerName());
                workOrderPartnerCode.setText(route.getPartnerCode());
                workOrderPartnerAddress.setText(route.getPartnerAddress().toUpperCase());
                workOrderPartnerContact.setText(route.getPartnerContact());
            }

            workOrderDescription.setText(route.getWorkOrderDescription());
            workOrderNote.setText(route.getWorkOrderNote());
        }
        db.close();
    }

    private void initAddedServices(int typeID) {

        addedServicesLabel = findViewById(R.id.addedServicesLabel);
        addedServicesList = new ArrayList<>();
        addedServicesRv = findViewById(R.id.addedServicesRv);
        addedServicesRv.setHasFixedSize(false);
        addedServicesRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
        addedServicesRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this), WorkOrderActivity.this, addedServicesRv));
        if(typeID == DRAFT_TAG) {
             db.open();
            addedServicesList = db.getAddedServices(workOrderResultID, RouteActivity.selectedRouteID, selectedProduct.getProductID());
            addedServiceAdapter = new AddedServiceAdapter(WorkOrderActivity.this, addedServicesList, this, Constants.ADDED_TABLES_WO_TAG);
            addedServicesRv.setAdapter(addedServiceAdapter);
              db.close();
        }
    }

    private void initAddedMaterials(int typeID) {

        addedMaterialsLabel = findViewById(R.id.addedMaterialsLabel);
        addedMaterialsList = new ArrayList<>();
        addedMaterialsRv = findViewById(R.id.addedMaterialsRv);
        addedMaterialsRv.setHasFixedSize(false);
        addedMaterialsRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
        addedMaterialsRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this), WorkOrderActivity.this, addedMaterialsRv));
        if(typeID == DRAFT_TAG) {
            db.open();
            addedMaterialsList = db.getAddedMaterials(workOrderResultID, RouteActivity.selectedRouteID);
            addedMaterialAdapter = new AddedMaterialAdapter(WorkOrderActivity.this, addedMaterialsList, this, Constants.ADDED_TABLES_WO_TAG);
            addedMaterialsRv.setAdapter(addedMaterialAdapter);
            db.close();
        }
    }

    private void initAddedFailures(int typeID) {

        addedFailuresLabel = findViewById(R.id.addedFailuresLabel);
        addedFailuresRv = findViewById(R.id.addedFailuresRv);
        addedFailuresAndCausesList = new ArrayList<>();
        addedFailuresRv.setHasFixedSize(false);
        addedFailuresRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
        addedFailuresRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this), WorkOrderActivity.this, addedFailuresRv));
        if(typeID == DRAFT_TAG) {
            db.open();
            addedFailuresAndCausesList = db.getAddedFailuresAndCauses(workOrderResultID, RouteActivity.selectedRouteID);
            addedFailuresAndCausesAdapter = new AddedFailuresAndCausesAdapter(WorkOrderActivity.this, addedFailuresAndCausesList, this, Constants.ADDED_TABLES_WO_TAG);
            addedFailuresRv.setAdapter(addedFailuresAndCausesAdapter);
            db.close();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {

        //onClickListener assignment
        checkInBtn.setOnClickListener(this);
        scanBarcodeBtn.setOnClickListener(this);
        takePictureBtn.setOnClickListener(this);
        servicesBtn.setOnClickListener(this);
        materialsBtn.setOnClickListener(this);
        failuresBtn.setOnClickListener(this);
        getCustomerSignatureBtn.setOnClickListener(this);
        printReceiptBtn.setOnClickListener(this);
        workOrderChangeProductBtn.setOnClickListener(this);
        sendDataToServerBtn.setOnClickListener(this);
        workOrderProdDocBtn.setOnClickListener(this);
        workOrderProdHistBtn.setOnClickListener(this);
        checkFailureForWO.setOnClickListener(this);

        //Postavlja se textWatcher da bi znali da li je barkod kucan ili skeniran.
        workOrderProductSrNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isBarcodeScanned = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        workOrderProductPurchaseDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (workOrderProductPurchaseDate.getRight() - workOrderProductPurchaseDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDatePickerDialog(workOrderProductPurchaseDate);

                        return true;
                    }
                }
                return false;
            }

        });

//        workOrderProductProductionDate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getRawX() >= (workOrderProductProductionDate.getRight() - workOrderProductProductionDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        showDatePickerDialog(workOrderProductProductionDate);
//
//                        return true;
//                    }
//                }
//                return false;
//            }
//
//        });

        warrantyCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.open();
                if(!isChecked)
                    workOrderProductSrNum.setText(getResources().getString(R.string.customer_sr_num));
                else
                    workOrderProductSrNum.setText("");
                WorkOrderType workOrderTypeModel = db.getWorkOrderTypeByWarranty(isChecked);
                String newWorkOrderCode = getResources().getString(R.string.work_order_change_warranty, workOrderTypeModel.getTypeCode(), route.getWorkOrderCode().substring(3));
                db.workOrderCodeAndID_U(route.getWorkOrderID(), newWorkOrderCode, workOrderTypeModel.getWorkOrderTypeID(), isChecked);
                workOrderCode.setText(newWorkOrderCode);
                workOrderType.setText(workOrderTypeModel.getTypeName());
                Utilities.showToast("Izmenjen tip radnog naloga", WorkOrderActivity.this);
                db.close();
            }
        });

        escalatedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEscaleted = isChecked;
            }
        });

        radioBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && isReceiptPrinted) {
                        closedStatus = 1;
                        printReceiptBtn.setEnabled(false);
                    }else if(isChecked){
                        closedStatus = 1;
                        printReceiptBtn.setEnabled(true);
                    } else {
                        closedStatus = 0;
                        printReceiptBtn.setEnabled(false);
                    }


            }
        });
        radioBtnNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    closedStatus = 0;
                    printReceiptBtn.setEnabled(false);
                }else{
                    closedStatus = 1;
                    printReceiptBtn.setEnabled(true);
                }
            }
        });

        failureCauseOtherDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failureCauseNote = "";
                failureCauseLbl.setVisibility(View.GONE);
                failureCauseOtherLayout.setVisibility(View.GONE);
                disableChangeProductButton();
            }
        });

        customerRejectedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               isCustomerRejectedChanged(isChecked);
            }
        });
    }

    @Override
    public void onStartSendingData() {
        showLoadingDialog();
    }

    @Override
    public void onSuccessSendingData(int dataType) {

        //dataType 0 = radni nalog   1 = sve ostalo
        loadingDialog.cancel();
        Utilities.showToast("Podaci uspešno poslati na server!", WorkOrderActivity.this);
        if (dataType == Constants.SENDING_DATA_TYPE_WO) {
            sendDataToServerBtn.setEnabled(false);
            showCheckOutDialog();
        } else {
            finish();
        }
    }

    @Override
    public void onErrorSendingData(String errorMessage, int dataType) {
        loadingDialog.cancel();
        Utilities.writeErrorToFile(new Exception(errorMessage));
        Utilities.showToast("Došlo je do greške prilikom slanja podataka na server. Proverite Internet konekciju i pokušajte ponovo. GREŠKA: " + errorMessage, WorkOrderActivity.this);
        if (dataType == Constants.SENDING_DATA_TYPE_WO) {
            sendDataToServerBtn.setEnabled(false);
            showCheckOutDialog();
        }else {
            finish();
        }

    }

    @Override
    public void onServiceSelected(Service service) {
        selectedService = service;
        unitSpentEt.setText(service.getNormTime());
        textInputLayoutForHint.setHint(getResources().getString(R.string.time_unit, service.getUnitOfMeasure()));
        unitSpentEt.setError(null);
        unitSpentEt.requestFocus();
    }

    @Override
    public void onServiceNormClicked(Service serviceClicked) {
        db.open();
        ServiceNorm serviceNorm = db.getServiceNormForServiceID(serviceClicked.getServiceID(), selectedProduct.getProductID());
        db.close();
        if(serviceNorm!= null){
           showNormDialog(serviceClicked, serviceNorm);
        }else{
            Utilities.showToast("Za ovu uslugu ne postoji norma", WorkOrderActivity.this);
        }
    }

    @Override
    public void onServiceDeleted(Service service, int listSize) {
        try {
            db.open();
            db.addedService_D(workOrderResultID,RouteActivity.selectedRouteID, service.getServiceID());
            if (listSize < 1) hideUIElementsIfListEmpty(Constants.SHOW_UI_SERVICE_TAG);
            db.close();
        } catch (Exception ex) {
            ErrorClass.handle(ex, WorkOrderActivity.this);
        }
    }

    @Override
    public void onFailureAndCauseDeleted(AddedFailuresAndCauses addedFailuresAndCauses, int listSize) {
        db.open();
        db.addedFailuresAndCauses_D(workOrderResultID, RouteActivity.selectedRouteID, addedFailuresAndCauses.getFailureID(), addedFailuresAndCauses.getFailureCauseID());
        if (listSize < 1) hideUIElementsIfListEmpty(Constants.SHOW_UI_FAILURE_TAG);
        db.close();
    }

    @Override
    public void onMaterialDeleted(Material material, int listSize) {
        try {
            db.open();
            db.addedMaterial_D(workOrderResultID, RouteActivity.selectedRouteID, material.getMaterialID());
            if (listSize < 1) hideUIElementsIfListEmpty(Constants.SHOW_UI_MATERIAL_TAG);
            db.close();
        } catch (Exception ex) {
            ErrorClass.handle(ex, WorkOrderActivity.this);
        }
    }

    @Override
    public void onMaterialSelected(Material material) {
        selectedMaterial = material;
        unitSpentEt.setText("");
        textInputLayoutForHint.setHint(getResources().getString(R.string.quantity_unit, material.getUnitOfMeasure()));
        unitSpentEt.setError(null);
        unitSpentEt.requestFocus();
    }

    @Override
    public void onProductSelected(Product product) {
        selectedProduct = product;

    }

    @Override
    public void onFailureCauseSelected(FailureCause failureCause) {
        selectedFailureCause = failureCause;
    }




    private void setWorkOrderPollQuestions(ArrayList<PollQuestion> pollQuestionArrayList) {

        ArrayList<String> groupTitle = new ArrayList<>();
        groupTitle.add("Pitanja");

        questionAdapter = new PollQuestionAdapter(this, groupTitle, pollQuestionArrayList);
        expandableListView.setAdapter(questionAdapter);

        setExpandableListViewHeight(expandableListView, -1);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setExpandableListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }

    private void startBarcodeScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Skeniraj barkod proizvoda |  Blic: pojačaj/utišaj tasteri");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

    }

    private void setExpandableListViewHeight(ExpandableListView listView, int group) {
        PollQuestionAdapter listAdapter = (PollQuestionAdapter) listView.getExpandableListAdapter();
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

    private void onCheckInBtnClicked() throws Exception {

        Utilities.CheckInResult rez;
        rez = Utilities.getLastBestLocation(WorkOrderActivity.this);

        if (rez == null) {
            DialogBuilder.showOkDialogWithCallback(WorkOrderActivity.this, "Upozorenje", "Problem sa GPS lokacijom", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
        }


        if (rez.getFirst().equals("success") && !rez.getSecond().isEmpty()) {
            CheckIn();


        } else if (rez.getFirst().equals("failure")
                && rez.getSecond().equals("NIJE UKLJUCEN GPS!")) {
            try {
                DialogBuilder.showOkDialogWithCallback(WorkOrderActivity.this, "Upozorenje", "Morate uključiti GPS!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(
                                new Intent(
                                        android.provider.Settings.ACTION_SETTINGS),
                                0);
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
        } else if (rez.getFirst().equals("failure")
                && rez.getSecond().equals("bez koordinata")) {
            CheckIn();


        } else {
            return;
        }
    }

    private void CheckIn() throws Exception {
        db.open();

        //TODO Ovde provera da li je na ruti
        //	isInRoute = db.getInRouteStatus(Route.selectedObjectID, Route.routeID);


        if (isCheckedIn) {
            showCheckOutDialog();
        } else {
            isCheckedIn = true;
            changeUIOnCheckIn();
            checkInID = Utilities.getCheckInID(RouteActivity.selectedRouteID, MainActivity.employeeID);
            db.CheckIn_I(checkInID, RouteActivity.selectedRouteID, MainActivity.employeeID,
                    isCheckedIn, "","",  isInRoute);


            //Znaci da postoji draft
            if(workOrderResultID != -1){
                DialogBuilder.showInfoDialog(WorkOrderActivity.this, "Postoji draft", "Postoji draft za ovaj radni nalog. Da li želite da nastavite rad?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initRoute(DRAFT_TAG);
                    }
                });
            }else {

                //TODO Ovde ide kreiranje workOrderResult cisto da bi se dobio wokrOrderResultID koji ce se koristiti kasnije

                db.workOrderResult_I(RouteActivity.selectedRouteID,
                        selectedProduct.getProductID(),
                        "", purchaseDate, productionDate,
                        workOrderCustomerName.getText().toString().trim(),
                        workOrderCustomerAddress.getText().toString().trim(),
                        workOrderCustomerAddressNo.getText().toString().trim(),
                        workOrderCustomerCity.getText().toString().trim(),
                        workOrderCustomerPhone.getText().toString().trim(),
                        workOrderNote.getText().toString().trim(),
                        workOrderDescription.getText().toString().trim(),
                        signatureNote,failureCauseNote, isEscaleted, isBarcodeScanned, isWorkOrderSigned, closedStatus);

                workOrderResultID = db.getWorkOrderResultID(RouteActivity.selectedRouteID);
            }

            nc = new NetworkClass(WorkOrderActivity.this, this);
        }
    }


    private void changeUIOnCheckIn() {
        int whiteColor = getResources().getColor(R.color.blue_gray);
        ColorStateList csl = ColorStateList.valueOf(whiteColor);

        if (!isCheckedIn) {
            checkInBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.checked_out_icon, 0, 0, 0);
            checkInBtn.setCompoundDrawablePadding(8);
            checkInBtn.setText(getResources().getString(R.string.check_in));
        } else {
            checkInBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.checked_in_icon, 0, 0, 0);
            checkInBtn.setCompoundDrawablePadding(8);
            checkInBtn.setText(getResources().getString(R.string.check_out));
        }

        setViewAndChildrenEnabled(basicContainerRelativeLay, true, csl);
        if (route.getWorkOrderCode().startsWith("522"))
            expandableListView.setVisibility(View.VISIBLE);
        setEditTextFocusable();
        disableChangeProductButton();
        printReceiptBtn.setEnabled(false);
    }

    private void verifyPermissions() throws Exception {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            };

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[3]) == PackageManager.PERMISSION_GRANTED
            ) {
                onCheckInBtnClicked();


            } else {
                ActivityCompat.requestPermissions(WorkOrderActivity.this, permissions, REQUEST_PERMISSION_CODE);
            }


        }
    }

    private void showCheckOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.dialog_check_out, null);
        builder.setView(view);

        AppCompatSpinner spinner = view.findViewById(R.id.checkOutDialogSpinner);
        final AppCompatEditText editText = view.findViewById(R.id.checkOutDialogEditText);
        Button cancelBtn = view.findViewById(R.id.checkOutDialogCancelBtn);
        Button submitBtn = view.findViewById(R.id.checkOutDialogSubmitBtn);
        final TextInputEditText checkOutDialogKmEditText = view.findViewById(R.id.checkOutDialogKmEditText);
        if(isDone) {
            cancelBtn.setEnabled(false);
            cancelBtn.setTextColor(ContextCompat.getColor(WorkOrderActivity.this, R.color.light_gray));
        }
        final String[] commentsArray;
        try {
            db.open();
            commentsArray = db.getCheckOutComments();
            db.close();
            spinner.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_selectable_list_item, commentsArray) {
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {

                    TextView textView = (TextView) super.getDropDownView(position,
                            convertView, parent);
                    textView.setTextSize(16);
                    textView.setPadding(5, 5, 5, 5);
                    textView.setText(commentsArray[position]);
                    return textView;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position,
                            convertView, parent);
                    textView.setTextSize(16);
                    textView.setTextColor(ContextCompat.getColor(WorkOrderActivity.this, R.color.white));
                    textView.setPadding(5, 5, 5, 5);
                    textView.setText(commentsArray[position]);
                    return textView;
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editText.setText(commentsArray[position]);
                    editText.setError(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }

        checkOutDialog = builder.create();
        checkOutDialog.setCancelable(false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutDialog.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Objects.requireNonNull(editText.getText()).toString())) {
                    try {
                        if (!TextUtils.isEmpty(Objects.requireNonNull(checkOutDialogKmEditText.getText()).toString())) {
                            onSubmitButtonClicked(editText.getText().toString(),checkOutDialogKmEditText.getText().toString() );
                        }else{
                            checkOutDialogKmEditText.setError("Morate uneti kilometražu!");
                        }
                    } catch (Exception e) {
                        Utilities.writeErrorToFile(e);
                    }
                } else
                    editText.setError("Morate uneti komentar za CheckOut ili odaberite jedan iz liste!");
            }
        });


        checkOutDialog.show();
    }

    private void showNoSignatureDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog noSigDialog;
        View view = getLayoutInflater().inflate(R.layout.dialog_no_signature_comment, null);
        builder.setView(view);
        final AppCompatSpinner spinner = view.findViewById(R.id.noSigDialogSpinner);
        Button cancelBtn = view.findViewById(R.id.noSigDialogCancelBtn);
        Button submitBtn = view.findViewById(R.id.noSigDialogSubmitBtn);
        try {
            db.open();
            final String[] commentsArray = db.getNoSigComments();
            db.close();

            spinner.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_selectable_list_item, commentsArray) {
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {

                    TextView textView = (TextView) super.getDropDownView(position,
                            convertView, parent);
                    textView.setTextSize(16);
                    textView.setPadding(5, 5, 5, 5);
                    textView.setText(commentsArray[position]);
                    return textView;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position,
                            convertView, parent);
                    textView.setTextSize(16);
                    textView.setTextColor(ContextCompat.getColor(WorkOrderActivity.this, R.color.white));
                    textView.setPadding(5, 5, 5, 5);
                    textView.setText(commentsArray[position]);
                    return textView;
                }
            });


            noSigDialog = builder.create();
            noSigDialog.setCancelable(false);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noSigDialog.dismiss();
                }
            });

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spinner.getSelectedItemPosition() > 0) {
                        signatureNote = commentsArray[spinner.getSelectedItemPosition()];
                        validateDataBeforeSending();
                        noSigDialog.cancel();
                    } else {
                        Utilities.showToast("Morate odabrati razlog", WorkOrderActivity.this);
                    }
                }
            });

            noSigDialog.show();
        } catch (Exception e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }
    }

    private void onSubmitButtonClicked(String comment, String mileage) {

        try {
            db.open();
            if (db.CheckIn_I(checkInID, RouteActivity.selectedRouteID, MainActivity.employeeID,
                    false, comment, mileage, isInRoute)) {

                isCheckedIn = false;
                changeUIOnCheckIn();
                if(!isDone)
                updateWorkOrderResultOnSendOrCheckOut(isDone);
                checkOutDialog.cancel();
                nc.sendCheckInToServer();

            } else {
                Utilities.showToast("Došlo je do greške pri upisu CheckOut-a u bazu. Pokušajte ponovo", WorkOrderActivity.this);
                checkOutDialog.cancel();
            }


            db.close();
        } catch (Exception e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }
    }

    private void showSelectServiceDialog() {

        try {
            db.open();
            final AlertDialog servicesDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_services, null);
            builder.setView(view);
            AppCompatCheckBox allServicesCb = view.findViewById(R.id.allServicesCb);
            allServicesCb.setVisibility(View.VISIBLE);
            final EditText servicesEt = view.findViewById(R.id.servicesEt);
            textInputLayoutForHint = view.findViewById(R.id.unitSpentLay);
            unitSpentEt = view.findViewById(R.id.unitSpentEt);
            final RecyclerView servicesRv = view.findViewById(R.id.servicesRv);
            servicesRv.setHasFixedSize(true);
            servicesRv.setLayoutManager(new LinearLayoutManager(WorkOrderActivity.this));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(servicesRv.getContext(), DividerItemDecoration.VERTICAL);
            servicesRv.addItemDecoration(dividerItemDecoration);
            Button servicesCancelBtn = view.findViewById(R.id.servicesCancelBtn);
            Button servicesChooseBtn = view.findViewById(R.id.servicesChooseBtn);
            serviceList = db.getWorkOrderServiceList(RouteActivity.selectedRouteID, selectedProduct.getProductID());
            servicesAdapter = new ServicesAdapter(WorkOrderActivity.this, serviceList, WorkOrderActivity.this);
            servicesRv.setAdapter(servicesAdapter);
            servicesDialog = builder.create();
            servicesDialog.show();

            servicesCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    servicesDialog.dismiss();
                }
            });

            servicesChooseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (servicesAdapter.getSelectedPosition() == -1 || selectedService == null) {
                        Utilities.showToast("Niste odabrali ni jednu uslugu!", WorkOrderActivity.this);
                    } else if (TextUtils.isEmpty(unitSpentEt.getText().toString())) {
                        unitSpentEt.setError("Niste uneli vrednost!");
                    } else {

                        try {

                            db.open();
                            db.addedService_IU(
                                    workOrderResultID,
                                    RouteActivity.selectedRouteID,
                                    selectedService.getServiceID(),
                                    selectedService.getServiceName(),
                                    selectedService.getUnitOfMeasure(),
                                    (int)Double.parseDouble(unitSpentEt.getText().toString())
                            );

                            addedServicesList = new ArrayList<>();
                            addedServicesList = db.getAddedServices(workOrderResultID, RouteActivity.selectedRouteID, selectedProduct.getProductID());
                            addedServiceAdapter = new AddedServiceAdapter(WorkOrderActivity.this, addedServicesList, WorkOrderActivity.this, Constants.ADDED_TABLES_WO_TAG);
                            addedServicesRv.setAdapter(addedServiceAdapter);
                            if (addedServicesLabel.getVisibility() == View.GONE)
                                showUIElements(Constants.SHOW_UI_SERVICE_TAG);
                            unitSpentEt.setText("");
                            db.close();
                        } catch (Exception ex) {
                            ErrorClass.handle(ex, WorkOrderActivity.this);
                        }
                        Utilities.showToast("Usluga dodata", WorkOrderActivity.this);
                        servicesDialog.dismiss();
                    }
                }
            });

            servicesEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    servicesAdapter.setSelectedPosition(RecyclerView.NO_POSITION);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterService(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            allServicesCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        db.open();
                        unitSpentEt.setText("");
                        servicesEt.setText("");
                        serviceList = db.getServiceList(selectedProduct.getProductID());
                        servicesAdapter = new ServicesAdapter(WorkOrderActivity.this, serviceList, WorkOrderActivity.this);
                        servicesRv.setAdapter(servicesAdapter);
                        db.close();
                    } else {
                        db.open();
                        unitSpentEt.setText("");
                        servicesEt.setText("");
                        serviceList = db.getWorkOrderServiceList(RouteActivity.selectedRouteID, selectedProduct.getProductID());
                        servicesAdapter = new ServicesAdapter(WorkOrderActivity.this, serviceList, WorkOrderActivity.this);
                        servicesRv.setAdapter(servicesAdapter);
                        db.close();
                    }
                }
            });

            db.close();
        } catch (Exception ex) {
            ErrorClass.handle(ex, WorkOrderActivity.this);
        }

    }


    private void filterService(String text){

        List<Service> newList = new ArrayList<>();

        for(Service model:serviceList ){

            if(model.getServiceName().toLowerCase().contains(text.toLowerCase()) || model.getServiceCode().toLowerCase().contains(text.toLowerCase())){
                newList.add(model);
            }
        }
        servicesAdapter.filterList(newList);
    }

    private void showSelectMaterialDialog() {

        try {
            db.open();
            final AlertDialog materialDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_services, null);
            builder.setView(view);
            EditText materialsEt = view.findViewById(R.id.servicesEt);
            unitSpentEt = view.findViewById(R.id.unitSpentEt);
            textInputLayoutForHint = view.findViewById(R.id.unitSpentLay);
            textInputLayoutForHint.setHint(getResources().getString(R.string.quantity));
            materialsEt.setHint(getResources().getString(R.string.material_search));
            RecyclerView materialsRv = view.findViewById(R.id.servicesRv);
            materialsRv.setHasFixedSize(true);
            materialsRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
          //  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(materialsRv.getContext(), DividerItemDecoration.VERTICAL);
            materialsRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this),WorkOrderActivity.this ,materialsRv));
            Button materialCancelBtn = view.findViewById(R.id.servicesCancelBtn);
            Button materialChooseBtn = view.findViewById(R.id.servicesChooseBtn);
            materialList = db.getMaterialList(selectedProduct.getProductID());
            materialsAdapter = new MaterialsAdapter(WorkOrderActivity.this, materialList, this);
            materialsRv.setAdapter(materialsAdapter);
            materialDialog = builder.create();
            materialDialog.show();

            materialCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                }
            });

            materialChooseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (materialsAdapter.getSelectedPosition() == -1 || selectedMaterial == null) {
                        Utilities.showToast("Niste odabrali ni jedan materijal!", WorkOrderActivity.this);
                    } else if (TextUtils.isEmpty(unitSpentEt.getText().toString())) {
                        unitSpentEt.setError("Niste uneli vrednost!");
                    } else {
                        try {

                            db.open();
                            db.addedMaterial_IU(
                                    workOrderResultID,
                                    RouteActivity.selectedRouteID,
                                    selectedMaterial.getMaterialID(),
                                    selectedMaterial.getMaterialName(),
                                    selectedMaterial.getUnitOfMeasure(),
                                    Integer.valueOf(unitSpentEt.getText().toString())
                            );

                            addedMaterialsList = new ArrayList<>();
                            addedMaterialsList = db.getAddedMaterials(workOrderResultID, RouteActivity.selectedRouteID);
                            addedMaterialAdapter = new AddedMaterialAdapter(WorkOrderActivity.this, addedMaterialsList, WorkOrderActivity.this, Constants.ADDED_TABLES_WO_TAG);
                            addedMaterialsRv.setAdapter(addedMaterialAdapter);
                            if (addedMaterialsLabel.getVisibility() == View.GONE)
                                showUIElements(Constants.SHOW_UI_MATERIAL_TAG);
                            unitSpentEt.setText("");
                            materialDialog.dismiss();
                            db.close();

                        } catch (Exception ex) {
                            ErrorClass.handle(ex, WorkOrderActivity.this);
                        }
                        Utilities.showToast("Materijal dodat", WorkOrderActivity.this);

                    }
                }
            });



            materialsEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    materialsAdapter.setSelectedPosition(RecyclerView.NO_POSITION);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterMaterial(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            db.close();


        } catch (Exception ex) {
            ErrorClass.handle(ex, WorkOrderActivity.this);
        }

    }

    private void filterMaterial(String text){

        List<Material> newList = new ArrayList<>();

        for(Material model:materialList ){

            if(model.getMaterialName().toLowerCase().contains(text.toLowerCase()) || model.getMaterialCode().toLowerCase().contains(text.toLowerCase())){
                newList.add(model);
            }
        }
        materialsAdapter.filterList(newList);
    }


    private void showSelectFailureDialog() {
        try {
            db.open();
            final AlertDialog failureDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_failures, null);
            builder.setView(view);
            final AppCompatSpinner failuresSpinner = view.findViewById(R.id.failuresSpinner);
            AppCompatCheckBox allFailuresCb = view.findViewById(R.id.allFailuresCb);
            final TextInputEditText failureCauseEt = view.findViewById(R.id.failureCauseEt);
            failureCauseRv = view.findViewById(R.id.failureCauseRv);
            failureCauseEtLay = view.findViewById(R.id.failureCauseEtLay);
            failureCauseOtherCommLay = view.findViewById(R.id.failureCauseOtherCommLay);
            failureCauseOtherComm = view.findViewById(R.id.failureCauseOtherComm);
            failureCauseRv.setHasFixedSize(true);
            failureCauseRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
            failureCauseRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this), WorkOrderActivity.this, failureCauseRv));
            Button failuresCancelBtn = view.findViewById(R.id.failuresCancelBtn);
            Button failuresChooseBtn = view.findViewById(R.id.failuresChooseBtn);
            failureList = db.getWorkOrderFailureList(RouteActivity.selectedRouteID);
            setFailureSpinner(failuresSpinner, failureList);


            failureDialog = builder.create();
            failureDialog.show();

            failuresCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    failureDialog.dismiss();
                }
            });

            failureCauseEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (failureCauseAdapter != null)
                        failureCauseAdapter.setSelectedPosition(RecyclerView.NO_POSITION);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (failureCauseAdapter != null)
                        filterFailureCause(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            allFailuresCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        db.open();

                        if (isChecked) {
                            failureList = db.getFailureList(selectedProduct.getProductID());
                            setFailureSpinner(failuresSpinner, failureList);
                        } else {
                            failureList = db.getWorkOrderFailureList(RouteActivity.selectedRouteID);
                            setFailureSpinner(failuresSpinner, failureList);

                        }
                        selectedFailureCause = null;
                        failureCauseEt.setText("");
                        failureCauseEt.clearFocus();

                        db.close();
                    } catch (SQLException e) {
                        ErrorClass.handle(e, WorkOrderActivity.this);
                    }
                }

            });


            failuresChooseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedFailureID == -1)
                        Utilities.showToast("Niste odabrali kvar", WorkOrderActivity.this);
                    else {
                        db.open();
                        String failureCode = db.getFailureCodeByID(selectedFailureID).trim();
                        db.close();
                        if(TextUtils.equals(failureCode, "999")){
                            if(failureCauseOtherComm.getText().toString().isEmpty()){
                                failureCauseOtherComm.setError("Niste uneli tekst");
                            }else{
                                failureCauseNote = failureCauseOtherComm.getText().toString();
                                failureCauseLbl.setVisibility(View.VISIBLE);
                                failureCauseOtherLayout.setVisibility(View.VISIBLE);
                                failureCauseOtherText.setText(failureCauseNote);
                                disableChangeProductButton();
                                Utilities.showToast("Dodat komentar za uzrok kvara", WorkOrderActivity.this);
                                failureDialog.dismiss();

                            }
                        }
                        else if (selectedFailureCause == null || failureCauseAdapter.getSelectedPosition() == -1)
                            Utilities.showToast("Niste odabrali uzrok kvara", WorkOrderActivity.this);
                        else {
                            db.open();
                            db.addedFailuresAndCauses_IU(workOrderResultID, RouteActivity.selectedRouteID, selectedFailureID, selectedFailureCause.getFailureCauseID());

                            addedFailuresAndCausesList = new ArrayList<>();
                            addedFailuresAndCausesList = db.getAddedFailuresAndCauses(workOrderResultID, RouteActivity.selectedRouteID);
                            addedFailuresAndCausesAdapter = new AddedFailuresAndCausesAdapter(WorkOrderActivity.this, addedFailuresAndCausesList, WorkOrderActivity.this, Constants.ADDED_TABLES_WO_TAG);
                            addedFailuresRv.setAdapter(addedFailuresAndCausesAdapter);
                            if (addedFailuresLabel.getVisibility() == View.GONE)
                                showUIElements(Constants.SHOW_UI_FAILURE_TAG);
                            Utilities.showToast("Kvar i uzrok kvara dodati", WorkOrderActivity.this);
                            failureDialog.dismiss();

                            db.close();

                        }
                    }
                }
            });

            db.close();
        } catch (Exception ex) {
            ErrorClass.handle(ex, WorkOrderActivity.this);
        }
    }

    private void setFailureSpinner(final AppCompatSpinner failuresSpinner, final List<Failure> failureList) {
        failuresSpinner.setAdapter(new ArrayAdapter<Failure>(WorkOrderActivity.this, android.R.layout.simple_selectable_list_item, failureList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Failure failure = super.getItem(position);
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextSize(16);
                textView.setTextColor(ContextCompat.getColor(WorkOrderActivity.this, R.color.white));
                textView.setText(Objects.requireNonNull(failure).getFailureName());
                selectedFailureID = failure.getFailureID();

                return textView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Failure failure = super.getItem(position);
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextSize(16);
                textView.setTextColor(ContextCompat.getColor(WorkOrderActivity.this, R.color.white));
                textView.setText(Objects.requireNonNull(failure).getFailureName());
                selectedFailureID = failure.getFailureID();
                return textView;
            }
        });

        failuresSpinner.setSelection(0);
        failuresSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    db.open();
                    selectedFailureID = failureList.get(position).getFailureID();
                   if(TextUtils.equals(db.getFailureCodeByID(selectedFailureID).trim(), "999")){

                       failureCauseRv.setVisibility(View.INVISIBLE);
                       failureCauseEtLay.setVisibility(View.INVISIBLE);
                       failureCauseOtherCommLay.setVisibility(View.VISIBLE);


                    }else {
                       failureCauseRv.setVisibility(View.VISIBLE);
                       failureCauseEtLay.setVisibility(View.VISIBLE);
                       failureCauseOtherCommLay.setVisibility(View.INVISIBLE);
                       failureCauseList = db.getFailureCauseList(selectedFailureID, selectedProduct.getProductID());

                       failureCauseAdapter = new FailureCauseAdapter(WorkOrderActivity.this, failureCauseList, failureCauseList, WorkOrderActivity.this);
                       failureCauseRv.setAdapter(failureCauseAdapter);
                   }
                    db.close();
                } catch (SQLException e) {
                    ErrorClass.handle(e, WorkOrderActivity.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void filterFailureCause(String text){

        List<FailureCause> newList = new ArrayList<>();

        for(FailureCause model:failureCauseList ){

            if(model.getFailureCauseName().toLowerCase().contains(text.toLowerCase()) || model.getFailureCauseCode().toLowerCase().contains(text.toLowerCase())){
                newList.add(model);
            }
        }
        failureCauseAdapter.filterList(newList);
    }

    private void showChangeProductDialog() {

        try {
            db.open();
            final AlertDialog productDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_services, null);
            builder.setView(view);
            EditText productsEt = view.findViewById(R.id.servicesEt);
            textInputLayoutForHint = view.findViewById(R.id.unitSpentLay);
            textInputLayoutForHint.setVisibility(View.GONE);
            productsEt.setHint(getResources().getString(R.string.product_search));
            RecyclerView productsRv = view.findViewById(R.id.servicesRv);
            productsRv.setHasFixedSize(true);
            productsRv.setLayoutManager(new LinearLayoutManager(WorkOrderActivity.this));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productsRv.getContext(), DividerItemDecoration.VERTICAL);
            productsRv.addItemDecoration(dividerItemDecoration);
            Button productCancelBtn = view.findViewById(R.id.servicesCancelBtn);
            Button productChooseBtn = view.findViewById(R.id.servicesChooseBtn);
            productList = db.getProductList();
             productsAdapter = new ProductsAdapter(WorkOrderActivity.this, productList, this);
            productsRv.setAdapter(productsAdapter);
            productDialog = builder.create();
            productDialog.show();
            productCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productDialog.dismiss();
                }
            });

            productChooseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productsAdapter.getSelectedPosition() == -1 || selectedProduct == null) {
                        Utilities.showToast("Niste odabrali ni jedan proizvod!", WorkOrderActivity.this);
                    } else {
                        try {
                            workOrderProductName.setText(selectedProduct.getProductName());
                            workOrderProductCode.setText(selectedProduct.getProductCode());
                        //    workOrderProductModel.setText(selectedProduct.getModel());
                            Utilities.showToast("Proizvod izmenjen.", WorkOrderActivity.this);
                            productDialog.dismiss();
                        } catch (Exception ex) {
                            ErrorClass.handle(ex, WorkOrderActivity.this);
                        }
                    }
                }
            });

            productsEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    productsAdapter.setSelectedPosition(RecyclerView.NO_POSITION);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterProduct(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            db.close();
        } catch (SQLException e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        } catch (Resources.NotFoundException e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }

    }

    private void filterProduct(String text){

        List<Product> newList = new ArrayList<>();

        for(Product model:productList ){

            if(model.getProductName().toLowerCase().contains(text.toLowerCase()) || model.getProductCode().toLowerCase().contains(text.toLowerCase())){
                newList.add(model);
            }
        }
        productsAdapter.filterList(newList);
    }

    private void showLoadingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }

    private void hideUIElementsIfListEmpty(int tag) {
        if (tag == Constants.SHOW_UI_SERVICE_TAG) {
            addedServicesLabel.setVisibility(View.GONE);
            addedServicesRv.setVisibility(View.GONE);
        } else if (tag == Constants.SHOW_UI_MATERIAL_TAG) {
            addedMaterialsLabel.setVisibility(View.GONE);
            addedMaterialsRv.setVisibility(View.GONE);
        } else if (tag == Constants.SHOW_UI_FAILURE_TAG) {
            addedFailuresLabel.setVisibility(View.GONE);
            addedFailuresRv.setVisibility(View.GONE);
        }
        disableChangeProductButton();
    }

    private void showUIElements(int tag) {
        if (tag == Constants.SHOW_UI_SERVICE_TAG) {
            addedServicesLabel.setVisibility(View.VISIBLE);
            addedServicesRv.setVisibility(View.VISIBLE);
        } else if (tag == Constants.SHOW_UI_MATERIAL_TAG) {
            addedMaterialsLabel.setVisibility(View.VISIBLE);
            addedMaterialsRv.setVisibility(View.VISIBLE);
        } else if (tag == Constants.SHOW_UI_FAILURE_TAG) {
            addedFailuresLabel.setVisibility(View.VISIBLE);
            addedFailuresRv.setVisibility(View.VISIBLE);
        }
        disableChangeProductButton();
    }

    private void hideUIElementsOnStart() {
        if (addedServicesList.size() < 1) {
            addedServicesLabel.setVisibility(View.GONE);
            addedServicesRv.setVisibility(View.GONE);
        }

        if (addedMaterialsList.size() < 1) {
            addedMaterialsLabel.setVisibility(View.GONE);
            addedMaterialsRv.setVisibility(View.GONE);
        }

        if (addedFailuresAndCausesList.size() < 1) {
            addedFailuresLabel.setVisibility(View.GONE);
            addedFailuresRv.setVisibility(View.GONE);
        }

    }

    private void setViewAndChildrenEnabled(View view, boolean enabled, ColorStateList csl) {
        view.setEnabled(enabled);

        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = (TextInputLayout) view;
            textInputLayout.setDefaultHintTextColor(csl);
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled, csl);
            }
        }
    }

    private void setEditTextFocusable() {
        workOrderProductSrNum.setFocusable(true);
        //  workOrderProductPower.setFocusable(true);
        workOrderCustomerName.setFocusable(true);
        workOrderCustomerAddress.setFocusable(true);
        workOrderCustomerCity.setFocusable(true);
        workOrderCustomerPhone.setFocusable(true);
        workOrderPartnerName.setFocusable(true);
        workOrderPartnerCode.setFocusable(true);
        workOrderPartnerAddress.setFocusable(true);
        workOrderPartnerContact.setFocusable(true);
        workOrderNote.setFocusable(true);
        workOrderDescription.setFocusable(true);

        workOrderProductSrNum.setClickable(true);
        // workOrderProductPower.setClickable(true);
        workOrderCustomerName.setClickable(true);
        workOrderCustomerAddress.setClickable(true);
        workOrderCustomerCity.setClickable(true);
        workOrderCustomerPhone.setClickable(true);
        workOrderPartnerName.setClickable(true);
        workOrderPartnerCode.setClickable(true);
        workOrderPartnerAddress.setClickable(true);
        workOrderPartnerContact.setClickable(true);
        workOrderNote.setClickable(true);
        workOrderDescription.setClickable(true);

    }

    private void showDatePickerDialog(final TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(WorkOrderActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();
                SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                editText.setText(getResources().getString(R.string.selected_date, dayOfMonth, month + 1, year));
//                if (editText.getId() == R.id.workOrderProductProductionDate) {
//                    workOrderResult.setProductionDate(dateFormatGmt.format(date.getTime()));
//                    productionDate = dateFormatGmt.format(date.getTime()).split(" ")[0];
//
//                } else {
                    workOrderResult.setPurchaseDate(dateFormatGmt.format(date.getTime()));
                    purchaseDate = dateFormatGmt.format(date.getTime()).split(" ")[0];

                //}

            }
        }, year, month, dayOfMonth);

        datePickerDialog.getWindow().setLayout(300, 500);
        datePickerDialog.show();
    }

    private void checkIfDraftExists() {

        db.open();
        workOrderResultID = db.getWorkOrderResultID(RouteActivity.selectedRouteID);
        db.close();
    }

    private void validateDataBeforeSending() {


      //  isReceiptPrinted = true; //DELETEME izbrisi ovo postavljanje, ovo je samo za test
        boolean hasImage = false;
        try {
            db.open();
            hasImage = db.checkIfImageForWorkOrderExists(RouteActivity.selectedRouteID);
            db.close();
        } catch (SQLException e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
        }


        //TODO ovde ide provera pre pravljenja objekta WorkOrderResult za slanje (da li je potpisan, slikan, zavrsen itd)
        if (!validateAndFillListWorkOrderPollQuestions()) {
            Utilities.showToast("Niste odgovorili na sva pitanja iz liste", WorkOrderActivity.this);
        } else if (!validateCustomerData()) {
            Utilities.showToast("Niste uneli sve potrebne podatke o kupcu.", WorkOrderActivity.this);
        } else if (addedServicesRv.getVisibility() == View.GONE && addedMaterialsRv.getVisibility() == View.GONE && !workOrderCode.getText().toString().startsWith("522") && !customerRejectedCb.isChecked()) {
            Utilities.showToast("Morate uneti bar jedan materijal ili uslugu.", WorkOrderActivity.this);
        }else if (!hasImage) {
            Utilities.showToast("Morate napraviti bar jednu sliku za radni nalog", WorkOrderActivity.this);
        } else if (TextUtils.isEmpty(workOrderProductSrNum.getText().toString().trim()) || workOrderProductSrNum.getText().toString().trim().length() != 17) {
            Utilities.showToast("Serijski broj artikla nije unet pravilno", WorkOrderActivity.this);
        } else if (isWorkOrderFinishedRg.getCheckedRadioButtonId() == -1) {
            Utilities.showToast("Morate označiti da li je nalog završen ili ne", WorkOrderActivity.this);
        } else if (!isReceiptPrinted && radioBtnYes.isChecked() && !customerRejectedCb.isChecked()) {
            Utilities.showToast("Nije odštampan račun", WorkOrderActivity.this);
        }else if (!isWorkOrderSigned && signatureNote == null) {
            showNoSignatureDialog();
        } else {

            if (TextUtils.isEmpty(purchaseDate)) {
                int week = Integer.parseInt(workOrderProductSrNum.getText().toString().trim().substring(2, 4));
                if (week < 1 || week > 52) {
                    Utilities.showToast("Pogrešno unet serijski broj ili nije unet datum kupovine", WorkOrderActivity.this);
                    return;
                } else {
                    purchaseDate = formPurchaseDateFromSrNumber(workOrderProductSrNum.getText().toString().trim());
                    workOrderResult.setPurchaseDate(purchaseDate);
                }
            }
            if (TextUtils.isEmpty(productionDate)) {
                int week = Integer.parseInt(workOrderProductSrNum.getText().toString().trim().substring(2, 4));
                if (week < 1 || week > 52) {
                    Utilities.showToast("Pogrešno unet serijski broj ili nije unet datum proizvodnje", WorkOrderActivity.this);
                    return;
                } else {
                    productionDate = formPurchaseDateFromSrNumber(workOrderProductSrNum.getText().toString().trim());
                    workOrderResult.setProductionDate(productionDate);
                }
            }

            try {

                db.open();

                for (PollQuestion pollQuestion : completedPollQuestionList) {
                    db.addedPollQuestions_I(
                            workOrderResultID,
                            RouteActivity.selectedRouteID,
                            pollQuestion.getWorkOrderQuestionID(),
                            pollQuestion.getPollQuestionAnswer()
                    );

                }

                //todo ovde ide upadte naloga za slanje
                isDone = true;
                updateWorkOrderResultOnSendOrCheckOut(isDone);


                db.close();
            } catch (SQLException e) {
                ErrorClass.handle(e, WorkOrderActivity.this);
            } catch (Exception e) {
                ErrorClass.handle(e, WorkOrderActivity.this);
            }

            nc.sendWorkOrderResult();

        }
    }

    private void updateWorkOrderResultOnSendOrCheckOut(boolean isDone){
        db.open();
        db.workOrderResult_U(
                workOrderResultID,
                RouteActivity.selectedRouteID, selectedProduct.getProductID(),
                workOrderProductSrNum.getText().toString().trim(),
                purchaseDate, productionDate,
                workOrderCustomerName.getText().toString().trim(),
                workOrderCustomerAddress.getText().toString().trim(),
                workOrderCustomerAddressNo.getText().toString().trim(),
                workOrderCustomerCity.getText().toString().trim(),
                workOrderCustomerPhone.getText().toString().trim(),
                isLegalPersonCb.isChecked(),
                warrantyCb.isChecked(),
                customerRejectedCb.isChecked(),
                workOrderNote.getText().toString().trim(), workOrderDescription.getText().toString().trim(),
                signatureNote == null ? "" : signatureNote, failureCauseNote, isEscaleted, isBarcodeScanned, isWorkOrderSigned, closedStatus,
                isDone
        );
        db.close();
    }

    private String formPurchaseDateFromSrNumber(String serialNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf("20" + serialNumber.substring(0, 2)));
        cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(serialNumber.substring(2, 4)));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return sdf.format(cal.getTime());
    }


    //Method that set input filters for allCaps letters [0], filter for letters and space char only [1] and filter that does not allow serbian letters [2].
    private InputFilter[] getCustomerNameInputFilters() {
        InputFilter[] inputFilters = new InputFilter[3];
        inputFilters[0] = new InputFilter.AllCaps();

        inputFilters[1] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {

                        return "";
                    }
                }
                return null;
            }
        };

        inputFilters[2] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.toString().matches(".*[ĆČŠĐŽ].*")) {
                    Utilities.showToast("Karakteri Ć, Č, Š, Đ i Ž nisu dozvoljeni", WorkOrderActivity.this);
                    String[] resultArray = dest.toString().split(" ");
                    if (resultArray.length > 0) {
                        return resultArray[resultArray.length - 1];
                    } else {
                        return dest.toString();
                    }
                }
                return null;
            }
        };

        return inputFilters;

    }

    //Method that set input filters for  filter for letters and space char only [0] and filter that does not allow serbian letters [1].
    private InputFilter[] getCustomerAddressInputFilters() {
        InputFilter[] inputFilters = new InputFilter[2];


        inputFilters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {

                        return "";
                    }
                }
                return null;
            }
        };

        inputFilters[1] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.toString().matches(".*[ćčšđžĆČŠĐŽ].*")) {
                    Utilities.showToast("Karakteri Ć, Č, Š, Đ i Ž nisu dozvoljeni", WorkOrderActivity.this);
                    String[] resultArray = dest.toString().split(" ");
                    if (resultArray.length > 0) {
                        return resultArray[resultArray.length - 1];
                    } else {
                        return dest.toString();
                    }
                }
                return null;
            }
        };

        return inputFilters;

    }

    private InputFilter[] getCustomerAddressNoInputFilters() {
        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i)) && !TextUtils.equals(source, "/") && !Character.isLetter(source.charAt(i))) {

                        return "";
                    }
                }
                return null;
            }
        };

        return inputFilters;
    }

    private boolean validateCustomerData() {
        return !TextUtils.isEmpty(workOrderCustomerName.getText().toString()) &&
                !TextUtils.isEmpty(workOrderCustomerAddress.getText().toString()) &&
                !TextUtils.isEmpty(workOrderCustomerAddressNo.getText().toString()) &&
                !TextUtils.isEmpty(workOrderCustomerCity.getText().toString()) &&
                !TextUtils.isEmpty(workOrderCustomerPhone.getText().toString()) &&
                workOrderCustomerPhone.getText().toString().length() >= 8 &&
                workOrderCustomerPhone.getText().toString().length() <= 10;
    }

    private boolean validateAndFillListWorkOrderPollQuestions() {

        completedPollQuestionList.clear();

        try {
            if (!route.getWorkOrderCode().startsWith("522"))
                return true;


            for (int i = 0; i < questionAdapter.getGroupCount(); i++) {
                for (int k = 0; k < questionAdapter.getChildrenCount(i); k++) {


                    PollQuestion pollQuestion = (PollQuestion) questionAdapter.getChild(i, k);

                    if (pollQuestion.getPollQuestionAnswer() == null)
                        return false;
                    else if (TextUtils.isEmpty(pollQuestion.getPollQuestionAnswer()))
                        return false;
                    else
                        completedPollQuestionList.add(pollQuestion);

                }
            }
            return true;
        } catch (Exception e) {
            ErrorClass.handle(e, WorkOrderActivity.this);
            return false;
        }
    }

    private void setWorkOrderSignedLabel(boolean isWorkOrderSigned){
        if (isWorkOrderSigned) {
            String text = getString(R.string.hasSignature, "jeste");
            Spanned styledText = Html.fromHtml(text);
            hasSignatureLabel.setText(styledText);
        } else {
            String text = getString(R.string.hasSignature, "nije");
            Spanned styledText = Html.fromHtml(text);
            hasSignatureLabel.setText(styledText);
        }
    }

    private void setWorkOrderPrintedLabel(boolean isReceiptPrinted){
        if (isReceiptPrinted) {
            String text = getString(R.string.isPrinted, "jeste");
            Spanned styledText = Html.fromHtml(text);
            isReceiptPrintedLabel.setText(styledText);
            radioBtnYes.setChecked(true);
            radioBtnYes.setEnabled(false);
            radioBtnNo.setEnabled(false);


        } else {
            String text = getString(R.string.isPrinted, "nije");
            Spanned styledText = Html.fromHtml(text);
            isReceiptPrintedLabel.setText(styledText);
            radioBtnYes.setEnabled(true);
            radioBtnNo.setEnabled(true);
        }
    }

    private void showNormDialog(Service service, ServiceNorm serviceNorm){
        final AlertDialog normDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_norm, null);
        builder.setView(view);
        Resources resources = getResources();
        TextView normServiceTitle = view.findViewById(R.id.normServiceTitle);
        TextView normNameTv = view.findViewById(R.id.normNameTv);
        TextView normCodeTv = view.findViewById(R.id.normCodeTv);
        TextView normTimeTv = view.findViewById(R.id.normTimeTv);
        Button closeNormBtn = view.findViewById(R.id.closeNormBtn);
        normServiceTitle.setText(resources.getString(R.string.norm_service_title, service.getServiceName().trim()));
        normNameTv.setText(resources.getString(R.string.norm_name, serviceNorm.getNormName().trim()));
        normCodeTv.setText(resources.getString(R.string.norm_code, serviceNorm.getNormCode().trim()));
        normTimeTv.setText(resources.getString(R.string.norm_time, serviceNorm.getNormTime().trim(), service.getUnitOfMeasure().trim()));

        normDialog = builder.create();
        normDialog.show();

        closeNormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normDialog.dismiss();
            }
        });
    }

    private void showFailureFowWorkOrderDialog(String workOrderCode, int workOrderID, int productMaterialID, int productColorID){
        final AlertDialog failureDialog;
        FailureForWorkOrderAdapter adapter;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_failure_for_wo, null);
        builder.setView(view);
        Resources resources = getResources();
        RecyclerView failureForWoRv = view.findViewById(R.id.failureForWoRv);
        TextView failureForWoTitle = view.findViewById(R.id.failureForWoTitle);
        TextView failureForWoMatColor = view.findViewById(R.id.failureForWoMatColor);
        TextView failureForWoMatType = view.findViewById(R.id.failureForWoMatType);
        TextView noFailureForWoTv = view.findViewById(R.id.noFailureForWoTv);
        Button closeFailureForWoBtn = view.findViewById(R.id.closeFailureForWoBtn);
        failureForWoTitle.setText(resources.getString(R.string.failure_for_wo_title, workOrderCode));

        db.open();
        List<AddedFailuresAndCauses> failuresForWoList = db.getFailuresForWorkOrder(workOrderID);
        String colorName = db.getColorNameByColorID(productColorID);
        db.close();
        failureForWoMatColor.setText(resources.getString(R.string.color, colorName));
        failureForWoMatType.setText(resources.getString(R.string.type, getMaterialTypeNameByID(productMaterialID)));


        failureForWoRv.setHasFixedSize(true);
        failureForWoRv.setLayoutManager(Utilities.getLinearLayoutManager(WorkOrderActivity.this));
        failureForWoRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(WorkOrderActivity.this), WorkOrderActivity.this, failureForWoRv));
        adapter = new FailureForWorkOrderAdapter(WorkOrderActivity.this, failuresForWoList);
        failureForWoRv.setAdapter(adapter);

        if(failuresForWoList.isEmpty()){
            noFailureForWoTv.setVisibility(View.VISIBLE);
            failureForWoRv.setVisibility(View.INVISIBLE);
        }else{
            noFailureForWoTv.setVisibility(View.INVISIBLE);
            failureForWoRv.setVisibility(View.VISIBLE);
        }


        failureDialog = builder.create();
        failureDialog.show();

        closeFailureForWoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failureDialog.dismiss();
            }
        });
    }


    private void disableChangeProductButton(){
        if(addedMaterialsRv.getVisibility() == View.GONE && addedServicesRv.getVisibility() == View.GONE && addedFailuresRv.getVisibility() == View.GONE && failureCauseNote.isEmpty()) {
            workOrderChangeProductBtn.setEnabled(true);
            workOrderChangeProductBtn.setClickable(true);
        }else{
            workOrderChangeProductBtn.setEnabled(false);
            workOrderChangeProductBtn.setClickable(false);
        }
    }

    private String getMaterialTypeNameByID(int materialTypeID){
        if(materialTypeID == 1)
            return "Levo";
        else if (materialTypeID == 2)
            return "Desno";
        else
            return "Nepoznato";
    }

    private void isCustomerRejectedChanged(boolean isCustomerRejected){

        if(isCustomerRejected){
            db.open();
            db.addedServices_D(workOrderResultID, RouteActivity.selectedRouteID);
            db.addedMaterials_D(workOrderResultID, RouteActivity.selectedRouteID);
            db.addedFailuresAndCauses_D(workOrderResultID, RouteActivity.selectedRouteID);
            hideUIElementsIfListEmpty(Constants.SHOW_UI_SERVICE_TAG);
            hideUIElementsIfListEmpty(Constants.SHOW_UI_MATERIAL_TAG);
            hideUIElementsIfListEmpty(Constants.SHOW_UI_FAILURE_TAG);
            db.close();
            workOrderNote.setText(getResources().getString(R.string.customer_rejected));
        }else{
            workOrderNote.setText(failureCauseNote);
        }
    }

}
