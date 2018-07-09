package com.sageart.spartan.spartan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText emailBox, nameBox, passwordBox, password_conf;
    Button signupButton;
    String URL = "http://gentle-garden-55289.herokuapp.com/api/register";
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupButton = (Button) findViewById(R.id.signup_button);
        emailBox = (EditText) findViewById(R.id.editText);
        nameBox = (EditText) findViewById(R.id.editText2);
        passwordBox = (EditText) findViewById(R.id.editText3);
        password_conf = (EditText) findViewById(R.id.editText4);

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        JSONObject response = new JSONObject();
                        try {
                            response = new JSONObject(s);
                            user_name = response.getString("user_name");
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        Toast.makeText(signup.this, s , Toast.LENGTH_LONG).show();
                        if(user_name != ""){
                            Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(signup.this,LoginActivity.class));
                        }
                        else{
                            Toast.makeText(signup.this, "Signup Failed. Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError){
                        Toast.makeText(signup.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email", emailBox.getText().toString());
                        parameters.put("password", passwordBox.getText().toString());
                        parameters.put("name", nameBox.getText().toString());
                        parameters.put("c_password", password_conf.getText().toString());

                        return parameters;
                    }
                };
                RequestQueue rQueue = Volley.newRequestQueue(signup.this);
                rQueue.add(request);
                request.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            }
        });

    }
}
