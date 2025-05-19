# 🛒 Backend Test – Test d'Intégration Exemple

Ce projet illustre un cas d’usage **réaliste et professionnel** d’un backend Spring Boot avec :

- Gestion de commandes (création, validation, calcul total)
- Accès aux clients et aux articles via repository
- Réduction pour clients VIP
- Validation des entrées (client + liste d’items)
- Tests d'intégration complets avec postgresql, flywaydb, testcontainers & junit-jupiter

---

## 📦 Fonctionnalités

- 🔍 Vérifie que tous les items d’une commande existent
- ❌ Lève une exception claire si un client ou des items sont manquants
- 💸 Applique une réduction de 10% pour les clients VIP
- ✅ Tests d'intégration pour les cas nominaux et les erreurs
- 🧪 Utilisation propre de spring-boot-starter-test + postgresql + flywaydb + testcontainers + assertions

---

## 🧱 Structure

```
src/
├── main/
│   └── java/fr/backendtest/testintegration/
│       ├── dto/
│       │   ├── OrderRequest.java
│       │   └── OrderResponse.java
│       ├── entity/
│       │   ├── Customer.java
│       │   ├── Item.java
│       │   └── Order.java
│       ├── exception/
│       │   ├── CustomerNotFoundException.java
│       │   └── ItemNotFoundException.java
│       ├── repository/
│       │   ├── CustomerRepository.java
│       │   ├── ItemRepository.java
│       │   └── OrderRepository.java
│       ├── service/
│       │   ├── impl/
│       │   │   └── OrderServiceImpl.java
│       │   └── OrderService.java
│       └── TestIntegrationApplication.java
│
└── test/
    └── java/fr/backendtest/testintegration/
        ├── service/
        │    └── OrderServiceIT.java
        └── TestIntegrationApplicationTests.java
```

---

## ⚙️ Prérequis

- Java 21+ ✅
- Maven ☕
- Spring Boot 3.x
- IDE : IntelliJ, VS Code ou Eclipse

---

## 🚀 Pour lancer le projet

```bash
# Clone le projet
git clone https://github.com/amrine/backend-test.git

# Lance les tests
./mvnw test
```

---

## 🧪 Pour exécuter les tests

Les tests sont dans `OrderServiceIT.java` et couvrent :

| Test                  | Ce qu’il vérifie                 |
|-----------------------|----------------------------------|
| ✅ placeOrder avec VIP | Calcul total avec réduction      |
| ✅ placeOrder sans VIP | Calcul total sans réduction      |
| ❌ Items manquants     | Lève `ItemNotFoundException`     |
| ❌ Client inconnu      | Lève `CustomerNotFoundException` |

---

## 📚 Technologies utilisées

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Docker
- Testcontainers (PostgreSQL)
- JUnit 5
- Transactional tests
- @SpringBootTest
- Maven
- Clean Architecture principles
---

## 🧪 Types de tests d'intégration couverts

| Test                                | Objectif                                                    |
|-------------------------------------|-------------------------------------------------------------|
| ✔ Service avec logique métier       | Calcul total, réduction VIP, validations                    |
| ✔ Persistance JPA                   | Sauvegarde réelle des entités, relations @ManyToMany        |
| ✔ Séquences PostgreSQL              | Réinitialisation propre des IDs entre les tests             |
| ✔ Chargement lazy vs eager          | Contrôle de `LazyInitializationException`                  |
| ✔ Isolation entre les tests         | Réinitialisation via `TRUNCATE` et `RESTART IDENTITY`       |

---

## 🧠 Exemple de commande JSON simulée

```json
{
  "customerId": 1,
  "itemIds": [101, 102]
}
```

---

## 📌 Ce que tu peux faire ensuite

- 🧹 Extraire DatabaseCleaner dans un module réutilisable (voir [test-utils](../test-utils))
- 🔁 Ajouter des tests API avec MockMvc ou RestAssured
- 📦 Créer un module test-support partagé entre projets
- 🧪 Utiliser @Sql pour injecter des jeux de données
- 🔐 Simuler des users Spring Security avec @WithMockUser
- 📈 Mesurer la couverture avec JaCoCo (tests unitaires + intégration)
- ⚙️ Intégrer les tests dans un pipeline CI avec Testcontainers

---

## 🙌 Auteur

Ce projet est conçu pour apprendre les **tests backend propres et solides**.  
Tu peux l’adapter pour des démos, des entretiens techniques ou des formations internes.
