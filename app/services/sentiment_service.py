from app.models.sentiment_model import predict
from app.preprocessing.feature_extraction import extract_keywords
from app.schemas.logger import get_logger

logger = get_logger("sentiment_service")


class SentimentService:

    def analyze(self, product_id: str, text: str) -> dict:
        """
        Analyse un avis et retourne sentiment + keywords.

        Args:
            product_id: ID produit Spring Boot
            text: Texte brut de l'avis

        Returns:
            Dict compatible SentimentResponse
        """
        if not text or not text.strip():
            return {
                "product_id": product_id,
                "sentiment":  "neutre",
                "score":      0.5,
                "label":      1,
                "keywords":   []
            }

        result   = predict(text)
        keywords = extract_keywords(text)

        logger.info(
            f"Produit {product_id} → {result['sentiment']} "
            f"({result['score']}) | keywords: {keywords}"
        )

        return {
            "product_id": product_id,
            "sentiment":  result["sentiment"],
            "score":      result["score"],
            "label":      result["label"],
            "keywords":   keywords
        }