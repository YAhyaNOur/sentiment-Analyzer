from pathlib import Path

BASE_DIR         = Path(__file__).resolve().parent.parent.parent
DATA_DIR         = BASE_DIR / "app" / "data"
DATASET_PATH     = DATA_DIR / "Reviews.csv"
SAVED_MODELS_DIR = BASE_DIR / "saved_models"
MODEL_PATH       = SAVED_MODELS_DIR / "sentiment_model.pkl"
VECTORIZER_PATH  = SAVED_MODELS_DIR / "vectorizer.pkl"
LOG_FILE         = BASE_DIR / "app.log"
API_TITLE        = "AI Sentiment E-Commerce"
DEBUG            = True