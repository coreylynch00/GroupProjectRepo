from sklearn.linear_model import LogisticRegression
import pandas as pd
from sklearn.metrics import accuracy_score
import pyrebase

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

df = pd.read_csv('bcancer-training-set.csv')

# print(df.shape)

# Define features
feature_names = ['mean_radius', 'mean_texture', 'mean_perimeter', 'mean_area', 'mean_smoothness']

# Create Input and Output
X = df[feature_names]
y = df.diagnosis

# Define Model
model = LogisticRegression(solver='lbfgs', max_iter=1000)

# Fit Model
model.fit(X, y)

# Make Predictions
y_pred = model.predict(X)

# Print Accuracy of Model
acc = accuracy_score(y, y_pred)
db.child("BCancerResultAccuracy").child().set(f"{acc * 100:.2f}% Accurate")
