package com.maishapay.myshop.service;

import com.maishapay.myshop.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiServices {

    @GET("/products")
    Call<List<Product>> products(
    );

}
