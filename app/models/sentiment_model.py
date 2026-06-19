import joblib
from app.core.config import MODEL_PATH, VECTORIZER_PATH
from app.preprocessing.text_cleaning import clean_review
from app.preprocessing.feature_extraction import label_to_sentiment
from app.schemas.logger import get_logger

logger = get_logger("sentiment_model")

_model      = None
_vectorizer = None


def _load():
    global _model, _vectorizer
    if _model is None:
        logger.info("Chargement du modèle...")
        _model      = joblib.load(MODEL_PATH)
        _vectorizer = joblib.load(VECTORIZER_PATH)
        logger.info("Modèle chargé.")


def predict(text: str) -> dict:
    """
    Prédit le sentiment d'un avis.

    Returns:
        {"label": int, "sentiment": str, "score": float}
    """
    _load()

    cleaned = clean_review(text)

    if not cleaned.strip():
        return {"label": 1, "sentiment": "neutre", "score": 0.5}

    vec   = _vectorizer.transform([cleaned])
    label = int(_model.predict(vec)[0])
    proba = _model.predict_proba(vec)[0]
    score = round(float(max(proba)), 3)

    return {
        "label":     label,
        "sentiment": label_to_sentiment(label),
        "score":     score
    }