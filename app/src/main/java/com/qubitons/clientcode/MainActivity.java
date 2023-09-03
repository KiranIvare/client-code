package com.qubitons.clientcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qubitons.clientcode.utils.HttpClientUtils;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;

    private HttpClientUtils httpClientUtils = new HttpClientUtils();

    private Logger log = Logger.getLogger(String.valueOf(MainActivity.class));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //mTextViewResult = findViewById(R.id.client_code);
        final EditText editTextClientCode = findViewById(R.id.client_code);
        final Button submitButton = findViewById(R.id.submitbtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the client code entered by the user
                String clientCode = editTextClientCode.getText().toString().trim();

                if (isValidClientCode(clientCode)) {
                    // Construct the URL with the client code
                    String url = "https://www.qubitons.com/clients/" + clientCode;
                    Map<String, Object> response;
                    try {
                        response = httpClientUtils.performHttpCallAndReturnMap(url);
                    } catch (IOException e) {
                        response = null;
                    }
                    if(response != null) {
                        String status = (String) response.get("status");
                        if(status.equals("success")) {
                            // Open the URL in the default web browser
                            Map<String, Object> map = (Map<String, Object>) response.get("message");
                            log.info("Response " + map);
                            Intent browserIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                            browserIntent.putExtra("client_url", (String) map.get("client_url"));
                            startActivity(browserIntent);
                        } else {
                            //In case of failure
                            Map<String, Object> data = (Map<String, Object>) response.get("message");
                            if(data != null) {
                                String message = (String) data.get("data");
                                log.info("Error Response" +  message);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Response", Toast.LENGTH_SHORT);
                    }

                } else {
                    // Handle invalid client code, e.g., show an error message
                    mTextViewResult.setText("Invalid Client Code");
                }
            }
        });
    }

    // Validate the client code (customize this according to your validation criteria)
    private boolean isValidClientCode(String clientCode) {
        // For example, check if the client code is not empty
        return !clientCode.isEmpty();
    }
}
