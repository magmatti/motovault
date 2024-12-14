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

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TextRecognitionActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvResponse;
    private Uri selectedImageUri;

    // Launchery do obsługi galerii i kamery
    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    // Obsługa galerii
                    if (data != null && data.getData() != null) {
                        selectedImageUri = data.getData(); // Obraz z galerii
                    }
                    // Obsługa kamery
                    else if (selectedImageUri != null) {
                        // Jeśli URI zostało wcześniej ustawione w pickImageCamera()
                        Log.d("TAG", "Image captured from camera: " + selectedImageUri);
                    }
                    imageView.setImageURI(selectedImageUri); // Wyświetlenie obrazu
                } else {
                    Toast.makeText(this, "Operacja anulowana", Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnChooseFromGallery = findViewById(R.id.btnChooseFromGallery);
        Button btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);
        tvResponse = findViewById(R.id.tvResponse);

        // Obsługa przycisku "Zrób zdjęcie"
        btnTakePhoto.setOnClickListener(v -> takePhoto());

        // Obsługa przycisku "Wybierz z galerii"
        btnChooseFromGallery.setOnClickListener(v -> pickImageFromGallery());

        // Obsługa przycisku "Wyślij do mikroserwisu"
        btnUpload.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                recognizeTextFromImage(selectedImageUri);
            } else {
                Toast.makeText(this, "Wybierz obraz przed wysłaniem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Otwórz galerię przy użyciu GetContent
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent); // Użyj wspólnego launchera
    }


    // Obsługa wybranego obrazu
    private void handleImageSelection(Uri uri) {
        if (uri != null) {
            selectedImageUri = uri;
            try {
                // Wyświetl obraz w ImageView
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Błąd podczas wczytywania obrazu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Uruchom aparat do robienia zdjęcia
    private void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");

        selectedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        activityResultLauncher.launch(intent); // Użyj wspólnego launchera
    }



    // Rozpoznawanie tekstu z obrazu
    private void recognizeTextFromImage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Tworzenie InputImage z bitmapy
            InputImage image = InputImage.fromBitmap(bitmap, 0);

            // Inicjalizacja rozpoznawania tekstu z TextRecognizerOptions
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            recognizer.process(image)
                    .addOnSuccessListener(text -> {
                        // Odbierz rozpoznany tekst
                        String recognizedText = text.getText();
                        // Teraz nie wyświetlamy tekstu, ale wysyłamy go do mikroserwisu
                        uploadText(new TextRequest(recognizedText));
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        Toast.makeText(this, "Błąd rozpoznawania tekstu", Toast.LENGTH_SHORT).show();
                    });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd podczas przygotowywania obrazu", Toast.LENGTH_SHORT).show();
        }
    }

    // Wysyłanie rozpoznanego tekstu do serwera
    private void uploadText(TextRequest textRequest) {
        // Konfiguracja Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://57.128.197.7:8000/") // Adres lokalnego serwera
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        // Wysłanie obiektu TextRequest
        Call<ResponseModel> call = service.uploadText(textRequest);

        // Wykonywanie żądania w tle
        new Thread(() -> {
            try {
                Response<ResponseModel> response = call.execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        ResponseModel responseModel = response.body();
                        if (responseModel != null) {
                            // Przetwarzamy odpowiedź JSON z serwera i wyświetlamy tylko dane
                            String suma = responseModel.getResult().get(0);
                            String data = responseModel.getResult().get(1);
                            // Wyświetlamy tylko odpowiedź w formie JSON (suma, data)
                            tvResponse.setText("Suma: " + suma + "\nData: " + data);
                        }
                    } else {
                        tvResponse.setText("Błąd serwera: " + response.code());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Błąd połączenia z serwerem", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
