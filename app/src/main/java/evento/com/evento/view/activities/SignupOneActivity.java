package evento.com.evento.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import evento.com.evento.R;
import evento.com.evento.utils.Commons;

public class SignupOneActivity extends AppCompatActivity {

    EditText mEmail , mPassword1, mPassword2;
    Button mContinueButton;
    String emailstr, password1str, password2str ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        mEmail = (EditText) findViewById(R.id.ETemailSignup);
        mPassword1 = (EditText) findViewById(R.id.ETpassword1);
        mPassword2 = (EditText) findViewById(R.id.ETpassword2);
        mContinueButton = (Button) findViewById(R.id.BTcontinue);

    }

    public static boolean emailValidation(String email) {
        try {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
                    "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
            e.notify();
        }
        return false;
    }

    public void onContinueClick (View v){
        emailstr = mEmail.getText().toString().trim();
        password1str = mPassword1.getText().toString();
        password2str = mPassword2.getText().toString();
        if (v.getId() == R.id.BTcontinue) {

            if(emailstr.equals("")){
                mEmail.setError("Email is required!");
            }
            else if (emailValidation(emailstr) == false){
                mEmail.setError("Enter correct email format");
            }
            else if (password1str.trim().equals("")){
                mPassword1.setError("Password is required!");
            }
            else if(!password1str.equals(password2str)){
                mPassword2.setError("Passwords don't match!");
                mPassword2.setError("");
            }
            else {
                Commons.currentActiveUser.setEmail(mEmail.getText().toString());
                Commons.currentActiveUser.setPassword(mPassword1.getText().toString());
                Intent intent = new Intent(SignupOneActivity.this, SignupTwoActivity.class);
                startActivity(intent);
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        }
    }
}
