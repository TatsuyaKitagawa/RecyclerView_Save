package com.example.tatsuya.recyclerview_save;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tatsuya on 2017/04/03.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    public View.OnClickListener onItemClickListener;
    ArrayList<ListItem> datalist;

    public ListAdapter(Context context, ArrayList<ListItem> listItems){
        datalist=listItems;
        layoutInflater=layoutInflater.from(context);
    }

    public void setItemListClickListener(View.OnClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.list_item,parent,false);
        if(onItemClickListener !=null){
            view.setOnClickListener(onItemClickListener);
        }
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem data=datalist.get(position);
        holder.textView.setText(data.getTitle());
    }



    @Override
    public int getItemCount() {
        return datalist.size();
    }

    final public void removeAtPosition(int positon){
        if(positon<datalist.size()){
            datalist.remove(positon);
            notifyItemRemoved(positon);
        }

    }

    final public void  move(int fromPostion,int toPostion){
        final ListItem item=datalist.get(fromPostion);
        datalist.remove(fromPostion);
        datalist.add(toPostion,item);
        notifyItemMoved(fromPostion,toPostion);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView){
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.list_name);
        }
    }
}
