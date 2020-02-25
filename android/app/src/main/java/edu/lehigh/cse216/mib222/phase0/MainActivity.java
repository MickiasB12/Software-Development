package edu.lehigh.cse216.mib222.phase0;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;


import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.BaseAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    ArrayList<Datum> mData = new ArrayList<>(); //ArrayList for all Datum objects
    private RequestQueue queue; //gets, and posts the JSON request with the backend
    String title;  //holds the value of title
    String message;  //holds the value of message
    Button back1; //navigation bar from post_layout.xml to content_main.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls the supper class of Oncreate bundle
        loadActivity(); //calls loadActivity
    }
    /*This will do all the work of onCreate method. I did this because I wanna implement the back button by calling this function recursively
    instead of transiting between other activities. This makes the work easy as you can do both the Post page, and the Get post page in
    MainActivity.java
    */
    private void loadActivity(){
        setContentView(R.layout.activity_main); //calls the activity_main.xml

        final Button buttonParse = findViewById(R.id.button_parse);  //gets the button_parse id from activity_main.xml
        final Button post = findViewById(R.id.post); //gets the post_id button
        Toolbar toolbar = findViewById(R.id.toolbar);  //toolbar for navigation
        setSupportActionBar(toolbar);
        Log.d("mib222", "Debug Message from onCreate"); //gets the log message for debugging
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        //sets OnClickListener as soon as post button is clicked
        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                postData(); //calls the postData()
                v.setVisibility(View.GONE); //hides the post button
                buttonParse.setVisibility(View.GONE); //hides the Get Message button
            }
        });
        //sets OnClickListener as soon as buttonParse button is clicked
        buttonParse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                jsonParse(); //calls the jsonParse()
                v.setVisibility(View.GONE); //hides the Get Message button
                post.setVisibility(View.GONE);  //hides the Post message

            }
        });
    }
    /*Implements the POST request to the backend. It takes the Title and message and post it to the database in Heroku.
    It creates random ID for each object inserted
    */
    private void postData() {

        setContentView(R.layout.post_layout); //switches to post_layout.xml

        final EditText titleInput = (EditText) findViewById(R.id.title); //to hold the title
        final EditText messageInput = (EditText) findViewById(R.id.message); //to hold the message post

        final Button submit = (Button) findViewById(R.id.submit); //to post the data to the backend server
        back1 = (Button) findViewById(R.id.back); //navigation bar back to activity_main.xml
        //when the back button is clicked:
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadActivity();//calls the loadActivity again and switches back to activity_main.xml
                v.setVisibility(View.GONE); //hides the back button
                submit.setVisibility(View.GONE); //hides the Submit button
            }
        });
        //when the submit button is clicked:
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleInput.getText().toString(); //gets the string from the Title field
                message = messageInput.getText().toString(); //gets the string from the Message field

                JSONObject object = new JSONObject(); //initializes a json object
                try {
                    //input your API parameters
                    object.put("mTitle", title);
                    object.put("mMessage", message);
                } catch (JSONException e) {//if error
                    Toast.makeText(getApplicationContext(), "Error posting", Toast.LENGTH_LONG).show(); //if failure
                    e.printStackTrace();
                }
                // Enter the correct url for your api service site
                String url = "https://super-deuper-epic-app.herokuapp.com/messages";
                //After thi s, it calls the Post request to heroku (Theoretically), but it doesn't work
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show(); //if successful
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error posting", Toast.LENGTH_LONG).show(); //if failure
                    }
                });
                queue.add(jsonObjectRequest); //adds the the request on RequestQueue
                loadActivity(); //go back to activity_main.xml
            }
        });
    }
    /*
    *jsonParse() is used to do the GET REQUEST from the backend and display it on the app. The app show messages,
    * it has the like and dislike button working properly.
     *  */

    private void jsonParse(){
       // String url = "https://api.myjson.com/bins/x89bs"; //the URL where we parse the JSON
        String url = "https://super-deuper-epic-app.herokuapp.com/messages";
    // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        populateListFromVolley(response); //call this method to list all the objects
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //if error
                error.printStackTrace();
            }
        });

    // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    /**
     * This method makes a list of all the messages with RecyclerView
     * @param response holds the JSONObject so we can parse the array and then parse each object from it
     * */

    private void populateListFromVolley(JSONObject response){
        final int countLikes = 0;
        try {
            JSONArray json= response.getJSONArray("mData"); //gets teh json array called mData
            for (int i = 0; i < json.length(); ++i) {
                int id = json.getJSONObject(i).getInt("mId"); //gets the object mID from mData
                String mTitle = json.getJSONObject(i).getString("mSubject"); //gets the object mTitle from mData
                String mContent = json.getJSONObject(i).getString("mMessage"); //gets the object mContent from mData
                String mCreated = json.getJSONObject(i).getString("mLikes"); //gets the object mCreated from mData
                Button like = findViewById(R.id.like); //gets the specific like button id
                Button dislike = findViewById(R.id.dislike); //gets the specific the dislike button id

                mData.add(new Datum(id, mTitle, mContent, mCreated, like, dislike)); //adds all the Datum parameters
            }
        } catch (final JSONException e) {
            Log.d("mib222", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("mib222", "Successfully parsed JSON file.");
        RecyclerView rv = (RecyclerView) findViewById(R.id.datum_list_view); //calls the recyclerView from datum_list_view id
        rv.setLayoutManager(new LinearLayoutManager(this)); //set the LAyout manager of recyclerView
        ItemListAdapter adapter = new ItemListAdapter(this, mData); //calls the ItemListAdapter
        rv.setAdapter(adapter); //adds the item's adapter to the recycler view
        //when one of fields in the Get message is clicked
        adapter.setClickListener(new ItemListAdapter.ClickListener() {
            @Override
            public void onClick(Datum d) {
                //return the Toast
                Toast.makeText(MainActivity.this,  d.mTitle + " -->" + d.mContent + " -->" + d.mCreated, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 789) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the "extra" string of data
                Toast.makeText(MainActivity.this, data.getStringExtra("result"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
            i.putExtra("label_contents", "CSE216 is the best");
            startActivityForResult(i, 789); // 789 is the number that will come back to us
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
