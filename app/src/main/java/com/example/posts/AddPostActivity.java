package com.example.posts;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPostActivity extends AppCompatActivity {
    private TextInputEditText mEditTitle;
    private TextInputEditText mEditBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initView();
    }

    public void add(View view) {
        String title = mEditTitle.getText().toString();
        String body = mEditBody.getText().toString();

        Post post = new Post();
        post.title = title;
        post.body = body;

        if (TextUtils.isEmpty(mEditTitle.getText().toString())) {
            mEditTitle.requestFocus();
            mEditTitle.setError("Напишите заголовок");
            return;
        }
        if (TextUtils.isEmpty(mEditBody.getText().toString())) {
            mEditBody.requestFocus();
            mEditBody.setError("Напишите текст");
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi api = retrofit.create(PostApi.class);

        api.sendPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.e("onResponse", "code: " + response.code());
                Log.e("onResponse", "message: " + response.message());
                if (response.code() == 200) {
                    Toast.makeText(AddPostActivity.this, " Пост добавлен", Toast.LENGTH_SHORT).show();
                    AddPostActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                Log.e("onFailure", throwable.toString());
            }
        });
        Toast.makeText(AddPostActivity.this, "Пост добавлен", Toast.LENGTH_SHORT).show();
        AddPostActivity.this.finish();
    }

    private void initView() {
        mEditTitle = findViewById(R.id.title_editText);
        mEditBody = findViewById(R.id.body_editText);
    }
}