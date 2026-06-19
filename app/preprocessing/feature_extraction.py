import re
from typing import List

ECOMMERCE_KEYWORDS = [
    # Qualité produit
    "quality", "fresh", "taste", "flavor", "smell", "texture",
    "expired", "broken", "damaged", "defective", "excellent",
    # Livraison
    "delivery", "shipping", "arrived", "package", "packaging",
    "damaged", "late", "fast", "slow", "tracking",
    # Prix
    "price", "expensive", "cheap", "value", "worth", "cost",
    "overpriced", "affordable", "discount",
    # Service
    "service", "support", "refund", "return", "replace",
    "customer", "complaint", "helpful", "response",
    # Expérience
    "love", "hate", "recommend", "disappointed", "satisfied",
    "amazing", "terrible", "perfect", "awful", "great", "poor",
]


def extract_keywords(text: str) -> List[str]:
    """
    Extrait les mots-clés e-commerce depuis un avis.
    Utile pour le dashboard vendeur — savoir POURQUOI l'avis est négatif.
    """
    if not text or not isinstance(text, str):
        return []

    text_lower = text.lower()
    found = []

    for kw in ECOMMERCE_KEYWORDS:
        if re.search(r"\b" + re.escape(kw) + r"\b", text_lower):
            found.append(kw)

    return list(set(found))


def map_score_to_label(score: int) -> int:
    """
    Convertit la note Amazon (1-5) en label sentiment.
    1-2 → 0 (négatif)
    3   → 1 (neutre)
    4-5 → 2 (positif)
    """
    if score <= 2:
        return 0
    elif score == 3:
        return 1
    else:
        return 2


def label_to_sentiment(label: int) -> str:
    return {0: "négatif", 1: "neutre", 2: "positif"}.get(label, "inconnu")