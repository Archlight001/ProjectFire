package denokela.com.projectfire;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Boolean doubleBacktoExitPressedOnce= false;

    private static final String TAG = "Class: ";
    private Boolean hasbeenScheduled = false;
    private Integer JOB_ID=764198;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        for(JobInfo jobInfo:scheduler.getAllPendingJobs()){
            if(jobInfo.getId()==JOB_ID){
                hasbeenScheduled=true;
                break;
            }
        }
        if(hasbeenScheduled == false){
            addBirthdayReminder();
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.Projectbackcolor));
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void addBirthdayReminder() {
        ComponentName componentName = new ComponentName(this,Jservice.class);
        JobInfo info = new JobInfo.Builder(JOB_ID,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_METERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultcode = scheduler.schedule(info);
        if(resultcode == JobScheduler.RESULT_SUCCESS){

            Log.d(TAG,"Job Scheduled" );
        }else{
            Log.d(TAG,"Job Scheduling failed" );
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.baradmin_id:
                final AlertDialog adminidalert= new AlertDialog.Builder(this,R.style.MyDialogTheme).create();
                final ProgressDialog progressDialog= new ProgressDialog(this,R.style.MyDialogTheme);
                progressDialog.setMessage("Getting Administrative ID...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                Bundle grabit = getIntent().getExtras();
                String username = grabit.getString("Username");
                String password = grabit.getString("Password");

                UrlConnectivity getadmidid = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        if(output !=null && !output.equals("") && output.contains("FB")){
                            progressDialog.dismiss();
                            adminidalert.setMessage("Your Administrative ID is "+ output);
                            adminidalert.show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Sorry, An error occured please try again later",Toast.LENGTH_LONG).show();
                        }
                    }
                },"GetAdmin");
                getadmidid.execute(username,password);
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if(doubleBacktoExitPressedOnce){
                super.onBackPressed();
                return;
            }
            this.doubleBacktoExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBacktoExitPressedOnce=false;
                }
            },2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;
            case R.id.nav_add_member:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserReg_Fragment()).commit();
                break;
            case R.id.nav_current_excos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CurrentExcos_List()).commit();
                break;
            case R.id.nav_view_excos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListExcos_Set()).commit();
                break;
            case R.id.nav_assign_excos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpgradeDatabase()).commit();
                break;
            case R.id.nav_bdays:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Birthdays_Of_The_Month()).commit();
                break;
            case R.id.nav_exit:
                finish();
                break;
    }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
