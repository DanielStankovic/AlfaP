package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Pair;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.adapter.ProductDocumentAdapter;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.Api;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.ApiClient;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductDocument;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class ProductDocumentationActivity extends AppCompatActivity implements View.OnClickListener, ProductDocumentAdapter.ProductDocumentListener {

    private TextView documentationProdName, documentationProdCode, noDocumentationLbl;
    private Button  documentationDownloadDocBtn;
    private RecyclerView documentationRv;
    private BrokerSQLite db;
    private NetworkClass nc;
    private AlertDialog loadingDialog;
    private Product selectedProduct;
    private List<ProductDocument> documentList;
    private ProductDocumentAdapter adapter;
    private Api apiInterface;
    private ProductDocument selectedProductDocument;
    private DownloadFileFromServer downloadFileFromServer;
    private TextView progressDownloadTv;
    private ProgressBar progressBarDownload;
    private String documentPathOnSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_documentation);

        init();
        setupProduct();

        if(selectedProduct != null) {
            if (Utilities.isOnline(ProductDocumentationActivity.this)) {
                new GetProductDocFromServer().execute();
            } else {
                Utilities.showToast("Niste povezani na internet. Prikazuju se podaci sa tableta.", ProductDocumentationActivity.this);
                new GetProductDocAsync().execute();
            }
        }
        else
            Utilities.showToast("Došlo je do greške pri odabiru artikla. Napustite formu i pokušajte ponovo!", ProductDocumentationActivity.this);


        // new GetProductDocAsync().execute();

    }

    private void setupProduct() {
        String selProd = Objects.requireNonNull(getIntent().getExtras()).getString("selectedProduct");
        selectedProduct = new Gson().fromJson(selProd, Product.class);
        if(selectedProduct != null){
            documentationProdName.setText(selectedProduct.getProductName().trim());
            documentationProdCode.setText(getResources().getString(R.string.productCodeLbl, selectedProduct.getProductCode()));
        }
    }

    private void init() {

        db = new BrokerSQLite(this);
        nc = new NetworkClass(this);
        apiInterface = ApiClient.getApiClient().create(Api.class);

        documentPathOnSystem =  Environment.getExternalStorageDirectory().toString() + "/ProductDocuments/";

        documentationProdName = findViewById(R.id.documentationProdName);
        documentationProdCode = findViewById(R.id.documentationProdCode);
        noDocumentationLbl = findViewById(R.id.noDocumentationLbl);

        documentationDownloadDocBtn = findViewById(R.id.documentationDownloadDocBtn);
        documentationRv = findViewById(R.id.documentationRv);

        documentationRv.setHasFixedSize(false);
        documentationRv.setLayoutManager(Utilities.getLinearLayoutManager(ProductDocumentationActivity.this));
        documentationRv.addItemDecoration(Utilities.getRecyclerViewDecoration(Utilities.getLinearLayoutManager(ProductDocumentationActivity.this), ProductDocumentationActivity.this,documentationRv));

        documentationDownloadDocBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.documentationDownloadDocBtn:
            if(Utilities.isOnline(ProductDocumentationActivity.this))
                new GetProductDocFromServer().execute();
            else
                Utilities.showToast("Niste povezani na internet. Pokušajte ponovo kasnije!", ProductDocumentationActivity.this);

                break;
            default:
                break;
        }
    }

    @Override
    public void onButtonClicked(ProductDocument productDocument) {
        selectedProductDocument = productDocument;
        if(productDocument.isDownloaded()){
            //Ovo je uradjeno zato sto Android ima neki Seciruty kada se otvaraju fajlovi preko URIja. Ovaj deo je dodat da bi se to zaobislo.
            if(Build.VERSION.SDK_INT>=24){
                try{
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                    opetDownloadedFile(productDocument.getDocumentName());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }else{

            if (Utilities.isOnline(ProductDocumentationActivity.this)) {
                downloadFileFromServer(productDocument);
            } else {
                Utilities.showToast("Niste povezani na internet. Pokušajte ponovo kasnije!", ProductDocumentationActivity.this);
            }

        }
    }


    private class GetProductDocAsync extends AsyncTask<Void, Void, Void>{

        private boolean isOk = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                db.open();
                documentList = db.getDocumentList(selectedProduct.getProductID(), documentPathOnSystem);
                isOk = true;
                db.close();

            }catch (Exception ex){
                isOk = false;
                Utilities.writeErrorToFile(ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(isOk){
                showListOrTv(documentList.isEmpty());
                if(!documentList.isEmpty()){
                    adapter = new ProductDocumentAdapter(ProductDocumentationActivity.this, documentList, ProductDocumentationActivity.this);
                    documentationRv.setAdapter(adapter);
                }
            }else {
                Utilities.showToast("Došlo je do problema pri preuzimanju podataka iz baze.", ProductDocumentationActivity.this);
            }

            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();

            super.onPostExecute(aVoid);
        }
    }


    private class GetProductDocFromServer extends AsyncTask<Void, Void, Void> {
        private boolean isOk = false;

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                nc.syncProductDocument(selectedProduct.getProductID());
                db.open();
                documentList = db.getDocumentList(selectedProduct.getProductID(), documentPathOnSystem);
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
                showListOrTv(documentList.isEmpty());
                if (!documentList.isEmpty()) {
                    adapter = new ProductDocumentAdapter(ProductDocumentationActivity.this, documentList, ProductDocumentationActivity.this);
                    documentationRv.setAdapter(adapter);
                    Utilities.showToast("Preuzeti sveži podaci.", ProductDocumentationActivity.this);
                }

            } else {
                Utilities.showToast("Došlo je do greške prilikom sinhronizacije stanja magacina!", ProductDocumentationActivity.this);
            }

            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.cancel();

            super.onPostExecute(aVoid);
        }
    }

    private class DownloadFileFromServer extends AsyncTask<ResponseBody, Pair<Integer, Long>, String>{

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ResponseBody... responseBodies) {
            saveToDisk(responseBodies[0], selectedProductDocument.getDocumentName());
            return null;
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Long>... progress) {
            if (progress[0].first == 100)
               Utilities.showToast("Fajl preuzet uspešno!", ProductDocumentationActivity.this);

            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
                progressBarDownload.setProgress(currentProgress);

                progressDownloadTv.setText("Preuzimanje " + currentProgress + "%");

            }

            if (progress[0].first == -1) {
                Utilities.showToast("Došlo je do greške!", ProductDocumentationActivity.this);
            }
        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String s) {

            db.open();
            documentList = db.getDocumentList(selectedProduct.getProductID(), documentPathOnSystem);
            db.close();
            showListOrTv(documentList.isEmpty());
            if (!documentList.isEmpty()) {
                adapter = new ProductDocumentAdapter(ProductDocumentationActivity.this, documentList, ProductDocumentationActivity.this);
                documentationRv.setAdapter(adapter);
            }

            if(loadingDialog.isShowing())
                loadingDialog.cancel();
            super.onPostExecute(s);
        }
    }


    private void saveToDisk(ResponseBody body, String filename) {
        try {

            File myDir = new File(documentPathOnSystem);
            myDir.mkdirs();
            File destinationFile = new File(documentPathOnSystem, filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();

                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadFileFromServer.doProgress(pairs);
                }

                outputStream.flush();

                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadFileFromServer.doProgress(pairs);
                return;
            } catch (IOException e) {
                Utilities.writeErrorToFile(e);
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadFileFromServer.doProgress(pairs);
                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
           Utilities.writeErrorToFile(e);
            return;
        }
    }



    private void downloadFileFromServer(ProductDocument productDocument){
        Call<ResponseBody> call = apiInterface.downloadProductDocument(ApiClient.SERVICE_ADDRESS+"ProductDocuments/"+productDocument.getDocumentName());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    downloadFileFromServer = new DownloadFileFromServer();
                    downloadFileFromServer.execute(response.body());

                }else{
                    Utilities.writeErrorToFile(new Exception(response.errorBody() + response.message()));
                    Utilities.showToast("Došlo je do greške pri preuzimanju fajla sa servera. Greška: " + response.errorBody(),ProductDocumentationActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.writeErrorToFile(new Exception(t.getMessage()));
                Utilities.showToast("Došlo je do greške pri preuzimanju fajla sa servera.",ProductDocumentationActivity.this);
            }
        });
    }

    private void opetDownloadedFile(String productDocumentName){
        File file = new File(documentPathOnSystem, productDocumentName);
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        String type = map.getMimeTypeFromExtension(ext);

        if (type == null)
            type = "*/*";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(file);

        intent.setDataAndType(data, type);

        startActivity(intent);
    }
    private void showListOrTv(boolean isListEmpty){
        if(isListEmpty){
            documentationRv.setVisibility(View.GONE);
            noDocumentationLbl.setVisibility(View.VISIBLE);
        }else{
            documentationRv.setVisibility(View.VISIBLE);
            noDocumentationLbl.setVisibility(View.GONE);
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

    private void showProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_progress_download, null);
        progressDownloadTv = view.findViewById(R.id.progressDownloadTv);
        progressBarDownload = view.findViewById(R.id.progressBarDownload);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }
}
