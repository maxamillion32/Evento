package evento.com.evento.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import evento.com.evento.R;
import evento.com.evento.controllers.UserController;
import evento.com.evento.utils.Commons;

public class LoginActivity extends AppCompatActivity {

    Button mlogin;
    Button msignup;
    EditText email;
    EditText password;
    TextView forgottenPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        email=(EditText) findViewById(R.id.ETemailLogin);
        forgottenPass = (TextView) findViewById(R.id.forgotPassword);
        password=(EditText) findViewById(R.id.ETpassword);
        mlogin = (Button) findViewById(R.id.BTlogin);
        msignup = (Button) findViewById(R.id.BTregister);

        mlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Commons.currentActiveUser.setEmail(email.getText().toString());
                Commons.currentActiveUser.setPassword(password.getText().toString());
                UserController.login(LoginActivity.this,MainActivity.class);
            }
        });

        forgottenPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(email.getText().toString().trim().equals("")||email.getText().toString()==null){
                    Toast.makeText(LoginActivity.this, "please enter your the email", Toast.LENGTH_SHORT);
                }
                UserController.resetPasswordBySendEmail(email.getText().toString(), LoginActivity.this);
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupOneActivity.class);
                startActivity(intent);
            }
        });


    }
}
