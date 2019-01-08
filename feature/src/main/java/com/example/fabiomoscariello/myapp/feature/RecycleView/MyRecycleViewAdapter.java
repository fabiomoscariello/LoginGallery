package com.example.fabiomoscariello.myapp.feature.RecycleView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.net.Uri;
import com.example.fabiomoscariello.myapp.feature.R;


import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Uri> listURI;
    //Costruttore RecycleViewAdapter

    public MyRecycleViewAdapter(Context context,ArrayList<Uri>listURT) {
        this.context = context;
        this.listURI=listURT;
    }

    //ViewHolder che mi gestisce la visuale di un item del Recycle
    public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    ViewHolder(View itemView)
    {
        super(itemView);
        imageView=itemView.findViewById(R.id.ImageView_id);
    }
    }
    //Gonfia il layout della cella dall xml dell'Item quando necessario@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imageView.setImageURI(listURI.get(position));
    }

    @Override
    public int getItemCount() {
        return listURI.size();
    }
}
