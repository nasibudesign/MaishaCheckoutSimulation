package com.maishapay.myshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maishapay.checkout.MaishaPay;
import com.maishapay.myshop.service.MaishaPayServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Product> productArrayList;
    private Context context;

    public MyAdapter(List<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitem, parent, false);
        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Product currentItem = productArrayList.get(position);
        Picasso.get()
                .load(currentItem.getImage())
                .resize(200, 200)
                .centerInside()
                .into(((ViewHolder) holder).imageView);
        holder.priceText.setText(currentItem.getPrice() + " CDF");
        holder.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaishaPay.checkout((Activity) context,
                        MaishaPayServices.yourApiOptions,
                        MaishaPayServices.yourApiKey,
                        MaishaPayServices.yourGateway_mode,
                        String.valueOf(currentItem.getPrice()),
                        MaishaPay.CDF,
                        currentItem.getDescription(),
                        MaishaPayServices.yourLogo_url
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceText;
        Button payButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageShop);
            priceText = itemView.findViewById(R.id.priceText);
            payButton = itemView.findViewById(R.id.payButton);
        }
    }
}
