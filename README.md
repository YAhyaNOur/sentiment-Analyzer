# Sentiment E-commerce Analyzer

Application d'analyse de sentiment pour les avis clients e-commerce, combinant un moteur de **Machine Learning (Python/NLP)** et une **API métier Spring Boot**, avec des dashboards dédiés aux clients et vendeurs.

---

##  Architecture

Le projet est composé de trois parties principales :

- **`app/`** — Service Python (Machine Learning / NLP) :
  - Prétraitement des avis clients
  - Entraînement du modèle de classification
  - Exposition de l'API d'analyse de sentiment avec FastAPI
  - Utilisation des modèles sauvegardés (`sentiment_model.pkl` et `vectorizer.pkl`)

- **`Analyzer/`** — Backend Spring Boot :
  - Gestion des utilisateurs
  - Gestion des produits et avis
  - Stockage des résultats d'analyse
  - Authentification JWT et sécurité

- **`frontend/`** — Interface utilisateur :
  - Dashboard client
  - Dashboard vendeur
  - Page de connexion


## 📂 Structure du projet

```
Sentiment-E-commerce-Analyzer/
│
├── app/                         # Service Python (Machine Learning / NLP)
│   ├── core/
│   ├── data/
│   │   └── Reviews.csv
│   ├── training.py
│   ├── models/
│   ├── preprocessing/
│   ├── schemas/
│   ├── services/
│   ├── saved_models/
│   │   ├── sentiment_model.pkl
│   │   └── vectorizer.pkl
│   └── main.py
│
├── frontend/                    # Interface utilisateur HTML
│   ├── Dashboard-client.html
│   ├── Dashboard-vendeur.html
│   └── login.html
│
└── Analyzer/                    # Backend Spring Boot
    └── src/
        └── main/
            └── java/
                └── com/
                    └── example/
                        └── demo/
                            ├── config/
                            ├── controller/
                            ├── entity/
                            ├── repository/
                            ├── security/
                            └── service/
```


---

##  Machine Learning

Le service IA utilise :

- Python
- FastAPI
- NLP
- Scikit-learn
- TF-IDF Vectorization

Pipeline d'analyse :


Avis client
↓
Prétraitement du texte
↓
Vectorisation TF-IDF
↓
Modèle Machine Learning
↓
Classification du sentiment


Résultat :

- Positive 😊
- Neutral 😐
- Negative 😡


---

## ⚙️ Technologies utilisées

### AI Service
- Python
- FastAPI
- Scikit-learn
- NLP

### Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Gradle

### Frontend
- HTML
- CSS
- JavaScript


---

## Fonctionnalités

### Client
- Consulter les produits
- Ajouter des avis
- Obtenir l'analyse automatique du sentiment

### Vendeur
- Consulter les retours clients
- Visualiser les analyses
- Suivre la satisfaction client


---

##  Sécurité

Le backend utilise :

- Authentification JWT
- Spring Security
- Gestion des utilisateurs et rôles


---

##  Auteur

**Nour Yahya**  
Data Science & Artificial Intelligence Engineering Student
