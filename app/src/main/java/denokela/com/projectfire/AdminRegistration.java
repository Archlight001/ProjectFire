package denokela.com.projectfire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminRegistration extends AppCompatActivity {
    EditText first_name,surname,user_name,pass_word,conf_password,admin_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        first_name= (EditText) findViewById(R.id.etfirstname);
        surname = (EditText) findViewById(R.id.etSurname);
        user_name= (EditText) findViewById(R.id.etuName);
        pass_word= (EditText) findViewById(R.id.etPass);
        conf_password= (EditText) findViewById(R.id.etConPassword);
        admin_id = (EditText) findViewById(R.id.etAdminId);
    }

    public void Register(View view) {
        String fname= first_name.getText().toString().trim();
        String sname= surname.getText().toString().trim();
        String uname= user_name.getText().toString().trim();
        String pword= pass_word.getText().toString().trim();
        String conpword= conf_password.getText().toString().trim();
        String adid= admin_id.getText().toString().trim();


         if(fname.equals("")||sname.equals("")||uname.equals("")||pword.equals("")||
                conpword.equals("")||adid.equals("")){
            Toast.makeText(this,"Please Fill all Fields",Toast.LENGTH_LONG).show();
        }
        else if(pword.equals(conpword)) {
            String type = "Register";
            DataBank data = new DataBank(this);
            data.execute(type,fname, sname, uname,pword,adid);
        }
        else{
            Toast.makeText(this,"Passwords Don't Match",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginScreen.class));
        finish();
    }
}
