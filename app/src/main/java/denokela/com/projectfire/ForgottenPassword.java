package denokela.com.projectfire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgottenPassword extends AppCompatActivity {

    EditText etFPAdminId, etFPUsername, etFPNewPassword, etFPConNewPassword;
    Button btnFPReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        etFPAdminId = (EditText) findViewById(R.id.etFrgtAdminId);
        etFPUsername = (EditText) findViewById(R.id.etFrgtUsername);
        etFPNewPassword= (EditText) findViewById(R.id.etFrgtNewPassword);
        etFPConNewPassword = (EditText) findViewById(R.id.etFrgtConNewPassword);
        btnFPReset = (Button) findViewById(R.id.btnsetPassword);

    }

    public void ResetPassword(View view) {
        String fpadminid = etFPAdminId.getText().toString().trim();
        String fpusername= etFPUsername.getText().toString().trim();
        String fpnewpassword = etFPNewPassword.getText().toString().trim();
        String fpconnewpassword = etFPConNewPassword.getText().toString().trim();



        if(fpadminid.equals("")||fpusername.equals("")||fpnewpassword.equals("")
                ||fpconnewpassword.equals("")
                ){
            Toast.makeText(this,"Please Fill all Fields",Toast.LENGTH_LONG).show();
        }else if(fpnewpassword.equals(fpconnewpassword)){
            String type= "Reset";
            DataBank data = new DataBank(this);
            data.execute(type,fpadminid,fpusername,fpnewpassword);

        }
        else{
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginScreen.class));
        finish();
    }
}
