import pandas as pd

df = pd.read_csv(r"C:\Users\user\Desktop\Sentiment e-commerce\app\data\Reviews.csv",
                 low_memory=False)

print("Shape:", df.shape)
print("\nColonnes:", df.columns.tolist())
print("\nDistribution Score:\n", df["Score"].value_counts().sort_index())
print("\nValeurs nulles:\n", df.isnull().sum())

print("\nExemple avis positif (5 étoiles):")
print(df[df["Score"] == 5]["Text"].iloc[0])

print("\nExemple avis négatif (1 étoile):")
print(df[df["Score"] == 1]["Text"].iloc[0])