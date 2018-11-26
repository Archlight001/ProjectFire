package denokela.com.projectfire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import denokela.com.projectfire.LoginScreen;
import denokela.com.projectfire.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if(!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
            Thread close = new Thread(){
                @Override
                public void run() {
                    try{
                        sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        finish();
                    }
                }
            };
            close.start();

        }
        else{

            Thread next = new Thread(){
                @Override
                public void run() {
                    try{
                        sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        startActivity(new Intent("com.denokela.gmail.LoginScreen"));
                        finish();
                    }
                }
            };
            next.start();

        }

    }
    public boolean isConnected(Context context){
        ConnectivityManager cm= (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
           android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
           android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

           if(mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting())return true;
           else return false;
        }
        else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have mobile Data or Wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
    }

}
