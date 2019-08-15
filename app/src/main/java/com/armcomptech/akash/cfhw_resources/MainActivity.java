package com.armcomptech.akash.cfhw_resources;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.armcomptech.akash.cfhw_resources.adapter.MyArrayAdapter;
import com.armcomptech.akash.cfhw_resources.model.MyDataModel;
import com.armcomptech.akash.cfhw_resources.parser.JSONParser;
import com.armcomptech.akash.cfhw_resources.util.InternetConnection;
import com.armcomptech.akash.cfhw_resources.util.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static MainActivity instance;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;

    //to change
    String targetSheet = "Emergency_Hotlines"; //by default first one is chosen

    public String getSheet() {
        return targetSheet;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        /*
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /*
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapter(this, list);

        /*
         * Getting List and Setting List Adapter
         */
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //to change
//                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getFirstName() + " => " + list.get(position).getLastName() + " => " + list.get(position).getMiddleName(), Snackbar.LENGTH_LONG).show();
            }
        });

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Emergency_Hotlines) {
            targetSheet = "Emergency_Hotlines";
        } else if (id == R.id.Worker_Centers) {
            targetSheet = "Worker_Centers";
        } else if (id == R.id.Advocacy_Organizations) {
            targetSheet = "Advocacy_Organizations";
        } else if (id == R.id.Government_Organizations) {
            targetSheet = "Government_Organizations";
        } else if (id == R.id.Immigration) {
            targetSheet = "Immigration";
        } else if (id == R.id.Housing) {
            targetSheet = "Housing";
        } else if (id == R.id.Employment_Training_And_Workforce_Development) {
            targetSheet = "Employment_Training_And_Workforce_Development";
        } else if (id == R.id.Community_Services) {
            targetSheet = "Community_Services";
        } else if (id == R.id.Comprehensive) {
            targetSheet = "Comprehensive";
        } else if (id == R.id.Psychological_Health_And_Counseling) {
            targetSheet = "Psychological_Health_And_Counseling";
        } else if (id == R.id.Domestic_Abuse_And_Sexual_Assault) {
            targetSheet = "Domestic_Abuse_And_Sexual_Assault";
        } else if (id == R.id.Health_Insurance) {
            targetSheet = "Health_Insurance";
        } else if (id == R.id.Food_Pantries) {
            targetSheet = "Food_Pantries";
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //get the data
        list = new ArrayList<>();
        adapter = new MyArrayAdapter(this, list);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    @SuppressLint("StaticFieldLeak")
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
             * Progress Dialog for User Interaction
             */

            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Contacting UIC");
            dialog.setMessage("Fetching data");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /*
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();

            try {
                // Check Whether Its NULL
                if (jsonObject != null) {
                    // Check Length
                    if(jsonObject.length() > 0) {
                        // Getting Array named "contacts" From MAIN Json Object
                        JSONArray array;
                        if (targetSheet.equals("Emergency_Hotlines")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Emergency_Hotlines);
                        }
                        else if (targetSheet.equals("Worker_Centers")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Worker_Centers);
                        }
                        else if (targetSheet.equals("Advocacy_Organizations")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Advocacy_Organizations);
                        }
                        else if (targetSheet.equals("Government_Organizations")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Government_Organizations);
                        }
                        else if (targetSheet.equals("Immigration")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Immigration);
                        }
                        else if (targetSheet.equals("Housing")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Housing);
                        }
                        else if (targetSheet.equals("Employment_Training")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Employment_Training);
                        }
                        else if (targetSheet.equals("Substance_Abuse")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Substance_Abuse);
                        }
                        else if (targetSheet.equals("Community_Service")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Community_Service);
                        }
                        else if (targetSheet.equals("Comprehensive")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Comprehensive);
                        }
                        else if (targetSheet.equals("Psychological_Health_and_Counseling")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Psychological_Health_and_Counseling);
                        }
                        else if (targetSheet.equals("Domestic_Abuse_And_Sexual_Assault")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Domestic_Abuse_And_Sexual_Assault);
                        }
                        else if (targetSheet.equals("Health_Insurance")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Health_Insurance);
                        }
                        else if (targetSheet.equals("Food_Pantries")) {
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Food_Pantries);
                        }
                        else {
                            //default is Emergency Hotlines
                            array = jsonObject.getJSONArray(Keys.KEY_CONTACTS_Emergency_Hotlines);
                        }


                        // Check Length of Array
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                //Creating Every time New Objectvand Adding into List
                                MyDataModel model = new MyDataModel();

                                // Getting Inner Object from contacts array and From that We will get Name of that Contact

                                //to change
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String Organization = innerObject.getString(Keys.KEY_Organization);
                                String Description  = innerObject.getString(Keys.KEY_Description);
                                String Location = innerObject.getString(Keys.KEY_Location);
                                String Website = innerObject.getString(Keys.KEY_Website);
                                String Phone_Number = innerObject.getString(Keys.KEY_Phone_Number);
                                String Email = innerObject.getString(Keys.KEY_Email);

                                //Getting Object from Object "phone"
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                //to change
                                model.setOrganization(Organization);
                                model.setDescription(Description);
                                model.setLocation(Location);
                                model.setWebsite(Website);
                                model.setPhone_Number(Phone_Number);
                                model.setEmail(Email);

                                // Adding name and phone concatenation in List
                                list.add(model);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}