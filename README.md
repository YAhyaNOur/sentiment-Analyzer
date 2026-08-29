# Sentiment E-commerce Analyzer

A sentiment analysis platform for e-commerce customer reviews, combining a **Machine Learning (Python/NLP) engine** with a **Spring Boot business API**, and dedicated dashboards for customers and sellers.

---

## Table of Contents

- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Machine Learning](#machine-learning)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Security](#security)
- [Author](#author)

---

## Architecture

The project consists of three main components:

| Component | Description |
|---|---|
| **`app/`** | Python service (Machine Learning / NLP) — review preprocessing, model training, sentiment analysis API exposed via FastAPI, and inference using the saved models (`sentiment_model.pkl`, `vectorizer.pkl`). |
| **`Analyzer/`** | Spring Boot backend — user management, product and review management, storage of analysis results, JWT authentication and security. |
| **`frontend/`** | User interface — customer dashboard, seller dashboard, login page. |

---

## Project Structure

```
Sentiment-E-commerce-Analyzer/
│
├── app/                         # Python service (Machine Learning / NLP)
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
├── frontend/                    # HTML user interface
│   ├── Dashboard-client.html
│   ├── Dashboard-vendeur.html
│   └── login.html
│
└── Analyzer/                    # Spring Boot backend
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

## Machine Learning

The AI service is built with:

- Python
- FastAPI
- NLP
- Scikit-learn
- TF-IDF Vectorization

**Analysis pipeline:**

```
Customer review
      ↓
Text preprocessing
      ↓
TF-IDF vectorization
      ↓
Machine Learning model
      ↓
Sentiment classification
```

**Output classes:**

| Class | Label |
|---|---|
| 😊 | Positive |
| 😐 | Neutral |
| 😡 | Negative |

---

## Technologies Used

**AI Service**
- Python
- FastAPI
- Scikit-learn
- NLP

**Backend**
- Java
- Spring Boot
- Spring Security
- JWT
- Gradle

**Frontend**
- HTML
- CSS
- JavaScript

---

## Features

**Customer**
- Browse products
- Submit reviews
- Receive automatic sentiment analysis

**Seller**
- View customer feedback
- Visualize sentiment analytics
- Track customer satisfaction over time

---

## Security

The backend implements:

- JWT-based authentication
- Spring Security
- User and role management

---

## Author

**Nour Yahya**
Data Science & Artificial Intelligence Engineering Student
