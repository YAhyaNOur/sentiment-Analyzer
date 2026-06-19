"""
Script d'entraînement — à lancer UNE SEULE FOIS.
Génère saved_models/sentiment_model.pkl et vectorizer.pkl
"""
import pandas as pd
import joblib
from pathlib import Path
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report

from app.core.config import DATASET_PATH, MODEL_PATH, VECTORIZER_PATH
from app.preprocessing.text_cleaning import clean_review
from app.preprocessing.feature_extraction import map_score_to_label

print("=" * 50)
print("Chargement du dataset...")
df = pd.read_csv(DATASET_PATH, low_memory=False)

# Garder uniquement les colonnes utiles
df = df[["Score", "Text"]].dropna()
df["Score"] = pd.to_numeric(df["Score"], errors="coerce")
df = df.dropna(subset=["Score"])
df["Score"] = df["Score"].astype(int)

print(f"Dataset chargé : {len(df)} avis")
print(f"Distribution Score:\n{df['Score'].value_counts().sort_index()}")

# Mapping Score → label sentiment
df["label"] = df["Score"].apply(map_score_to_label)
print(f"\nDistribution labels:\n{df['label'].value_counts().sort_index()}")
print("0=négatif, 1=neutre, 2=positif")

# Nettoyage texte
print("\nNettoyage des textes...")
df["clean_text"] = df["Text"].apply(clean_review)

# Supprimer textes vides après nettoyage
df = df[df["clean_text"].str.strip() != ""]
print(f"Après nettoyage : {len(df)} avis")

# Split train/test
X_train, X_test, y_train, y_test = train_test_split(
    df["clean_text"], df["label"],
    test_size=0.2,
    random_state=42,
    stratify=df["label"]
)

print(f"\nTrain : {len(X_train)} | Test : {len(X_test)}")

# TF-IDF
print("\nVectorisation TF-IDF...")
vectorizer = TfidfVectorizer(
    max_features=50000,
    ngram_range=(1, 2),    # unigrammes + bigrammes
    min_df=3,
    sublinear_tf=True
)
X_train_tfidf = vectorizer.fit_transform(X_train)
X_test_tfidf  = vectorizer.transform(X_test)

# Entraînement
print("Entraînement du modèle...")
model = LogisticRegression(
    max_iter=1000,
    C=1.0,
    class_weight="balanced",   # gère le déséquilibre négatif/neutre/positif
    random_state=42
)
model.fit(X_train_tfidf, y_train)

# Évaluation
print("\n" + "=" * 50)
print("RÉSULTATS :")
y_pred = model.predict(X_test_tfidf)
print(classification_report(y_test, y_pred,
      target_names=["négatif", "neutre", "positif"]))

# Sauvegarde
Path(MODEL_PATH).parent.mkdir(parents=True, exist_ok=True)
joblib.dump(model, MODEL_PATH)
joblib.dump(vectorizer, VECTORIZER_PATH)
print(f"\nModèle sauvegardé → {MODEL_PATH}")
print(f"Vectorizer sauvegardé → {VECTORIZER_PATH}")
print("=" * 50)