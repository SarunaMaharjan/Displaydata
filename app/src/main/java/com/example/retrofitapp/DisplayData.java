package com.example.retrofitapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

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

public class DisplayData extends AppCompatActivity {



    String BASE_URL = "http://192.168.123.10:8000";

    ListView details_list;
    ListViewAdapter displayAdapter;

    ArrayList<String> question = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        details_list = (ListView) findViewById(R.id.retrieve);
        displayAdapter = new ListViewAdapter(getApplicationContext(), question, courses);
        displayData();

        details_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                Intent i = new Intent(getApplicationContext(),UpdateData.class);

                i.putExtra("question",question.get(position));
                i.putExtra("courses",courses.get(position));

                startActivity(i);

            }
        });

        details_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long ids) {
                delete(question.get(position));
                return true;
            }
        });
    }


    public void displayData() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.read api = adapter.create(AppConfig.read.class);

        api.readData(new Callback<JsonElement>() {
                         @Override
                         public void success(JsonElement result, Response response) {

                             String myResponse = result.toString();
                             Log.d("response", "" + myResponse);

                             try {
                                 JSONObject jObj = new JSONObject("myResponse");

                                 int success = jObj.getInt("success");

                                 if (success == 1) {

                                     JSONArray jsonArray = jObj.getJSONArray("questions");

                                     for (int i = 0; i < jsonArray.length(); i++) {

                                         JSONObject jo = jsonArray.getJSONObject(i);

                                         question.add(jo.getString("question"));
                                         courses.add(jo.getString("courses"));

                                     }

                                     details_list.setAdapter(displayAdapter);

                                 } else {
                                     Toast.makeText(getApplicationContext(), "No Details Found", Toast.LENGTH_SHORT).show();
                                 }
                             } catch (JSONException e) {
                                 Log.d("exception", e.toString());
                             }
                         }

                         @Override
                         public void failure(RetrofitError error) {
                             Log.d("Failure", error.toString());
                             Toast.makeText(DisplayData.this, error.toString(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void delete(String id){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.delete api = adapter.create(AppConfig.delete.class);

        api.deleteData(
                id,
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

                            if(success == 1){
                                Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                recreate();
                            } else{
                                Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(DisplayData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

}
