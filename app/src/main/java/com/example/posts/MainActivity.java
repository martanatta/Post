package com.example.posts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Post");
        initView();

        mAdapter = new PostAdapter(this, mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        PostClickListener listener = new PostClickListener() {
            @Override
            public void onClick(int position) {
                Post item = mItems.get(position);

                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                intent.putExtra("post_id", item.getId());
                startActivity(intent);
            }
        };

        mAdapter.setOnItemClickListener(listener);
        getData();
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi api = retrofit.create(PostApi.class);
        api.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.e("onResponse", "code: " + response.code());
                Log.e("onResponse", "string: " + response.toString());
                initData(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("onResponse", t.toString());
            }
        });
    }

    private void initData(List<Post> items) {
        mItems.clear();
        mItems.addAll(items);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    public void addPost(View view) {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }
}