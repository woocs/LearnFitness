package com.example.woo.learnfitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "com.example.woo.learnfitness";
    List<Video> vList;
    private ProgressDialog pDialog;
    RequestQueue queue;
    String Id;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(R.string.title_home);
                    transaction.replace(R.id.content, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_categories:
                    setTitle(R.string.title_categories);
                    transaction.replace(R.id.content, new CategoriesFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    setTitle(R.string.title_favorite);
                    transaction.replace(R.id.content, new FavoriteFragment()).commit();
                    return true;
                case R.id.navigation_account_box:
                    setTitle(R.string.title_account_box);
                    transaction.replace(R.id.content, new ProfileFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        setTitle(R.string.title_home);
        transaction.replace(R.id.content, new HomeFragment()).commit();

        pDialog = new ProgressDialog(this);
        vList = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Id = preferences.getString("userId", null);

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void downloadVideo(Context context, String url) {

        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            vList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject videoResponse = (JSONObject) response.get(i);
                                String viewvideo = videoResponse.getString("video");
                                String title = videoResponse.getString("title");
                                String categories = videoResponse.getString("categories");
                                String favorite = videoResponse.getString("favorite");
                                String likes = videoResponse.getString("likes");
                                String user_id = videoResponse.getString("user_id");
                                Video video = new Video(viewvideo, title, categories, favorite, likes, user_id);
                                vList.add(video);
                            }
                            loadVideo();
                        } catch (Exception e) {

                        }

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        jsonObjectRequest.setTag(TAG);

        queue.add(jsonObjectRequest);
    }

    private void loadVideo() {
        final VideoAdapter adapter = new VideoAdapter(this, R.layout.home_video, vList);
        ListView listViewVideo = (ListView) findViewById(R.id.listViewVideo);
        listViewVideo.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       downloadVideo(getApplicationContext(), getString(R.string.get_video_url));
    }

}
