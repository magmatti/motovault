
import os
import uvicorn
import base64
from fastapi import FastAPI, HTTPException, File, UploadFile
from pydantic import BaseModel
import re
from google import genai
from PIL import Image
import io

# Konfiguracja Gemini API
client = genai.Client(
    api_key=os.getenv("API_KEY")
)

# Inicjalizacja API
app = FastAPI()

# Model odpowiedzi
class TextRequest(BaseModel):
    text: str

@app.post("/extract-vin/")
async def extract_vin(request: TextRequest):
    try:
        # Oczyszczanie tekstu
        text = re.sub(r"\s+", " ", request.text)

        # GenerativeAI - Rozpoznanie treści dowodu=
        response = client.models.generate_content(
        model='gemini-2.0-flash-exp', contents=f"""
        Oto tekst z dowodu rejestracyjnego: '{text}'.
        Wypisz numer VIN (17-znakowy alfanumeryczny kod) i zwróć go z caps lockiem.
        """
        )

        if not response.text:
            raise HTTPException(status_code=400, detail="Brak odpowiedzi z modelu.")

        extracted_text = response.text.strip()
        print(f"Response from Gemini: {extracted_text}")

        # Schemat do wykrycia numeru VIN
        vin_pattern = r"\b[A-HJ-NPR-Z0-9]{17}\b"  # Numer VIN (17 znaków, wyłączając I, O, Q)

        # Usuwanie niepotrzebnych spacji
        cleaned_text = re.sub(r"\s+", " ", extracted_text)

        # Szukaj numeru VIN
        vin_match = re.search(vin_pattern, cleaned_text)

        if vin_match:
            result = vin_match.group(0) # Zwraca znaleziony numer VIN
        else:
            raise HTTPException(status_code=400, detail="Nie udało się znaleźć numeru VIN w tekście dowodu rejestracyjnego.")

        # Zwrócenie wyniku
        print(f"Processed VIN: {result}")
        return {"vin": result}

    except Exception as e:
        print(f"Error occurred: {e}")
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8082)
