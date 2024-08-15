package com.example.journalapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //Variables

    private Context context;
    private List<Journal> journalList;

    public MyAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Journal currentJournal = journalList.get(position);

        holder.title.setText(currentJournal.getTitle());
        holder.thoughts.setText(currentJournal.getThoughts());
        holder.naame.setText(currentJournal.getUserName());

        String imageUrl = currentJournal.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(currentJournal.getTimeAdded().getSeconds()*1000);
        holder.dateAdded.setText(timeAgo);

        //Glide Library to display image

        Glide.with(context).load(imageUrl).fitCenter().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }



    //View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, thoughts, dateAdded, naame;
        public ImageView image, shareButton;
        public String userId, username;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            naame = itemView.findViewById(R.id.journal_row_username);
            image = itemView.findViewById(R.id.image_journal_list);
            shareButton = itemView.findViewById(R.id.journal_row_share);


            shareButton.setOnClickListener(v -> {
                shareContent("Check out this amazing post!"); // Replace with your content
            });


        }
        private void shareContent(String content) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, content); // For text content
            shareIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(shareIntent, "Share via")); // For sharing text

        }

    }
}
