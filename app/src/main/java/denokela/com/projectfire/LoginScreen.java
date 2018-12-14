package denokela.com.projectfire;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class LoginScreen extends AppCompatActivity {
    EditText username, password;
    Button LogIn, Register, FrgtPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        LogIn = (Button) findViewById(R.id.btnLogin);


        //Register New Admin Activity Start
        Register = (Button) findViewById(R.id.btnRegister);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, AdminRegistration.class));
                finish();
            }
        });

        //Forgotten Password Activity Start
        FrgtPass = (Button) findViewById(R.id.btnForgtPass);
        FrgtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, ForgottenPassword.class));
                finish();
            }
        });


    }

    public void LogIn(View view) {


        String user_name = username.getText().toString().trim();
        String pass_word = password.getText().toString().trim();

        if (user_name.equals("") || pass_word.equals("")) {

            Toast.makeText(this, "Please Fill all Fields", Toast.LENGTH_LONG).show();
        } else {
            String type = "Login";
            DataBank data = new DataBank(this);
            data.execute(type, user_name, pass_word);


        }
    }

}

