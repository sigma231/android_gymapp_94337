package com.sageart.spartan.spartan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    EditText emailBox, passwordBox;
    Button loginButton;
    TextView registerLink;
    String URL = "http://10.0.2.2:8000/api/login";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailBox = (EditText) findViewById(R.id.editText);
        passwordBox = (EditText) findViewById(R.id.editText2);
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String s) {
//                            Toast.makeText(LoginActivity.this, s , Toast.LENGTH_LONG).show();
//                            if(s.equals("true")){
////                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                            }
//                            else{
//                                Toast.makeText(LoginActivity.this, "Incorrect Details", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }, new Response.ErrorListener(){
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError){
//                            Toast.makeText(LoginActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
//                        }
//                    })
                    {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> parameters = new HashMap<String, String>();
//                            parameters.put("email", emailBox.getText().toString());
//                            parameters.put("password", passwordBox.getText().toString());
//                            return parameters;
//                        }
                };
//                    RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
//                    rQueue.add(request);
//                    request.setRetryPolicy(new DefaultRetryPolicy( 10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            }
        });

    }

}

