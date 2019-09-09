package com.proptit.android.ptitme.network;

import com.proptit.android.ptitme.model.Data;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("todos")
    Single<List<Data>> getData();
}
