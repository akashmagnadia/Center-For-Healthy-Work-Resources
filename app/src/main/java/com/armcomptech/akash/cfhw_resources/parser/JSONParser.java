package com.armcomptech.akash.cfhw_resources.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.armcomptech.akash.cfhw_resources.MainActivity;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class JSONParser {

    //to change sheet number or name
    private static final String MAIN_URL_Emergency_Hotlines = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1J9VOEeiBjkvw8s4mtx8xj3d0unSFzdmnDuOoV5pQ8Io&sheet=Emergency_Hotlines";
    private static final String MAIN_URL_Worker_Centers = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Worker_Centers";
    private static final String MAIN_URL_Advocacy_Organizations = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Advocacy_Organizations";
    private static final String MAIN_URL_Government_Organizations = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Government_Organizations";
    private static final String MAIN_URL_Immigration = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Immigration";
    private static final String MAIN_URL_Housing = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Housing";
    private static final String MAIN_URL_Employment_Training = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Employment_Training ";
    private static final String MAIN_URL_Substance_Abuse = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Substance_Abuse";
    private static final String MAIN_URL_Community_Service = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Community_Service";
    private static final String MAIN_URL_Comprehensive = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Comprehensive";
    private static final String MAIN_URL_Psychological_Health_and_Counseling = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Psychological_Health_and_Counseling";
    private static final String MAIN_URL_Domestic_Abuse_And_Sexual_Assault = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Domestic_Abuse_And_Sexual_Assault";
    private static final String MAIN_URL_Health_Insurance = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Health_Insurance";
    private static final String MAIN_URL_Food_Pantries = "https://script.google.com/macros/s/AKfycbxOnnZ9GLKmqHy2rdvoKUutLWvFn4XmvtmReNxkDPPhQ1cyMME/exec?id=1oywEIsjhgG2QCc8llbFv-mO0JpjUFKVdtGi8d9hjn5k&sheet=Food_Pantries";

    public static final String TAG = "TAG";

    private static final String KEY_USER_ID = "user_id";

    private static Response response;

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();

            if (MainActivity.getInstance().getSheet() .equals("Emergency_Hotlines")){
                Request request = new Request.Builder().url(MAIN_URL_Emergency_Hotlines).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Worker_Centers")) {
                Request request = new Request.Builder().url(MAIN_URL_Worker_Centers).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Advocacy_Organizations")) {
                Request request = new Request.Builder().url(MAIN_URL_Advocacy_Organizations).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Government_Organizations")) {
                Request request = new Request.Builder().url(MAIN_URL_Government_Organizations).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Immigration")) {
                Request request = new Request.Builder().url(MAIN_URL_Immigration).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Housing")) {
                Request request = new Request.Builder().url(MAIN_URL_Housing).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Employment_Training")) {
                Request request = new Request.Builder().url(MAIN_URL_Employment_Training).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Substance_Abuse")) {
                Request request = new Request.Builder().url(MAIN_URL_Substance_Abuse).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Community_Service")) {
                Request request = new Request.Builder().url(MAIN_URL_Community_Service).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Comprehensive")) {
                Request request = new Request.Builder().url(MAIN_URL_Comprehensive).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Psychological_Health_and_Counseling")) {
                Request request = new Request.Builder().url(MAIN_URL_Psychological_Health_and_Counseling).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Domestic_Abuse_And_Sexual_Assault")) {
                Request request = new Request.Builder().url(MAIN_URL_Domestic_Abuse_And_Sexual_Assault).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Health_Insurance")) {
                Request request = new Request.Builder().url(MAIN_URL_Health_Insurance).build();
                response = client.newCall(request).execute();
            }
            else if (MainActivity.getInstance().getSheet() .equals("Food_Pantries")) {
                Request request = new Request.Builder().url(MAIN_URL_Food_Pantries).build();
                response = client.newCall(request).execute();
            }
            else {
                //by default go to Emergency Hotlines page
                Request request = new Request.Builder().url(MAIN_URL_Emergency_Hotlines).build();
                response = client.newCall(request).execute();
            }

            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject getDataById(int userId) {

        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(KEY_USER_ID, Integer.toString(userId))
                    .build();

            Request request = new Request.Builder()
                    .url(MAIN_URL_Emergency_Hotlines)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        } catch (IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}