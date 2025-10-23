
# 🚗 MotoVault Project - Startup Guide

## 🧭 Project Overview

MotoVault is an Android application designed to store crucial data about your car in one place.

The project comprises three main components:

- **Android Mobile App**: Developed using Kotlin and Jetpack Compose.
- **Spring Boot REST API**: Handles backend operations like business logic and database data manipulation.
- **Microservices**: Modular services that handle VIN and recipt scanning functionality.

---

## 📁 Project Structure

```
motovault/
├── microservice/        # Contains individual microservices
├── mobile/              # Android application source code
├── server/              # Spring Boot REST API
└── README.md            # Project documentation
```

---

## 🚀 Running the Microservices

Each microservice is located within the `microservice/` directory. To run them:

1. **Navigate to the Microservice Directory**:

   ```bash
   cd motovault/microservice/
   ```

2. **Run each microservice**:

   ```bash
   python3 receipt_microservice.py

   python3 vin_microservice.py
   ```

---

## 📱 Running the Android Application

The Android application source code is located in the `mobile/` directory. To run the app:

1. **Open the Project in Android Studio**:

   ```bash
   cd motovault/mobile
   ```

   Then, open the project using Android Studio.

2. **Build the Project**:

   Allow Android Studio to synchronize and build the project. Ensure that all dependencies are resolved.

3. **Run the Application**:

   Connect an Android device or start an emulator, then click the "Run" button in Android Studio to install and launch the app.

4. **Configure API Endpoints**:

   Open Constants.kt file.

   ```bash
    cd  mobile/app/src/main/java/com/uken/motovault/api

   ```

   Pass your server endpoints and API keys.

   ```kotlin

    // Spring Boot REST API config
    const val REST_API_BASE_URL = ""

    // VIN Decoder API Config
    const val VIN_DECODER_BASE_URL = ""
    const val VIN_DECODER_API_KEY = ""
    const val VIN_DECODER_SECRET_KEY = ""
    const val VIN_DECODER_ACTION = "decode"

    // Microservice connection
    const val MICROSERVICE_CONNECTION_STRING = ""

   ```

---

## 🌐 Running the Spring Boot REST API

The REST API is located in the `server/` directory. To run it:

1. **Navigate to the Server Directory**:

   ```bash
   cd motovault/server
   ```

2. **Build and Run the Application**:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

3. **Access the API**:

   By default, the API will be accessible at `http://localhost:8080/`. You can test the endpoints using tools like curl or postman.

4. **API Documentation**:

   Check API docs using this link: 
    `http://localhost:8080/swagger-ui/index.html#/`.

---

## ⚙️ Additional Configuration

- **Database Setup**:

  Ensure that required database is set up and accessible. Update the `application.properties` or `application.yml` files with the correct database connection details. Note that you have to use PostgreSQL database.

- **Firebase setup**:

  1. Create new project on Firebase. 
  2. Add Google and email sign-in options.
  3. Navigate to root of the mobile's app project.
  4. Run gradle signingReport and copy SHA256 fingerprint.

  ```bash
    ./gradlew signingReport
  ``` 
  5. Paste SHA256 fingerprint to Firebase console in order to add your Android emulator to trusted devices.

- **VIN Decoder API setup**:
    1. Open `https://vindecoder.eu/` in your browser.
    2. Create new account and copy generated API keys.
    3. Paste your API Keys in `Constants.kt` file.

    ```kotlin
    const val VIN_DECODER_BASE_URL = "https://api.vindecoder.eu/3.2"
    const val VIN_DECODER_API_KEY = ""
    const val VIN_DECODER_SECRET_KEY = ""
    const val VIN_DECODER_ACTION = "decode"
    ```

---

##  👨‍💻 Contributors

* [github.com/magmatti](https://github.com/magmatti)
* [github.com/xKaiLee](https://github.com/xKaiLee)
* [github.com/Kubczor](https://github.com/Kubczor)
