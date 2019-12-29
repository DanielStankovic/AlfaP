package com.kolibrialfaplam.microbs.kolibrialfaplam.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.NetworkClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ImageType;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.ErrorClass;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Deflater;

public class TakePictureActivity extends AppCompatActivity implements View.OnClickListener, NetworkClass.SendDataInterface {

    private  final int CAMERA_PIC_REQUEST = 1337;

    private  String file_name = "";
    private File photo = null;
    private Uri selectedImageUri;
    private Bitmap thumbnail;

    private AppCompatSpinner takePictureSpinner;
    private ImageView takePictureImageView;
    private TextInputEditText takePictureEt;
    private Button takePictureBackBtn;
    private Button takePictureSendBtn;
    private Button takeImageBtn;
    private AlertDialog loadingDialog;

    private BrokerSQLite db;
    private NetworkClass nc;


    private ArrayList<ImageType> imageTypeList;
    private int imageTypeID;
    private String imageTypeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        //views initialization
        init();
    }

    private void init() {
        takePictureSpinner = findViewById(R.id.takePictureSpinner);
        takePictureImageView = findViewById(R.id.takePictureImageView);
        takePictureEt = findViewById(R.id.takePictureEt);
        takePictureBackBtn = findViewById(R.id.takePictureBackBtn);
        takePictureSendBtn = findViewById(R.id.takePictureSendBtn);
        takeImageBtn = findViewById(R.id.takeImageBtn);

        nc = new NetworkClass(TakePictureActivity.this, this);
        db = new BrokerSQLite(this);

        takePictureBackBtn.setOnClickListener(this);
        takePictureSendBtn.setOnClickListener(this);
        takeImageBtn.setOnClickListener(this);

        Glide.with(TakePictureActivity.this).load(R.drawable.button_background).into(takePictureImageView);
        setSpinner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePictureBackBtn:
                finish();
                break;
            case R.id.takePictureSendBtn:
               if(isSpinnerSelected())
                sendImageToServer();

                break;
            case R.id.takeImageBtn:

                try {
                    if(imageTypeID > 0)
                    startCamera();
                    else
                        Utilities.showToast("Niste odabrali tip slike", TakePictureActivity.this);
                } catch (Exception e) {
                    ErrorClass.handle(e, TakePictureActivity.this);
                }
                break;
        }
    }

    private void startCamera() throws Exception {
        file_name = formImageTitle() + ".jpg";
        photo = null;
        boolean cacheDir;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {

            photo = new File(Utilities.getDCIMPath(), file_name);
            cacheDir = false;

        }
        else
        {
            photo = new File(getCacheDir(), file_name);
            cacheDir = true;
        }
        if (photo != null)
        {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            selectedImageUri = Uri.fromFile(photo);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivityForResult(intent, CAMERA_PIC_REQUEST);

            deleteLastFromDCIM(cacheDir);
        }
    }
    private void sendImageToServer() {
        try{
        if(thumbnail==null||thumbnail.isRecycled())
        {
            Utilities.showToast("Pritisnuli ste dugme vise od jednog puta!", TakePictureActivity.this);
            return;
        }

            db.open();
            String imageString = Utilities.imageToString(thumbnail);
            if(TextUtils.isEmpty(imageString)){
                Utilities.showToast("Došlo je do greške pri parsiranju slike. Pokušajte kasnije", TakePictureActivity.this);
                return;
            }
            String imageTitle = formImageTitle();
            db.workOrderImage_I(
                    RouteActivity.selectedRouteID,
                    imageTypeID,
                    imageTitle,
                    imageString,
                    takePictureEt.getText().toString().trim()
            );
            db.close();
            nc.sendWorkOrderImage();
        }catch (Exception ex){
            ErrorClass.handle(ex, TakePictureActivity.this);
        }
    }

    private String formImageTitle(){

        return String.valueOf(MainActivity.employeeID) +
                "_" +
                RouteActivity.selectedRouteID +
                "_" +
                imageTypeID +
                "_" +
                Utilities.getCurrentDateTime().replaceAll("[ ]", "_").replaceAll("[:]", "-");
    }

    private boolean isSpinnerSelected() {
        if(imageTypeID <= 0){
            Utilities.showToast("Niste odabrali tip slike", TakePictureActivity.this);
            return false;
        }else if(thumbnail == null) {
            Utilities.showToast("Niste napravili sliku", TakePictureActivity.this);
            return false;
        }else if (TextUtils.equals(imageTypeName, "Ostalo") && TextUtils.isEmpty(takePictureEt.getText().toString().trim())){
                takePictureEt.setError("Napomena je obavezna kada je tip slike \"Ostalo\"");
                return false;
            }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST)
        {

            if (resultCode == Activity.RESULT_OK)
            {
                // TODO ovo zameni kada bude vremena
                try
                {
                    thumbnail = BitmapFactory.decodeFile(selectedImageUri.getPath());

                    double ratio = ((double) thumbnail.getWidth()) / 1024;
                    int height = (int) (((double) thumbnail.getHeight()) / ratio);
                    thumbnail = Bitmap.createScaledBitmap(thumbnail, 1024, height, true);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(),
                            matrix, true);

                    file_name = selectedImageUri.getPath();
                    Glide.with(TakePictureActivity.this).load(file_name).into(takePictureImageView);
                    deleteLastFromDCIMCamera();
                    // Proveriti da li je moguce da se ispravi ovo da se ne
                    // poziva dva puta, zbog memorije
                }
                catch (Exception e)
                {
                    try
                    {
                        thumbnail = BitmapFactory.decodeFile(selectedImageUri.getPath());
                        double ratio = ((double) thumbnail.getWidth()) / 1024;
                        int height = (int) (((double) thumbnail.getHeight()) / ratio);
                        thumbnail = Bitmap.createScaledBitmap(thumbnail, 1024, height, true);
                        file_name = selectedImageUri.getPath();
                        Glide.with(TakePictureActivity.this).load(file_name).into(takePictureImageView);
                    }
                    catch (Exception ex)
                    {
                        ErrorClass.handle(ex, TakePictureActivity.this);
                    }
                }
            }
            else
            {
                selectedImageUri = null;
            }
        }
    }


    private void setSpinner(){
        try {
            db.open();
            imageTypeList = db.getImageTypeList();
            takePictureSpinner.setAdapter(new ArrayAdapter<ImageType>(TakePictureActivity.this, android.R.layout.simple_selectable_list_item, imageTypeList){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    ImageType imageType = super.getItem(position);
                    TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                    textView.setTextSize(16);
                    textView.setTextColor(ContextCompat.getColor(TakePictureActivity.this, R.color.white));
                    textView.setText(Objects.requireNonNull(imageType).getImageTypeName());
                    imageTypeID = imageType.getImageTypeID();
                    imageTypeName = imageType.getImageTypeName();
                    return textView;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    ImageType imageType = super.getItem(position);
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setTextSize(16);
                    textView.setTextColor(ContextCompat.getColor(TakePictureActivity.this, R.color.white));
                    textView.setText(Objects.requireNonNull(imageType).getImageTypeName());
                    imageTypeID = imageType.getImageTypeID();
                    imageTypeName = imageType.getImageTypeName();
                    return textView;
                }
            });
            takePictureSpinner.setSelection(0);

            takePictureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageTypeID = imageTypeList.get(position).getImageTypeID();
                imageTypeName = imageTypeList.get(position).getImageTypeName();
                    takePictureEt.setError(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    takePictureEt.setError(null);
                }
            });

            db.close();
        } catch (SQLException e) {
            ErrorClass.handle(e, TakePictureActivity.this);
        }
    }

    @Override
    public void onStartSendingData() {
        showLoadingDialog();
    }

    @Override
    public void onSuccessSendingData(int dataType) {
        loadingDialog.cancel();
        Utilities.showToast("Podaci uspešno poslati na server!", TakePictureActivity.this);
        finish();
    }

    @Override
    public void onErrorSendingData(String errorMessage, int dataType) {
        loadingDialog.cancel();
        Utilities.writeErrorToFile(new Exception(errorMessage));
        Utilities.showToast("Došlo je do greške prilikom slanja potpisa na server. Proverite internet konekciju i pokušajte opet kasnije", TakePictureActivity.this);
        finish();
    }

    private void showLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_data, null);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }

    private boolean deleteLastFromDCIM(boolean cacheDir) throws Exception
    {
        boolean success = false;
        String putanjaX;

        if (cacheDir)
        {
            putanjaX = getCacheDir().getAbsolutePath();
        }
        else
        {
            putanjaX = Utilities.getDCIMPath();
        }

        File[] images = new File(putanjaX).listFiles();

        if (images != null && images.length > 0)
        {
            File latestSavedImage = images[0];
            for (int i = 1; i < images.length; ++i)
            {
                if (images[i].lastModified() > latestSavedImage.lastModified())
                {
                    latestSavedImage = images[i];
                }
            }
            success = latestSavedImage.delete();
        }

        return success;

    }

    private boolean deleteLastFromDCIMCamera() {

        boolean success = false;
        try {
            File[] images = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM/Camera").listFiles();
            File latestSavedImage = images[0];
            for (int i = 1; i < images.length; ++i) {
                if (images[i].lastModified() > latestSavedImage.lastModified()) {
                    latestSavedImage = images[i];
                }
            }

            success =  latestSavedImage.delete();
            // OR just use success = latestSavedImage.delete();
//            success = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + "DCIM/Camera/"
//                    + latestSavedImage).delete();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return success;
        }

    }

}
