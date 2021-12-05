import numpy as np
import pandas as pd
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

# df = pd.read_csv('pima-indians-diabetes.csv')
# print(df.head())

# FIREBASE CONNECTION AND READING DATA
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

# Create reference
db = firebase.database()

# Read data from Firebase
report = db.child("DiabetesMedicalReport").get()
# print(report.val())
for report in report.each():
    print(report.val())


