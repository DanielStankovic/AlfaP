package com.kolibrialfaplam.microbs.kolibrialfaplam.data;

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
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WarehouseState;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrder;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderResult;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.WorkOrderType;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api {


    @GET("Login/{username}/{password}/{imei}")
    Call<List<Employee>> getEmployee(@Path("username") String userName, @Path("password") String password, @Path("imei") String imei);

    @GET("SignInEmployee/{employeeID}/{status}")
    Call<String> getSignIn(@Path("employeeID") String employeeID, @Path("status") String status);

    @GET("GetApplicationVersion")
    Call<List<ApplicationVersion>> getApplicationVersion();

    @POST("GetProductRangeByModifiedDate")
    Call<List<ProductRange>> getProductRangeByModifiedDate(@Body PostRequest postRequest);

    @POST("GetProductCategoryByModifiedDate")
    Call<List<ProductCategory>> getProductCategoryByModifiedDate(@Body PostRequest postRequest);

    @POST("GetFailureByModifiedDate")
    Call<List<Failure>> getFailureByModifiedDate(@Body PostRequest postRequest);

    @POST("GetFailureCauseByModifiedDate")
    Call<List<FailureCause>> getFailureCauseByModifiedDate(@Body PostRequest postRequest);

    @POST("GetServicesByModifiedDate")
    Call<List<Service>> getServicesByModifiedDate(@Body PostRequest postRequest);

    @POST("GetServiceNormByModifiedDate")
    Call<List<ServiceNorm>> getServiceNormByModifiedDate(@Body PostRequest postRequest);

    @POST("GetProductByModifiedDate")
    Call<List<Product>> getProductByModifiedDate(@Body PostRequest postRequest);

    @POST("GetPriceListByModifiedDate")
    Call<List<PriceList>> getPriceListByModifiedDate(@Body PostRequest postRequest);

    @POST("GetPartnerByModifiedDate")
    Call<List<Partner>> getPartnerByModifiedDate(@Body PostRequest postRequest);

    @POST("GetRegionByModifiedDate")
    Call<List<Region>> getRegionByModifiedDate(@Body PostRequest postRequest);

    @POST("GetRegionPlaceByModifiedDate")
    Call<List<RegionPlace>> getRegionPlaceByModifiedDate(@Body PostRequest postRequest);

    @POST("GetMaterialByModifiedDate")
    Call<List<Material>> getMaterialByModifiedDate(@Body PostRequest postRequest);

    @POST("GetWarehouseByModifiedDate")
    Call<List<Warehouse>> getWarehouseByModifiedDate(@Body PostRequest postRequest);

    @POST("GetWarehouseStateByModifiedDate")
    Call<List<WarehouseState>> getWarehouseStateByModifiedDate(@Body PostRequest postRequest);

    @POST("GetCheckInCommByModifiedDate")
    Call<List<CheckInPreDefComment>> getCheckInPreDefComment(@Body PostRequest postRequest);

    @POST("InsertCheckIn")
    Call<String> sendCheckIn(@Body List<CheckIn> checkInList);

    @POST("GetWorkOrderByModifiedDate")
    Call<List<WorkOrder>> getWorkOrderByModifiedDate(@Body PostRequest postRequest);

    @POST("GetWorkOrderTypeByModDate")
    Call<List<WorkOrderType>> getWorkOrderTypeByModDate(@Body PostRequest postRequest);

    @POST("InsertSignatureImage")
    Call<String> insertSignatureImage(@Body List<SignatureImage> signatureImageList);

    @POST("GetImageTypeByModDate")
    Call<List<ImageType>> getImageTypeByModDate(@Body PostRequest postRequest);

    @POST("InsertWorkOrderImage")
    Call<String> insertWorkOrderImage(@Body List<WorkOrderImage> workOrderImageList);

    @POST("InsertWorkOrderResult")
    Call<String> insertWorkOrderResult(@Body List<WorkOrderResult> workOrderResultList);

    @POST("GetPriorityTypeByModifiedDate")
    Call<List<PriorityType>> getPriorityTypeByModifiedDate(@Body PostRequest postRequest);

    @POST("InsertStartStop")
    Call<String> insertStartStop(@Body List<StartStop> startStopList);

    @POST("GetStartStopStatus")
    Call<StartStop> getStartStopStatus(@Body int employeeID);

    @POST("GetDocumentationByModDate")
    Call<List<ProductDocument>> getDocumentationByModDate(@Body PostRequest postRequest);

    @Streaming
    @GET
    Call<ResponseBody> downloadProductDocument(@Url String urlString);

    @POST("GetPollQuesTypeByModDate")
    Call<List<PollQuestionType>> getPollQuesTypeByModDate(@Body PostRequest postRequest);

    @POST("GetPollQuestionsByModDate")
    Call<List<PollQuestion>> getPollQuestionsByModDate(@Body PostRequest postRequest);

    @POST("GetNoSigCommentsByModDate")
    Call<List<NoSignatureComment>> getNoSigCommentsByModDate(@Body PostRequest postRequest);

    @POST("GetProductMaterialByModDate")
    Call<List<ProductMaterial>> getProductMaterialByModDate(@Body PostRequest postRequest);

    @POST("GetProductServiceByModDate")
    Call<List<ProductService>> getProductServiceByModDate(@Body PostRequest postRequest);

    @POST("GetProductFailureByModDate")
    Call<List<ProductFailure>> getProductFailureByModDate(@Body PostRequest postRequest);

    @POST("GetWorkOrderHistory")
    Call<List<History>> getWorkOrderHistory(@Body PostRequest postRequest);

    @POST("GetWorkOrderDetailsByWorkOrderID")
    Call<List<Route>> getWorkOrderDetailsByWorkOrderID(@Body PostRequest postRequest);

    @POST("GetProductColorByModDate")
    Call<List<ProductColor>> getProductColorByModDate(@Body PostRequest postRequest);

    @POST("GetAddedFeaturesForWO")
    Call<List<Route>> getAddedFeaturesForWorkOrdInProgress(@Body PostRequest postRequest);
}
