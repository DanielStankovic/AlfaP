package com.kolibrialfaplam.microbs.kolibrialfaplam.data;

import android.content.Context;

import android.database.SQLException;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.MainActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ApplicationVersion;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.CheckIn;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.CheckInPreDefComment;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Employee;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Failure;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.FailureCause;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.History;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.NoSignatureComment;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestionType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PriorityType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductColor;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductDocument;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductFailure;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductMaterial;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductService;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.StartStop;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WarehouseState;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderImage;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ImageType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Partner;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PostRequest;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PriceList;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductCategory;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductRange;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Region;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.RegionPlace;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ServiceNorm;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.SignatureImage;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Warehouse;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderResult;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

public class NetworkClass {

    private Context context;
    private BrokerSQLite db;


    private int progress = 0;
    private TextView progressBarTv;
    private ProgressBar progressBar;
    public boolean isLogin;
    private String userName;
    private String password;
    private String imei;
    private increaseDialogProgressInterface mInterface = null;
    private SendDataInterface sendDataInterface = null;


    private Api apiInterface;


    public NetworkClass(Context context) {
        this.context = context;
        apiInterface = ApiClient.getApiClient().create(Api.class);
        db = new BrokerSQLite(context);
    }

    public NetworkClass(Context context, Boolean _isLogin, String userName, String password, String imei, TextView progressBarTv, ProgressBar progressBar, increaseDialogProgressInterface mInterface) {
        this.isLogin = _isLogin;
        this.userName = userName;
        this.password = password;
        this.imei = imei;
        this.context = context;
        this.mInterface = mInterface;
        db = new BrokerSQLite(context);
        this.progressBarTv = progressBarTv;
        this.progressBar = progressBar;
        this.progress = 0;
        apiInterface = ApiClient.getApiClient().create(Api.class);
    }


    public NetworkClass(Context context, increaseDialogProgressInterface mInterface) {

        this.context = context;
        this.mInterface = mInterface;
        db = new BrokerSQLite(context);
        this.progress = 0;
        apiInterface = ApiClient.getApiClient().create(Api.class);
    }

    public NetworkClass(Context context, increaseDialogProgressInterface mInterface, SendDataInterface sendInterface) {

        this.context = context;
        this.mInterface = mInterface;
        db = new BrokerSQLite(context);
        this.progress = 0;
        apiInterface = ApiClient.getApiClient().create(Api.class);
        this.sendDataInterface = sendInterface;
    }

    public NetworkClass(Context context, SendDataInterface mInterface) {

        this.context = context;
        this.sendDataInterface = mInterface;
        db = new BrokerSQLite(context);
        apiInterface = ApiClient.getApiClient().create(Api.class);
    }


    /*********************** Preuzimanje podataka ************************/
    public int syncData(int progress) throws Exception {

        try {

            this.progress = progress;
            int i = 0;

            //  incProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
            mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
            syncEmployee();
            MainActivity.nivoSinhronizacije = ++i;
            mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
            signInEmployee("0");
            MainActivity.nivoSinhronizacije = ++i;
            mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
            syncApplicationVersion();
            // ukoliko postoji nova app da se prekine sync
            if (MainActivity.newVersionCode != MainActivity.versionCode) {
                return i;
            }

            if (isLogin) {

                if (MainActivity.employeeID > 0) {

                    //TODO Brisanje baza

                }
            }


            syncAllData(i);

            signInEmployee("1");
            // sve je proslo kako treba
            MainActivity.nivoSinhronizacije = -1;
        } catch (Exception e) {
            throw e;
        }

        return this.progress;
    }

    private void syncAllData(int i) throws Exception {

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductRange();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductCategory();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProduct();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncFailure();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncFailureCause();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncService();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncServiceNorm();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncPriceList();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncPartner();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncRegion();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncRegionPlace();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncMaterial();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncWarehouse();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncPriorityType();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncWarehouseState();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncCheckInPreDefComment();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncWorkOrders();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncWorkOrdersType();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncImageType();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncStartStopStatus();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncPollQuestionType();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncPollQuestions();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncNoSignatureComments();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductMaterial();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductService();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductFailure();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncProductColor();

        MainActivity.nivoSinhronizacije = ++i;
        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
        syncAddedFeaturesForWorkOrdInProgress();

//        MainActivity.nivoSinhronizacije = ++i;
//        mInterface.increaseProgress(5, MainActivity.imeNivoaSinhronizacije[i]);
//        syncWorkOrderService();

    }




    //////////////////////////////////////////////////////////region synchronization/////////////////////////////////////////////////////////////////

    private void syncEmployee() throws Exception {
        if (userName == null || password == null || imei == null) {
            return;
        }
        db.open();
        Call<List<Employee>> call = apiInterface.getEmployee(userName, password, imei);
        Response<List<Employee>> response = call.execute();
        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (response.body().size() > 0) {
                    List<Employee> employeeList = response.body();
                    Employee employee = employeeList.get(0);
                    if (isLogin) {
                        db.employee_D();


                        db.employee_I(
                                employee.getEmployeeID(),
                                employee.getEmployeeName(),
                                employee.getUsername(),
                                employee.getPassword(),
                                employee.getDeviceNo(),
                                employee.getEmployeeTypeID(),
                                employee.getErpID(),
                                employee.getSupervisorID(),
                                employee.getCheckInCount()
                        );


                    } else {

                        db.employee_U(
                                employee.getEmployeeID(),
                                employee.getEmployeeName(),
                                employee.getUsername(),
                                employee.getPassword(),
                                employee.getDeviceNo(),
                                employee.getEmployeeTypeID(),
                                employee.getErpID(),
                                employee.getSupervisorID(),
                                employee.getCheckInCount()
                        );


                    }
                    MainActivity.deviceNo = employee.getDeviceNo();
                    MainActivity.employeeID = employee.getEmployeeID();

                } else {
                    throw new RuntimeException("Nema tog IMEI-a ili je pogresna sifra komercijaliste.");
                }
            } else {
                throw new RuntimeException("Nema tog IMEI-a ili je pogresna sifra komercijaliste");
            }
        } else {
            throw new RuntimeException("Nema tog IMEI-a ili je pogresna sifra komercijaliste");
        }
        db.close();
    }

    public void signInEmployee(String status) throws IOException {
        db.open();
        Call<String> call = apiInterface.getSignIn(String.valueOf(MainActivity.employeeID), status);
        Response<String> response = call.execute();
        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (!response.body().equalsIgnoreCase("OK")) {
                    Utilities.writeErrorToFile(new Exception("Greška kod SignIn na serveru"));
                }
            } else {
                Utilities.writeErrorToFile(new Exception("Greška kod SignIn na serveru"));
            }
        } else {
            Utilities.writeErrorToFile(new Exception("Greška kod SignIn na serveru"));
        }
        db.close();
    }

    private void syncApplicationVersion() throws Exception {

        db.open();


        Call<List<ApplicationVersion>> call = apiInterface.getApplicationVersion();
        Response<List<ApplicationVersion>> response = call.execute();
        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (response.body().size() > 0) {

                    ApplicationVersion applicationVersion = response.body().get(0);
                    db.applicationVersion_D();
                    if (MainActivity.versionCode == applicationVersion.getVersionCode()) {
                        db.applicationVersion_I(
                                applicationVersion.getApplicationVersionID(),
                                applicationVersion.getVersionCode(),
                                applicationVersion.getVersionName(),
                                applicationVersion.getDescription(),
                                applicationVersion.getDownloadLink(),
                                applicationVersion.getCreatedDate()
                        );



                    }

                    MainActivity.newVersionCode = applicationVersion.getVersionCode();
                    MainActivity.newVersionName = applicationVersion.getVersionName();
                    MainActivity.newVersionDownloadLink = applicationVersion.getDownloadLink();

                } else {
                    throw new RuntimeException("Došlo je do greške pri sinhronizaciji verzije aplikacije");
                }
            } else {
                throw new RuntimeException("Došlo je do greške pri sinhronizaciji verzije aplikacije");
            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji verzije aplikacije");
        }

        db.close();
    }


    private void syncProductRange() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("ProductRange");
        Call<List<ProductRange>> call = apiInterface.getProductRangeByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ProductRange>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductRange productRange : response.body()) {
                db.productRange_D(productRange.getProductRangeID());
                if (productRange.isActive()) {
                    db.productRange_I(
                            productRange.getProductRangeID(),
                            productRange.getProductRangeName(),
                            productRange.getProductRangeCode(),
                            productRange.getModifiedDate()
                    );
                }

            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji brendova artikla");
        }

        db.close();
    }

    private void syncProductCategory() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("ProductCategory");
        Call<List<ProductCategory>> call = apiInterface.getProductCategoryByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID)
        );
        Response<List<ProductCategory>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductCategory productCategory : response.body()) {
                db.productCategory_D(productCategory.getProductCategoryID());
                if (productCategory.isActive()) {
                    db.productCategory_I(
                            productCategory.getProductCategoryID(),
                            productCategory.getProductRangeID(),
                            productCategory.getCategoryName(),
                            productCategory.getCategoryCode(),
                            productCategory.getModifiedDate()
                    );
                }

            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji kategorija artikla");
        }
        db.close();
    }


    private void syncProduct() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("Product");
        Call<List<Product>> call = apiInterface.getProductByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID)
        );
        Response<List<Product>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Product product : response.body()) {
                db.product_D(product.getProductID());
                if (product.isActive()) {
                    db.product_I(
                            product.getProductID(),
                            product.getProductName(),
                            product.getProductCode(),
                            product.getModel(),
                            product.getBarcode(),
                            product.getDescription(),
                            product.getModifiedDate(),
                            product.getProductRangeID(),
                            product.getUnitOfMeasure(),
                            product.getWidth(),
                            product.getHeight(),
                            product.getLength(),
                            product.getWeight()
                    );
                }

            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji kategorija artikla");
        }
        db.close();
    }

    private void syncFailure() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("Failure");
        Call<List<Failure>> call = apiInterface.getFailureByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Failure>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Failure failure : response.body()) {
                db.failure_D(failure.getFailureID());

                if (failure.isActive()) {
                    db.failure_I(
                            failure.getFailureID(),
                            failure.getFailureName(),
                            failure.getFailureCode(),
                            failure.getDescription(),
                            failure.getModifiedDate());
                }

            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji kvarova");
        }
        db.close();
    }

    private void syncFailureCause() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("FailureCause");
        Call<List<FailureCause>> call = apiInterface.getFailureCauseByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<FailureCause>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (FailureCause failureCause : response.body()) {
                db.failureCause_D(failureCause.getFailureCauseID());
                if (failureCause.isActive()) {
                    db.failureCause_I(
                            failureCause.getFailureCauseID(),
                            failureCause.getFailureID(),
                            failureCause.getFailureCauseName(),
                            failureCause.getFailureCauseCode(),
                            failureCause.getDescription(),
                            failureCause.getModifiedDate());
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji uzroka kvarova");
        }
        db.close();
    }


    private void syncService() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("Service");
        Call<List<Service>> call = apiInterface.getServicesByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Service>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Service service : response.body()) {
                db.service_D(service.getServiceID());
                if (service.isActive()) {
                    db.service_I(
                            service.getServiceID(),
                            service.getServiceName(),
                            service.getServiceCode(),
                            service.getUnitOfMeasure(),
                            service.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji usluga");
        }
        db.close();
    }

    private void syncServiceNorm() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("ServiceNorm");
        Call<List<ServiceNorm>> call = apiInterface.getServiceNormByModifiedDate(
                new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ServiceNorm>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ServiceNorm serviceNorm : response.body()) {
                db.serviceNorm_D(serviceNorm.getServiceNormID());
                if (serviceNorm.isActive()) {
                    db.serviceNorm_I(
                            serviceNorm.getServiceNormID(),
                            serviceNorm.getServiceID(),
                            serviceNorm.getProductID(),
                            serviceNorm.getNormName(),
                            serviceNorm.getNormCode(),
                            serviceNorm.getNormTime(),
                            serviceNorm.getModifiedDate()
                    );
                }
            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji normi za usluge");
        }
        db.close();
    }


    private void syncPriceList() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("PriceList");
        Call<List<PriceList>> call = apiInterface.getPriceListByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<PriceList>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (PriceList priceList : response.body()) {
                db.priceList_D(priceList.getPriceListID());
                if (priceList.isActive()) {
                    db.priceList_I(
                            priceList.getPriceListID(),
                            priceList.getPriceListCode(),
                            priceList.getEntityType(),
                            priceList.getEntityCode(),
                            priceList.getPrice(),
                            priceList.getPriceListDate(),
                            priceList.getModifiedDate()
                    );
                }

            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji cenovnika");
        }

        db.close();
    }


    private void syncPartner() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("Partner");
        Call<List<Partner>> call = apiInterface.getPartnerByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Partner>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Partner partner : response.body()) {
                db.partner_D(partner.getPartnerID());
                if (partner.isActive()) {
                    db.partner_I(
                            partner.getPartnerID(),
                            partner.getPartnerName(),
                            partner.getPartnerCode(),
                            partner.getAddress(),
                            partner.getContact(),
                            partner.getPib(),
                            partner.isLegal(),
                            partner.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji partnera");
        }
        db.close();
    }


    private void syncRegion() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("Region");
        Call<List<Region>> call = apiInterface.getRegionByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Region>> response = call.execute();
        if (checkResponseFromServer(response)) {

            for (Region region : response.body()) {
                db.region_D(region.getRegionID());
                if (region.isActive()) {
                    db.region_I(
                            region.getRegionID(),
                            region.getRegionName(),
                            region.getRegionCode(),
                            region.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji regiona");
        }

        db.close();
    }

    private void syncRegionPlace() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("RegionPlace");
        Call<List<RegionPlace>> call = apiInterface.getRegionPlaceByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<RegionPlace>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (RegionPlace regionPlace : response.body()) {
                db.regionPlace_D(regionPlace.getRegionPlaceID());
                if (regionPlace.isActive()) {
                    db.regionPlace_I(
                            regionPlace.getRegionPlaceID(),
                            regionPlace.getRegionID(),
                            regionPlace.getRegionPlaceName(),
                            regionPlace.getRegionPlaceCode(),
                            regionPlace.getModifiedDate()
                    );
                }

            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji mesta po regionima");
        }

        db.close();
    }


    private void syncMaterial() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("Material");
        Call<List<Material>> call = apiInterface.getMaterialByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Material>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Material material : response.body()) {
                db.material_D(material.getMaterialID());
                if (material.isActive()) {
                    db.material_I(
                            material.getMaterialID(),
                            material.getMaterialName(),
                            material.getMaterialCode(),
                            material.getUnitOfMeasure(),
                            material.getModifiedDate()
                    );
                }
            }

        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji materijala");
        }
        db.close();
    }


    private void syncWarehouse() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("Warehouse");
        Call<List<Warehouse>> call = apiInterface.getWarehouseByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<Warehouse>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (Warehouse warehouse : response.body()) {
                db.warehouse_D(warehouse.getWarehouseID());
                if (warehouse.isActive()) {
                    db.warehouse_I(
                            warehouse.getWarehouseID(),
                            warehouse.getWarehouseName(),
                            warehouse.getWarehouseCode(),
                            warehouse.getWarehousePosition(),
                            warehouse.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji skladišta");
        }
        db.close();
    }

    private void syncPriorityType() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("PriorityType");
        Call<List<PriorityType>> call = apiInterface.getPriorityTypeByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<PriorityType>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (PriorityType priorityType : response.body()) {
                db.priorityType_D(priorityType.getPriorityTypeID());
                if (priorityType.isActive()) {
                    db.priorityType_I(
                            priorityType.getPriorityTypeID(),
                            priorityType.getPriorityTypeName(),
                            priorityType.getPriorityTypeCode(),
                            priorityType.getPriorityTypeDescription(),
                            priorityType.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji tipova prioriteta");
        }
        db.close();
    }


    public void syncWarehouseState() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("WarehouseState");
        Call<List<WarehouseState>> call = apiInterface.getWarehouseStateByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<WarehouseState>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (WarehouseState warehouseState : response.body()) {
                db.warehouseState_D(warehouseState.getMaterialID());
                db.warehouseState_I(
                        warehouseState.getMaterialID(),
                        warehouseState.getWarehouseID(),
                        warehouseState.getRealQuantity(),
                        warehouseState.getReservedQuantity(),
                        warehouseState.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji stanja skladišta");
        }
        db.close();
    }


    private void syncCheckInPreDefComment() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("CheckInPreDefComment");
        Call<List<CheckInPreDefComment>> call = apiInterface.getCheckInPreDefComment(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<CheckInPreDefComment>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (CheckInPreDefComment checkInPreDefComment : response.body()) {
                db.checkInPreDefComment_D(checkInPreDefComment.getCommentID());
                if (checkInPreDefComment.isActive()) {
                    db.checkInPreDefComment_I(
                            checkInPreDefComment.getCommentID(),
                            checkInPreDefComment.getComment(),
                            checkInPreDefComment.getModifiedDate()
                    );
                }

            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji komentara za CheckOut");
        }
        db.close();
    }


    private void syncWorkOrders() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("WorkOrder");
        Call<List<WorkOrder>> call = apiInterface.getWorkOrderByModifiedDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<WorkOrder>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (WorkOrder workOrder : response.body()) {


                try {
                    int workOrderID = workOrder.getWorkOrderID();
                    db.workOrder_D(workOrderID);
                    db.workOrderService_D(workOrderID);
                    db.workOrderFailure_D(workOrderID);
                    db.workOrder_I(
                            workOrder.getWorkOrderID(),
                            workOrder.getWorkOrderName(),
                            workOrder.getWorkOrderCode(),
                            workOrder.getUpisCode(),
                            workOrder.getWorkOrderTypeID(),
                            workOrder.getWorkOrderPollID(),
                            workOrder.getWorkOrderDate(),
                            workOrder.getPartnerID(),
                            workOrder.getCustomerName(),
                            workOrder.getCustomerAddress(),
                            workOrder.getCustomerAddressNo(),
                            workOrder.getCustomerCity(),
                            workOrder.getCustomerPhone(),
                            workOrder.isLegalPerson(),
                            workOrder.isCustomerWarning(),
                            workOrder.getStatusID(),
                            workOrder.getProductID(),
                            workOrder.getQuantity(),
                            workOrder.getRegionID(),
                            workOrder.getRegionPlaceID(),
                            workOrder.getPlannedDate(),
                            workOrder.getRealizationDate(),
                            workOrder.isInWarranty(),
                            workOrder.getModifiedDate(),
                            workOrder.getDescription(),
                            workOrder.getNote(),
                            workOrder.getPriorityTypeID(),
                            workOrder.getMaterialTypeID(),
                            workOrder.getProductColorID()
                    );

                    for (Integer serviceID : workOrder.getWorkOrderServiceList()) {
                        db.workOrderService_I(workOrderID, serviceID);
                    }

                    for (FailureCause failureCause : workOrder.getWorkOrderFailureCauseList()) {
                        db.workOrderFailureCause_I(workOrderID, failureCause.getFailureID(), failureCause.getFailureCauseID());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji radnih naloga");
        }
        db.close();

    }


    private void syncWorkOrdersType() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("WorkOrderType");
        Call<List<WorkOrderType>> call = apiInterface.getWorkOrderTypeByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<WorkOrderType>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (WorkOrderType workOrderType : response.body()) {
                db.workOrderType_D(workOrderType.getWorkOrderTypeID());
                if (workOrderType.isActive()) {
                    db.workOrderType_I(
                            workOrderType.getWorkOrderTypeID(),
                            workOrderType.getTypeName(),
                            workOrderType.getTypeCode(),
                            workOrderType.getModifiedDate()
                    );
                }

            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji tipova radnih naloga");
        }
        db.close();
    }


    private void syncImageType() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("ImageType");
        Call<List<ImageType>> call = apiInterface.getImageTypeByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ImageType>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ImageType imageType : response.body()) {
                db.imageType_D(imageType.getImageTypeID());
                if (imageType.isActive()) {
                    db.imageType_I(
                            imageType.getImageTypeID(),
                            imageType.getImageTypeCode(),
                            imageType.getImageTypeName(),
                            imageType.getModifiedDate()
                    );
                }

            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji tipova slika");
        }

        db.close();
    }

    private void syncStartStopStatus() throws IOException {
        db.open();
        Call<StartStop> call = apiInterface.getStartStopStatus(MainActivity.employeeID);
        Response<StartStop> response = call.execute();
        if (checkResponseFromServer(response)) {
            if (response.body().getEmployeeID() != -1) {
                db.startStop_D();
                db.startStop_I(MainActivity.employeeID, response.body().getTime(), response.body().isStarted());
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji start-stop statusa");
        }
        db.close();
    }

    public void syncProductDocument(int productID) throws Exception {

        //OVDE SE UMESTO EmployeeID prosledjuje productID ali se koristi ista klasa PostRequest. Da se ne bi pravila nova klasa a parametar je isti.
        //Isti komentar je postavljen i na wcfu.

        db.open();
        String modifiedDate = getModifiedDateForTable("ProductDocumentation");
        Call<List<ProductDocument>> call = apiInterface.getDocumentationByModDate(new PostRequest(modifiedDate, productID));
        Response<List<ProductDocument>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductDocument productDocument : response.body()) {
                db.productDocument_D(productDocument.getProductDocumentationID());
                db.productDocument_I(
                        productDocument.getProductDocumentationID(),
                        productDocument.getProductID(),
                        productDocument.getDocumentName(),
                        productDocument.getDocumentType(),
                        productDocument.getDocumentCode(),
                        productDocument.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji dokumenata za artikle");
        }
        db.close();
    }

    private void syncPollQuestionType() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("PollQuestionType");
        Call<List<PollQuestionType>> call = apiInterface.getPollQuesTypeByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<PollQuestionType>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (PollQuestionType pollQuestionType : response.body()) {
                db.pollQuestionType_D(pollQuestionType.getWorkOrderQuestionTypeID());
                db.pollQuestionType_I(
                        pollQuestionType.getWorkOrderQuestionTypeID(),
                        pollQuestionType.getTypeName(),
                        pollQuestionType.getTypeCode(),
                        pollQuestionType.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji tipova pitanja");
        }
        db.close();
    }

    private void syncPollQuestions() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("PollQuestion");
        Call<List<PollQuestion>> call = apiInterface.getPollQuestionsByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<PollQuestion>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (PollQuestion pollQuestion : response.body()) {
                db.pollQuestion_D(pollQuestion.getWorkOrderQuestionID());
                db.pollQuestion_I(
                        pollQuestion.getWorkOrderQuestionID(),
                        pollQuestion.getWorkOrderPollID(),
                        pollQuestion.getQuestionText(),
                        pollQuestion.getWorkOrderQuestionTypeID(),
                        pollQuestion.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji pitanja");
        }
        db.close();
    }


    private void syncNoSignatureComments() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("NoSignatureComment");
        Call<List<NoSignatureComment>> call = apiInterface.getNoSigCommentsByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<NoSignatureComment>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (NoSignatureComment noSignatureComment : response.body()) {
                db.noSignatureComment_D(noSignatureComment.getCommentID());
                if (noSignatureComment.isActive()) {
                    db.noSignatureComment_I(
                            noSignatureComment.getCommentID(),
                            noSignatureComment.getCommentText(),
                            noSignatureComment.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji komentara za potpis");
        }

        db.close();

    }

    private void syncProductMaterial() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("ProductMaterial");
        Call<List<ProductMaterial>> call = apiInterface.getProductMaterialByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ProductMaterial>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductMaterial productMaterial : response.body()) {
                db.productMaterial_D(productMaterial.getProductID(), productMaterial.getMaterialID());
                db.productMaterial_I(productMaterial.getProductID(),
                        productMaterial.getMaterialID(),
                        productMaterial.getModifiedDate());
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji Artikal-Materijal veze");
        }
        db.close();

    }


    private void syncProductService() throws Exception {

        db.open();
        String modifiedDate = getModifiedDateForTable("ProductService");
        Call<List<ProductService>> call = apiInterface.getProductServiceByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ProductService>> response = call.execute();
        if (checkResponseFromServer(response)) {

            for (ProductService productService : response.body()) {
                db.productService_D(productService.getProductID(), productService.getServiceID());
                db.productService_I(
                        productService.getProductID(),
                        productService.getServiceID(),
                        productService.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji Artikal-Materijal veze");
        }
        db.close();
    }


    private void syncProductFailure() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("ProductFailure");
        Call<List<ProductFailure>> call = apiInterface.getProductFailureByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ProductFailure>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductFailure productFailure : response.body()) {
                db.productFailure_D(productFailure.getProductID(), productFailure.getFailureID(), productFailure.getFailureCauseID());
                db.productFailure_I(
                        productFailure.getProductID(),
                        productFailure.getFailureID(),
                        productFailure.getFailureCauseID(),
                        productFailure.getModifiedDate()
                );
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji Artikal-Kvarovi veze");
        }
        db.close();

    }


    private void syncProductColor() throws Exception {
        db.open();
        String modifiedDate = getModifiedDateForTable("ProductColor");
        Call<List<ProductColor>> call = apiInterface.getProductColorByModDate(new PostRequest(modifiedDate, MainActivity.employeeID));
        Response<List<ProductColor>> response = call.execute();
        if (checkResponseFromServer(response)) {
            for (ProductColor productColor : response.body()) {
                db.productColor_D(productColor.getProductColorID());
                if (productColor.isActive()) {
                    db.productColor_I(
                            productColor.getProductColorID(),
                            productColor.getProductColorName(),
                            productColor.getModifiedDate()
                    );
                }
            }
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji boja materijala proizvoda.");
        }

        db.close();
    }


    private void syncAddedFeaturesForWorkOrdInProgress() throws Exception {
    db.open();

        List<String> modifiedDates = new ArrayList<>();
        modifiedDates.add(getModifiedDateForAddedFeatures("AddedServices"));
        modifiedDates.add(getModifiedDateForAddedFeatures("AddedMaterials"));
        modifiedDates.add(getModifiedDateForAddedFeatures("AddedFailuresAndCauses"));

    Call<List<Route>> call = apiInterface.getAddedFeaturesForWorkOrdInProgress(new PostRequest(modifiedDates, MainActivity.employeeID));
    Response<List<Route>> response = call.execute();
        if (checkResponseFromServer(response)) {
           for(Route route : response.body()) {
               db.addedFeaturesForWorkOrdInProgress_I(
                       route.getWorkOrderID(),
                       route.getWorkOrderServiceList(),
                       route.getWorkOrderMaterialList(),
                       route.getWorkOrderFailureList()
               );
           }
        }else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji dodatih stavki za otvoreni radni nalog.");
        }
    db.close();
    }

/////////////////////////////////////////////////////////////endregion//////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////region sending data to server from login and on button "SEND ALL DATA TO SERVER " pressed /////////////////////////////////////////////////////////////////

    public boolean sendCheckInToServerAll() throws IOException {
        boolean isOk = false;
        db.open();

        List<CheckIn> checkInList = db.getUnsentCheckIn();
        if (checkInList.isEmpty()) {
            return true;

        }

        Call<String> call = apiInterface.sendCheckIn(checkInList);
        Response<String> response = call.execute();
        if (checkSendingResponse(response)) {

            db.open();
            db.CheckIn_U();
            db.sentCheckIn_D();
            db.close();
            isOk = true;
        }

        db.close();

        return isOk;
    }


    public boolean sendSignatureToServerAll() throws IOException {
        boolean isOk = false;
        db.open();

        List<SignatureImage> signatureImageList = db.getUnsentSignatureImage();
        if (signatureImageList.isEmpty()) {
            return true;
        }
        Call<String> call = apiInterface.insertSignatureImage(signatureImageList);
        Response<String> response = call.execute();
        if (checkSendingResponse(response)) {

            db.open();
            db.signatureImage_U();
            db.close();
            isOk = true;

        }

        db.close();
        return isOk;
    }

    public boolean sendWorkOrderImageAll() throws IOException {
        boolean isOk = false;
        db.open();

        List<WorkOrderImage> workOrderImageList = db.getUnsentWorkOrderImage();
        if (workOrderImageList.isEmpty()) {
            return true;
        }
        Call<String> call = apiInterface.insertWorkOrderImage(workOrderImageList);
        Response<String> response = call.execute();
        if (checkSendingResponse(response)) {

            db.open();
            db.workOrderImage_U();
            db.close();
            isOk = true;
        }
        db.close();
        return isOk;
    }


    public boolean sendWorkOrderResultAll() throws IOException {
        boolean isOk = false;
        db.open();

        final List<WorkOrderResult> workOrderResultList = db.getUnsentWorkOrderResult();
        if (workOrderResultList.isEmpty()) {
            return true;
        }

        Call<String> call = apiInterface.insertWorkOrderResult(workOrderResultList);
        Response<String> response = call.execute();
        if (checkSendingResponse(response)) {
            db.open();
            db.workOrderResult_U();
            db.workOrder_U(workOrderResultList);
            db.addedTables_U(workOrderResultList);
            db.close();
            isOk = true;
        }
        db.close();

        return isOk;
    }

    public boolean sendStartStopToServerAll() throws IOException {
        boolean isOk = false;
        db.open();

        List<StartStop> startStopList = db.unsentStartStop();
        if (startStopList.isEmpty()) {

            return true;
        }

        Call<String> call = apiInterface.insertStartStop(startStopList);
        Response<String> response = call.execute();
        if (checkSendingResponse(response)) {
            db.open();
            db.startStop_U();
            db.close();
            isOk = true;
        }

        db.close();
        return isOk;

    }

    //endregion


    //////////////////////////////////////////////////////////region sending data to server from forms /////////////////////////////////////////////////////////////////
    public void sendCheckInToServer() {
        db.open();
        sendDataInterface.onStartSendingData();
        List<CheckIn> checkInList = db.getUnsentCheckIn();
        if (checkInList.isEmpty()) {
            sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
            return;
        }
        Call<String> call = apiInterface.sendCheckIn(checkInList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (checkSendingResponse(response)) {
                    try {
                        db.open();
                        db.CheckIn_U();
                        db.sentCheckIn_D();
                        db.close();
                    } catch (SQLException e) {
                        Utilities.writeErrorToFile(e);
                    }
                    sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);

                } else {
                    sendDataInterface.onErrorSendingData(response.body(), Constants.SENDING_DATA_TYPE_OTHER);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendDataInterface.onErrorSendingData(t.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
            }
        });

        db.close();
    }


    public void sendSignatureToServer() {
        try {
            db.open();
            sendDataInterface.onStartSendingData();
            List<SignatureImage> signatureImageList = db.getUnsentSignatureImage();
            if (signatureImageList.isEmpty()) {
                sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                return;
            }
            Call<String> call = apiInterface.insertSignatureImage(signatureImageList);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (checkSendingResponse(response)) {
                        try {
                            db.open();
                            db.signatureImage_U();
                            db.close();
                            sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                        } catch (SQLException e) {
                            Utilities.writeErrorToFile(e);
                            sendDataInterface.onErrorSendingData(e.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
                        }
                    } else {
                        sendDataInterface.onErrorSendingData(response.body(), Constants.SENDING_DATA_TYPE_OTHER);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    sendDataInterface.onErrorSendingData(t.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
                }
            });
            db.close();
        } catch (SQLException e) {
            Utilities.writeErrorToFile(e);
            sendDataInterface.onErrorSendingData(e.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);

        }
    }

    public void sendWorkOrderImage() {
        try {
            db.open();
            sendDataInterface.onStartSendingData();
            List<WorkOrderImage> workOrderImageList = db.getUnsentWorkOrderImage();
            if (workOrderImageList.isEmpty()) {
                sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                return;
            }
            Call<String> call = apiInterface.insertWorkOrderImage(workOrderImageList);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (checkSendingResponse(response)) {
                        db.open();
                        db.workOrderImage_U();
                        db.close();
                        sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                    } else {
                        sendDataInterface.onErrorSendingData(response.body(), Constants.SENDING_DATA_TYPE_OTHER);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    sendDataInterface.onErrorSendingData(t.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
                }
            });
            db.close();
        } catch (Exception ex) {
            Utilities.writeErrorToFile(ex);
            sendDataInterface.onErrorSendingData(ex.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
        }
    }


    public void sendWorkOrderResult() {
        try {
            db.open();
            sendDataInterface.onStartSendingData();
            final List<WorkOrderResult> workOrderResultList = db.getUnsentWorkOrderResult();
            if (workOrderResultList.isEmpty()) {
                sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_WO);
                return;
            }

            Call<String> call = apiInterface.insertWorkOrderResult(workOrderResultList);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (checkSendingResponse(response)) {
                        db.open();
                        db.workOrderResult_U();
                        db.workOrder_U(workOrderResultList);
                        db.addedTables_U(workOrderResultList);
                        sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_WO);
                        db.close();
                    } else {
                        sendDataInterface.onErrorSendingData(response.body(), Constants.SENDING_DATA_TYPE_WO);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    sendDataInterface.onErrorSendingData(t.getMessage(), Constants.SENDING_DATA_TYPE_WO);
                }
            });
            db.close();
        } catch (SQLException ex) {
            Utilities.writeErrorToFile(ex);
            sendDataInterface.onErrorSendingData(ex.getMessage(), Constants.SENDING_DATA_TYPE_WO);
        }

    }

    public void sendStartStopToServer() {
        try {
            db.open();
            sendDataInterface.onStartSendingData();
            List<StartStop> startStopList = db.unsentStartStop();
            if (startStopList.isEmpty()) {
                sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                return;
            }

            Call<String> call = apiInterface.insertStartStop(startStopList);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (checkSendingResponse(response)) {
                        try {
                            db.open();
                            db.startStop_U();
                            sendDataInterface.onSuccessSendingData(Constants.SENDING_DATA_TYPE_OTHER);
                            db.close();
                        } catch (SQLException e) {
                            Utilities.writeErrorToFile(e);
                            sendDataInterface.onErrorSendingData(e.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
                        }
                    } else {
                        sendDataInterface.onErrorSendingData(response.body(), Constants.SENDING_DATA_TYPE_OTHER);
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    sendDataInterface.onErrorSendingData(t.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
                }
            });
            db.close();
        } catch (SQLException e) {
            Utilities.writeErrorToFile(e);
            sendDataInterface.onErrorSendingData(e.getMessage(), Constants.SENDING_DATA_TYPE_OTHER);
        }
    }


    //endregion

    public List<History> getWorkOrdersHistory(String historyParam) throws IOException {

        List<History> workOrderHistoryList = new ArrayList<>();
        //Note koristim parametar ModifiedDate da prosledim serialNumber
        Call<List<History>> call = apiInterface.getWorkOrderHistory(new PostRequest(historyParam, MainActivity.employeeID));
        Response<List<History>> response = call.execute();
        if (checkResponseFromServer(response)) {
            workOrderHistoryList = response.body();
        } else {
            throw new RuntimeException("Došlo je do greške pri prikupljanju istorije radnih naloga");
        }

        return workOrderHistoryList;
    }

    public Route getWorkOrderDetailsByWorkOrderID(int workOrderResultID) throws IOException {
        Route route = null;
        //Note koristim parametar EmployeeID da prosledim workOrderResultID
        Call<List<Route>> call = apiInterface.getWorkOrderDetailsByWorkOrderID(new PostRequest("", workOrderResultID));
        Response<List<Route>> response = call.execute();
        if (checkResponseFromServer(response)) {
            route = response.body().get(0);
        } else {
            throw new RuntimeException("Došlo je do greške pri sinhronizaciji detalja o radnom nalogu");
        }

        return route;
    }

    private String getModifiedDateForTable(String tableName) throws Exception {
        String modifiedDate = db.getModifiedDate(tableName);
        boolean isValidDate = modifiedDate.contains("-");
        if (!isValidDate && !modifiedDate.equalsIgnoreCase("SVI")) {
            modifiedDate = Utilities.dateFormatChange(modifiedDate);
        }
        return modifiedDate;
    }

    private String getModifiedDateForAddedFeatures(String tableName) throws Exception {
        String modifiedDate = db.getModifiedDateForAddedFeatures(tableName);
        boolean isValidDate = modifiedDate.contains("-");
        if (!isValidDate && !modifiedDate.equalsIgnoreCase("SVI")) {
            modifiedDate = Utilities.dateFormatChange(modifiedDate);
        }
        return modifiedDate;
    }

    private boolean checkResponseFromServer(Response response) {
        return response.isSuccessful() && response.body() != null;
    }

    private boolean checkSendingResponse(Response<String> response) {
        return response.isSuccessful() && response.body() != null && TextUtils.equals("success", response.body());
    }


    public interface increaseDialogProgressInterface {
        void increaseProgress(int progress, String title);
    }

    public interface SendDataInterface {
        void onStartSendingData();


        void onSuccessSendingData(int dataType);

        void onErrorSendingData(String errorMessage, int dataType);
    }


}
