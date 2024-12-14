package com.uken.motovault;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // Użyj ResponseModel jako odpowiedzi
    @POST("process-receipt/")
    Call<ResponseModel> uploadText(@Body TextRequest textRequest);
}
