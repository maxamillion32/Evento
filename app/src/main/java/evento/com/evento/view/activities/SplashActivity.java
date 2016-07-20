package evento.com.evento.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import evento.com.evento.R;
import evento.com.evento.controllers.UserController;
import evento.com.evento.utils.Commons;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Commons.firebaseAuth.getCurrentUser()!=null){
            UserController.userIsLoggedIn(SplashActivity.this, MainActivity.class);
        }else {
            Commons.changeActivity(SplashActivity.this,LoginActivity.class);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
