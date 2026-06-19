import re
from nltk.corpus import stopwords
import nltk

try:
    nltk.data.find("corpora/stopwords")
except LookupError:
    nltk.download("stopwords", quiet=True)

TECH_WHITELIST = {
    "not", "no", "never", "nothing", "neither", "nor",
    "cannot", "cant", "wont", "dont", "didnt", "isnt",
    "wasnt", "wouldnt", "shouldnt", "couldnt", "good",
    "bad", "great", "poor", "love", "hate", "best", "worst"
}


def clean_review(text: str) -> str:
    """
    Nettoie un avis Amazon pour TF-IDF.
    - Conserve les mots de négation (not, never, no...)
    - Supprime URLs, HTML, chiffres isolés
    - Supprime stopwords sauf whitelist
    """
    if not text or not isinstance(text, str):
        return ""

    # Supprimer HTML
    text = re.sub(r"<[^>]+>", " ", text)

    # Supprimer URLs
    text = re.sub(r"https?://\S+|www\.\S+", " ", text)

    # Minuscules
    text = text.lower()

    # Contractions importantes → forme complète
    contractions = {
        "won't": "will not", "can't": "cannot", "n't": " not",
        "i'm": "i am", "it's": "it is", "that's": "that is",
        "don't": "do not", "didn't": "did not", "isn't": "is not",
        "wasn't": "was not", "wouldn't": "would not",
    }
    for k, v in contractions.items():
        text = text.replace(k, v)

    # Supprimer caractères spéciaux sauf apostrophes
    text = re.sub(r"[^a-z\s]", " ", text)

    # Supprimer chiffres isolés
    text = re.sub(r"\b\d+\b", " ", text)

    # Tokenisation + stopwords
    stop_words = set(stopwords.words("english")) - TECH_WHITELIST
    tokens = text.split()
    cleaned = [
        w for w in tokens
        if (w not in stop_words or w in TECH_WHITELIST)
        and len(w) > 2
    ]

    return " ".join(cleaned)