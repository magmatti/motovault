package com.uken.motovault.text_recognition.vin_scan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceVin {

    @POST("extract-vin/")
    Call<ResponseModelVin> uploadText(@Body TextRequestVin textRequest);
}
