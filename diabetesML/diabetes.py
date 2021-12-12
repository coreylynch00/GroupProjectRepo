import uuid

import numpy as np
import pandas as pd
import uuid
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
import keras
from tensorflow.python import tf2
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation
from keras.callbacks import ModelCheckpoint
import pyrebase
from collections import OrderedDict
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_score
from sklearn.metrics import accuracy_score


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

# Create DataFrame
df = pd.read_csv('diabetes-training-set.csv')

"""
print("Diabetes data set dimensions : {}".format(df.shape))
print(df.groupby('outcome').size())
# Checking data where value is equal to 0
print("Total : ", df[df.bp == 0].shape[0])
print("Total : ", df[df.glucose == 0].shape[0])
print("Total : ", df[df.skinThickness == 0].shape[0])
print("Total : ", df[df.bmi == 0].shape[0])
print("Total : ", df[df.insulin == 0].shape[0])
"""

# Data Cleansing
df_clean = df[(df.bp != 0) & (df.bmi != 0) & (df.glucose != 0) & (df.skinThickness != 0) & (df.insulin != 0)]
# print(df_clean.shape)

# Define features
feature_names = ['pregnancies', 'glucose', 'bp', 'skinThickness', 'insulin', 'bmi', 'dpf', 'age']

# Create Input and Output
X = df_clean[feature_names]
y = df_clean.outcome

# Define Model
model = LogisticRegression(solver='lbfgs', max_iter=1000)

# Fit Model
model.fit(X, y)

# Make Predictions
yhat = model.predict(X)

"""
# Print Accuracy of Model
acc = accuracy_score(y, yhat)
print(acc)
"""

# Make Predictions Based on Input
new_input = [[float(fb_pregnancies), float(fb_glucose), float(fb_bp), float(fb_skinThickness), float(fb_insulin),
              float(fb_bmi), float(fb_dpf), float(fb_age)]]
new_output = model.predict(new_input)
# print(new_output)
if new_output == 0:
    result = "Unlikely."
    db.push(result)
else:
    result = "Likely."
    db.push(result)
