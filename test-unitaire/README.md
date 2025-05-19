# 🛒 Backend Test – Test Unitaire Exemple

Ce projet illustre un cas d’usage **réaliste et professionnel** d’un backend Spring Boot avec :

- Gestion de commandes (création, validation, calcul total)
- Accès aux clients et aux articles via repository
- Réduction pour clients VIP
- Validation des entrées (client + liste d’items)
- Tests unitaires complets avec JUnit & Mockito

---

## 📦 Fonctionnalités

- 🔍 Vérifie que tous les items d’une commande existent
- ❌ Lève une exception claire si un client ou des items sont manquants
- 💸 Applique une réduction de 10% pour les clients VIP
- ✅ Tests unitaires pour les cas nominaux et les erreurs
- 🧪 Utilisation propre de Mockito + ArgumentCaptor + assertions

---

## 🧱 Structure

```
src/
├── main/
│   └── java/fr/backendtest/testunitaire/
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
│       └── service/
│           ├── impl/
│           │   └── OrderServiceImpl.java
│           └── OrderService.java
│
└── test/
    └── java/fr/backendtest/testunitaire/
        └── service/
            └── OrderServiceTest.java
```

---

## ⚙️ Prérequis

- Java 17+ ✅
- Maven ☕
- Spring Boot 3.x
- IDE : IntelliJ, VS Code ou Eclipse

---

## 🚀 Pour lancer le projet

```bash
# Clone le projet
git clone https://github.com/amrine/backend-test.git
cd test-unitaire

# Lance les tests
./mvnw test
```

---

## 🧪 Pour exécuter les tests

Les tests sont dans `OrderServiceTest.java` et couvrent :

| Test                  | Ce qu’il vérifie                 |
|-----------------------|----------------------------------|
| ✅ placeOrder avec VIP | Calcul total avec réduction      |
| ✅ placeOrder sans VIP | Calcul total sans réduction      |
| ❌ Items manquants     | Lève `ItemNotFoundException`     |
| ❌ Client inconnu      | Lève `CustomerNotFoundException` |

---

## 📚 Technologies utilisées

- Java 17
- Spring Boot
- JUnit 5
- Mockito
- Maven
- Clean Architecture principles

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

- Ajouter une API REST autour de `OrderService`
- Connecter à une vraie base de données (PostgreSQL, H2)
- Ajouter une couche `@ControllerAdvice` pour exposer les exceptions proprement
- Étendre avec des coupons ou frais de port

---

## 🙌 Auteur

Ce projet est conçu pour apprendre les **tests backend propres et solides**.  
Tu peux l’adapter pour des démos, des entretiens techniques ou des formations internes.
