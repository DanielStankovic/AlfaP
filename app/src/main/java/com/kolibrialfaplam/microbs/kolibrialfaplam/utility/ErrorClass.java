package com.kolibrialfaplam.microbs.kolibrialfaplam.utility;

import android.app.Activity;
import android.database.SQLException;

import org.json.JSONException;

import java.io.IOException;

public class ErrorClass {

    public static void handle(Exception ex,Activity context)
    {
        Utilities.writeErrorToFile(ex);

        Utilities.getErrorFileSize(context);

        ex.printStackTrace();



        if(ex != null)
        {
            if(ex instanceof IOException)
            {
                DialogBuilder.showOkDialog(context, "Nema interneta", ex.getMessage());
               // Popup("Nema internet",ex.getMessage(), context);
            }
            else if(ex instanceof SQLException)
            {
                DialogBuilder.showOkDialog(context, "Greška u lokalnoj bazi!", ex.getMessage());
               // Popup("Greska u lokalnoj bazi!",ex.getMessage(), context);
            }
            else if(ex instanceof JSONException)
            {
                DialogBuilder.showOkDialog(context, "Greška pri parsiranju jsona!", ex.getMessage());
               // Popup("Greska pri parsiranju jsona!",ex.getMessage(), context);
            }
            else
            {
                DialogBuilder.showOkDialog(context, "Exception", ex.getMessage());
               // Popup("Exception",ex.getMessage(), context);
            }
        }


    }

//    private static void Popup(final String naziv,final String text, final Activity currentOnTopActivity)
//    {
//
//        try
//        {
//            if (currentOnTopActivity!=null && !currentOnTopActivity.isFinishing()) {
//                currentOnTopActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try
//                        {
//                            final Dialog d=new Dialog(currentOnTopActivity);
//                            d.setTitle(naziv);
//                            TextView tv = new TextView(currentOnTopActivity);
//                            tv.setText(text);
//                            d.setContentView(tv);
//                            d.show();
//                        }
//                        catch (Exception e)
//                        {
//                            Utilities.writeErrorToFile(e);
//                        }
//                    }
//
//                });
//            }
//        }
//        catch (Exception e)
//        {
//            Utilities.writeErrorToFile(e);
//        }
//
//
//    }

}
