import numpy as np
import pandas as pd
import pyrebase
import time
import datetime
from bcancer_model import model

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
report = db.child("BCancerMedicalReport").order_by_child("ts").get()

uid_list = []
for item in report:
    uid = item.key()
    uid_list.append(uid)

# print(uid_list)

for report in report.each():
    dict_report = report.val()
    print(dict_report)
    res = list(dict_report.values())

# print(res)
fb_radius = res[2]
fb_texture = res[4]
fb_perimeter = res[1]
fb_area = res[0]
fb_smoothness = res[3]


# Make Predictions Based on Input
new_input = [[float(fb_radius), float(fb_texture), float(fb_perimeter), float(fb_area), float(fb_smoothness)]]
new_output = model.predict(new_input)

if new_output == 0:
    result = "UNLIKELY"
    db.child("BCancerResult").child(uid_list[-1]).set(result)
    print(result)
    # print(uid_list[-1])
else:
    result = "LIKELY"
    db.child("BCancerResult").child(uid_list[-1]).set(result)
    print(result)
    # print(uid_list[-1])
