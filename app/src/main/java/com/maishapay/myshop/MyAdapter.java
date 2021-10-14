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
import java.util.Currency;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Product> productArrayList;
    private Context context;
    private String currency;

    public MyAdapter(List<Product> productArrayList, Context context, String currency) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.currency = currency;
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
        String price = currentItem.getPrice() + " " + currency;
        holder.priceText.setText(price);
        holder.nameText.setText(currentItem.getTitle());
        holder.payButton.setOnClickListener(v -> MaishaPay.checkout((Activity) context,
                "null",
                MaishaPayServices.yourApiKey,
                MaishaPayServices.yourGateway_mode,
                String.valueOf(currentItem.getPrice()),
                MaishaPay.CDF,
                currentItem.getDescription(),
                MaishaPayServices.yourLogo_url
        ));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceText, nameText;
        Button payButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageShop);
            priceText = itemView.findViewById(R.id.priceText);
            nameText = itemView.findViewById(R.id.nameText);
            payButton = itemView.findViewById(R.id.payButton);
        }
    }
}
