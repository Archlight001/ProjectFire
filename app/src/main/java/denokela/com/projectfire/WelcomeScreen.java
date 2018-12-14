package denokela.com.projectfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

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
                final AlertDialog adminidalert= new AlertDialog.Builder(this).create();
                final ProgressDialog progressDialog= new ProgressDialog(this);
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
            super.onBackPressed();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UpgradeDatabase()).commit();
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
