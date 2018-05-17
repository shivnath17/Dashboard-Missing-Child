package com.example.pratyush.jsonandrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Explore extends AppCompatActivity implements ListAdapter.OnItemClickListener{

    public static final String EXTRA_URL="imageUrl";
    public static final String EXTRA_NAME="name";
    public static final String EXTRA_DESCRIPTION="description";



    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private ArrayList<ListItem> mListItem;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        mRecyclerView=findViewById(R.id.myRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListItem=new ArrayList<>();
        mRequestQueue= Volley.newRequestQueue(this);

        parseJSON();

    }
    public void parseJSON(){
        String url="https://api.myjson.com/bins/fyov7";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("response");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        String name=jsonObject1.getString("name");
                        String description=jsonObject1.getString("description");
                        String image=jsonObject1.getString("image_url");

                        mListItem.add(new ListItem(name,description,image));
                    }
                    mListAdapter=new ListAdapter(Explore.this,mListItem);
                    mRecyclerView.setAdapter(mListAdapter);
                    mListAdapter.setOnItemClickListener(Explore.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailExplore.class);
        ListItem clickedItem=mListItem.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImage());
        detailIntent.putExtra(EXTRA_NAME,clickedItem.getName());
        detailIntent.putExtra(EXTRA_DESCRIPTION,clickedItem.getDescription());
        startActivity(detailIntent);

    }
}
