from sklearn.linear_model import LogisticRegression
import pandas as pd
from sklearn.metrics import accuracy_score

df = pd.read_csv('heart_training_set.csv')

# print(df.shape)

# Define features
feature_names = ['age', 'cp', 'trestbps', 'chol', 'fbs', 'restecg', 'thalach', 'exang', 'oldpeak', 'slope', 'ca', 'thal']

# Create Input and Output
X = df[feature_names]
y = df.target

# Define Model
model = LogisticRegression(solver='lbfgs', max_iter=1000)

# Fit Model
model.fit(X, y)

# Make Predictions
y_pred = model.predict(X)

# Print Accuracy of Model
acc = accuracy_score(y, y_pred)
print(f"{acc * 100:.2f}% Accurate")

# Take new input to make prediction
new_input = [[67, 0, 160, 286, 0, 0, 108, 1, 1.5, 1, 3, 2]]
new_output = model.predict(new_input)
# print(new_output)
if new_output == 0:
    result = "UNLIKELY"
    print(result)
else:
    result = "LIKELY"
    print(result)
