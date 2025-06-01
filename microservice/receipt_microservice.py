import google.generativeai as genai
import os
import uvicorn
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import re

#przechowywanie api
genai.configure(api_key=os.getenv("API_KEY"))

#inicjalizacja api
app = FastAPI()

#model tekstowy dla danych z paragonu
class TextRequest(BaseModel):
    text: str

#przyjmowanie danych wejściowych
@app.post("/process-receipt/")
async def process_receipt(request: TextRequest):
    try:

        #oczyszczanie tekstu
        text =re.sub(r"\s+", " ", request.text)
        #print(f"Received text: {text}")
        #Gemini rozpoznaje
        model = genai.GenerativeModel("gemini-1.5-flash")
        prompt = f"""
        Oto tekst z paragonu: '{text}'.
        Wypisz łączną sumę i znajdz datę i przekonwertuj ją na format "YYYY-MM-DD"
        """
        response = model.generate_content(prompt)

        if not response.text:
            raise HTTPException(status_code=400, detail="Brak odpowiedzi z modelu.")

        extracted_text = response.text.strip()
        print(f"Response from Gemini: {extracted_text}")

        #schemat dla sumy i daty
        sum_pattern = r"(\d+[\.,]?\d{2})\s"  #format dla pieniedzy (pln)
        date_pattern = r"\b(\d{4})[-./](\d{2})[-./](\d{2})\b"  #przyklawode formaty daty 22.03.2023, 22/03/2023, 22-03-2023

        #usuwanie niepotrzebnych spacji
        cleaned_text = re.sub(r"\s+", " ", extracted_text)

        #szukaj sumy i daty
        sum_match = re.search(sum_pattern, cleaned_text)
        date_match = re.search(date_pattern, cleaned_text)


        if sum_match and date_match:
            result = [sum_match.group(1), date_match.group(0)]  #zwraca to co znalazl
        else:
            #petla awaryjna w sytuacji, gdy nie znajdziemy daty
            num_paragon_pattern = r"\d{10}"  #numer paragonu
            num_paragon_match = re.search(num_paragon_pattern, cleaned_text)
            if num_paragon_match:
                num_paragon = num_paragon_match.group(0)
                #pierwsze 6 cyfr w numerze paragonu to data w formacie ddmmyy
                date_from_paragon = num_paragon[:6]
                date_from_paragon = f"{date_from_paragon[:2]}.{date_from_paragon[2:4]}.{date_from_paragon[4:]}"
                result = [sum_match.group(1), date_from_paragon]
            else:
                raise HTTPException(status_code=400, detail="Nie udało się znaleźć sumy lub daty w tekście paragonu.")

        #zwrocenei wyniku
        print(f"Processed result: {result}")
        return {"result": result}

    except Exception as e:
        print(f"Error occurred: {e}")
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
