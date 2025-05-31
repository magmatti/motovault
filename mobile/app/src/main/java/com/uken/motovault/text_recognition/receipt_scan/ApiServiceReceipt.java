package com.uken.motovault.text_recognition.receipt_scan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceReceipt {

    @POST("process-receipt/")
    Call<ResponseModelReceipt> uploadText(@Body TextRequestReceipt textRequest);
}
