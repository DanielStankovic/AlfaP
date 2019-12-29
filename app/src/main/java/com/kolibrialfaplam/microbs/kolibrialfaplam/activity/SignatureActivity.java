package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;

import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;
import com.simplify.ink.InkView;



public class SignatureActivity extends AppCompatActivity implements View.OnClickListener, NetworkClass.SendDataInterface {

    private InkView ink;
    private Button clearSignatureBtn;
    private Button sendSignatureBtn;
    private NetworkClass nc;
    private AlertDialog loadingDialog;
    private BrokerSQLite db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        init();

    }

    private void init() {
        clearSignatureBtn = findViewById(R.id.clearSignatureBtn);
        sendSignatureBtn = findViewById(R.id.sendSignatureBtn);
        ink = findViewById(R.id.ink);
        ink.setColor(getResources().getColor(android.R.color.white));
        ink.setMinStrokeWidth(1.5f);
        ink.setMaxStrokeWidth(6f);

        nc = new NetworkClass(SignatureActivity.this, this);
        db = new BrokerSQLite(this);

        clearSignatureBtn.setOnClickListener(this);
        sendSignatureBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearSignatureBtn:
                ink.clear();
                break;

            case R.id.sendSignatureBtn:
                if(!ink.isViewEmpty()){
                  sendSignatureImageToServer();
                }else{
                    Utilities.showToast("Morate uneti potpis", SignatureActivity.this);
                }
                break;

            default:
                break;
        }
    }

    private void sendSignatureImageToServer() {
        try {
            db.open();
            Bitmap signatureBitmap = ink.getBitmap();
            String imageString = Utilities.imageToString(signatureBitmap);
            if(TextUtils.isEmpty(imageString)){
                Utilities.showToast("Došlo je do greške pri parsiranju slike. Pokušajte kasnije", SignatureActivity.this);
                return;
            }
            String imageTitle = formImageTitle();
            db.signatureImage_I(
                    RouteActivity.selectedRouteID,
                    imageTitle,
                    imageString
            );
            db.close();
            nc.sendSignatureToServer();
        } catch (Exception e) {
            ErrorClass.handle(e, SignatureActivity.this);
        }
    }



    private String formImageTitle(){

        return String.valueOf(MainActivity.employeeID) +
                "_" +
                RouteActivity.selectedRouteID +
                "_" +
                Utilities.getCurrentDateTime().replaceAll("[ ]", "_").replaceAll("[:]", "-");
    }

    @Override
    public void onStartSendingData() {
        showLoadingDialog();
    }

    @Override
    public void onSuccessSendingData(int dataType) {
        loadingDialog.cancel();
        Utilities.showToast("Podaci uspešno poslati na server!", SignatureActivity.this);
        finish();
    }

    @Override
    public void onErrorSendingData(String errorMessage, int dataType) {
        loadingDialog.cancel();
        Utilities.writeErrorToFile(new Exception(errorMessage));
        Utilities.showToast("Došlo je do greške prilikom slanja potpina na server. Proverite internet konekciju i pokušajte opet kasnije", SignatureActivity.this);
        finish();
    }

    private void showLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SignatureActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }
}
