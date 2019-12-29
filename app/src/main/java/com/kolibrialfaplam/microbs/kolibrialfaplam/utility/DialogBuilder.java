package com.kolibrialfaplam.microbs.kolibrialfaplam.utility;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;


import com.kolibrialfaplam.microbs.kolibrialfaplam.R;

import androidx.appcompat.app.AlertDialog;


public class DialogBuilder {


    public static void showInfoDialog(Context context, String title, String message, final DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.myDialog);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("DA", listener);
        builder.setNegativeButton("NE", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showOkDialog(Context context, String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.myDialog);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showOkDialogWithCallback(Context context, String title, String message, DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.myDialog);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("OK", listener);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showYesNoDialog(Context context, String title, String message, final DialogInterface.OnClickListener listener1, final DialogInterface.OnClickListener listener2){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.myDialog);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("DA", listener1);
        builder.setNegativeButton("NE", listener2);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
