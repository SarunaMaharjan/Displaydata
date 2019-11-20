package com.example.retrofitapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateData extends AppCompatActivity {

    String BASE_URL = "http://192.168.123.8:8000/";

    EditText edt_question, edt_courses;
    Button btn_update;

    String question, courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edt_question = (EditText) findViewById(R.id.question);
        edt_courses = (EditText) findViewById(R.id.courses);

        btn_update = (Button) findViewById(R.id.update);

        Intent i = getIntent();
        question = i.getStringExtra("question");
        courses = i.getStringExtra("courses");

        edt_question.setText(question);
        edt_courses.setText(courses);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update_data(question);

            }
        });

    }

   /* public void update_data(String question) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.update api = adapter.create(AppConfig.update.class);

        api.updateData(
                edt_question.getText().toString(),
                edt_courses.getText().toString(),

                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DisplayData.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(UpdateData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }*/

}
