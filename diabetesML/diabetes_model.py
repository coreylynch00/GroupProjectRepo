from sklearn.linear_model import LogisticRegression
import pandas as pd
from sklearn.metrics import accuracy_score

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

# Print Accuracy of Model
acc = accuracy_score(y, yhat)
print(f"{acc * 100:.2f}% Accurate")
