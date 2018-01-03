package com.example.woo.learnfitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyVideoActivity extends AppCompatActivity {
    public static final String TAG = "com.example.woo.learnfitness";
    List<Video> vList;
    private ProgressDialog pDialog;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("My Video");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        vList = new ArrayList<>();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyVideoActivity.this,UploadVideoActivity.class));

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
