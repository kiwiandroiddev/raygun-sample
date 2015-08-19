package nz.co.kiwiandroiddev.raygunsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import main.java.com.mindscapehq.android.raygun4android.RaygunClient;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunUserInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map initialCustomData = new HashMap();

        initialCustomData.put("firstkey", "firstvalue");

        RaygunClient.Init(getApplicationContext()); // This sets up the client with the API key as provided in your AndroidManifest.xml
        RaygunClient.AttachExceptionHandler();      // This attaches a pre-made exception handler to catch all uncaught exceptions, and send them to Raygun

        RaygunClient.SetUserCustomData(initialCustomData);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RaygunUserInfo user = new RaygunUserInfo();
                user.FullName = "User Name";
                user.FirstName = "User";
                user.Email = "a@b.com";
                user.Uuid = "a uuid";
                user.IsAnonymous = false;

                Map tw = new HashMap();

                tw.put("secondkey", "secondvalue");

                RaygunClient.SetUser(user);
                RaygunClient.Send(new Exception("Congratulations, you have sent errors with Raygun4Android"), null, tw); // Example 1: Manual exception creation & sending

                int i = 3 / 0;                          // Example 2: A real exception will be thrown here, which will be caught & sent by RaygunClient
            }
        });
    }
}
