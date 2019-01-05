package emsi.saas.pfa.pfa_saas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pwd;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*email=(EditText) findViewById(R.id.email);
        pwd=(EditText) findViewById(R.id.pwd);*/
        btn=(Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllAssembles.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Logg.d();
        sendFcmRegistrationToken();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logg.d();
    }

    private void sendFcmRegistrationToken() {
        Intent intent = new Intent(this, FcmTokenRegistrationService.class);
        startService(intent);
    }

    private void checkGoogleApiAvailability() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (resultCode == ConnectionResult.SUCCESS) {
                Logg.d("GoogleApi is available");
            } else {
                apiAvailability.getErrorDialog(this, resultCode, 1).show();
            }
        }
    }
}
