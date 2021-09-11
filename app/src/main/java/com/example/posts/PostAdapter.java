package com.example.posts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Post> mList;
    private PostClickListener mClickListener;

    public PostAdapter(Context ctx, List<Post> items) {
        inflater = LayoutInflater.from(ctx);
        this.mList = items;
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_posts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Post item = mList.get(position);
        holder.mEditTitle.setText(item.title);
        holder.mEditBody.setText(item.body);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(PostClickListener listener) {
        mClickListener = listener;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView mCardView;
        private TextView mEditTitle;
        private TextView mEditBody;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mEditTitle = itemView.findViewById(R.id.title_textView);
            mEditBody = itemView.findViewById(R.id.body_textView);
        }
    }
}