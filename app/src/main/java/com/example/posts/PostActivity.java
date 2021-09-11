package com.example.posts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    private TextView mTextId;
    private TextView mTextTitle;
    private TextView mTextBody;

    private Retrofit mRetrofit;
    private int mPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mPostId = extras.getInt("post_id");
        getData();
    }

    private void getData() {
        PostApi api = mRetrofit.create(PostApi.class);
        api.getOnePost(mPostId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.e("onResponse", "code: " + response.code());
                Log.e("onResponse", "message: " + response.message());
                initPost(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                Log.e("onFailure", throwable.toString());
            }
        });
    }

    private void initPost(Post item) {
        mTextId.setText(String.valueOf(item.getId()));
        mTextTitle.setText(item.title);
        mTextBody.setText(item.body);
    }

    private void initView() {
        mTextId = findViewById(R.id.text_id);
        mTextTitle = findViewById(R.id.title_textView);
        mTextBody = findViewById(R.id.body_textView);
    }

    public void Close(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }
}