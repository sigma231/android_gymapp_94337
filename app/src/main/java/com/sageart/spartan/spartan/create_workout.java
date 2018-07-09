package com.sageart.spartan.spartan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class create_workout extends AppCompatActivity {
    Calendar myCalendar = Calendar.getInstance();
    EditText editText;
    EditText editText2;
    EditText editText3;
    Button create_workout_button;
    DatePickerDialog.OnDateSetListener date;
    String URL = "http://gentle-garden-55289.herokuapp.com/api/createWorkout";
    String user_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        editText= (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences",
                MODE_PRIVATE);
        user_id = preferences.getString("user_id", "null");
        Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();

        create_workout_button = (Button) findViewById(R.id.create_workout_button);
        create_workout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));




                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        JSONObject response = new JSONObject();
                        int user_id = -1;
                        try {
                            response = new JSONObject(s);

                            user_id = response.getInt("user_id");
                            String user = Integer.toString(user_id);
                            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_id", user);
                            editor.commit();


                        }catch (Exception e){
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError){
                        Toast.makeText(create_workout.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();

                        parameters.put("date", editText.getText().toString());
                        parameters.put("type", editText2.getText().toString());
                        parameters.put("sets", editText3.getText().toString());
                        parameters.put("trainer_id", "1");
                        parameters.put("user_id", user_id);
                        return parameters;
                    }
                };
                RequestQueue rQueue = Volley.newRequestQueue(create_workout.this);
                rQueue.add(request);
                request.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            }
        });

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }


}
