package com.kolibrialfaplam.microbs.kolibrialfaplam.utility;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.MainActivity;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Utilities {


    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void deleteApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void writeErrorToFile(Exception e) {


        StringBuilder text = new StringBuilder();
        try {
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd. MMM yyyy. HH:mm:ss");
            String formattedDate = df.format(c.getTime());

            File file = new File(MainActivity.ERROR_PATH, MainActivity.ERROR_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

            PrintWriter writer = new PrintWriter(new FileOutputStream(file));
            writer.append("\n---------------------------" + formattedDate + "--------------------------------\n");
            e.printStackTrace(writer);
            writer.append(text.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getErrorFileSize(Context context) {
        final long FILE_MAX_SIZE = 7;
        File file = new File(MainActivity.ERROR_PATH, MainActivity.ERROR_FILE);

        long sizeInBytes = file.length();
        // transform in MB
        long sizeInMb = sizeInBytes / (1024 * 1024);

        if (sizeInMb > FILE_MAX_SIZE) {
            SendMail mail = new SendMail();
            mail.setAddAttachment(true);
            mail.setMailContentType(SendMail.MailContentType.MAIL_TEXT_TYPE);
            mail.setSubject("AUTOMATSKI MAIL SA Gudmarka-A ANDROID");
            mail.setMailTo("kolibri@micro-bs.com");
            mail.setBody("Automatsko slanje maila kako error log fajl ne bi bio prevelik za slanje.");
            mail.execute(context);
        }
    }

    public static String dateFormatChange(String str) {
        String time = str.split(" ")[1];
        str = str.split(" ")[0];
        // ukoliko datum izgleda ovako 5/30/2012
        if (str.contains("/")) {
            String[] temp = str.split("/");

            if (temp[0].length() < 2) {
                temp[0] = "0" + temp[0];
            }
            if (temp[1].length() < 2) {
                temp[1] = "0" + temp[1];
            }

            str = temp[2] + "-" + temp[0] + "-" + temp[1];
        } else {
            String[] temp = str.split("\\.");

            if (temp[0].length() < 2) {
                temp[0] = "0" + temp[0];
            }
            if (temp[1].length() < 2) {
                temp[1] = "0" + temp[1];
            }

            str = temp[2] + "-" + temp[1] + "-" + temp[0];
        }
        return str + " " + time;
    }

    public static String changeDateFormatToLocalFormat(String date)
    {
        try
        {
            if (date.contains("-"))
            {
                date = date.split(" ")[0];
                String day = date.split("-")[2];
                String month = date.split("-")[1];
                String year = date.split("-")[0];
                return day + "." + month + "." + year + ".";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            writeErrorToFile(e);
        }


        return date;
    }

    public static String changeDateTimeFormatToLocalFormat(String date)
    {
        try
        {
            String time = date.split(" ")[1].substring(0, 8);
            if (date.contains("-"))
            {
                date = date.split(" ")[0];
                String day = date.split("-")[2];
                String month = date.split("-")[1];
                String year = date.split("-")[0];
                return day + "." + month + "." + year + ". " + time;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            writeErrorToFile(e);
        }


        return date;
    }

    public static int calculateDaysBetweenDateAndToday(String dateParam){
        int daysBetween = 500;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            Date endDate = sdf.parse(dateParam);
            Date today = sdf.parse(getCurrentDateTime());
            long diff = endDate.getTime() - today.getTime();
            daysBetween = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if(daysBetween < 0) daysBetween = 0;

        } catch (ParseException e) {
            Utilities.writeErrorToFile(e);
        }

        return daysBetween;
    }

    public static String getCurrentDateTime()
    {

        String currentResult = "";
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getDefault());

        Date d = new Date();
        currentResult = dateFormatGmt.format(d.getTime()).toString();

        return currentResult;

    }

    public static String getTomorrowDateAndTimeForLogin() {
        String result = "";

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatGmt.setTimeZone(TimeZone.getDefault());
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();
        result = dateFormatGmt.format(tomorrow.getTime()).toString();
        result = result + " 08:00:00";

        return result;
    }

    public static String imageToString(Bitmap bitmap){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85,byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(imgByte, Base64.DEFAULT);
            System.gc();
            imgByte = null;
            return imageString;
        } catch (Exception e) {
            writeErrorToFile(e);
        }
        return "";
    }

    @SuppressLint("MissingPermission") // nema potrebe da se proverava ovde posto se proverava kada se kliken an dugme u WorkOrderActivity.
    public static CheckInResult getLastBestLocation(Context context) throws Exception {
        String coordSucc = "success";
        String coordUnsucc = "failure";
        Location location = null;
        try {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            boolean isNetworkPasive = false;
            // getting GPS statusDialog
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network statusDialog
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            isNetworkPasive = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
            if (!isGPSEnabled) {
                return new CheckInResult(coordUnsucc, "NIJE UKLJUCEN GPS!");
            } else {
                if (isNetworkEnabled) {



                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10,
                            new LocationListener() {

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onProviderEnabled(String provider) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onProviderDisabled(String provider) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onLocationChanged(Location location) {
                                    // TODO Auto-generated method stub

                                }
                            });

                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        return new CheckInResult(coordSucc, location.toString());

                    }
                }
                if (isNetworkPasive)
                {
                    Criteria criteria = new Criteria();
                    String bestProvider = locationManager.getBestProvider(criteria, false);
                    location = locationManager.getLastKnownLocation(bestProvider);

                    List<String> providers = locationManager.getProviders(true);

                    Location l = null;

                    for (int i = providers.size() - 1; i >= 0; i--)
                    {
                        l = locationManager.getLastKnownLocation(providers.get(i));
                        if (l != null)
                        {
                            location = l;
                            return new CheckInResult(coordSucc, location.toString());
                        }
                    }

                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 10,
                                new LocationListener() {

                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    public void onProviderEnabled(String provider)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    public void onProviderDisabled(String provider)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onLocationChanged(Location location)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                });

                        return new CheckInResult(coordUnsucc,"bez koordinata");
                    }
                    else

                    if (locationManager != null)
                    {

                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        return new CheckInResult(coordSucc, location.toString());
                    }

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            writeErrorToFile(e);
            return null;
        }

        return new CheckInResult(coordSucc,location.toString());
    }

    @SuppressLint("MissingPermission")
    public static Location getLocationCoordinates(Context context) throws Exception
    {
        Location location = null;
        try
        {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            boolean isNetworkPasive = false;
            // getting GPS statusDialog
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network statusDialog
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            isNetworkPasive = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
            if (!isGPSEnabled)
            {
                return null;
            }
            else
            {
                if (isNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10,
                            new LocationListener() {

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras)
                                {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onProviderEnabled(String provider)
                                {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onProviderDisabled(String provider)
                                {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void onLocationChanged(Location location)
                                {
                                    // TODO Auto-generated method stub

                                }
                            });

                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        return location;
                    }
                }
                if (isNetworkPasive)
                {
                    Criteria criteria = new Criteria();
                    String bestProvider = locationManager.getBestProvider(criteria, false);
                    location = locationManager.getLastKnownLocation(bestProvider);

                    List<String> providers = locationManager.getProviders(true);

                    Location l = null;

                    for (int i = providers.size() - 1; i >= 0; i--)
                    {
                        l = locationManager.getLastKnownLocation(providers.get(i));
                        if (l != null)
                        {
                            location = l;
                            return location;
                        }
                    }

                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 10,
                                new LocationListener() {

                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    public void onProviderEnabled(String provider)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    public void onProviderDisabled(String provider)
                                    {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onLocationChanged(Location location)
                                    {
                                        // TODO Auto-generated method stub

                                    }
                                });

                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            return location;
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return location;
    }

    public static String getCheckInID(int selectedRouteID, int employeeID) {


        StringBuilder sb = new StringBuilder();
        sb.append(selectedRouteID);
        sb.append(employeeID);
        sb.append("_");

        String dateToSplit = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        String dateFormat = dateToSplit.split(" ")[0];
        String timeFormat = dateToSplit.split(" ")[1];

        sb.append(dateFormat.split("/")[0].substring(dateFormat.split("/")[0].length() - 2));
        sb.append(dateFormat.split("/")[1]);
        sb.append(dateFormat.split("/")[2]);
        sb.append("_");
        sb.append(timeFormat.split(":")[0]);
        sb.append(timeFormat.split(":")[1]);
        sb.append(timeFormat.split(":")[2]);

        return sb.toString();

    }

    public static String getDCIMPath()
    {
        String path = Environment.getExternalStorageDirectory().toString();

        File temp = new File(path, "/TempMBS/");
        if (!temp.exists())
        {
            temp.mkdirs();
        }

        return temp.getAbsolutePath();
    }

    public static String removeSerbianChars(String stringToFormat) {
    return  stringToFormat.replaceAll("[Šš]", "s").replaceAll("[Đđ]", "dj").replaceAll("[Čč]", "c").replaceAll("[Ćć]", "c")
            .replaceAll("[Žž]", "z");
    }


    public static class CheckInResult
    {
        private final String  firstValue;
        private final String secondValue;

        public CheckInResult (String pr,String dr)
        {
            this.firstValue=pr;
            this.secondValue =dr;

        }

        public String getFirst()
        {
            return firstValue;
        }
        public String getSecond()
        {
            return secondValue;
        }
    }

    public static boolean isOnline(Context context) {
        boolean connected = false;
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {

        }
        return connected;
    }


    public static LinearLayoutManager getLinearLayoutManager(Context context) {
        return new NpaLinearLayoutManager(context);
    }

    public static DividerItemDecoration getRecyclerViewDecoration(LinearLayoutManager linearLayoutManager, Context context, RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divide_white)));
        return dividerItemDecoration;
    }

    public static String formatLabelLines(String textToFormat, int offset) {

        if(textToFormat.length()> offset){
            return textToFormat.substring(0, offset - 1) + ".\r\n";
        }else
            return textToFormat + "\r\n";
    }

    public static String formatLabelLinesForService(String textToFormat, int offset) {

        if(textToFormat.length()> offset){
            return textToFormat.substring(0, offset - 1) + ". ";
        }else{
            return  StringUtils.rightPad(textToFormat, 30, " ");
        }

    }


    public static String formatLabelLinesForMaterial(String textToFormat, int offset) {

        if(textToFormat.length()> offset){
            return textToFormat.substring(0, offset - 1) + ". ";
        }else{
            return  StringUtils.rightPad(textToFormat, 31, " ");
        }

    }
    public static String formatLabelLinesForEmployee(String failure, String failureCause) {
        int offset = 21;
        String formattedFailure;
        String formattedFailureCause;
        if(failure.length() > offset)
            formattedFailure  = failure.substring(0, offset - 1) + ".";
        else
            formattedFailure = StringUtils.rightPad(failure, offset, " ");

        if(failureCause.length() > offset)
            formattedFailureCause  = failureCause.substring(0, offset - 1) + ".";
        else
            formattedFailureCause = failureCause;

     return formattedFailure + formattedFailureCause + "\r\n";
    }

    public static String formatLabelLinesForDesc(String textToFormat) {

        String insert = "\r\n";
        int period = 47;

        StringBuilder builder = new StringBuilder(
                textToFormat.length() + insert.length() * (textToFormat.length()/period)+1);

        int index = 0;
        String prefix = "";
        while (index < textToFormat.length())
        {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(textToFormat.substring(index,
                    Math.min(index + period, textToFormat.length())));
            index += period;
        }
        builder.append(insert);
        return builder.toString();
    }

    public static String formatLabelLinesForFailuresAndCauses(String textToFormat, int offset) {

        if(textToFormat.length()> offset){
            return textToFormat.substring(0, offset - 1) + ".\r\n";
        }else{
            return  StringUtils.rightPad(textToFormat, 47, " ") + "\r\n";
        }
    }

    public static void deleteErrorFile()
    {
        File file = new File(MainActivity.ERROR_PATH, MainActivity.ERROR_FILE);
        if (file.exists())
        {
            file.delete();
        }
    }

    public static boolean ifExistsErrorLogFile()
    {
        File file = new File(MainActivity.ERROR_PATH, MainActivity.ERROR_FILE);
        return file.exists();
    }

    public static boolean exportDB(Context context) {
        boolean isOk = false;
        Activity activity = (Activity) context;
        String PACKAGE_NAME = activity.getApplication().getPackageName();

        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + PACKAGE_NAME + "/databases/"
                + Constants.SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, Constants.SAMPLE_DB_NAME);
        if (currentDB.exists()) {
            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                isOk = true;
            } catch (IOException e) {
                isOk = false;
                ErrorClass.handle(e, activity);
            }
        }
        return isOk;

    }

}
