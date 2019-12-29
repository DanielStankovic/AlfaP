package com.kolibrialfaplam.microbs.kolibrialfaplam.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.MainActivity;

public class SendMail extends AsyncTask<Context, Void, Void> {

    private boolean isOK = false;
    private Context context = null;
    private String error = "Email nije poslat.";
    private String body;
    private String subject;
    private String mailTo = "";
    private boolean addAttachment = false;
    private MailContentType mailContentType;

    @Override
    protected Void doInBackground(Context... params)
    {
        context = params[0];
        Mail m = new Mail("kolibri@micro-bs.com", "softver!");

        String[] toArr = { mailTo };
        m.setTo(toArr);
        m.setFrom("kolibri@micro-bs.com");
        m.setSubject(subject);
        m.setBody(body);
        m.setMailContentType(mailContentType);

        try
        {
            if (addAttachment && Utilities.ifExistsErrorLogFile())
            {
                Utilities.exportDB(context);
                String path = Environment.getExternalStorageDirectory() + "/";
                String dbName = "Kolibri_T.db";

                m.addAttachment(path + dbName);
                m.addAttachment(MainActivity.ERROR_PATH + MainActivity.ERROR_FILE);
            }



            if (m.send())
            //if (1 == 1)
            {
                isOK = true;
            }
            else
            {
                isOK = false;
            }
        }
        catch (Exception e)
        {
            error = "Greška: " + e.getMessage();
            Utilities.writeErrorToFile(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        try
        {
            if (isOK)
            {
                Utilities.showToast("Email poslat", context);
                // ukoliko je postal error fajl obrisi ga, da se nebi podaci
                // talozili, da fajl moze da se posalje preko maila kao attachment
                if (addAttachment)
                {
                    Utilities.deleteErrorFile();
                }
            }
            else
            {
                Utilities.showToast(error, context);
            }
        }
        catch (Exception e)
        {
            Utilities.writeErrorToFile(e);
        }
        super.onPostExecute(result);
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getMailTo()
    {
        return mailTo;
    }

    public void setMailTo(String mailTo)
    {
        this.mailTo = mailTo;
    }

    public boolean isAddAttachment()
    {
        return addAttachment;
    }

    public void setAddAttachment(boolean addAttachment)
    {
        this.addAttachment = addAttachment;
    }

    public MailContentType getMailContentType()
    {
        return mailContentType;
    }

    public void setMailContentType(MailContentType mailContentType)
    {
        this.mailContentType = mailContentType;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public enum MailContentType
    {
        MAIL_HTML_TYPE, MAIL_TEXT_TYPE;
    }

}
