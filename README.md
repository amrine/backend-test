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
├── README.md                   <-- Ce fichier
├── pom.xml                     <-- POM parent
├── test-utils/                 <-- Module utils
├── test-unitaire/              <-- Module 1 : Test unitaire
├── test-integration/           <-- Module 2 : Test d'Intégration
└── test-api/                   <-- Module 3 : Test d'API
```

---

## 📚 Modules disponibles

| Module                | Description                                                 | Lien                                           |
|-----------------------|-------------------------------------------------------------|------------------------------------------------|
| 🛒 `test-unitaire`    | Service de commande avec tests unitaires et mocks (Mockito) | Voir le [README](./test-unitaire/README.md)    |
| 🛒 `test-integration` | Service de commande avec tests d'intégration                | Voir le [README](./test-integration/README.md) |
| 🛒 `test-api`         | Service de commande avec tests d'API                        | Voir le [README](./test-api/README.md)         |

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

## Tests d'intégration

### 🔍 Définition
Les tests d’intégration vérifient que plusieurs composants fonctionnent ensemble : service, repository, base de données, configuration Spring...
➡️ L’objectif : s’assurer que l’architecture ne se casse pas une fois les blocs connectés.

### 🎯 Cas d’utilisation
✔ Valider les requêtes vers la base de données (via JPA, JDBC, etc.)

✔ Vérifier que Spring Boot démarre correctement (contexte, injection, etc.)

✔ Tester l’interaction réelle entre les couches (ex : service ↔ repo ↔ DB)

### 🛠 Outils
SpringBootTest, Testcontainers, PostgreSQL, Docker

## Tests d’API

### 🔍 Définition
Les tests d’API valident que les endpoints REST exposés par votre application fonctionnent correctement : statut HTTP, format de réponse, contenu retourné, gestion des erreurs…
➡️ L’objectif : s’assurer que l’interface publique respecte bien le contrat attendu.

### 🎯 Cas d’utilisation
✔ Vérifier que chaque endpoint renvoie la bonne donnée (GET, POST, PUT, DELETE)

✔ Contrôler les statuts HTTP selon les cas (200, 400, 403, 404, 500…)

✔ Valider la structure et le contenu du JSON retourné

✔ Tester les règles métier exposées via l’API

### 🛠 Outils
MockMvc, SpringBootTest, Cucumber, JSONAssert


### 🧩 Exemple
- Voir [OrderServiceTest.java](./test-unitaire/src/test/java/fr/backendtest/testunitaire/service/OrderServiceTest.java)
- Voir [OrderServiceIT.java](./test-integration/src/test/java/fr/backendtest/testintegration/service/OrderServiceIT.java)
- Voir [OrderControllerIT.java](./test-api/src/test/java/fr/backendtest/testapi/controller/OrderControllerIT.java)
- Voir [OrderSteps.java](./test-api/src/test/java/fr/backendtest/testapi/cucumber/steps/OrderSteps.java)

---

## 🙌 Contribution

Tu peux t’inspirer de ce modèle pour structurer tes projets d’entreprise, de formation ou même de démo technique.  
Chaque module est **isolé**, **documenté** et **testé indépendamment**.

