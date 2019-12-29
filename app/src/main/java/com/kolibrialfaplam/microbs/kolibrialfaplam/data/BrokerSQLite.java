package com.kolibrialfaplam.microbs.kolibrialfaplam.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.text.TextUtils;
import android.util.SparseArray;


import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.MainActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.CheckIn;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Draft;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Failure;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.FailureCause;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductDocument;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ServiceNorm;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.StartStop;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Warehouse;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderImage;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ImageType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.SignatureImage;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderResult;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BrokerSQLite {
    private static final int _dbVersion = 1;// AKTIVNA:1;
    private static final String _dbName = "Kolibri_T.db";

    private static final String _tblEmployee = "Employee";
    private static final String _tblApplicationVersion = "ApplicationVersion";
    private static final String _tblLoginDate = "LoginDate";
    private static final String _tblProductRange = "ProductRange";
    private static final String _tblProductCategory = "ProductCategory";
    private static final String _tblProduct = "Product";
    private static final String _tblFailure = "Failure";
    private static final String _tblFailureCause = "FailureCause";
    private static final String _tblAddedFailuresAndCauses = "AddedFailuresAndCauses";
    private static final String _tblService = "Service";
    private static final String _tblServiceNorm = "ServiceNorm";
    private static final String _tblAddedServices = "AddedServices";
    private static final String _tblPriceList = "PriceList";
    private static final String _tblPartner = "Partner";
    private static final String _tblRegion = "Region";
    private static final String _tblRegionPlace = "RegionPlace";
    private static final String _tblMaterial = "Material";
    private static final String _tblAddedMaterials = "AddedMaterials";
    private static final String _tblWarehouse = "Warehouse";
    private static final String _tblWarehouseState = "WarehouseState";
    private static final String _tblPriorityType = "PriorityType";
    private static final String _tblCheckIn = "CheckIn";
    private static final String _tblCheckInPreDefComment = "CheckInPreDefComment";
    private static final String _tblWorkOrder = "WorkOrder";
    private static final String _tblWorkOrderService = "WorkOrderService";
    private static final String _tblWorkOrderFailureCause = "WorkOrderFailureCause";
    private static final String _tblWorkOrderType = "WorkOrderType";
    private static final String _tblImageType = "ImageType";
    private static final String _tblSignatureImage = "SignatureImage";
    private static final String _tblWorkOrderImage = "WorkOrderImage";
    private static final String _tblWorkOrderResult = "WorkOrderResult";
    private static final String _tblStartStop = "StartStop";
    private static final String _tblProductDocumentation = "ProductDocumentation";
    private static final String _tblPollQuestionType = "PollQuestionType";
    private static final String _tblPollQuestion = "PollQuestion";
    private static final String _tblNoSignatureComment = "NoSignatureComment";
    private static final String _tblProductMaterial = "ProductMaterial";
    private static final String _tblProductService = "ProductService";
    private static final String _tblProductFailure = "ProductFailure";
    private static final String _tblAddedPollQuestions = "AddedPollQuestions";
    private static final String _tblProductColor = "ProductColor";
    private static final String _tblWorkOrderPrintInfo = "WorkOrderPrintInfo";

    private DbHelper _dbHelper;
    private Context _dbContext;
    private SQLiteDatabase _dbDatabase = null;



    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {

            super(context, _dbName, null, _dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // Employee
            db.execSQL(" CREATE TABLE " + _tblEmployee + " ("
                    + " EmployeeID INTEGER," + " EmployeeName TEXT,"
                    + " Username  TEXT," + " Password  TEXT, DeviceNo TEXT, "
                    + " EmployeeTypeID INTEGER,"
                    + " ERPID INTEGER," + " SupervisorID INTEGER,"
                    + " CheckInCount INTEGER"
                    + " )");

            // Application version
            db.execSQL(" CREATE TABLE " + _tblApplicationVersion + " ("
                    + " ApplicationVersionID INTEGER,"
                    + " VersionCode INTEGER," + " VersionName TEXT,"
                    + " Description TEXT," + " DownloadLink TEXT,"
                    + " CreatedDate TIMESTAMP)");

            // LoginDate
            db.execSQL(" CREATE TABLE " + _tblLoginDate + " ("
                    + " LoginDate TIMESTAMP )");

            // ProductRange
            db.execSQL(" CREATE TABLE " + _tblProductRange + " ("
                    + " ProductRangeID INTEGER,"
                    + " ProductRangeName TEXT,"
                    + " ProductRangeCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            // ProductCategory
            db.execSQL(" CREATE TABLE " + _tblProductCategory + " ("
                    + " ProductCategoryID INTEGER,"
                    + " ProductRangeID INTEGER,"
                    + " ProductCategoryName TEXT,"
                    + " ProductCategoryCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            // Product
            db.execSQL(" CREATE TABLE " + _tblProduct + " ("
                    + " ProductID INTEGER,"
                    + " ProductName TEXT,"
                    + " ProductCode TEXT,"
                    + " Model TEXT,"
                    + " Barcode TEXT,"
                    + " Description TEXT,"
                    + " ModifiedDate TIMESTAMP,"
                    + " ProductRangeID INTEGER,"
                    + " UnitOfMeasure TEXT,"
                    + " Width NUMERIC,"
                    + " Height NUMERIC,"
                    + " Length NUMERIC,"
                    + " Weight NUMERIC"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblFailure + " ("
                    + " FailureID INTEGER,"
                    + " FailureName TEXT,"
                    + " FailureCode TEXT,"
                    + " Description TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblFailureCause + " ("
                    + " FailureCauseID INTEGER,"
                    + " FailureID INTEGER,"
                    + " FailureCauseName TEXT,"
                    + " FailureCauseCode TEXT,"
                    + " Description TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblAddedFailuresAndCauses + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderResultID INTEGER,"
                    + " FailureID INTEGER,"
                    + " FailureCauseID INTEGER,"
                    + " CreatedDate TIMESTAMP,"
                    + " IsSent INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblService + " ("
                    + " ServiceID INTEGER,"
                    + " ServiceName TEXT,"
                    + " ServiceCode TEXT,"
                    + " UnitOfMeasure TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblServiceNorm + " ("
                    + " ServiceNormID INTEGER,"
                    + " ServiceID INTEGER,"
                    + " ProductID INTEGER,"
                    + " NormName TEXT,"
                    + " NormCode TEXT,"
                    + " NormTime TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblAddedServices + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderResultID INTEGER,"
                    + " ServiceID INTEGER,"
                    + " ServiceName TEXT,"
                    + " UnitSpent INTEGER,"
                    + " UnitOfMeasure TEXT,"
                    + " CreatedDate TIMESTAMP,"
                    + " IsSent INTEGER"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblPriceList + " ("
                    + " PriceListID INTEGER,"
                    + " PriceListCode TEXT,"
                    + " EntityType INTEGER,"
                    + " EntityCode TEXT,"
                    + " Price NUMERIC,"
                    + " PriceListDate TIMESTAMP,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblPartner + " ("
                    + " PartnerID INTEGER,"
                    + " PartnerName TEXT,"
                    + " PartnerCode TEXT,"
                    + " Address TEXT,"
                    + " Contact TEXT,"
                    + " PIB TEXT,"
                    + " IsLegal INTEGER,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblRegion + " ("
                    + " RegionID INTEGER,"
                    + " RegionName TEXT,"
                    + " RegionCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblRegionPlace + " ("
                    + " RegionPlaceID INTEGER,"
                    + " RegionID INTEGER,"
                    + " RegionPlaceName TEXT,"
                    + " RegionPlaceCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblMaterial + " ("
                    + " MaterialID INTEGER,"
                    + " MaterialName TEXT,"
                    + " MaterialCode TEXT,"
                    + " UnitOfMeasure TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblAddedMaterials + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderResultID INTEGER,"
                    + " MaterialID INTEGER,"
                    + " MaterialName TEXT,"
                    + " UnitOfMeasure TEXT,"
                    + " QuantitySpent INTEGER,"
                    + " CreatedDate TIMESTAMP,"
                    + " IsSent INTEGER"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblWarehouse + " ("
                    + " WarehouseID INTEGER,"
                    + " WarehouseName TEXT,"
                    + " WarehouseCode TEXT,"
                    + " WarehousePosition TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWarehouseState + " ("
                    + " MaterialID INTEGER,"
                    + " WarehouseID INTEGER,"
                    + " EmployeeID INTEGER,"
                    + " RealQuantity NUMERIC,"
                    + " ReservedQuantity NUMERIC,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblPriorityType + " ("
                    + " PriorityTypeID INTEGER,"
                    + " PriorityTypeName TEXT,"
                    + " PriorityTypeCode TEXT,"
                    + " PriorityTypeDescription TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblCheckIn + " ("
                    + " CheckInID TEXT NOT NULL,"
                    + " WorkOrderID INTEGER,"
                    + " EmployeeID INTEGER,"
                    + " CheckInDate TIMESTAMP,"
                    + " CheckOutDate TIMESTAMP,"
                    + " IsSent INTEGER,"
                    + " IsInRoute INTEGER,"
                    + " Comment TEXT,"
                    + " Mileage TEXT,"
                    + " CheckInCoordinates TEXT,"
                    + " CheckOutCoordinates TEXT"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblCheckInPreDefComment + " ("
                    + " CommentID INTEGER,"
                    + " Comment TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrder + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderName TEXT,"
                    + " WorkOrderCode TEXT,"
                    + " UpisCode TEXT,"
                    + " WorkOrderTypeID INTEGER,"
                    + " WorkOrderPollID INTEGER,"
                    + " WorkOrderDate TIMESTAMP,"
                    + " PartnerID INTEGER,"
                    + " CustomerName TEXT,"
                    + " CustomerAddress TEXT,"
                    + " CustomerAddressNo TEXT,"
                    + " CustomerCity TEXT,"
                    + " CustomerPhone TEXT,"
                    + " IsLegalPerson INTEGER,"
                    + " CustomerWarning INTEGER,"
                    + " StatusID INTEGER,"
                    + " ProductID INTEGER,"
                    + " Quantity NUMERIC,"
                    + " RegionID INTEGER,"
                    + " RegionPlaceID INTEGER,"
                    + " PlannedDate TIMESTAMP,"
                    + " RealizationDate TIMESTAMP,"
                    + " InWarranty INTEGER,"
                    + " ModifiedDate TIMESTAMP,"
                    + " Description TEXT,"
                    + " Note TEXT,"
                    + " PriorityTypeID INTEGER,"
                    + " MaterialTypeID INTEGER,"
                    + " ProductColorID INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrderService + " ("
                    + " WorkOrderID INTEGER,"
                    + " ServiceID INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrderFailureCause + " ("
                    + " WorkOrderID INTEGER,"
                    + " FailureID INTEGER,"
                    + " FailureCauseID INTEGER"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblWorkOrderType + " ("
                    + " WorkOrderTypeID INTEGER,"
                    + " TypeName TEXT,"
                    + " TypeCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblImageType + " ("
                    + " ImageTypeID INTEGER,"
                    + " ImageTypeCode INTEGER,"
                    + " ImageTypeName TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblSignatureImage + " ("
                    + " WorkOrderID INTEGER,"
                    + " ImageTitle TEXT,"
                    + " ImageString TEXT,"
                    + " CreatedDate TIMESTAMP,"
                    + " SentDate TIMESTAMP,"
                    + " IsSent INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrderImage + " ("
                    + " WorkOrderID INTEGER,"
                    + " ImageTypeID INTEGER,"
                    + " ImageTitle TEXT,"
                    + " ImageString TEXT,"
                    + " Note TEXT,"
                    + " CreatedDate TIMESTAMP,"
                    + " SentDate TIMESTAMP,"
                    + " IsSent INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrderResult + " ("
                    + " WorkOrderResultID INTEGER PRIMARY KEY,"
                    + " WorkOrderID INTEGER,"
                    + " ProductID INTEGER,"
                    + " SerialNumber TEXT,"
                    + " Power INTEGER,"
                    + " ProductPurchaseDate TEXT,"
                    + " ProductProductionDate TEXT,"
                    + " CustomerName TEXT,"
                    + " CustomerAddress TEXT,"
                    + " CustomerAddressNo TEXT,"
                    + " CustomerCity TEXT,"
                    + " CustomerPhone TEXT,"
                    + " IsLegalPerson INTEGER,"
                    + " InWarranty INTEGER,"
                    + " ResultNote TEXT,"
                    + " IsEscalated INTEGER,"
                    + " ResultDescription TEXT,"
                    + " FailureCauseNote TEXT,"
                    + " SignatureNote TEXT,"
                    + " HasSignature INTEGER,"
                    + " IsCustomerRejected INTEGER,"
                    + " SerialNoScanned INTEGER,"
                    + " IsClosed INTEGER,"
                    + " CreatedDate TIMESTAMP,"
                    + " SentDate TIMESTAMP,"
                    + " IsDone INTEGER,"
                    + " IsSent INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblStartStop + " ("
                    + " EmployeeID INTEGER,"
                    + " Time TIMESTAMP,"
                    + " IsStarted INTEGER,"
                    + " IsSent INTEGER"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblProductDocumentation + " ("
                    + " ProductDocumentationID INTEGER,"
                    + " ProductID INTEGER,"
                    + " DocumentType TEXT,"
                    + " DocumentName TEXT,"
                    + " DocumentCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblPollQuestionType + " ("
                    + " WorkOrderQuestionTypeID INTEGER,"
                    + " TypeName TEXT,"
                    + " TypeCode TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblPollQuestion + " ("
                    + " WorkOrderQuestionID INTEGER,"
                    + " WorkOrderPollID INTEGER,"
                    + " QuestionText TEXT,"
                    + " WorkOrderQuestionTypeID INTEGER,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblNoSignatureComment + " ("
                    + " CommentID INTEGER,"
                    + " CommentText TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblProductMaterial + " ("
                    + " ProductID INTEGER,"
                    + " MaterialID INTEGER,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblProductService + " ("
                    + " ProductID INTEGER,"
                    + " ServiceID INTEGER,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblProductFailure + " ("
                    + " ProductID INTEGER,"
                    + " FailureID INTEGER,"
                    + " FailureCauseID INTEGER,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblAddedPollQuestions + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderResultID INTEGER,"
                    + " WorkOrderQuestionID INTEGER,"
                    + " PollQuestionAnswer TEXT,"
                    + " IsSent INTEGER"
                    + " )");


            db.execSQL(" CREATE TABLE " + _tblProductColor + " ("
                    + " ProductColorID INTEGER,"
                    + " ProductColorName TEXT,"
                    + " ModifiedDate TIMESTAMP"
                    + " )");

            db.execSQL(" CREATE TABLE " + _tblWorkOrderPrintInfo + " ("
                    + " WorkOrderID INTEGER,"
                    + " WorkOrderResultID INTEGER,"
                    + " CreatedDate TIMESTAMP"
                    + " )");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (oldVersion != newVersion) {


                db.execSQL("DROP TABLE IF EXISTS " + _tblEmployee);
                db.execSQL("DROP TABLE IF EXISTS " + _tblApplicationVersion);
                db.execSQL("DROP TABLE IF EXISTS " + _tblLoginDate);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductRange);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductCategory);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProduct);
                db.execSQL("DROP TABLE IF EXISTS " + _tblFailure);
                db.execSQL("DROP TABLE IF EXISTS " + _tblFailureCause);
                db.execSQL("DROP TABLE IF EXISTS " + _tblAddedFailuresAndCauses);
                db.execSQL("DROP TABLE IF EXISTS " + _tblService);
                db.execSQL("DROP TABLE IF EXISTS " + _tblServiceNorm);
                db.execSQL("DROP TABLE IF EXISTS " + _tblAddedServices);
                db.execSQL("DROP TABLE IF EXISTS " + _tblPriceList);
                db.execSQL("DROP TABLE IF EXISTS " + _tblPartner);
                db.execSQL("DROP TABLE IF EXISTS " + _tblRegion);
                db.execSQL("DROP TABLE IF EXISTS " + _tblRegionPlace);
                db.execSQL("DROP TABLE IF EXISTS " + _tblMaterial);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWarehouse);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWarehouseState);
                db.execSQL("DROP TABLE IF EXISTS " + _tblPriorityType);
                db.execSQL("DROP TABLE IF EXISTS " + _tblCheckIn);
                db.execSQL("DROP TABLE IF EXISTS " + _tblCheckInPreDefComment);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrder);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderType);
                db.execSQL("DROP TABLE IF EXISTS " + _tblImageType);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderService);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderFailureCause);
                db.execSQL("DROP TABLE IF EXISTS " + _tblSignatureImage);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderImage);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderResult);
                db.execSQL("DROP TABLE IF EXISTS " + _tblStartStop);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductDocumentation);
                db.execSQL("DROP TABLE IF EXISTS " + _tblPollQuestionType);
                db.execSQL("DROP TABLE IF EXISTS " + _tblPollQuestion);
                db.execSQL("DROP TABLE IF EXISTS " + _tblNoSignatureComment);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductMaterial);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductService);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductFailure);
                db.execSQL("DROP TABLE IF EXISTS " + _tblAddedPollQuestions);
                db.execSQL("DROP TABLE IF EXISTS " + _tblProductColor);
                db.execSQL("DROP TABLE IF EXISTS " + _tblWorkOrderPrintInfo);
                onCreate(db);
            }
        }
    }

    public void firstInit() throws SQLException {
        if (_dbDatabase == null) {
            DbHelper local = new DbHelper(_dbContext);
            local.getWritableDatabase();
            local = new DbHelper(_dbContext);
            local.getWritableDatabase();
        }
    }

    public BrokerSQLite(Context c) {
        _dbContext = c;
        //  context = c;

    }

    public BrokerSQLite open() throws SQLException {
        if (_dbDatabase == null) {
            _dbHelper = new DbHelper(_dbContext);
            _dbDatabase = _dbHelper.getWritableDatabase();
        } else if (!_dbDatabase.isOpen()) {
            _dbDatabase = _dbHelper.getWritableDatabase();
        }

        return this;
    }

    public void close() {
        _dbDatabase.close();
    }

    public Integer getEmployeeID(String userName, String password) {
        int employeeID = 0;

        String[] columns = new String[]{"EmployeeID", "Username", "Password",
                "DeviceNo"};

        Cursor c = _dbDatabase.query(_tblEmployee, columns, null, null, null,
                null, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (userName.equalsIgnoreCase(c.getString(1))
                    && password.equalsIgnoreCase(c.getString(2)))// &&
            // c.getString(2))
            {
                employeeID = c.getInt(0);
                MainActivity.deviceNo = c.getString(3);
            }
        }

        return employeeID;
    }

    public int getEmployeeID() {
        int employeeID = -1;
        String sql = "SELECT EmployeeID FROM " + _tblEmployee
                + " LIMIT 1";

        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            employeeID = c.getInt(c.getColumnIndex("EmployeeID"));
        }
        c.close();
        return employeeID;

    }

    public void employee_D() throws Exception {
        _dbDatabase.delete(_tblEmployee, null, null);
    }

    public void employee_I(int employeeID, String employeeName, String username, String password,
                           String deviceNo, int employeeTypeID, int erpID,
                           int supervisorID, int checkInCount) {

        ContentValues cv = new ContentValues();

        cv.put("EmployeeID", employeeID);
        cv.put("EmployeeName", employeeName);
        cv.put("Username", username);
        cv.put("Password", password);
        cv.put("DeviceNo", deviceNo);
        cv.put("EmployeeTypeID", employeeTypeID);
        cv.put("ErpID", erpID);
        cv.put("SupervisorID", supervisorID);
        cv.put("CheckInCount", checkInCount);

        _dbDatabase.insert(_tblEmployee, null, cv);
    }


    public void employee_U(int employeeID, String employeeName, String username, String password,
                           String deviceNo, int employeeTypeID, int erpID,
                           int supervisorID, int checkInCount) {

        ContentValues cv = new ContentValues();

        cv.put("EmployeeID", employeeID);
        cv.put("EmployeeName", employeeName);
        cv.put("Username", username);
        cv.put("Password", password);
        cv.put("DeviceNo", deviceNo);
        cv.put("EmployeeTypeID", employeeTypeID);
        cv.put("ErpID", erpID);
        cv.put("SupervisorID", supervisorID);
        cv.put("CheckInCount", checkInCount);

        _dbDatabase.update(_tblEmployee, cv, " EmployeeID= " + employeeID, null);
    }

    public String getEmployeeNameByID(int employeeID) {
        String employeeName = "";
        String sql = "SELECT EmployeeName FROM Employee WHERE EmployeeID = " + employeeID + " LIMIT 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            employeeName = c.getString(c.getColumnIndex("EmployeeName"));
        }
        c.close();
        return employeeName;
    }


    public String getModifiedDate(String tableName) throws Exception {
        String modifiedDate = "";
        if (!tableName.equalsIgnoreCase("ApplicationVersion")) {
            String sql = "";
            sql = " SELECT ModifiedDate " + " FROM " + tableName
                    + " ORDER BY ModifiedDate" + " DESC ";

            Cursor c = _dbDatabase.rawQuery(sql, null);
            /*
             * if (c.isNull(0)) { modifiedDate = "SVI"; }
             */
            c.moveToFirst();
            if (!c.isAfterLast())
                if (c.isNull(0) || c.getString(0).equalsIgnoreCase("")) {
                    modifiedDate = "SVI";
                } else {
                    modifiedDate = c.getString(0);
                }

            if (modifiedDate.equalsIgnoreCase("")) {
                modifiedDate = "SVI";
            }
            c.close();
        } else {
            modifiedDate = "SVI";
        }

        return modifiedDate;
    }

    public String getModifiedDateForAddedFeatures(String tableName) throws Exception {
        String modifiedDate = "";
        if (!tableName.equalsIgnoreCase("ApplicationVersion")) {
            String sql = "";
            sql = " SELECT CreatedDate " + " FROM " + tableName
                    + " WHERE WorkOrderResultID IS NULL"
                    + " ORDER BY CreatedDate" + " DESC ";

            Cursor c = _dbDatabase.rawQuery(sql, null);
            /*
             * if (c.isNull(0)) { modifiedDate = "SVI"; }
             */
            c.moveToFirst();
            if (!c.isAfterLast())
                if (c.isNull(0) || c.getString(0).equalsIgnoreCase("")) {
                    modifiedDate = "SVI";
                } else {
                    modifiedDate = c.getString(0);
                }

            if (modifiedDate.equalsIgnoreCase("")) {
                modifiedDate = "SVI";
            }
            c.close();
        } else {
            modifiedDate = "SVI";
        }

        return modifiedDate;
    }

    public void applicationVersion_D() {
        _dbDatabase.delete(_tblApplicationVersion, null, null);
    }

    public void applicationVersion_I(int applicationVersionID, int versionCode, String versionName,
                                     String description, String downloadLink, String createdDate) {
        ContentValues cv = new ContentValues();
        cv.put("ApplicationVersionID", applicationVersionID);
        cv.put("VersionCode", versionCode);
        cv.put("VersionName", versionName);
        cv.put("Description", description);
        cv.put("DownloadLink", downloadLink);
        cv.put("CreatedDate", createdDate);

        _dbDatabase.insert(_tblApplicationVersion, null, cv);
    }


    public void loginDate_I(String date) {

        ContentValues cv = new ContentValues();

        cv.put("LoginDate", date);

        _dbDatabase.insert(_tblLoginDate, null, cv);

    }

    public void loginDate_U(String date) {

        ContentValues cv = new ContentValues();

        cv.put("LoginDate", date);

        _dbDatabase.update(_tblLoginDate, cv, null, null);

    }

    public String getLoginDate() {
        String date = "";

        String sql = "SELECT LoginDate from " + _tblLoginDate;

        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            date = c.getString(0);
        }
        c.close();
        return date;

    }

    public void productRange_D(int productRangeID) {
        _dbDatabase.delete(_tblProductRange, "ProductRangeID = " + productRangeID, null);
    }

    public void productRange_I(int productRangeID, String productRangeName, String productRangeCode, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("ProductRangeID", productRangeID);
        cv.put("ProductRangeName", productRangeName);
        cv.put("ProductRangeCode", productRangeCode);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductRange, null, cv);
    }

    public void productCategory_D(int productCategoryID) {
        _dbDatabase.delete(_tblProductCategory, "ProductCategoryID = " + productCategoryID, null);
    }

    public void productCategory_I(int productCategoryID, int productRangeID, String productCategoryName, String productCategoryCode, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("ProductCategoryID", productCategoryID);
        cv.put("ProductRangeID", productRangeID);
        cv.put("ProductCategoryName", productCategoryName);
        cv.put("ProductCategoryCode", productCategoryCode);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductCategory, null, cv);
    }


    public void product_D(int productID) {
        _dbDatabase.delete(_tblProduct, "ProductID = " + productID, null);
    }

    public void product_I(int productID, String productName, String productCode, String model,
                          String barcode, String description, String modifiedDate, int productRangeID,
                          String unitOfMeasure, double width, double height,
                          double length, double weight) {

        ContentValues cv = new ContentValues();
        cv.put("ProductID", productID);
        cv.put("ProductName", productName);
        cv.put("ProductCode", productCode);
        cv.put("Model", model);
        cv.put("Barcode", barcode);
        cv.put("Description", description);
        cv.put("ModifiedDate", modifiedDate);
        cv.put("ProductRangeID", productRangeID);
        cv.put("UnitOfMeasure", unitOfMeasure);
        cv.put("Width", width);
        cv.put("Height", height);
        cv.put("Length", length);
        cv.put("Weight", weight);

        _dbDatabase.insert(_tblProduct, null, cv);

    }

    public List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, ProductCode, Model FROM " + _tblProduct + " ORDER BY ProductName";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Product product = new Product();
            product.setProductID(c.getInt(c.getColumnIndex("ProductID")));
            product.setProductName(c.getString(c.getColumnIndex("ProductName")));
            product.setProductCode(c.getString(c.getColumnIndex("ProductCode")));
            product.setModel(c.getString(c.getColumnIndex("Model")));
            productList.add(product);
        }
        c.close();
        return productList;
    }

    public Product getProductByProductID(int productID) {
        Product product = null;
        String sql = "SELECT ProductID, ProductCode, ProductName, Model FROM " + _tblProduct
                + " WHERE ProductID = " + productID + " LIMIT 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            product = new Product();
            product.setProductID(c.getInt(c.getColumnIndex("ProductID")));
            product.setProductName(c.getString(c.getColumnIndex("ProductName")));
            product.setProductCode(c.getString(c.getColumnIndex("ProductCode")));
            product.setModel(c.getString(c.getColumnIndex("Model")));
        }
        c.close();
        return product;
    }

    public void failure_D(int failureID) {
        _dbDatabase.delete(_tblFailure, "FailureID = " + failureID, null);
    }

    public void failure_I(int failureID, String failureName, String failureCode,
                          String description, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("FailureID", failureID);
        cv.put("FailureName", failureName);
        cv.put("FailureCode", failureCode);
        cv.put("Description", description);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblFailure, null, cv);
    }

    public List<Failure> getFailureList(int selectedProductID) {
        List<Failure> failureList = new ArrayList<>();
        Failure failure;
        failure = new Failure();
        failure.setFailureID(-1);
        failure.setFailureName("--Odaberite kvar--");
        failureList.add(failure);

        String sql = "SELECT DISTINCT f.FailureID, f.FailureName FROM Failure f " +
                " LEFT JOIN ProductFailure pf on pf.FailureID = f.FailureID " +
                " WHERE pf.ProductID = " + selectedProductID + " OR f.FailureCode = 999 " +
                " ORDER BY f.FailureCode";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            failure = new Failure();
            failure.setFailureID(c.getInt(c.getColumnIndex("FailureID")));
            failure.setFailureName(c.getString(c.getColumnIndex("FailureName")));
            failureList.add(failure);
        }
        c.close();
        return failureList;
    }

    public List<AddedFailuresAndCauses> getAddedFailuresAndCauses(int workOrderResultID, int selectedRouteID) {
        List<AddedFailuresAndCauses> addedFailuresAndCausesList = new ArrayList<>();
        String sql = "SELECT afc.FailureID, afc.FailureCauseID, f.FailureName, fc.FailureCauseName, afc.CreatedDate FROM AddedFailuresAndCauses afc " +
                " INNER JOIN Failure f on f.FailureID = afc.FailureID " +
                " INNER JOIN FailureCause fc on fc.FailureCauseID = afc.FailureCauseID " +
                " WHERE afc.WorkOrderResultID = " + workOrderResultID + " AND afc.WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            AddedFailuresAndCauses addedFailuresAndCauses = new AddedFailuresAndCauses(
                    c.getInt(c.getColumnIndex("FailureID")),
                    c.getInt(c.getColumnIndex("FailureCauseID")),
                    c.getString(c.getColumnIndex("FailureName")),
                    c.getString(c.getColumnIndex("FailureCauseName")),
                    c.getString(c.getColumnIndex("CreatedDate")));

            addedFailuresAndCausesList.add(addedFailuresAndCauses);
        }
        c.close();
        return addedFailuresAndCausesList;
    }

    public List<AddedFailuresAndCauses> getAddedFailuresAndCausesForPrinting(int selectedRouteID) {
        List<AddedFailuresAndCauses> addedFailuresAndCausesList = new ArrayList<>();
        String sql = "SELECT DISTINCT f.FailureName, fc.FailureCauseName, afc.CreatedDate FROM AddedFailuresAndCauses afc " +
                " INNER JOIN Failure f on f.FailureID = afc.FailureID " +
                " INNER JOIN FailureCause fc on fc.FailureCauseID = afc.FailureCauseID " +
                " WHERE afc.WorkOrderID = " + selectedRouteID +
                " ORDER BY afc.CreatedDate";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            AddedFailuresAndCauses addedFailuresAndCauses = new AddedFailuresAndCauses();
            addedFailuresAndCauses.setFailureName(c.getString(c.getColumnIndex("FailureName")));
            addedFailuresAndCauses.setFailureCauseName(c.getString(c.getColumnIndex("FailureCauseName")));
            addedFailuresAndCausesList.add(addedFailuresAndCauses);
        }
        c.close();
        return addedFailuresAndCausesList;
    }

    public List<PollQuestion> getAddedPollQuestions(int workOrderResultID, int selectedRouteID) {
        List<PollQuestion> addedPollQuestionList = new ArrayList<>();
        String sql = "SELECT WorkOrderQuestionID, PollQuestionAnswer FROM " + _tblAddedPollQuestions
                + " WHERE WorkOrderID = " + selectedRouteID + " AND WorkOrderResultID = " + workOrderResultID;

        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            PollQuestion pollQuestion = new PollQuestion();
            pollQuestion.setWorkOrderQuestionID(c.getInt(c.getColumnIndex("WorkOrderQuestionID")));
            pollQuestion.setPollQuestionAnswer(c.getString(c.getColumnIndex("PollQuestionAnswer")));
            addedPollQuestionList.add(pollQuestion);
        }
        c.close();
        return addedPollQuestionList;
    }

    public List<PollQuestion> getAddedPollQuestionsDraft(int workOrderResultID, int selectedRouteID) {
        List<PollQuestion> addedPollQuestionList = new ArrayList<>();
        String sql = "SELECT pq.QuestionText,  apq.PollQuestionAnswer "
                + " FROM AddedPollQuestions apq "
                + " INNER JOIN PollQuestion pq ON pq.WorkOrderQuestionID = apq.WorkOrderQuestionID"
                + " WHERE WorkOrderID = " + selectedRouteID + " AND WorkOrderResultID = " + workOrderResultID;

        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            PollQuestion pollQuestion = new PollQuestion();
            pollQuestion.setQuestionText(c.getString(c.getColumnIndex("QuestionText")));
            pollQuestion.setPollQuestionAnswer(c.getString(c.getColumnIndex("PollQuestionAnswer")));
            addedPollQuestionList.add(pollQuestion);
        }
        c.close();
        return addedPollQuestionList;
    }

    public List<Failure> getWorkOrderFailureList(int selectedRouteID) {
        List<Failure> failureList = new ArrayList<>();
        Failure failure;
        failure = new Failure();
        failure.setFailureID(-1);
        failure.setFailureName("--Odaberite kvar--");
        failureList.add(failure);
        String sql = "SELECT DISTINCT wor.FailureID, fai.FailureName FROM WorkOrderFailureCause wor " +
                " INNER JOIN Failure fai ON fai.FailureID = wor.FailureID " +
                " WHERE WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            failure = new Failure();
            failure.setFailureID(c.getInt(c.getColumnIndex("FailureID")));
            failure.setFailureName(c.getString(c.getColumnIndex("FailureName")));
            failureList.add(failure);
        }
        c.close();
        return failureList;
    }

    public void failureCause_D(int failureCauseID) {
        _dbDatabase.delete(_tblFailureCause, "FailureCauseID = " + failureCauseID, null);
    }

    public void failureCause_I(int failureCauseID, int failureID, String failureCauseName,
                               String failureCauseCode, String description, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("FailureCauseID", failureCauseID);
        cv.put("FailureID", failureID);
        cv.put("FailureCauseName", failureCauseName);
        cv.put("FailureCauseCode", failureCauseCode);
        cv.put("Description", description);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblFailureCause, null, cv);

    }

    public void addedFailuresAndCauses_IU(int workOrderResultID, int selectedRouteID, int failureID, int failureCauseID) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderResultID", workOrderResultID);
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("FailureID", failureID);
        cv.put("FailureCauseID", failureCauseID);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("IsSent", 0);


        if (checkIfAddedFailuresAndCausesExist(workOrderResultID, selectedRouteID, failureID, failureCauseID)) {
            _dbDatabase.update(_tblAddedFailuresAndCauses, cv, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND FailureID = " + failureID + " AND FailureCauseID = " + failureCauseID, null);
        } else {
            _dbDatabase.insert(_tblAddedFailuresAndCauses, null, cv);
        }

    }

    private boolean checkIfAddedFailuresAndCausesExist(int workOrderResultID, int selectedRouteID, int failureID, int failureCauseID) {

        String sql = "Select FailureID FROM " + _tblAddedFailuresAndCauses
                + " WHERE WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND FailureID = " + failureID + " AND FailureCauseID = " + failureCauseID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    private boolean checkIfAddedFailuresAndCausesForOpenWOExist(int selectedRouteID, int failureID, int failureCauseID, String createdDate) {

        String sql = "Select FailureID FROM " + _tblAddedFailuresAndCauses
                + " WHERE WorkOrderID = " + selectedRouteID + " AND FailureID = " + failureID + " AND FailureCauseID = " + failureCauseID + " AND CreatedDate = '" + createdDate + "' AND WorkOrderResultID IS  NULL";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public void addedFailuresAndCauses_D(int workOrderResultID, int selectedRouteID, int selectedFailureID, int failureCauseID) {
        _dbDatabase.delete(_tblAddedFailuresAndCauses, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND FailureID = " + selectedFailureID + " AND FailureCauseID = " + failureCauseID, null);
    }

    public void addedFailuresAndCauses_D(int workOrderResultID, int selectedRouteID) {
        _dbDatabase.delete(_tblAddedFailuresAndCauses, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID, null);
    }

    public List<FailureCause> getFailureCauseList(int selectedFailureID, int selectedProductID) {
        List<FailureCause> failureCauseList = new ArrayList<>();
        String sql = "SELECT fc.FailureCauseID, fc.FailureCauseName, fc.FailureCauseCode, fc.Description FROM FailureCause fc " +
                " INNER JOIN ProductFailure pf on pf.FailureCauseID = fc.FailureCauseID "
                + " WHERE fc.FailureID = " + selectedFailureID + " AND pf.ProductID = " + selectedProductID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            FailureCause failureCause = new FailureCause();
            failureCause.setFailureCauseID(c.getInt(c.getColumnIndex("FailureCauseID")));
            failureCause.setFailureCauseName(c.getString(c.getColumnIndex("FailureCauseName")));
            failureCause.setFailureCauseCode(c.getString(c.getColumnIndex("FailureCauseCode")));
            failureCause.setDescription(c.getString(c.getColumnIndex("Description")));
            failureCauseList.add(failureCause);
        }
        c.close();
        return failureCauseList;
    }

    public String getFailureCodeByID(int selectedFailureID) {
        String failureCode = "";
        String sql = "SELECT FailureCode FROM Failure WHERE FailureID = " + selectedFailureID + " LIMIT 1";

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            failureCode = c.getString(c.getColumnIndex("FailureCode"));
        }
        c.close();
        return failureCode;
    }


    public void service_D(int serviceID) {
        _dbDatabase.delete(_tblService, "ServiceID = " + serviceID, null);
    }

    public void service_I(int serviceID, String serviceName, String serviceCode, String unitOfMeasure, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("ServiceID", serviceID);
        cv.put("ServiceName", serviceName);
        cv.put("ServiceCode", serviceCode);
        cv.put("UnitOfMeasure", unitOfMeasure);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblService, null, cv);

    }


    public List<Service> getServiceList(int selectedProductID) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT DISTINCT s.ServiceID, s.ServiceName, s.ServiceCode, s.UnitOfMeasure, IFNULL(sn.NormTime, '') as NormTime, IFNULL(pl.Price, 0) as Price FROM Service s " +
                " INNER JOIN ProductService ps on ps.ServiceID = s.ServiceID " +
                " LEFT JOIN ServiceNorm sn on sn.ServiceID = s.ServiceID " +
                " INNER JOIN PriceList pl on pl.EntityType = 1 " +
                " WHERE ps.ProductID = " + selectedProductID + " AND sn.ProductID = " + selectedProductID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Service service = new Service();
            service.setServiceID(c.getInt(c.getColumnIndex("ServiceID")));
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setServiceCode(c.getString(c.getColumnIndex("ServiceCode")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setNormTime(c.getString(c.getColumnIndex("NormTime")));
            double normTime = c.getString(c.getColumnIndex("NormTime")).isEmpty() ? 0 : Double.parseDouble(c.getString(c.getColumnIndex("NormTime")));
            service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * normTime);
            serviceList.add(service);
        }
        c.close();
        return serviceList;
    }

    public List<Service> getWorkOrderServiceList(int selectedRouteID, int selectedProductID) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT DISTINCT wor.ServiceID, ser.ServiceName, ser.ServiceCode, ser.UnitOfMeasure, IFNULL(sn.NormTime, '') as NormTime, IFNULL(pl.Price, 0) as Price FROM WorkOrderService wor "
                + " INNER JOIN Service ser ON ser.ServiceID = wor.ServiceID "
                + " LEFT JOIN ServiceNorm sn on wor.ServiceID = sn.ServiceID"
                + " INNER JOIN PriceList pl on pl.EntityType = 1 "
                + " WHERE WorkOrderID = " + selectedRouteID + " AND sn.ProductID = " + selectedProductID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Service service = new Service();
            service.setServiceID(c.getInt(c.getColumnIndex("ServiceID")));
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setServiceCode(c.getString(c.getColumnIndex("ServiceCode")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setNormTime(c.getString(c.getColumnIndex("NormTime")));
            double normTime = c.getString(c.getColumnIndex("NormTime")).isEmpty() ? 0 : Double.parseDouble(c.getString(c.getColumnIndex("NormTime")));
            service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * normTime);
            serviceList.add(service);
        }
        c.close();
        return serviceList;

    }


    public void serviceNorm_D(int serviceNormID) {
        _dbDatabase.delete(_tblServiceNorm, "ServiceNormID = " + serviceNormID, null);
    }

    public void serviceNorm_I(int serviceNormID, int serviceID, int productID, String normName,
                              String normCode, String normTime, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("ServiceNormID", serviceNormID);
        cv.put("ServiceID", serviceID);
        cv.put("ProductID", productID);
        cv.put("NormName", normName);
        cv.put("NormCode", normCode);
        cv.put("NormTime", normTime);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblServiceNorm, null, cv);

    }

    public ServiceNorm getServiceNormForServiceID(int serviceID, int productID) {
        ServiceNorm norm = null;
        String sql = "SELECT NormName, NormCode, NormTime  FROM " + _tblServiceNorm + " WHERE ServiceID = " + serviceID + " AND ProductID = " + productID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            norm = new ServiceNorm();
            norm.setNormName(c.getString(c.getColumnIndex("NormName")));
            norm.setNormCode(c.getString(c.getColumnIndex("NormCode")));
            norm.setNormTime(c.getString(c.getColumnIndex("NormTime")));
        }
        c.close();
        return norm;
    }

    public void addedService_IU(int workOrderResultID, int selectedRouteID, int serviceID, String serviceName, String unitOfMeasure, Integer unitSpent) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderResultID", workOrderResultID);
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("ServiceID", serviceID);
        cv.put("ServiceName", serviceName);
        cv.put("UnitOfMeasure", unitOfMeasure);
        cv.put("UnitSpent", unitSpent);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("IsSent", 0);


        if (checkIfAddedServiceExists(workOrderResultID, selectedRouteID, serviceID)) {
            _dbDatabase.update(_tblAddedServices, cv, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND ServiceID = " + serviceID, null);
        } else {
            _dbDatabase.insert(_tblAddedServices, null, cv);
        }

    }

    public List<Service> getAddedServices(int workOrderResultID, int selectedRouteID, int selectedProductID) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT DISTINCT aser.ServiceID, aser.ServiceName, s.UnitOfMeasure, aser.UnitSpent, aser.CreatedDate,IFNULL(sn.NormTime, '') as NormTime, IFNULL(pl.Price, 0) as Price FROM AddedServices aser" +
                " INNER JOIN Service s on aser.ServiceID = s.ServiceID "
                + " LEFT JOIN ServiceNorm sn on aser.ServiceID = sn.ServiceID"
                + " INNER JOIN PriceList pl on pl.EntityType = 1 "
                + " WHERE WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND sn.ProductID = " + selectedProductID;
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Service service = new Service();
            service.setServiceID(c.getInt(c.getColumnIndex("ServiceID")));
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setUnitSpent(c.getInt(c.getColumnIndex("UnitSpent")));
            service.setNormTime(c.getString(c.getColumnIndex("NormTime")));
            double normTime = c.getString(c.getColumnIndex("NormTime")).isEmpty() ? 0 : Double.parseDouble(c.getString(c.getColumnIndex("NormTime")));
          //  service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * normTime);
            service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * c.getInt(c.getColumnIndex("UnitSpent")));
            service.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            serviceList.add(service);
        }
        c.close();
        return serviceList;
    }

    public List<Service> getAddedServicesForPrinting(int selectedRouteID, int selectedProductID) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT DISTINCT s.ServiceName, s.UnitOfMeasure, aser.UnitSpent, aser.CreatedDate, IFNULL(sn.NormTime, '') as NormTime, IFNULL(pl.Price, 0) as Price FROM AddedServices aser " +
                " INNER JOIN Service s on aser.ServiceID = s.ServiceID "
                + " LEFT JOIN ServiceNorm sn on aser.ServiceID = sn.ServiceID"
                + " INNER JOIN PriceList pl on pl.EntityType = 1 "
                + " WHERE aser.WorkOrderID = " + selectedRouteID + " AND sn.ProductID = " + selectedProductID
                + " ORDER BY aser.CreatedDate";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Service service = new Service();
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setUnitSpent(c.getInt(c.getColumnIndex("UnitSpent")));
            service.setNormTime(c.getString(c.getColumnIndex("NormTime")));
            double normTime = c.getString(c.getColumnIndex("NormTime")).isEmpty() ? 0 : Double.parseDouble(c.getString(c.getColumnIndex("NormTime")));
            //service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * normTime);
            service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * c.getInt(c.getColumnIndex("UnitSpent")));
            serviceList.add(service);
        }
        c.close();
        return serviceList;
    }

    public boolean checkIfAddedServiceExists(int workOrderResultID, int selectedRouteID, int serviceID) {

        String sql = "Select ServiceName FROM " + _tblAddedServices
                + " WHERE WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND ServiceID = " + serviceID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public boolean checkIfAddedServiceForOpenWOExists(int selectedRouteID, int serviceID, String createdDate) {

        String sql = "Select UnitSpent FROM " + _tblAddedServices
                + " WHERE WorkOrderID = " + selectedRouteID + " AND ServiceID = " + serviceID + " AND CreatedDate = '" + createdDate + "' AND WorkOrderResultID IS  NULL";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }


    public void addedService_D(int workOrderResultID, int selectedRouteID, int serviceID) {
        _dbDatabase.delete(_tblAddedServices, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND ServiceID = " + serviceID, null);
    }

    public void addedServices_D(int workOrderResultID, int selectedRouteID) {
        _dbDatabase.delete(_tblAddedServices, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID, null);
    }

    public Service getUpdatedAddedService(int selectedRouteID, int serviceID) {
        Service service = null;
        String sql = "SELECT SericeID, ServiceName, UnitOfMeasure, UnitSpent FROM " + _tblAddedServices
                + " WHERE WorkOrderID = " + selectedRouteID + " AND ServiceID = " + serviceID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            service = new Service();
            service.setServiceID(c.getInt(c.getColumnIndex("ServiceID")));
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setUnitSpent(c.getInt(c.getColumnIndex("UnitSpent")));
        }
        c.close();
        return service;
    }

    public List<Material> getMaterialList(int selectedProductID) {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT m.MaterialID, m.MaterialName, m.MaterialCode, m.UnitOfMeasure, IFNULL(pl.Price, 0) as Price FROM Material m " +
                "INNER JOIN ProductMaterial pm ON pm.MaterialID = m.MaterialID " +
                "LEFT JOIN PriceList pl ON pl.EntityCode = m.MaterialCode" +
                " WHERE pm.ProductID = " + selectedProductID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Material material = new Material();
            material.setMaterialID(c.getInt(c.getColumnIndex("MaterialID")));
            material.setMaterialName(c.getString(c.getColumnIndex("MaterialName")));
            material.setMaterialCode(c.getString(c.getColumnIndex("MaterialCode")));
            material.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            material.setPrice(c.getDouble(c.getColumnIndex("Price")));
            materialList.add(material);
        }
        c.close();
        return materialList;
    }


    public void addedMaterial_IU(int workOrderResultID, int selectedRouteID, int materialID, String materialName, String unitOfMeasure, int quantitySpent) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderResultID", workOrderResultID);
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("MaterialID", materialID);
        cv.put("MaterialName", materialName);
        cv.put("UnitOfMeasure", unitOfMeasure);
        cv.put("QuantitySpent", quantitySpent);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("IsSent", 0);

        if (checkIfAddedMaterialExists(workOrderResultID, selectedRouteID, materialID)) {
            _dbDatabase.update(_tblAddedMaterials, cv, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND MaterialID = " + materialID, null);

        } else {
            _dbDatabase.insert(_tblAddedMaterials, null, cv);
        }
    }

    public List<Material> getAddedMaterials(int workOrderResultID, int selectedRouteID) {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT am.MaterialID, am.MaterialName, am.UnitOfMeasure, am.QuantitySpent, am.CreatedDate, IFNULL(pl.Price, 0) as Price FROM AddedMaterials am " +
                " INNER JOIN Material m ON m.MaterialID = am.MaterialID " +
                " LEFT JOIN PriceList pl ON pl.EntityCode = m.MaterialCode "
                + " WHERE WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Material material = new Material();
            material.setMaterialID(c.getInt(c.getColumnIndex("MaterialID")));
            material.setMaterialName(c.getString(c.getColumnIndex("MaterialName")));
            material.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            material.setQuantitySpent(c.getInt(c.getColumnIndex("QuantitySpent")));
            material.setPrice(c.getDouble(c.getColumnIndex("Price")));
            material.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            materialList.add(material);
        }
        c.close();
        return materialList;
    }

    public List<Material> getAddedMaterialsForPrinting(int selectedRouteID) {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT DISTINCT m.MaterialName, m.UnitOfMeasure, am.QuantitySpent, am.CreatedDate, IFNULL(pl.Price, 0) as Price FROM AddedMaterials am " +
                " INNER JOIN Material m ON m.MaterialID = am.MaterialID" +
                " LEFT JOIN PriceList pl ON pl.EntityCode = m.MaterialCode " +
                " WHERE WorkOrderID = " + selectedRouteID
                + " ORDER BY am.CreatedDate";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Material material = new Material();
            material.setMaterialName(c.getString(c.getColumnIndex("MaterialName")));
            material.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            material.setQuantitySpent(c.getInt(c.getColumnIndex("QuantitySpent")));
            material.setPrice(c.getDouble(c.getColumnIndex("Price")));
            materialList.add(material);
        }
        c.close();
        return materialList;
    }

    public boolean checkIfAddedMaterialExists(int workOrderResultID, int selectedRouteID, int materialID) {

        String sql = "Select MaterialName FROM " + _tblAddedMaterials
                + " WHERE WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND MaterialID = " + materialID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public boolean checkIfAddedMaterialForOpenWOExists(int selectedRouteID, int materialID, String createdDate) {

        String sql = "Select QuantitySpent FROM " + _tblAddedMaterials
                + " WHERE WorkOrderID = " + selectedRouteID + " AND MaterialID = " + materialID + " AND CreatedDate = '" + createdDate + "' AND WorkOrderResultID IS  NULL";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public void addedMaterial_D(int workOrderResultID, int selectedRouteID, int materialID) {
        _dbDatabase.delete(_tblAddedMaterials, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID + " AND MaterialID = " + materialID, null);
    }

    public void addedMaterials_D(int workOrderResultID, int selectedRouteID) {
        _dbDatabase.delete(_tblAddedMaterials, "WorkOrderResultID = " + workOrderResultID + " AND WorkOrderID = " + selectedRouteID, null);
    }


    public void priceList_D(int priceListID) {
        _dbDatabase.delete(_tblPriceList, "PriceListID = " + priceListID, null);
    }

    public void priceList_I(int priceListID, String priceListCode, int entityType, String entityCode, double price, String priceListDate, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("PriceListID", priceListID);
        cv.put("PriceListCode", priceListCode);
        cv.put("EntityType", entityType);
        cv.put("EntityCode", entityCode);
        cv.put("Price", price);
        cv.put("PriceListDate", priceListDate);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblPriceList, null, cv);

    }


    public void partner_D(int partnerID) {

        _dbDatabase.delete(_tblPartner, "PartnerID = " + partnerID, null);

    }

    public void partner_I(int partnerID, String partnerName, String partnerCode,
                          String address, String contact, String pib, boolean legal, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("PartnerID", partnerID);
        cv.put("PartnerName", partnerName);
        cv.put("PartnerCode", partnerCode);
        cv.put("Address", address);
        cv.put("Contact", contact);
        cv.put("Pib", pib);
        cv.put("IsLegal", legal ? 1 : 0);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblPartner, null, cv);
    }


    public void region_D(int regionID) {
        _dbDatabase.delete(_tblRegion, "RegionID = " + regionID, null);
    }

    public void region_I(int regionID, String regionName, String regionCode, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("RegionID", regionID);
        cv.put("RegionName", regionName);
        cv.put("RegionCode", regionCode);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblRegion, null, cv);
    }


    public void regionPlace_D(int regionPlaceID) {
        _dbDatabase.delete(_tblRegionPlace, "RegionPlaceID = " + regionPlaceID, null);

    }

    public void regionPlace_I(int regionPlaceID, int regionID, String regionPlaceName, String regionPlaceCode, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("RegionPlaceID", regionPlaceID);
        cv.put("RegionID", regionID);
        cv.put("RegionPlaceName", regionPlaceName);
        cv.put("RegionPlaceCode", regionPlaceCode);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblRegionPlace, null, cv);
    }


    public void material_D(int materialID) {
        _dbDatabase.delete(_tblMaterial, "MaterialID = " + materialID, null);

    }

    public void material_I(int materialID, String materialName, String materialCode, String unitOfMeasure, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("MaterialID", materialID);
        cv.put("MaterialName", materialName);
        cv.put("MaterialCode", materialCode);
        cv.put("UnitOfMeasure", unitOfMeasure);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblMaterial, null, cv);

    }


    public void warehouse_D(int warehouseID) {
        _dbDatabase.delete(_tblWarehouse, "WarehouseID = " + warehouseID, null);

    }

    public void warehouse_I(int warehouseID, String warehouseName,
                            String warehouseCode, String warehousePosition, String modifiedDate) {


        ContentValues cv = new ContentValues();
        cv.put("WarehouseID", warehouseID);
        cv.put("WarehouseName", warehouseName);
        cv.put("WarehouseCode", warehouseCode);
        cv.put("WarehousePosition", warehousePosition);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblWarehouse, null, cv);

    }

    public void warehouseState_D(int materialID) {
        _dbDatabase.delete(_tblWarehouseState, "MaterialID = " + materialID, null);
    }

    public void warehouseState_I(int materialID, int warehouseID, double realQuantity, double reservedQuantity, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("MaterialID", materialID);
        cv.put("WarehouseID", warehouseID);
        cv.put("EmployeeID", MainActivity.employeeID);
        cv.put("RealQuantity", realQuantity);
        cv.put("ReservedQuantity", reservedQuantity);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblWarehouseState, null, cv);
    }

    public List<Material> getWarehouseStateMaterials(int employeeID) {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT m.MaterialName, m.MaterialCode, m.UnitOfMeasure, " +
                "whs.RealQuantity ,whs.ReservedQuantity   FROM WarehouseState whs " +
                " INNER JOIN Material m ON m.MaterialID = whs.MaterialID " +
                " WHERE whs.EmployeeID = " + employeeID +
                " ORDER BY m.MaterialName ";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Material material = new Material();
            material.setMaterialName(c.getString(c.getColumnIndex("MaterialName")));
            material.setMaterialCode(c.getString(c.getColumnIndex("MaterialCode")));
            material.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            material.setRealQuantity(c.getDouble(c.getColumnIndex("RealQuantity")));
            material.setReservedQuantity(c.getDouble(c.getColumnIndex("ReservedQuantity")));
            materialList.add(material);
        }
        c.close();
        return materialList;

    }

    public Warehouse getEmployeeWarehouse(int employeeID) {
        Warehouse warehouse = new Warehouse();
        String sql = "SELECT wh.WarehouseName, wh.WarehouseCode FROM WarehouseState whs " +
                " INNER JOIN Warehouse wh on wh.WarehouseID = whs.WarehouseID " +
                " WHERE whs.EmployeeID = " + employeeID +
                " ORDER BY whs.WarehouseID LIMIT 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            warehouse.setWarehouseName(c.getString(c.getColumnIndex("WarehouseName")));
            warehouse.setWarehouseCode(c.getString(c.getColumnIndex("WarehouseCode")));
        }
        c.close();
        return warehouse;
    }


    public void priorityType_D(int priorityTypeID) {
        _dbDatabase.delete(_tblPriorityType, "PriorityTypeID = " + priorityTypeID, null);
    }

    public void priorityType_I(int priorityTypeID, String priorityTypeName, String priorityTypeCode,
                               String priorityTypeDescription, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("PriorityTypeID", priorityTypeID);
        cv.put("PriorityTypeName", priorityTypeName);
        cv.put("PriorityTypeCode", priorityTypeCode);
        cv.put("PriorityTypeDescription", priorityTypeDescription);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblPriorityType, null, cv);
    }


    public void checkInPreDefComment_D(int commentID) {
        _dbDatabase.delete(_tblCheckInPreDefComment, "CommentID = " + commentID, null);
    }

    public void checkInPreDefComment_I(int commentID, String comment, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("CommentID", commentID);
        cv.put("comment", comment);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblCheckInPreDefComment, null, cv);

    }

    public void workOrder_D(int workOrderID) {
        _dbDatabase.delete(_tblWorkOrder, "WorkOrderID = " + workOrderID, null);
    }

    public void workOrder_I(int workOrderID, String workOrderName, String workOrderCode,
                            String upisCode, int workOrderTypeID, int workOrderPollID,
                            String workOrderDate, int partnerID, String customerName,
                            String customerAddress, String customerAddressNo, String customerCity, String customerPhone,
                            boolean legalPerson, boolean customerWarning, int statusID, int productID,
                            double quantity, int regionID, int regionPlaceID,
                            String plannedDate, String realizationDate, boolean inWarranty, String modifiedDate,
                            String description, String note, int priorityTypeID, int materialTypeID, int productColorID) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", workOrderID);
        cv.put("WorkOrderName", workOrderName);
        cv.put("WorkOrderCode", workOrderCode);
        cv.put("UpisCode", upisCode);
        cv.put("WorkOrderTypeID", workOrderTypeID);
        cv.put("WorkOrderPollID", workOrderPollID);
        cv.put("WorkOrderDate", workOrderDate);
        cv.put("PartnerID", partnerID);
        cv.put("CustomerName", customerName);
        cv.put("CustomerAddress", customerAddress);
        cv.put("CustomerAddressNo", customerAddressNo);
        cv.put("CustomerCity", customerCity);
        cv.put("CustomerPhone", customerPhone);
        cv.put("IsLegalPerson", legalPerson ? 1 : 0);
        cv.put("CustomerWarning", customerWarning ? 1 : 0);
        cv.put("StatusID", statusID);
        cv.put("ProductID", productID);
        cv.put("Quantity", quantity);
        cv.put("RegionID", regionID);
        cv.put("RegionPlaceID", regionPlaceID);
        cv.put("PlannedDate", plannedDate);
        cv.put("RealizationDate", realizationDate);
        cv.put("InWarranty", inWarranty ? 1 : 0);
        cv.put("ModifiedDate", modifiedDate);
        cv.put("Description", description);
        cv.put("Note", note);
        cv.put("PriorityTypeID", priorityTypeID);
        cv.put("MaterialTypeID", materialTypeID);
        cv.put("ProductColorID", productColorID);
        _dbDatabase.insert(_tblWorkOrder, null, cv);
    }

    public void workOrderService_D(int workOrderID) {
        _dbDatabase.delete(_tblWorkOrderService, "WorkOrderID = " + workOrderID, null);
    }

    public void workOrder_U(List<WorkOrderResult> workOrderResultList) {
        for (WorkOrderResult workOrderResult : workOrderResultList) {
            int statusID = workOrderResult.isClosed() ? 4 : 3;
            ContentValues cv = new ContentValues();
            cv.put("StatusID", statusID);
            _dbDatabase.update(_tblWorkOrder, cv, "WorkorderID = " + workOrderResult.getWorkOrderID(), null);


        }
    }


    public void workOrderService_I(int workOrderID, Integer serviceID) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", workOrderID);
        cv.put("ServiceID", serviceID);
        _dbDatabase.insert(_tblWorkOrderService, null, cv);
    }

    public void workOrderFailure_D(int workOrderID) {
        _dbDatabase.delete(_tblWorkOrderFailureCause, "WorkOrderID = " + workOrderID, null);
    }

    public void workOrderFailureCause_I(int workOrderID, int failureID,  int failureCauseID) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", workOrderID);
        cv.put("FailureID", failureID);
        cv.put("FailureCauseID", failureCauseID);
        _dbDatabase.insert(_tblWorkOrderFailureCause, null, cv);
    }

    public void workOrderType_D(int workOrderTypeID) {
        _dbDatabase.delete(_tblWorkOrderType, "WorkOrderTypeID = " + workOrderTypeID, null);
    }

    public void workOrderType_I(int workOrderTypeID, String typeName, String typeCode, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderTypeID", workOrderTypeID);
        cv.put("TypeName", typeName);
        cv.put("TypeCode", typeCode);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblWorkOrderType, null, cv);

    }


    public void imageType_D(int imageTypeID) {
        _dbDatabase.delete(_tblImageType, "ImageTypeID = " + imageTypeID, null);

    }

    public void imageType_I(int imageTypeID, int imageTypeCode, String imageTypeName, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("ImageTypeID", imageTypeID);
        cv.put("ImageTypeCode", imageTypeCode);
        cv.put("ImageTypeName", imageTypeName);
        cv.put("ModifiedDate", modifiedDate);

        _dbDatabase.insert(_tblImageType, null, cv);
    }


    public ArrayList<ImageType> getImageTypeList() {
        ArrayList<ImageType> imageTypeArrayList = new ArrayList<>();
        ImageType imageType;
        imageType = new ImageType();
        imageType.setImageTypeID(-1);
        imageType.setImageTypeName("--Odaberite tip slike--");
        imageTypeArrayList.add(imageType);
        String sql = " SELECT ImageTypeID, ImageTypeName FROM " + _tblImageType + " ORDER BY ImageTypeCode";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            imageType = new ImageType();
            imageType.setImageTypeID(c.getInt(c.getColumnIndex("ImageTypeID")));
            imageType.setImageTypeName(c.getString(c.getColumnIndex("ImageTypeName")));
            imageTypeArrayList.add(imageType);
        }
        c.close();
        return imageTypeArrayList;
    }


    public String[] getCheckOutComments() {
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        String sql = "SELECT Comment FROM " + _tblCheckInPreDefComment;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            list.add(c.getString(c.getColumnIndex("Comment")));
        }
        c.close();
        return list.toArray(new String[0]);
    }


    public boolean CheckIn_I(String checkInID, int selectedRouteID, int employeeID, boolean isCheckedIn, String comment, String mileage, boolean isInRoute) throws Exception {

        boolean succeeded = false;
        String coordinates = "";
        Location location = Utilities.getLocationCoordinates(_dbContext);
        if (location != null) {
            coordinates = location.getLatitude() + ","
                    + location.getLongitude();
        }

        try {

            if (isCheckedIn) {

                ContentValues cv = new ContentValues();
                cv.put("CheckInID", checkInID);
                cv.put("WorkOrderID", selectedRouteID);
                cv.put("EmployeeID", employeeID);
                cv.put("CheckInDate", Utilities.getCurrentDateTime());
                cv.put("IsSent", 0);
                cv.put("Comment", comment);
                cv.put("Mileage", mileage);
                cv.put("CheckOutDate", Utilities.getCurrentDateTime());
                cv.put("CheckInCoordinates", coordinates);
                cv.put("IsInRoute", isInRoute ? 1 : 0);

                succeeded = !TextUtils.equals(coordinates, "");

                _dbDatabase.insert(_tblCheckIn, null, cv);

            } else {

                ContentValues cv = new ContentValues();
                cv.put("CheckOutDate", Utilities.getCurrentDateTime());
                cv.put("Comment", comment);
                cv.put("Mileage", mileage);
                cv.put("CheckOutCoordinates", coordinates);

                _dbDatabase.update(_tblCheckIn, cv, "CheckInID = '" + checkInID
                        + "'", null);

                succeeded = true;
            }

        } catch (Exception e) {
            Utilities.writeErrorToFile(e);
        }
        return succeeded;
    }

    public List<CheckIn> getUnsentCheckIn() {
        List<CheckIn> checkInList = new ArrayList<>();
        String sql = "SELECT CheckInID, WorkOrderID, EmployeeID, CheckInDate, CheckOutDate, Comment, Mileage, CheckInCoordinates, IsInRoute FROM " + _tblCheckIn + " WHERE IsSent = 0";

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            checkInList.add(new CheckIn(
                            c.getString(c.getColumnIndex("CheckInID")),
                            c.getInt(c.getColumnIndex("WorkOrderID")),
                            c.getInt(c.getColumnIndex("EmployeeID")),
                            c.getString(c.getColumnIndex("CheckInDate")),
                            c.getString(c.getColumnIndex("CheckOutDate")),
                            c.getString(c.getColumnIndex("Comment")),
                            c.getString(c.getColumnIndex("Mileage")),
                            c.getString(c.getColumnIndex("CheckInCoordinates")),
                            c.getInt(c.getColumnIndex("IsInRoute")) == 1
                    )
            );
        }

        c.close();
        return checkInList;
    }

    public void CheckIn_U() {

        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);
        _dbDatabase.update(_tblCheckIn, cv, "IsSent = 0 ", null);
    }

    public void sentCheckIn_D() {

        _dbDatabase.delete(_tblCheckIn, "IsSent = 1", null);
    }


    public void signatureImage_I(int selectedRouteID, String imageTitle, String imageString) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("ImageTitle", imageTitle);
        cv.put("ImageString", imageString);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("SentDate", Utilities.getCurrentDateTime());
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblSignatureImage, null, cv);

    }


    public List<SignatureImage> getUnsentSignatureImage() {
        List<SignatureImage> signatureImageList = new ArrayList<>();
        String sql = "SELECT WorkOrderID, ImageTitle, ImageString, CreatedDate FROM " + _tblSignatureImage + " WHERE IsSent = 0";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            SignatureImage signatureImage = new SignatureImage(c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3));
            signatureImageList.add(signatureImage);
        }
        c.close();
        return signatureImageList;
    }

    public void signatureImage_U() {

        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);
        cv.put("SentDate", Utilities.getCurrentDateTime());
        _dbDatabase.update(_tblSignatureImage, cv, "IsSent = 0 ", null);
    }


    public void sentSignatureImage_D() {
        _dbDatabase.delete(_tblSignatureImage, "IsSent = 1 AND julianday('now') - julianday(SentDate) > 1", null);
    }

    public void workOrderImage_I(int selectedRouteID, int imageTypeID, String imageTitle, String imageString, String note) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("ImageTypeID", imageTypeID);
        cv.put("ImageTitle", imageTitle);
        cv.put("ImageString", imageString);
        cv.put("Note", note);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("SentDate", Utilities.getCurrentDateTime());
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblWorkOrderImage, null, cv);
    }


    public List<WorkOrderImage> getUnsentWorkOrderImage() {
        List<WorkOrderImage> workOrderImageList = new ArrayList<>();

        String sql = "SELECT WorkOrderID, ImageTypeID, ImageTitle, ImageString, Note, CreatedDate FROM " + _tblWorkOrderImage + " WHERE IsSent = 0";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            WorkOrderImage workOrderImage = new WorkOrderImage(
                    c.getInt(c.getColumnIndex("WorkOrderID")),
                    c.getInt(c.getColumnIndex("ImageTypeID")),
                    c.getString(c.getColumnIndex("ImageTitle")),
                    c.getString(c.getColumnIndex("ImageString")),
                    c.getString(c.getColumnIndex("Note")),
                    c.getString(c.getColumnIndex("CreatedDate")));
            workOrderImageList.add(workOrderImage);
        }
        c.close();
        return workOrderImageList;
    }


    public void workOrderImage_U() {
        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);
        cv.put("SentDate", Utilities.getCurrentDateTime());
        _dbDatabase.update(_tblWorkOrderImage, cv, "IsSent = 0 ", null);
    }

    public void sentWorkOrderImage_D() {
        _dbDatabase.delete(_tblWorkOrderImage, "IsSent = 1 AND julianday('now') - julianday(SentDate) > 1", null);
    }

    public boolean checkIfWorkOrderSigned(int selectedRouteID) {
        String sql = "SELECT ImageTitle FROM " + _tblSignatureImage + " WHERE WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public void workOrderResult_I(int selectedRouteID, int productID,
                                  String serialNumber, String purchaseDate, String productionDate, String customerName,
                                  String customerAddress, String customerAddressNo, String customerCity, String customerPhone,
                                  String workOrderNote, String workOrderDescription, String signatureNote, String failureCauseNote, boolean isEscalated,
                                  boolean isBarcodeScanned, boolean isWorkOrderSigned, int closedStatus) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("ProductID", productID);
        cv.put("SerialNumber", serialNumber);
        cv.put("ProductPurchaseDate", purchaseDate);
        cv.put("ProductProductionDate", productionDate);
        cv.put("CustomerName", customerName);
        cv.put("CustomerAddress", customerAddress);
        cv.put("CustomerAddressNo", customerAddressNo);
        cv.put("CustomerCity", customerCity);
        cv.put("CustomerPhone", customerPhone);
        cv.put("ResultNote", workOrderNote);
        cv.put("ResultDescription", workOrderDescription);
        cv.put("SignatureNote", signatureNote);
        cv.put("FailureCauseNote", failureCauseNote);
        cv.put("IsEscalated", isEscalated ? 1 : 0);
        cv.put("HasSignature", isWorkOrderSigned ? 1 : 0);
        cv.put("SerialNoScanned", isBarcodeScanned ? 1 : 0);
        cv.put("IsClosed", closedStatus);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("SentDate", Utilities.getCurrentDateTime());
        cv.put("IsDone", 0);
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblWorkOrderResult, null, cv);

    }

    public void workOrderResult_U(int workOrderResultID, int selectedRouteID, int productID,
                                  String serialNumber, String purchaseDate, String productionDate, String customerName,
                                  String customerAddress, String customerAddressNo, String customerCity, String customerPhone,
                                  boolean isLegalPerson, boolean isInWarranty, boolean isCustomerRejected,
                                  String workOrderNote, String workOrderDescription, String signatureNote, String failureCauseNote, boolean isEscalated,
                                  boolean isBarcodeScanned, boolean isWorkOrderSigned, int closedStatus, boolean isDone) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("ProductID", productID);
        cv.put("SerialNumber", serialNumber);
        cv.put("ProductPurchaseDate", purchaseDate);
        cv.put("ProductProductionDate", productionDate);
        cv.put("CustomerName", customerName);
        cv.put("CustomerAddress", customerAddress);
        cv.put("CustomerAddressNo", customerAddressNo);
        cv.put("CustomerCity", customerCity);
        cv.put("CustomerPhone", customerPhone);
        cv.put("IsLegalPerson", isLegalPerson ? 1 : 0);
        cv.put("InWarranty", isInWarranty ? 1 : 0);
        cv.put("ResultNote", workOrderNote);
        cv.put("ResultDescription", workOrderDescription);
        cv.put("FailureCauseNote", failureCauseNote);
        cv.put("SignatureNote", signatureNote);
        cv.put("IsEscalated", isEscalated ? 1 : 0);
        cv.put("HasSignature", isWorkOrderSigned ? 1 : 0);
        cv.put("IsCustomerRejected", isCustomerRejected ? 1 : 0);
        cv.put("SerialNoScanned", isBarcodeScanned ? 1 : 0);
        cv.put("IsClosed", closedStatus);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        cv.put("IsDone", isDone ? 1 : 0);

        _dbDatabase.update(_tblWorkOrderResult, cv, " WorkOrderResultID = " + workOrderResultID, null);
    }

    public List<WorkOrderResult> getUnsentWorkOrderResult() {
        List<WorkOrderResult> workOrderResultList = new ArrayList<>();
        String sql = "SELECT WorkOrderResultID, WorkOrderID,ProductID,SerialNumber,ProductPurchaseDate," +
                "ProductProductionDate,CustomerName, CustomerAddress, CustomerAddressNo, CustomerCity, CustomerPhone, IsLegalPerson, InWarranty, ResultNote,IsEscalated,ResultDescription,FailureCauseNote,SignatureNote,HasSignature, IsCustomerRejected, " +
                "SerialNoScanned,IsClosed,CreatedDate FROM " + _tblWorkOrderResult + " WHERE IsSent = 0 AND IsDone = 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            WorkOrderResult workOrderResult = new WorkOrderResult();
            workOrderResult.setWorkOrderID(c.getInt(c.getColumnIndex("WorkOrderID")));
            workOrderResult.setProductID(c.getInt(c.getColumnIndex("ProductID")));
            workOrderResult.setSerialNumber(c.getString(c.getColumnIndex("SerialNumber")));
            workOrderResult.setPurchaseDate(c.getString(c.getColumnIndex("ProductPurchaseDate")));
            workOrderResult.setProductionDate(c.getString(c.getColumnIndex("ProductProductionDate")));
            workOrderResult.setCustomerName(c.getString(c.getColumnIndex("CustomerName")));
            workOrderResult.setCustomerAddress(c.getString(c.getColumnIndex("CustomerAddress")));
            workOrderResult.setCustomerAddressNo(c.getString(c.getColumnIndex("CustomerAddressNo")));
            workOrderResult.setCustomerCity(c.getString(c.getColumnIndex("CustomerCity")));
            workOrderResult.setCustomerPhone(c.getString(c.getColumnIndex("CustomerPhone")));
            workOrderResult.setLegalPersion(c.getInt(c.getColumnIndex("IsLegalPerson")) == 1);
            workOrderResult.setInWarranty(c.getInt(c.getColumnIndex("InWarranty")) == 1);
            workOrderResult.setResultNote(c.getString(c.getColumnIndex("ResultNote")));
            workOrderResult.setResultDescription(c.getString(c.getColumnIndex("ResultDescription")));
            workOrderResult.setFailureCauseNote(c.getString(c.getColumnIndex("FailureCauseNote")));
            workOrderResult.setEscalated(c.getInt(c.getColumnIndex("IsEscalated")) == 1);
            workOrderResult.setSignatureNote(c.getString(c.getColumnIndex("SignatureNote")));
            workOrderResult.setHasSignature(c.getInt(c.getColumnIndex("HasSignature")) == 1);
            workOrderResult.setSerialNumberScanned(c.getInt(c.getColumnIndex("SerialNoScanned")) == 1);
            workOrderResult.setCustomerRejected(c.getInt(c.getColumnIndex("IsCustomerRejected")) == 1);
            workOrderResult.setClosed(c.getInt(c.getColumnIndex("IsClosed")) == 1);
            workOrderResult.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            workOrderResult.setWorkOrderServiceResult(getAddedServices(c.getInt(c.getColumnIndex("WorkOrderResultID")), c.getInt(c.getColumnIndex("WorkOrderID")), c.getInt(c.getColumnIndex("ProductID"))));
            workOrderResult.setWorkOrderMaterialResult(getAddedMaterials(c.getInt(c.getColumnIndex("WorkOrderResultID")), c.getInt(c.getColumnIndex("WorkOrderID"))));
            workOrderResult.setWorkOrderFailureResult(getAddedFailuresAndCauses(c.getInt(c.getColumnIndex("WorkOrderResultID")), c.getInt(c.getColumnIndex("WorkOrderID"))));
            workOrderResult.setWorkOrderPollQuestionResult(getAddedPollQuestions(c.getInt(c.getColumnIndex("WorkOrderResultID")), c.getInt(c.getColumnIndex("WorkOrderID"))));
            workOrderResult.setWorkOrderResultID(c.getInt(c.getColumnIndex("WorkOrderResultID")));

            workOrderResultList.add(workOrderResult);
        }
        c.close();
        return workOrderResultList;
    }


    public void workOrderResult_U() {

        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);
        cv.put("SentDate", Utilities.getCurrentDateTime());
        _dbDatabase.update(_tblWorkOrderResult, cv, "IsSent = 0 AND IsDone = 1", null);

    }


    public void sentWorkOrderResult_D() {
        _dbDatabase.delete(_tblWorkOrderResult, "IsSent = 1 AND julianday('now') - julianday(SentDate) > 1", null);
    }

    public void deleteFromAddedTablesForSentWO() {
        String sql = "SELECT WorkOrderResultID FROM " + _tblWorkOrderResult
                + " WHERE IsSent = 1 AND julianday('now') - julianday(SentDate) > 1";

        Cursor c = _dbDatabase.rawQuery(sql, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int workOrderResultID = c.getInt(c.getColumnIndex("WorkOrderResultID"));
            _dbDatabase.delete(_tblAddedServices, "WorkOrderResultID = " + workOrderResultID, null);
            _dbDatabase.delete(_tblAddedMaterials, "WorkOrderResultID = " + workOrderResultID, null);
            _dbDatabase.delete(_tblAddedFailuresAndCauses, "WorkOrderResultID = " + workOrderResultID, null);
            _dbDatabase.delete(_tblAddedPollQuestions, "WorkOrderResultID = " + workOrderResultID, null);
        }

        c.close();

    }


    public void deleteUnassignedAddedTables() {

        _dbDatabase.delete(_tblAddedServices, "WorkOrderResultID IS NULL OR WorkOrderResultID = -1", null);
        _dbDatabase.delete(_tblAddedMaterials, "WorkOrderResultID IS NULL OR WorkOrderResultID = -1", null);
        _dbDatabase.delete(_tblAddedFailuresAndCauses, "WorkOrderResultID IS NULL OR WorkOrderResultID = -1", null);
        _dbDatabase.delete(_tblAddedPollQuestions, "WorkOrderResultID IS NULL OR WorkOrderResultID = -1", null);
    }


    public Route getRouteByRouteID(int selectedRouteID) {
        Route route = null;
        String sql = " SELECT wo.WorkOrderID, wo.WorkOrderCode, wo.InWarranty, wo.WorkOrderPollID, wot.WorkOrderTypeID, wot.TypeName, p.PartnerID, p.PartnerName, p.PartnerCode, p.Address, p.Contact as PartnerContact, wo.CustomerName, " +
                " wo.CustomerCity, wo.CustomerAddress, wo.CustomerAddressNo, wo.CustomerPhone, wo.CustomerWarning, wo.IsLegalPerson, wo.ProductID, pr.ProductName, pr.ProductCode, pr.Model, wo.PlannedDate, wo.RegionID, r.RegionName, " +
                " wo.RegionPlaceID, rp.RegionPlaceName, wo.StatusID, wo.Description, wo.Note, wo.MaterialTypeID, wo.ProductColorID " +
                " FROM WorkOrder wo " +
                " INNER JOIN WorkOrderType wot ON wo.WorkOrderTypeID = wot.WorkOrderTypeID " +
                " INNER JOIN Partner p on p.PartnerID = wo.PartnerID " +
                " INNER JOIN Product pr on pr.ProductID = wo.ProductID " +
                " INNER JOIN Region r on r.RegionID = wo.RegionID " +
                " INNER JOIN RegionPlace rp on rp.RegionPlaceID = wo.RegionPlaceID " +
                " WHERE wo.WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            route = new Route(
                    c.getInt(c.getColumnIndex("WorkOrderID")),
                    c.getString(c.getColumnIndex("WorkOrderCode")),
                    c.getString(c.getColumnIndex("TypeName")),
                    c.getString(c.getColumnIndex("PartnerName")),
                    c.getString(c.getColumnIndex("PartnerCode")),
                    c.getString(c.getColumnIndex("CustomerName")),
                    c.getString(c.getColumnIndex("CustomerCity")),
                    c.getString(c.getColumnIndex("CustomerAddress")),
                    c.getString(c.getColumnIndex("CustomerAddressNo")),
                    c.getString(c.getColumnIndex("CustomerPhone")),
                    c.getInt(c.getColumnIndex("CustomerWarning")) == 1,
                    c.getString(c.getColumnIndex("ProductName")),
                    c.getString(c.getColumnIndex("ProductCode")),
                    c.getString(c.getColumnIndex("Model")),
                    c.getString(c.getColumnIndex("PlannedDate")),
                    c.getInt(c.getColumnIndex("StatusID")) == 2 ? "Otvoren" : "U toku"
            );
            route.setPartnerAddress(c.getString(c.getColumnIndex("Address")));
            route.setPartnerContact(c.getString(c.getColumnIndex("PartnerContact")));
            route.setProductID(c.getInt(c.getColumnIndex("ProductID")));
            route.setInWarranty(c.getInt(c.getColumnIndex("InWarranty")) == 1);
            route.setWorkOrderPollID(c.getInt(c.getColumnIndex("WorkOrderPollID")));
            route.setWorkOrderDescription(c.getString(c.getColumnIndex("Description")));
            route.setWorkOrderNote(c.getString(c.getColumnIndex("Note")));
            route.setLegalPerson(c.getInt(c.getColumnIndex("IsLegalPerson")) == 1);
            route.setMaterialTypeID(c.getInt(c.getColumnIndex("MaterialTypeID")));
            route.setProductColorID(c.getInt(c.getColumnIndex("ProductColorID")));
        }
        c.close();
        return route;
    }

    public ArrayList<Route> getRouteList(int workOrderTypeID, int partnerID, int regionID, int regionPlaceID, boolean showCriticalWorkOrders) {
        ArrayList<Route> routeArrayList = new ArrayList<>();

        StringBuilder conditionRules = new StringBuilder();
        if (workOrderTypeID != -1)
            conditionRules.append(" AND wot.WorkOrderTypeID = ").append(workOrderTypeID);
        if (partnerID != -1)
            conditionRules.append(" AND p.PartnerID = ").append(partnerID);
        if (regionID != -1)
            conditionRules.append(" AND wo.RegionID = ").append(regionID);
        if (regionPlaceID != -1)
            conditionRules.append(" AND wo.RegionPlaceID = ").append(regionPlaceID);
        if (showCriticalWorkOrders)
            conditionRules.append(" AND julianday(wo.PlannedDate) - julianday('now') <= 7 ");

        String sql = " SELECT wo.WorkOrderID, wo.WorkOrderCode, wot.WorkOrderTypeID, wot.TypeName, p.PartnerID, p.PartnerName, p.PartnerCode, wo.CustomerName, " +
                " wo.CustomerCity, wo.CustomerAddress, wo.CustomerAddressNo, wo.CustomerPhone, wo.CustomerWarning, pr.ProductName, pr.ProductCode, pr.Model, wo.PlannedDate, wo.RegionID, r.RegionName, " +
                " wo.RegionPlaceID, rp.RegionPlaceName, wo.StatusID " +
                " FROM WorkOrder wo " +
                " INNER JOIN WorkOrderType wot ON wo.WorkOrderTypeID = wot.WorkOrderTypeID " +
                " INNER JOIN Partner p on p.PartnerID = wo.PartnerID " +
                " INNER JOIN Product pr on pr.ProductID = wo.ProductID " +
                " INNER JOIN Region r on r.RegionID = wo.RegionID " +
                " INNER JOIN RegionPlace rp on rp.RegionPlaceID = wo.RegionPlaceID " +
                " WHERE wo.StatusID IN (2, 3)" +
                conditionRules.toString() +
                " ORDER BY wo.PlannedDate ";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Route route = new Route(
                    c.getInt(c.getColumnIndex("WorkOrderID")),
                    c.getString(c.getColumnIndex("WorkOrderCode")),
                    c.getString(c.getColumnIndex("TypeName")),
                    c.getString(c.getColumnIndex("PartnerName")),
                    c.getString(c.getColumnIndex("PartnerCode")),
                    c.getString(c.getColumnIndex("CustomerName")),
                    c.getString(c.getColumnIndex("CustomerCity")),
                    c.getString(c.getColumnIndex("CustomerAddress")),
                    c.getString(c.getColumnIndex("CustomerAddressNo")),
                    c.getString(c.getColumnIndex("CustomerPhone")),
                    c.getInt(c.getColumnIndex("CustomerWarning")) == 1,
                    c.getString(c.getColumnIndex("ProductName")),
                    c.getString(c.getColumnIndex("ProductCode")),
                    c.getString(c.getColumnIndex("Model")),
                    c.getString(c.getColumnIndex("PlannedDate")),
                    c.getInt(c.getColumnIndex("StatusID")) == 2 ? "Otvoren" : "U toku"
            );
            int workOrderResultID = getWorkOrderResultID(c.getInt(c.getColumnIndex("WorkOrderID")));
            route.setWorkOrderFailureList(getFailuresForWorkOrder(route.getWorkOrderID()));
            route.setDraft(workOrderResultID != -1);

            routeArrayList.add(route);
        }
        c.close();
        return routeArrayList;
    }


    public SparseArray<String> getWorkOrderTypeArray() {
        SparseArray<String> workOrderTypeArray = new SparseArray<>();
        workOrderTypeArray.put(-1, "--Odaberi--");
        String sql = "SELECT WorkOrderTypeID, TypeName FROM " + _tblWorkOrderType;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            workOrderTypeArray.put(
                    c.getInt(c.getColumnIndex("WorkOrderTypeID")),
                    c.getString(c.getColumnIndex("TypeName")));
        }
        c.close();
        return workOrderTypeArray;
    }

    public SparseArray<String> getPartnerArray() {
        SparseArray<String> partnerArray = new SparseArray<>();
        partnerArray.put(-1, "--Odaberi--");
        String sql = "SELECT p.PartnerID, p.PartnerName FROM Partner p " +
                " INNER JOIN WorkOrder wo on p.PartnerID = wo.PartnerID " +
                " ORDER BY p.PartnerName ";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            partnerArray.put(
                    c.getInt(c.getColumnIndex("PartnerID")),
                    c.getString(c.getColumnIndex("PartnerName")));
        }
        c.close();
        return partnerArray;
    }

    public SparseArray<String> getRegionArray() {
        SparseArray<String> regionArray = new SparseArray<>();
        regionArray.put(-1, "--Odaberi--");
        String sql = "SELECT r.RegionID, r.RegionName FROM Region r" +
                " INNER JOIN WorkOrder wo on r.RegionID = wo.RegionID " +
                " ORDER BY r.RegionName ";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            regionArray.put(
                    c.getInt(c.getColumnIndex("RegionID")),
                    c.getString(c.getColumnIndex("RegionName")));
        }
        c.close();
        return regionArray;
    }

    public SparseArray<String> getRegionPlaceArray(int regionID) {
        SparseArray<String> regionPlaceArray = new SparseArray<>();
        regionPlaceArray.put(-1, "--Odaberi--");
        if (regionID != -1) {
            String sql = "SELECT RegionPlaceID, RegionPlaceName FROM RegionPlace " +
                    "WHERE RegionID = " + regionID;
            Cursor c = _dbDatabase.rawQuery(sql, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                regionPlaceArray.put(
                        c.getInt(c.getColumnIndex("RegionPlaceID")),
                        c.getString(c.getColumnIndex("RegionPlaceName")));
            }
            c.close();
        }
        return regionPlaceArray;
    }


    public void startStop_I(int employeeID, boolean isStarted) {
        ContentValues cv = new ContentValues();
        cv.put("EmployeeID", employeeID);
        cv.put("Time", Utilities.getCurrentDateTime());
        cv.put("IsStarted", isStarted ? 1 : 0);
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblStartStop, null, cv);

    }

    public void startStop_I(int employeeID, String time, boolean isStarted) {
        ContentValues cv = new ContentValues();
        cv.put("EmployeeID", employeeID);
        cv.put("Time", time);
        cv.put("IsStarted", isStarted ? 1 : 0);
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblStartStop, null, cv);

    }

    public List<StartStop> unsentStartStop() {
        List<StartStop> startStopList = new ArrayList<>();
        String sql = "SELECT EmployeeID, Time, IsStarted FROM StartStop " +
                "WHERE IsSent = 0";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            StartStop startStop = new StartStop(
                    c.getInt(c.getColumnIndex("EmployeeID"))
                    , c.getString(c.getColumnIndex("Time"))
                    , (c.getInt(c.getColumnIndex("IsStarted"))) == 1);
            startStopList.add(startStop);
        }
        c.close();
        return startStopList;
    }

    public StartStop getStartStop(int employeeID) {
        StartStop startStop = null;
        String sql = "SELECT EmployeeID, Time, IsStarted FROM " + _tblStartStop
                + " WHERE EmployeeID = " + employeeID
                + " ORDER BY Time DESC LIMIT 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            startStop = new StartStop(
                    c.getInt(c.getColumnIndex("EmployeeID"))
                    , c.getString(c.getColumnIndex("Time"))
                    , (c.getInt(c.getColumnIndex("IsStarted"))) == 1);
        }
        c.close();
        return startStop;
    }

    public void startStop_U() {
        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);
        _dbDatabase.update(_tblStartStop, cv, "IsSent = 0 ", null);
    }

    public void startStop_D() {
        _dbDatabase.delete(_tblStartStop, null, null);
    }

    public List<ProductDocument> getDocumentList(int productID, String pathToFile) {
        List<ProductDocument> documentList = new ArrayList<>();
        String sql = "SELECT DocumentName, DocumentType FROM " + _tblProductDocumentation + " WHERE ProductID = " + productID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ProductDocument productDocument = new ProductDocument();
            productDocument.setDocumentName(c.getString(c.getColumnIndex("DocumentName")));
            productDocument.setDocumentType(c.getString(c.getColumnIndex("DocumentType")));
            if (new File(pathToFile, productDocument.getDocumentName()).exists())
                productDocument.setDownloaded(true);
            else
                productDocument.setDownloaded(false);
            documentList.add(productDocument);
        }
        c.close();
        return documentList;
    }


    public void sentStartStop_D() {
        _dbDatabase.delete(_tblStartStop, "IsSent = 1", null);
    }


    public void productDocument_D(int productDocumentationID) {
        _dbDatabase.delete(_tblProductDocumentation, "ProductDocumentationID = " + productDocumentationID, null);
    }

    public void productDocument_I(int productDocumentationID, int productID, String documentName, String documentType, String documentCode, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("ProductDocumentationID", productDocumentationID);
        cv.put("ProductID", productID);
        cv.put("DocumentName", documentName);
        cv.put("DocumentType", documentType);
        cv.put("DocumentCode", documentCode);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductDocumentation, null, cv);

    }

    public void workOrderCodeAndID_U(int workOrderID, String newWorkOrderCode, int workOrderTypeID, boolean inWarranty) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderTypeID", workOrderTypeID);
        cv.put("WorkOrderCode", newWorkOrderCode);
        cv.put("InWarranty", inWarranty ? 1 : 0);
        _dbDatabase.update(_tblWorkOrder, cv, "WorkOrderID = " + workOrderID, null);
    }

    public WorkOrderType getWorkOrderTypeByWarranty(boolean isChecked) {
        WorkOrderType workOrderType = null;

        String condition = isChecked ? "521" : "540";

        String sql = "SELECT WorkOrderTypeID, TypeName, TypeCode FROM " + _tblWorkOrderType
                + " WHERE TypeCode = '" + condition + "'";

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            workOrderType = new WorkOrderType();
            workOrderType.setWorkOrderTypeID(c.getInt(c.getColumnIndex("WorkOrderTypeID")));
            workOrderType.setTypeName(c.getString(c.getColumnIndex("TypeName")));
            workOrderType.setTypeCode(c.getString(c.getColumnIndex("TypeCode")));
        }
        c.close();
        return workOrderType;
    }


    public void pollQuestionType_D(int workOrderQuestionTypeID) {
        _dbDatabase.delete(_tblPollQuestionType, "WorkOrderQuestionTypeID = " + workOrderQuestionTypeID, null);
    }

    public void pollQuestionType_I(int workOrderQuestionTypeID, String typeName, String typeCode, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderQuestionTypeID", workOrderQuestionTypeID);
        cv.put("TypeName", typeName);
        cv.put("TypeCode", typeCode);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblPollQuestionType, null, cv);
    }


    public void pollQuestion_D(int workOrderQuestionID) {
        _dbDatabase.delete(_tblPollQuestion, "WorkOrderQuestionID = " + workOrderQuestionID, null);
    }

    public void pollQuestion_I(int workOrderQuestionID, int workOrderPollID, String questionText, int workOrderQuestionTypeID, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderQuestionID", workOrderQuestionID);
        cv.put("WorkOrderPollID", workOrderPollID);
        cv.put("QuestionText", questionText);
        cv.put("WorkOrderQuestionTypeID", workOrderQuestionTypeID);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblPollQuestion, null, cv);
    }

    public ArrayList<PollQuestion> getWorkOrderPollQuestions(int workOrderPollID) {
        ArrayList<PollQuestion> pollQuestionArrayList = new ArrayList<>();
        String sql = "SELECT WorkOrderQuestionID, WorkOrderQuestionTypeID, QuestionText FROM " + _tblPollQuestion
                + " WHERE WorkOrderPollID = " + workOrderPollID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            pollQuestionArrayList.add(new PollQuestion(
                    c.getInt(c.getColumnIndex("WorkOrderQuestionID")),
                    c.getInt(c.getColumnIndex("WorkOrderQuestionTypeID")) == 1 ? PollQuestion.QuestionType.YESNO : PollQuestion.QuestionType.FREEANSWER,
                    c.getString(c.getColumnIndex("QuestionText"))));
        }
        c.close();
        return pollQuestionArrayList;
    }


    public void noSignatureComment_D(int commentID) {
        _dbDatabase.delete(_tblNoSignatureComment, "CommentID = " + commentID, null);
    }


    public void noSignatureComment_I(int commentID, String commentText, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("CommentID", commentID);
        cv.put("CommentText", commentText);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblNoSignatureComment, null, cv);
    }

    public String[] getNoSigComments() {
        ArrayList<String> list = new ArrayList<>();
        list.add("--Odaberite komentar--");
        String sql = "SELECT CommentText FROM " + _tblNoSignatureComment;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            list.add(c.getString(c.getColumnIndex("CommentText")));
        }
        c.close();
        return list.toArray(new String[0]);
    }

    public boolean checkIfImageForWorkOrderExists(int selectedRouteID) {
        String sql = "SELECT ImageTypeID FROM " + _tblWorkOrderImage
                + " WHERE WorkOrderID = " + selectedRouteID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }


    public void productMaterial_D(int productID, int materialID) {
        _dbDatabase.delete(_tblProductMaterial, "ProductID = " + productID + " AND MaterialID = " + materialID, null);
    }

    public void productMaterial_I(int productID, int materialID, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("ProductID", productID);
        cv.put("MaterialID", materialID);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductMaterial, null, cv);
    }


    public void productService_D(int productID, int serviceID) {
        _dbDatabase.delete(_tblProductService, "ProductID = " + productID + " AND ServiceID = " + serviceID, null);

    }

    public void productService_I(int productID, int serviceID, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("ProductID", productID);
        cv.put("ServiceID", serviceID);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductService, null, cv);
    }

    public void productFailure_D(int productID, int failureID, int failureCauseID) {
        _dbDatabase.delete(_tblProductFailure, "ProductID = " + productID + " AND FailureID = " + failureID + " AND FailureCauseID = " + failureCauseID, null);
    }


    public void productFailure_I(int productID, int failureID, int failureCauseID, String modifiedDate) {

        ContentValues cv = new ContentValues();
        cv.put("ProductID", productID);
        cv.put("FailureID", failureID);
        cv.put("FailureCauseID", failureCauseID);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductFailure, null, cv);
    }


    public void addedPollQuestions_I(int workOrderResultID, int selectedRouteID, int workOrderQuestionID, String pollQuestionAnswer) {
        ContentValues cv = new ContentValues();
        cv.put("WorkOrderResultID", workOrderResultID);
        cv.put("WorkOrderID", selectedRouteID);
        cv.put("WorkOrderQuestionID", workOrderQuestionID);
        cv.put("PollQuestionAnswer", pollQuestionAnswer);
        cv.put("IsSent", 0);
        _dbDatabase.insert(_tblAddedPollQuestions, null, cv);
    }

    public int getWorkOrderResultID(int selectedRouteID) {
        int workOrderResultID = -1;
        String sql = "SELECT WorkOrderResultID FROM " + _tblWorkOrderResult
                + " WHERE WorkOrderID = " + selectedRouteID + " AND IsDone = 0 "
                + " ORDER BY CreatedDate DESC LIMIT 1";
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            workOrderResultID = c.getInt(c.getColumnIndex("WorkOrderResultID"));
        }
        c.close();
        return workOrderResultID;
    }


    public void addedTables_U(List<WorkOrderResult> workOrderResultList) {

        ContentValues cv = new ContentValues();
        cv.put("IsSent", 1);

        for (WorkOrderResult workOrderResult : workOrderResultList) {

            _dbDatabase.update(_tblAddedServices, cv, "WorkOrderResultID = " + workOrderResult.getWorkOrderResultID() + " AND WorkOrderID = " + workOrderResult.getWorkOrderID(), null);
            _dbDatabase.update(_tblAddedMaterials, cv, "WorkOrderResultID = " + workOrderResult.getWorkOrderResultID() + " AND WorkOrderID = " + workOrderResult.getWorkOrderID(), null);
            _dbDatabase.update(_tblAddedFailuresAndCauses, cv, "WorkOrderResultID = " + workOrderResult.getWorkOrderResultID() + " AND WorkOrderID = " + workOrderResult.getWorkOrderID(), null);
            _dbDatabase.update(_tblAddedPollQuestions, cv, "WorkOrderResultID = " + workOrderResult.getWorkOrderResultID() + " AND WorkOrderID = " + workOrderResult.getWorkOrderID(), null);

        }

    }


    public Route getDraftRoute(int workOrderResultID, int selectedRouteID, boolean isDone) {
        Route route = null;
        int doneFlag = isDone ? 1 : 0;

        String sql = " SELECT wor.WorkOrderResultID, wor.WorkOrderID, wot.TypeName, wo.WorkOrderCode, wo.PlannedDate, wo.InWarranty, wo.WorkOrderPollID, p.PartnerName, p.PartnerCode, p.Address, p.Contact as PartnerContact, " +
                " wor.CustomerName, wor.CustomerCity, wor.CustomerAddress, wor.CustomerAddressNo, wor.CustomerPhone, wor.IsLegalPerson, wo.CustomerWarning, wor.ProductID, pr.ProductName, pr.ProductCode, pr.Model, " +
                "wor.SerialNumber, wor.ProductPurchaseDate, wor.ProductProductionDate, wor.ResultNote, wor.ResultDescription, wor.FailureCauseNote, wor.IsEscalated, wor.SerialNoScanned, wor.HasSignature, wor.IsCustomerRejected ,wor.IsClosed, wo.ProductColorID, wo.MaterialTypeID " +
                "FROM WorkOrderResult wor " +
                " INNER JOIN WorkOrder wo ON wo.WorkOrderID = wor.WorkOrderID " +
                " INNER JOIN WorkOrderType wot ON wo.WorkOrderTypeID = wot.WorkOrderTypeID " +
                " INNER JOIN Partner p on p.PartnerID = wo.PartnerID " +
                " INNER JOIN Product pr on pr.ProductID = wor.ProductID " +
                " WHERE wor.WorkOrderResultID = " + workOrderResultID + " AND wor.WorkOrderID = " + selectedRouteID + " AND wor.IsDone = " + doneFlag;

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            route = new Route();
            route.setWorkOrderResultID(c.getInt(c.getColumnIndex("WorkOrderResultID")));
            route.setWorkOrderID(c.getInt(c.getColumnIndex("WorkOrderID")));
            route.setWorkOrderType(c.getString(c.getColumnIndex("TypeName")));
            route.setWorkOrderCode(c.getString(c.getColumnIndex("WorkOrderCode")));
            route.setPlannedDate(c.getString(c.getColumnIndex("PlannedDate")));
            route.setInWarranty(c.getInt(c.getColumnIndex("InWarranty")) == 1);
            route.setWorkOrderPollID(c.getInt(c.getColumnIndex("WorkOrderPollID")));
            route.setPartnerName(c.getString(c.getColumnIndex("PartnerName")));
            route.setPartnerCode(c.getString(c.getColumnIndex("PartnerCode")));
            route.setPartnerAddress(c.getString(c.getColumnIndex("Address")));
            route.setPartnerContact(c.getString(c.getColumnIndex("PartnerContact")));
            route.setCustomerName(c.getString(c.getColumnIndex("CustomerName")));
            route.setCustomerCity(c.getString(c.getColumnIndex("CustomerCity")));
            route.setCustomerAddress(c.getString(c.getColumnIndex("CustomerAddress")));
            route.setCustomerAddressNo(c.getString(c.getColumnIndex("CustomerAddressNo")));
            route.setCustomerPhone(c.getString(c.getColumnIndex("CustomerPhone")));
            route.setLegalPerson(c.getInt(c.getColumnIndex("IsLegalPerson")) == 1);
            route.setCustomerWarning(c.getInt(c.getColumnIndex("CustomerWarning")) == 1);
            route.setProductID(c.getInt(c.getColumnIndex("ProductID")));
            route.setProductName(c.getString(c.getColumnIndex("ProductName")));
            route.setProductCode(c.getString(c.getColumnIndex("ProductCode")));
            route.setProductModel(c.getString(c.getColumnIndex("Model")));
            route.setSerialNumber(c.getString(c.getColumnIndex("SerialNumber")));
            route.setProductPurchaseDate(c.getString(c.getColumnIndex("ProductPurchaseDate")));
            route.setProductProductionDate(c.getString(c.getColumnIndex("ProductProductionDate")));
            route.setWorkOrderNote(c.getString(c.getColumnIndex("ResultNote")));
            route.setWorkOrderDescription(c.getString(c.getColumnIndex("ResultDescription")));
            route.setFailureCauseNote(c.getString(c.getColumnIndex("FailureCauseNote")));
            route.setEscalated(c.getInt(c.getColumnIndex("IsEscalated")) == 1);
            route.setSerialNoScanned(c.getInt(c.getColumnIndex("SerialNoScanned")) == 1);
            route.setHasSignature(c.getInt(c.getColumnIndex("HasSignature")) == 1);
            route.setCustomerRejected(c.getInt(c.getColumnIndex("IsCustomerRejected")) == 1);
            route.setClosed(c.getInt(c.getColumnIndex("IsClosed")) == 1);
            route.setClosedStatus(c.getInt(c.getColumnIndex("IsClosed")));
            route.setProductColorID(c.getInt(c.getColumnIndex("ProductColorID")));
            route.setMaterialTypeID(c.getInt(c.getColumnIndex("MaterialTypeID")));

        }
        c.close();
        return route;

    }


    public List<Draft> getDraftItems() {
        List<Draft> draftItemsList = new ArrayList<>();

        draftItemsList.add(new Draft("Izveštaj o radnom nalogu", Constants.TYPE_GROUP_HEADER));


        String sql = " SELECT wor.WorkOrderResultID, wor.WorkOrderID, wo.WorkOrderCode, pr.ProductName, p.PartnerName, wor.CustomerName, wor.CustomerCity, wor.CustomerAddress, wor.CustomerAddressNo, wor.CreatedDate, wor.SentDate, wor.IsSent " +
                " FROM WorkOrderResult wor " +
                " INNER JOIN WorkOrder wo on wo.WorkOrderID = wor.WorkOrderID " +
                " INNER JOIN Product pr on pr.ProductID = wor.ProductID " +
                " INNER JOIN Partner p on p.PartnerID = wo.PartnerID " +
                " WHERE wor.IsDone = 1 AND (julianday('now') - julianday(wor.SentDate) < 2 OR wor.IsSent = 0) " +
                "  ORDER BY wor.CreatedDate DESC ";

        Cursor c = _dbDatabase.rawQuery(sql, null);

        if (c.getCount() < 1)
            draftItemsList.remove(draftItemsList.size() - 1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Draft draftWorkOrderResult = new Draft();

            draftWorkOrderResult.setWorkOrderResultID(c.getInt(c.getColumnIndex("WorkOrderResultID")));
            draftWorkOrderResult.setWorkOrderID(c.getInt(c.getColumnIndex("WorkOrderID")));
            draftWorkOrderResult.setWorkOrderCode(c.getString(c.getColumnIndex("WorkOrderCode")));
            draftWorkOrderResult.setProductName(c.getString(c.getColumnIndex("ProductName")));
            draftWorkOrderResult.setPartnerName(c.getString(c.getColumnIndex("PartnerName")));
            draftWorkOrderResult.setCustomerName(c.getString(c.getColumnIndex("CustomerName")));
            draftWorkOrderResult.setCustomerCity(c.getString(c.getColumnIndex("CustomerCity")));
            draftWorkOrderResult.setCustomerAddress(c.getString(c.getColumnIndex("CustomerAddress")) + " " + c.getString(c.getColumnIndex("CustomerAddressNo")));
            draftWorkOrderResult.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            draftWorkOrderResult.setSentDate(c.getInt(c.getColumnIndex("IsSent")) == 1 ? c.getString(c.getColumnIndex("SentDate")) : "");
            draftWorkOrderResult.setSent(c.getInt(c.getColumnIndex("IsSent")) == 1);
            draftWorkOrderResult.setItemType(Constants.TYPE_WO_RESULT);
            draftItemsList.add(draftWorkOrderResult);
        }

        c.close();

        draftItemsList.add(new Draft("Slike - radni nalog", Constants.TYPE_GROUP_HEADER));


        sql = " SELECT woi.WorkOrderID, it.ImageTypeName, woi.CreatedDate, woi.SentDate, woi.IsSent FROM WorkOrderImage woi " +
                " INNER JOIN ImageType it ON it.ImageTypeID = woi.ImageTypeID " +
                " WHERE julianday('now') - julianday(woi.SentDate) < 2 OR woi.IsSent = 0 " +
                " ORDER BY woi.CreatedDate DESC";

        c = _dbDatabase.rawQuery(sql, null);

        if (c.getCount() < 1)
            draftItemsList.remove(draftItemsList.size() - 1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Draft draftWorkOrderImage = new Draft();
            draftWorkOrderImage.setWorkOrderID(c.getInt(c.getColumnIndex("WorkOrderID")));
            draftWorkOrderImage.setWorkOrderCode(c.getString(c.getColumnIndex("ImageTypeName")));
            draftWorkOrderImage.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            draftWorkOrderImage.setSentDate(c.getInt(c.getColumnIndex("IsSent")) == 1 ? c.getString(c.getColumnIndex("SentDate")) : "");
            draftWorkOrderImage.setSent(c.getInt(c.getColumnIndex("IsSent")) == 1);
            draftWorkOrderImage.setItemType(Constants.TYPE_WO_IMAGE);
            draftItemsList.add(draftWorkOrderImage);
        }
        c.close();

        draftItemsList.add(new Draft("Slike - potpis", Constants.TYPE_GROUP_HEADER));


        sql = " SELECT WorkOrderID, CreatedDate, SentDate, IsSent FROM " + _tblSignatureImage
                + " WHERE julianday('now') - julianday(SentDate) < 2 OR IsSent = 0 " +
                " ORDER BY CreatedDate DESC ";

        c = _dbDatabase.rawQuery(sql, null);

        if (c.getCount() < 1)
            draftItemsList.remove(draftItemsList.size() - 1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Draft draftWorkOrderSignature = new Draft();
            draftWorkOrderSignature.setWorkOrderID(c.getInt(c.getColumnIndex("WorkOrderID")));
            draftWorkOrderSignature.setWorkOrderCode("Potpis");
            draftWorkOrderSignature.setCreatedDate(c.getString(c.getColumnIndex("CreatedDate")));
            draftWorkOrderSignature.setSentDate(c.getInt(c.getColumnIndex("IsSent")) == 1 ? c.getString(c.getColumnIndex("SentDate")) : "");
            draftWorkOrderSignature.setSent(c.getInt(c.getColumnIndex("IsSent")) == 1);
            draftWorkOrderSignature.setItemType(Constants.TYPE_WO_SIGNATURE);
            draftItemsList.add(draftWorkOrderSignature);
        }

        c.close();


        draftItemsList.add(new Draft("Check In", Constants.TYPE_GROUP_HEADER));

        sql = " SELECT CheckInID, CheckInDate, CheckOutDate, IsSent FROM " + _tblCheckIn
                + " WHERE julianday('now') - julianday(CheckInDate) < 2 " +
                " ORDER BY CheckOutDate DESC ";

        c = _dbDatabase.rawQuery(sql, null);

        if (c.getCount() < 1)
            draftItemsList.remove(draftItemsList.size() - 1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Draft draftCheckIn = new Draft();
            draftCheckIn.setWorkOrderCode(c.getString(c.getColumnIndex("CheckInID")));
            draftCheckIn.setCreatedDate(c.getString(c.getColumnIndex("CheckInDate")));
            draftCheckIn.setSentDate(c.getString(c.getColumnIndex("CheckOutDate")));
            draftCheckIn.setSent(c.getInt(c.getColumnIndex("IsSent")) == 1);
            draftCheckIn.setItemType(Constants.TYPE_CHECKIN);

            draftItemsList.add(draftCheckIn);
        }
        c.close();

        draftItemsList.add(new Draft("Start-Stop", Constants.TYPE_GROUP_HEADER));


        sql = " SELECT Time,IsStarted, IsSent FROM " + _tblStartStop
                + " WHERE julianday('now') - julianday(Time) < 2 " +
                "ORDER BY Time DESC";

        c = _dbDatabase.rawQuery(sql, null);

        if (c.getCount() < 1)
            draftItemsList.remove(draftItemsList.size() - 1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Draft draftStartStop = new Draft();
            draftStartStop.setWorkOrderCode(c.getString(c.getColumnIndex("Time")));
            draftStartStop.setProductName(c.getInt(c.getColumnIndex("IsStarted")) == 1 ? "START" : "STOP");
            draftStartStop.setSent(c.getInt(c.getColumnIndex("IsSent")) == 1);
            draftStartStop.setItemType(Constants.TYPE_STARTSTOP);
            draftItemsList.add(draftStartStop);
        }
        c.close();

        return draftItemsList;
    }

    public Service historyServiceUpdate(Service service, int selectedProductID) {
        String sql = "SELECT s.ServiceName, s.UnitOfMeasure, IFNULL(sn.NormTime, '') as NormTime, IFNULL(pl.Price, 0) as Price FROM Service s"
               + " LEFT JOIN ServiceNorm sn on sn.ServiceID = s.ServiceID "
               + " INNER JOIN PriceList pl on pl.EntityType = 1 "
                + " WHERE s.ServiceID = " + service.getServiceID() + " AND sn.ProductID = " + selectedProductID + " LIMIT 1";

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            service.setServiceName(c.getString(c.getColumnIndex("ServiceName")));
            service.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            service.setNormTime(c.getString(c.getColumnIndex("NormTime")));
            double normTime = c.getString(c.getColumnIndex("NormTime")).isEmpty() ? 0 : Double.parseDouble(c.getString(c.getColumnIndex("NormTime")));
          //  service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * normTime);
            service.setPrice(c.getDouble(c.getColumnIndex("Price")) / 3600 * service.getUnitSpent());
        }
        c.close();
        return service;
    }


    public Material historyMaterialUpdate(Material material) {
        String sql = "SELECT m.MaterialName, m.UnitOfMeasure, IFNULL(pl.Price, 0) as Price FROM Material m "
                +" LEFT JOIN PriceList pl ON pl.EntityCode = m.MaterialCode "
                + " WHERE m.MaterialID = " + material.getMaterialID() + " LIMIT 1";

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            material.setMaterialName(c.getString(c.getColumnIndex("MaterialName")));
            material.setUnitOfMeasure(c.getString(c.getColumnIndex("UnitOfMeasure")));
            material.setPrice(c.getDouble(c.getColumnIndex("Price")));
        }
        c.close();
        return material;
    }


    public void productColor_D(int productColorID) {
        _dbDatabase.delete(_tblProductColor, "ProductColorID = " + productColorID, null);
    }

    public void productColor_I(int productColorID, String productColorName, String modifiedDate) {
        ContentValues cv = new ContentValues();
        cv.put("ProductColorID", productColorID);
        cv.put("ProductColorName", productColorName);
        cv.put("ModifiedDate", modifiedDate);
        _dbDatabase.insert(_tblProductColor, null, cv);
    }

    public void addedFeaturesForWorkOrdInProgress_I(int workOrderID,
                                                    List<Service> workOrderServiceList,
                                                    List<Material> workOrderMaterialList,
                                                    List<AddedFailuresAndCauses> workOrderFailureList) {

        for (Service service : workOrderServiceList) {
            if(!checkIfAddedServiceForOpenWOExists(workOrderID, service.getServiceID(), service.getCreatedDate())) {
                ContentValues cv = new ContentValues();
                cv.put("WorkOrderID", workOrderID);
                cv.put("ServiceID", service.getServiceID());
                cv.put("UnitSpent", service.getUnitSpent());
                cv.put("CreatedDate", service.getCreatedDate());
                _dbDatabase.insert(_tblAddedServices, null, cv);
            }
        }

        for (Material material : workOrderMaterialList) {

            if (!checkIfAddedMaterialForOpenWOExists(workOrderID, material.getMaterialID(), material.getCreatedDate())) {
                ContentValues cv = new ContentValues();
                cv.put("WorkOrderID", workOrderID);
                cv.put("MaterialID", material.getMaterialID());
                cv.put("QuantitySpent", material.getQuantitySpent());
                cv.put("CreatedDate", material.getCreatedDate());
                _dbDatabase.insert(_tblAddedMaterials, null, cv);
            }
        }

        for (AddedFailuresAndCauses addedFailuresAndCauses : workOrderFailureList) {
            if (!checkIfAddedFailuresAndCausesForOpenWOExist(workOrderID, addedFailuresAndCauses.getFailureID(), addedFailuresAndCauses.getFailureCauseID(), addedFailuresAndCauses.getCreatedDate())) {
                ContentValues cv = new ContentValues();
                cv.put("WorkOrderID", workOrderID);
                cv.put("FailureID", addedFailuresAndCauses.getFailureID());
                cv.put("FailureCauseID", addedFailuresAndCauses.getFailureCauseID());
                cv.put("CreatedDate", addedFailuresAndCauses.getCreatedDate());
                _dbDatabase.insert(_tblAddedFailuresAndCauses, null, cv);
            }
        }

    }

    public void workOrderPrintInfo_I(int workOrderID, int workOrderResultID) {

        ContentValues cv = new ContentValues();
        cv.put("WorkOrderID", workOrderID);
        cv.put("WorkOrderResultID", workOrderResultID);
        cv.put("CreatedDate", Utilities.getCurrentDateTime());
        _dbDatabase.insert(_tblWorkOrderPrintInfo, null, cv);

    }

    public boolean checkIfWorkOrderPrintInfoExists(int selectedRouteID, int workOrderResultID) {

        String sql = "Select CreatedDate FROM " + _tblWorkOrderPrintInfo
                + " WHERE WorkOrderID = " + selectedRouteID + " AND WorkOrderResultID = " + workOrderResultID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }

        return false;
    }

    public List<AddedFailuresAndCauses> getFailuresForWorkOrder(int selectedRouteID) {
        List<AddedFailuresAndCauses> failuresForWorkOrderNames = new ArrayList<>();

        String sql = "SELECT DISTINCT wor.FailureCauseID, wor.FailureID, fc.FailureCauseName, f.FailureName FROM WorkOrderFailureCause wor " +
                " INNER JOIN FailureCause fc ON fc.FailureCauseID = wor.FailureCauseID " +
                " INNER JOIN Failure f ON f.FailureID = wor.FailureID" +
                " WHERE WorkOrderID = " + selectedRouteID;

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            AddedFailuresAndCauses afc = new AddedFailuresAndCauses();
            afc.setFailureName(c.getString(c.getColumnIndex("FailureName")));
            afc.setFailureCauseName(c.getString(c.getColumnIndex("FailureCauseName")));
            failuresForWorkOrderNames.add(afc);
        }
        c.close();
        return failuresForWorkOrderNames;
    }

    public String getColorNameByColorID(int productColorID) {

        String colorName = "";
        String sql = "SELECT ProductColorName FROM ProductColor "
                + " WHERE ProductColorID = " + productColorID;

        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            colorName = c.getString(c.getColumnIndex("ProductColorName"));
        }
        c.close();
        return colorName;
    }

    public String getEmployeeCodeByID(int employeeID) {
    String code = "";
    String sql = "SELECT EmployeeCode FROM " + _tblEmployee +
            " WHERE EmployeeID = "  + employeeID;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            code = c.getString(c.getColumnIndex("EmployeeCode"));
        }
        c.close();
        return  code;
    }

    public int getWareHouseIDByEmpCode(String employeeCode) {
        int warehouseID = -1;
        String sql = "SELECT WarehouseID FROM " + _tblWarehouse +
                " WHERE WarehousePosition = " + employeeCode;
        Cursor c = _dbDatabase.rawQuery(sql, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            warehouseID = c.getInt(c.getColumnIndex("WarehouseID"));
        }
        c.close();

        return  warehouseID;
    }

}
