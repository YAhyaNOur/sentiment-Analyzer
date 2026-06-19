from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware

from app.schemas.helpers import SentimentRequest, SentimentResponse
from app.services.sentiment_service import SentimentService
from app.schemas.logger import get_logger

logger  = get_logger("main")
app     = FastAPI(title="AI Sentiment E-Commerce", version="1.0.0")
service = SentimentService()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8081", "http://localhost:8080",
                   "http://127.0.0.1:5500"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/health")
def health():
    return {"status": "ok", "service": "AI Sentiment E-Commerce"}


@app.post("/api/ai/sentiment", response_model=SentimentResponse)
def analyze(req: SentimentRequest):
    """
    Reçoit un avis texte depuis Spring Boot.
    Retourne sentiment + score + keywords.

    Spring Boot envoie :
      { "product_id": "B001E4KFG0", "text": "Great product, fast delivery!" }
    """
    if not req.text or len(req.text.strip()) < 5:
        raise HTTPException(status_code=400, detail="Texte trop court.")

    logger.info(f"Avis reçu — produit {req.product_id} | {len(req.text)} chars")

    result = service.analyze(req.product_id, req.text)

    return SentimentResponse(**result)