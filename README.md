# AI-Based Recommendation System (Java + Apache Mahout)

A minimal, presentation-ready recommender system using **Mahout Taste**.  
Loads a CSV file of user-item ratings and prints **Top-N recommendations** in the console.

---

## 📌 Features
- **User-Based Collaborative Filtering** (Nearest-Neighbor with Pearson Correlation)
- **Item-Based Collaborative Filtering** (Pearson Correlation)
- **Simple Console UI**
- **Sample Dataset** included (`ratings.csv`)

---

## 🛠 Prerequisites
- Java **8+**
- Maven **3.6+**

---

## 🚀 Run Instructions
```bash
# Clean and build the project
mvn clean package

# Run the recommender app
mvn exec:java -Dexec.mainClass=com.example.App
