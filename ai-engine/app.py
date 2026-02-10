from flask import Flask, jsonify
from flask_cors import CORS
import pandas as pd
import requests

app = Flask(__name__)
CORS(app)

# SETTING: Where is Java living?
JAVA_BACKEND_URL = "http://localhost:8080/api/transactions"


@app.route('/predict', methods=['GET'])
def predict():
    try:
        # 1. FETCH DATA
        response = requests.get(JAVA_BACKEND_URL)
        data = response.json()

        # 🔍 DEBUG: See what we got
        print(f"DEBUG: Received data type: {type(data)}")

        # 2. FIX THE "SCALAR VALUES" ERROR
        # If Java sent a single dictionary, wrap it in a list [ ]
        if isinstance(data, dict):
            data = [data]

        # If data is completely empty, stop here
        if not data:
            return jsonify({
                "prediction": "No data available. Add transactions in Java.",
                "status": "neutral"
            })

        # 3. NOW IT IS SAFE TO LOAD INTO PANDAS
        df = pd.DataFrame(data)

        # --- MATH LOGIC ---
        current_balance = df['amount'].sum()
        expenses = df[df['amount'] < 0]

        unique_days = df['date'].nunique()
        if unique_days > 0:
            daily_burn_rate = abs(expenses['amount'].sum() / unique_days)
        else:
            daily_burn_rate = 0

        # --- PREDICTION LOGIC ---
        if daily_burn_rate == 0:
            message = "✅ Safe! You are not spending money."
            status = "safe"
            days_left = 999
        else:
            days_left = int(current_balance / daily_burn_rate)
            if days_left < 0:
                message = "⚠️ You are already in debt!"
                status = "danger"
            elif days_left < 15:
                message = f"🚨 CRITICAL: You go broke in {days_left} days!"
                status = "danger"
            else:
                message = f"✅ Good: You have {days_left} days of runway."
                status = "safe"

        return jsonify({
            "prediction": message,
            "status": status,
            "balance": round(current_balance, 2),
            "burn_rate": round(daily_burn_rate, 2)
        })

    except Exception as e:
        print(f"❌ ERROR: {e}")
        return jsonify({"prediction": f"AI Error: {str(e)}", "status": "neutral"})


if __name__ == '__main__':
    app.run(port=5000, debug=True)