package denokela.com.projectfire;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Jservice extends JobService {
    private boolean jobcancelled = false;
    private static final String TAG = "Class: ";
    private static final int uniqueID = 456;

    BufferedInputStream is;
    String line = null;
    String result = null;
    String [] fname;
    String [] sname;
    String [] bday;
    String [] bmonth;
    Integer count=0;
    Thread td;
    NotificationCompat.Builder builder;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Process Started");
        checkbirthday(jobParameters);

        return true;
    }

    private void checkbirthday(final JobParameters params){
        final String id="793347943";
        final String title  = "The title";
        td =new Thread() {
            @Override
            public void run() {
                if(jobcancelled){
                    return;
                }
                try {

                    URL url = new URL("http://192.168.43.194/FB_DATA/grab_bday_bmonth.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    is = new BufferedInputStream(con.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                try {
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    fname = new String[ja.length()];
                    sname = new String[ja.length()];
                    bday = new String[ja.length()];
                    bmonth = new String[ja.length()];
                    for (int i = 0; i <= ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        fname[i] = jo.getString("First Name");
                        sname[i] =jo.getString("Surname");
                        bday[i] = jo.getString("Birthday Day");
                        bmonth[i] = jo.getString("Birthday Month");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }try{
                    DateFormat month = new SimpleDateFormat("MMMM");
                    DateFormat day = new SimpleDateFormat("d");
                    Date date = new Date();
                    String monthstr = month.format(date);
                    String daystr = day.format(date);

                    for(int x=0; x<bmonth.length;x++){
                        Log.d(TAG, bday[x] + " "+ bmonth[x]);
                        if(daystr.equals(bday[x]) && monthstr.equals(bmonth[x])){
                            count++;
                            if(count >=2){

                                Log.d(TAG, fname[x] + " " + sname[x] + " " + "is having a birthday today");
                                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    int importance = NotificationManager.IMPORTANCE_HIGH;
                                    NotificationChannel mChannel = nm.getNotificationChannel(id);
                                    if (mChannel == null) {
                                        mChannel = new NotificationChannel(id, title, importance);
                                        mChannel.enableVibration(true);
                                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                                        nm.createNotificationChannel(mChannel);
                                    }
                                    builder = new NotificationCompat.Builder(getApplicationContext(),id);
                                    builder.setAutoCancel(true);
                                    builder.setSmallIcon(R.drawable.ic_notifications);
                                    builder.setContentTitle("Birthday Alert");
                                    builder.setContentText("Today is " + fname[x] + " " + sname[x] + " and Others Birthday");
                                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                    builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                                    builder.setWhen(System.currentTimeMillis());
                                }else{
                                    builder = new NotificationCompat.Builder(getApplicationContext(),id);
                                    builder.setAutoCancel(true);
                                    builder.setSmallIcon(R.drawable.ic_notifications);
                                    builder.setContentTitle("Birthday Alert");
                                    builder.setContentText("Today is " + fname[x] + " " + sname[x] + " and Others Birthday");
                                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                    builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                                    builder.setWhen(System.currentTimeMillis());
                                }





                                nm.notify(new Random().nextInt(), builder.build());


                            }else {
                                Log.d(TAG, fname[x] + " " + sname[x] + " " + "is having a birthday today");
                                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    int importance = NotificationManager.IMPORTANCE_HIGH;
                                    NotificationChannel mChannel = nm.getNotificationChannel(id);
                                    if (mChannel == null) {
                                        mChannel = new NotificationChannel(id, title, importance);
                                        mChannel.enableVibration(true);
                                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                                        nm.createNotificationChannel(mChannel);
                                    }
                                    builder = new NotificationCompat.Builder(getApplicationContext(),id);
                                    builder.setAutoCancel(true);
                                    builder.setSmallIcon(R.drawable.ic_notifications);
                                    builder.setContentTitle("Birthday Alert");
                                    builder.setContentText("Today is " + fname[x] + " " + sname[x] + "'s " + "Birthday");
                                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                    builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                                    builder.setWhen(System.currentTimeMillis());
                                }else{
                                    builder = new NotificationCompat.Builder(getApplicationContext(),id);
                                    builder.setAutoCancel(true);
                                    builder.setSmallIcon(R.drawable.ic_notifications);
                                    builder.setContentTitle("Birthday Alert");
                                    builder.setContentText("Today is " + fname[x] + " " + sname[x] + "'s " + "Birthday");
                                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                    builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                                    builder.setWhen(System.currentTimeMillis());
                                }





                                nm.notify(new Random().nextInt(), builder.build());

                            }
                        }
                    }


                    jobFinished(params,false);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        td.start();

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job Cancelled before completion");
        jobcancelled=true;
        return true;
    }
}
