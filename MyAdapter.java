package com.example.rimi.khanasurfing;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rimmi Biswas on 3/15/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<ListItem> items;
    AlertDialog.Builder ad;
Activity activity;
    public MyAdapter( String[] names, String[] prices, String[] contacts, String[] address,String[] desc,Activity activity)// Bitmap[] images)
    {
        super();
        this.activity=activity;
        items = new ArrayList<ListItem>();
        for (int i = 0; i < names.length; i++) {
            ListItem item = new ListItem();
            item.setName(names[i]);
            item.setPrice(prices[i]);
            item.setContact(contacts[i]);
            item.setAddress(address[i]);
            item.setDesc(desc[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_crd_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list = items.get(position);
        //holder.imageView.setImageBitmap(list.getImage());
        holder.textViewName.setText(list.getName());
        holder.textViewUrl.setText(list.getPrice());
        holder.textViewCon.setText(list.getContact());
        holder.textViewAdr.setText(list.getAddress());
        //holder.textViewDesc.setText(list.getDesc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView textViewName;
        public TextView textViewUrl;
        public TextView textViewCon;
       public TextView textViewAdr;
        public TextView textViewDesc;
        public ViewHolder(View itemView) {
            super(itemView);


            textViewName = (TextView) itemView.findViewById(R.id.person_name);
            textViewUrl = (TextView) itemView.findViewById(R.id.price);
            textViewCon = (TextView) itemView.findViewById(R.id.number);
            textViewAdr=(TextView)itemView.findViewById(R.id.adr);
            textViewDesc=(TextView)itemView.findViewById(R.id.desc);
            textViewCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = textViewCon.getText().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    v.getContext().startActivity(callIntent);
                }
            });
            textViewDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int p=getAdapterPosition();
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                    LayoutInflater inflater =activity.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.desc_window, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setCancelable(false);
                    final AlertDialog alert = dialogBuilder.create();

                    TextView desc=(TextView)dialogView.findViewById(R.id.descp);
                    ListItem list=items.get(p);
                    desc.setText(list.getDesc());

                    Button ok=(Button)dialogView.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });

                    alert.show();
                }
            });
        }

    }

}


