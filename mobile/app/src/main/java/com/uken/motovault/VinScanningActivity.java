package com.uken.motovault;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.uken.motovault.api.Constants;
import com.uken.motovault.text_recognition.vin_scan.ApiServiceVin;
import com.uken.motovault.text_recognition.vin_scan.ResponseModelVin;
import com.uken.motovault.text_recognition.vin_scan.TextRequestVin;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VinScanningActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvResponse;
    private Uri selectedImageUri;
    private final String TAG = "VinScanningActivity";

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                        }
                        else if (selectedImageUri != null) {
                            Log.d(TAG, "Image captured from camera: " + selectedImageUri);
                        }
                        imageView.setImageURI(selectedImageUri);
                    } else {
                        Toast.makeText(this, "Operacja anulowana", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnChooseFromGallery = findViewById(R.id.btnChooseFromGallery);
        Button btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);
        tvResponse = findViewById(R.id.tvResponse);

        btnTakePhoto.setOnClickListener(v -> takePhoto());
        btnChooseFromGallery.setOnClickListener(v -> pickImageFromGallery());

        btnUpload.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                recognizeTextFromImage(selectedImageUri);
            } else {
                Toast.makeText(this,
                        "Wybierz obraz przed wysłaniem",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    private void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");

        selectedImageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        activityResultLauncher.launch(intent);
    }

    private void recognizeTextFromImage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            InputImage image = InputImage.fromBitmap(bitmap, 0);
            TextRecognizer recognizer = TextRecognition.getClient(
                    TextRecognizerOptions.DEFAULT_OPTIONS);

            recognizer.process(image)
                    .addOnSuccessListener(text -> {
                        String recognizedText = text.getText();
                        uploadText(new TextRequestVin(recognizedText));
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        Toast.makeText(this, "Błąd rozpoznawania tekstu",
                                Toast.LENGTH_SHORT).show();
                    });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd podczas przygotowywania obrazu",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadText(TextRequestVin textRequest) {
        String connectionString = Constants.MICROSERVICE_CONNECTION_STRING;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectionString)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceVin service = retrofit.create(ApiServiceVin.class);
        Call<ResponseModelVin> call = service.uploadText(textRequest);

        new Thread(() -> {
            try {
                Response<ResponseModelVin> response = call.execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        ResponseModelVin responseModel = response.body();
                        if (responseModel != null) {
                            String vin = responseModel.getVin();
                            passRecognizedData(vin);
                            tvResponse.setText("vin: " + vin);
                        }
                    } else {
                        tvResponse.setText("Błąd serwera: " + response.code());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(
                    this,
                    "Błąd połączenia z serwerem",
                    Toast.LENGTH_SHORT
                ).show());
            }
        }).start();
    }

    private void passRecognizedData(String vin) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("vin", vin);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}