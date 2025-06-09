# 🌐 Backend Test – Test d'API REST avec MockMvc

Ce projet illustre un cas d’usage **réaliste et professionnel** d’un backend Spring Boot, avec une couche d’API REST testée via :

- Gestion de commandes (création, validation, calcul total)
- Accès aux clients et aux articles via repository
- Réduction pour clients VIP
- Validation des entrées (client + liste d’items)
- Tests d'intégration complets avec postgresql, flywaydb, testcontainers & junit-jupiter

---

## 📦 Fonctionnalités

- 🧾 Créer une commande via une API REST POST /api/orders
- 🔍 Valider les IDs client et article reçus
- 💸 Appliquer une réduction de 10% si client VIP
- ❌ Retourner les bons statuts HTTP (404, 400, 201…)
- ✅ Tests API sur les cas nominaux et d’erreurs
- 🧪 Utilisation propre de MockMvc + assertions JSON

---

## 🧱 Structure

```
src/
├── main/
│   └── java/fr/backendtest/testapi/
│       ├── controller/
│       │   └── OrderController.java
│       ├── dto/
│       │   ├── ApiError.java
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
│       └── TestAPIApplication.java
│
└── test/
    ├── java/fr/backendtest/testapi/
    │   ├── controller/
    │   │   └── OrderControllerTest.java
    │   └── cucumber/
    │       ├── steps/
    │       │   └── OrderSteps.java
    │       └── CucumberRunnerTest.java
    └── resources/features/
        └── place_order.feature
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

Les tests sont dans `OrderControllerIT.java` et couvrent :

| Test                  | Ce qu’il vérifie                               |
|-----------------------|------------------------------------------------|
| ✅ placeOrder avec VIP | Réponse 201 avec ID de commande dans le JSON   |
| ✅ placeOrder sans VIP | Réponse 201 avec ID de commande dans le JSON   
|
| ❌ Items manquants     | Retour 400 avec la liste des IDs manquants     |
| ❌ Client inconnu      | Retour 404 + message d’erreur clair            |

---

## 📚 Technologies utilisées

- Java 21+
- Spring Boot 3.x
- Spring MVC + MockMvc
- Cucumber
- JUnit 5
- JSONPath (pour assertions)
- Maven
- Jackson for JSON

---

## 🧪 Exemple de test API

```java
    mockMvc.perform(post("/api/v1/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
              "customerId": 1,
              "itemIds": [101, 102]
            }
        """))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.orderId").value(1));

```

---

## 🧪💥 Gestion des erreurs (via @ControllerAdvice)

```java
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
```
Permet un meilleur contrôle des réponses retournées aux clients API.

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

 - 🔒 Ajouter la sécurité via @WithMockUser ou Spring Security Test
 - 🔁 Tester d’autres endpoints : GET, PUT, DELETE
 - 📦 Créer une collection Postman liée à tes tests MockMvc
 - 📊 Suivre la couverture avec JaCoCo (unit + API)
 - 🚀 Déployer sur un environnement CI/CD avec exécution des tests API

---

## 🙌 Auteur

Ce projet est conçu pour apprendre les **tests backend propres et solides**.  
Tu peux l’adapter pour des démos, des entretiens techniques ou des formations internes.
