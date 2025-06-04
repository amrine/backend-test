# 🧪 Backend Testing Playground – Projet Multi-modules

Ce projet est une **démonstration structurée de plusieurs types de tests backend** à travers des modules Spring Boot indépendants, chacun axé sur un type ou une approche de test particulière.

## 🎯 Objectif

- Créer un **environnement pédagogique et professionnel** pour illustrer les différents styles de test backend
- Utiliser un projet **multi-modules Maven** pour organiser proprement chaque cas d’usage
- Permettre de tester, comparer, et intégrer plusieurs approches en toute clarté

---

## 🏗️ Structure du projet

```
backend-testing-playground/
├── README.md               <-- Ce fichier
├── pom.xml                 <-- POM parent
└── test-unitaire/           <-- Module 1 : Test unitaire
```

---

## 📚 Modules disponibles

| Module         | Description | Lien |
|----------------|-------------|------|
| 🛒 `test-unitaire` | Service de commande avec tests unitaires et mocks (Mockito) | Voir le [README](./test-unitaire/README.md) |

---

## ✅ Prérequis

- Java 21+
- Maven 3.9+
- IDE Java : IntelliJ, Eclipse ou VS Code

---

## 🔧 Commandes utiles

```bash
# Compiler tous les modules
./mvnw clean install

# Lancer les tests d’un sous-module
cd sous-module
./mvnw test
```

---
# Backend Tests

## Tests Unitaires

### 🔍 Définition
Les tests unitaires vérifient une méthode ou une classe isolée. Ils ne dépendent d’aucun service externe. C’est la base d’un code testable.

### 🎯 Cas d’utilisation
✔ Vérifier des règles métier simples

✔ Tester les cas limites (null, erreurs)

✔ Écrire en TDD (Test Driven Development)

### 🛠 Outils
JUnit, Mockito, AssertJ


### 🧩 Exemple
Voir [OrderServiceTest.java](./test-unitaire/src/test/java/fr/backendtest/testunitaire/service/OrderServiceTest.java)

---

## 🙌 Contribution

Tu peux t’inspirer de ce modèle pour structurer tes projets d’entreprise, de formation ou même de démo technique.  
Chaque module est **isolé**, **documenté** et **testé indépendamment**.

