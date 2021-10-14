package com.maishapay.myshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maishapay.checkout.MaishaPay;

import com.maishapay.myshop.service.ApiServices;
import com.maishapay.myshop.service.AppFactory;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<Product> productList = new ArrayList<>();
    MyAdapter adapter;
    TextView textViewError;
    ProgressBar progressDialog;
    Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewError = findViewById(R.id.errorText);
        _button = findViewById(R.id.refreshBtn);
        progressDialog = findViewById(R.id.progressLoadingDialog);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ApiServices apiServices = AppFactory.getRetrofit().create(ApiServices.class);
        Call<List<Product>> call = apiServices.products();
        progressDialog.setVisibility(View.VISIBLE);
        textViewError.setText(R.string.loading);
        _button.setVisibility(View.GONE);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,@NonNull Response<List<Product>> response) {
                progressDialog.setVisibility(View.VISIBLE);
                textViewError.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    progressDialog.setVisibility(View.GONE);
                    textViewError.setVisibility(View.GONE);
                    productList = response.body();
                    adapter = new MyAdapter(productList, MainActivity.this, MaishaPay.USD);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerView.setAdapter(adapter);
                }else {
                    progressDialog.setVisibility(View.GONE);
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText(R.string.error_happened);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull Throwable t) {
                progressDialog.setVisibility(View.GONE);
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText(R.string.error_happened);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MaishaPay.maishaPayCheckoutActivityRequestCode) {
            if (resultCode == MaishaPay.checkoutSuccess) {
                Log.e("succes", MaishaPay.checkoutSuccess + "");
                myAlert("succes","Achat effectuer",SweetAlertDialog.SUCCESS_TYPE);
            } else if (resultCode == MaishaPay.checkoutCancel) {
                Log.e("cancel", MaishaPay.checkoutCancel + "");
                myAlert("Annuler","Achat Annuler",SweetAlertDialog.WARNING_TYPE);
            } else if (resultCode == MaishaPay.checkoutFailure) {
                Log.e("failure", MaishaPay.checkoutFailure + "");
                myAlert("Echec","Une erreur est survenue",SweetAlertDialog.ERROR_TYPE);
            } else {
                Log.e("unknown", "unknown error");
            }
        } else {
            Log.e("unkown request code", "uknown");
            Toast.makeText(this, "autre resultat obtenue", Toast.LENGTH_SHORT).show();
        }

    }


    private void myAlert(String title, String msg, int type) {
        new SweetAlertDialog(MainActivity.this, type)
                .setContentText(msg)
                .setTitleText(title)
                .show();
    }
}