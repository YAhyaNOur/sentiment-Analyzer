from pydantic import BaseModel, Field
from typing import List


class SentimentRequest(BaseModel):
    """Requête envoyée par Spring Boot."""
    product_id: str
    text: str


class KeywordItem(BaseModel):
    word: str
    score: float


class SentimentResponse(BaseModel):
    """Réponse renvoyée à Spring Boot."""
    product_id: str
    sentiment: str            # "positif" | "neutre" | "négatif"
    score: float = Field(..., ge=0.0, le=1.0)
    label: int                # 0=négatif, 1=neutre, 2=positif
    keywords: List[str]       # sujets détectés (livraison, qualité...)

    class Config:
        json_schema_extra = {
            "example": {
                "product_id": "B001E4KFG0",
                "sentiment": "positif",
                "score": 0.923,
                "label": 2,
                "keywords": ["quality", "taste", "packaging"]
            }
        }