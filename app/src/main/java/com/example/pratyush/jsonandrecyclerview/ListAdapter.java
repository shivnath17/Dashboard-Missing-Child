package com.example.pratyush.jsonandrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context mContext;
    private ArrayList<ListItem> mListItem;
    public OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){

        mListener=listener;


    }

    public ListAdapter(Context mContext, ArrayList<ListItem> mListItem){
        this.mContext=mContext;
        this.mListItem=mListItem;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(mContext).inflate(R.layout.cardlist,parent, false);
        return new ListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        ListItem currentItem=mListItem.get(position);
        String image=currentItem.getImage();
        String name=currentItem.getName();
        String description=currentItem.getDescription();



        holder.name.setText(name);
        holder.description.setText(description);
        //Picasso.with(mContext).load(image).fit().centerInside().into(holder.image);
        Picasso.get().load(image).into(holder.image);



    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView name;
        public TextView description;

        public ListViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
