import numpy as np
import pandas as pd
import pyrebase
import time
import datetime
from heart_disease_model import model

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
report = db.child("HeartDiseaseMedicalReport").order_by_child("ts").get()

uid_list = []
for item in report:
    uid = item.key()
    uid_list.append(uid)

# print(uid_list)

for report in report.each():
    dict_report = report.val()
    # print(dict_report)
    res = list(dict_report.values())

# print(res)
fb_age = res[0]
fb_ca = res[1]
fb_chol = res[2]
fb_cp = res[3]
fb_exang = res[4]
fb_fbs = res[5]
fb_oldpeak = res[6]
fb_restecg = res[7]
fb_slope = res[8]
fb_thal = res[9]
fb_thalach = res[10]
fb_trestbps = res[11]

# Make Predictions Based on Input
new_input = [[float(fb_age), float(fb_cp), float(fb_trestbps), float(fb_chol), float(fb_fbs),
              float(fb_restecg), float(fb_thalach), float(fb_exang), float(fb_oldpeak), float(fb_slope), float(fb_ca),
              float(fb_thal)]]
new_output = model.predict(new_input)
# print(new_output)
if new_output == 0:
    result = "UNLIKELY"
    db.child("HeartDiseaseResult").child(uid_list[-1]).set(result)
    print(result)
    # print(uid_list[-1])
else:
    result = "LIKELY"
    db.child("HeartDiseaseResult").child(uid_list[-1]).set(result)
    print(result)
    # print(uid_list[-1])
