import uuid
import numpy as np
import pandas as pd
import pyrebase
from diabetes_model import model


# CONNECTING FIREBASE TO PYTHON
firebaseConfig = {
    "apiKey": "AIzaSyDvcv6TG1E-IxUJWtcEDHRy0vDomFYd_YI",
    "authDomain": "medicheckv3.firebaseapp.com",
    "databaseURL": "https://medicheckv3-default-rtdb.firebaseio.com",
    "projectId": "medicheckv3",
    "storageBucket": "medicheckv3.appspot.com",
    "messagingSenderId": "797392677036",
    "appId": "1:797392677036:web:807322344187de73c75709",
    "measurementId": "G-QP4JH83Z7C",
}

# Connect to Firebase
firebase = pyrebase.initialize_app(firebaseConfig)

# Create reference to Firebase
db = firebase.database()

# Reference To Auth Service
auth = firebase.auth()

# Read data from Firebase
report = db.child("DiabetesMedicalReport").get()

# print(report.val())
for report in report.each():
    dict_report = report.val()
    # print(dict_report)
    res = list(dict_report.values())

# print(res)
fb_age = res[0]
fb_bmi = res[1]
fb_bp = res[2]
fb_dpf = res[3]
fb_glucose = res[4]
fb_insulin = res[5]
fb_pregnancies = res[6]
fb_skinThickness = res[7]

# Make Predictions Based on Input
new_input = [[float(fb_pregnancies), float(fb_glucose), float(fb_bp), float(fb_skinThickness), float(fb_insulin),
              float(fb_bmi), float(fb_dpf), float(fb_age)]]
new_output = model.predict(new_input)
# print(new_output)
if new_output == 0:
    result = "Unlikely."
    print(result)
    db.push(result)
else:
    result = "Likely."
    print(result)
    db.push(result)
