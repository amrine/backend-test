# 🥇 Backend Test – Golden Master Testing

Ce projet illustre un cas d’usage **réaliste et professionnel** d’un backend Spring Boot, avec une couche d’API REST testée via :

- Gestion de commandes (création, validation, calcul total)
- Accès aux clients et aux articles via repository
- Réduction pour clients VIP
- Validation des entrées (client + liste d’items)
- Tests d'intégration complets avec postgresql, flywaydb, testcontainers & junit-jupiter


Ce module illustre l’usage des **Golden Master Tests** dans un backend Spring Boot.  
L’objectif est de **capturer le comportement validé d’un système** (API ou service) et de garantir qu’il **ne change pas après une modification** (refactoring, optimisation, etc.).

---

## 📦 Fonctionnalités testées

- 🔁 Comparaison de la réponse actuelle à une **version de référence** (Golden)
- 💡 Génération automatique des Golden Files si absents
- ⚖️ Gestion des cas valides et des erreurs (exceptions métier)
- 🧾 Validation stricte des JSON sauf pour les champs volatiles (`timestamp`, etc.)
- 🧪 20 cas de test automatisés via `@ParameterizedTest` et `@MethodSource`

---

## 🧱 Structure du projet

```
src/
├── main/
│   └── java/fr/backendtest/goldentest/
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
    ├── java/fr/backendtest/goldentest/
    │   └── OrderApiGoldenTest.java
    │    
    └── resources/
        └── goldenmaster/
            ├── order_01.json
            ├── .....
            ├── .....
            ├── order_20.json
            ├── order_missing_items.json
            └── order_unknown_customer.json
```

---

## ⚙️ Technologies utilisées

- Java 21+ ✅
- Maven ☕
- Spring Boot 3.x
- IDE : IntelliJ, VS Code ou Eclipse
- Spring MVC + MockMvc  
- JUnit 5 + `@ParameterizedTest`  
- Instancio – génération réaliste d’objets  
- JSONAssert + CustomComparator – comparaison stricte de JSON  
- ObjectMapper (Jackson) – sérialisation/JSON pretty-print

---

## 🧪 Tests couverts

| Test                         | Ce qu’il vérifie                                      |
|------------------------------|--------------------------------------------------------|
| ✅ Golden test 1–20           | Réponse correcte et stable pour commandes valides     |
| ❌ Golden test Items manquants    | Items manquants → `ItemNotFoundException`             |
| ❌ Golden test client inconnu | Client absent → `CustomerNotFoundException`           |
| 🔄 Golden file absent        | Création automatique du fichier de référence          |

---

## 📄 Exemple de réponse JSON (cas valide)

```json
{
  "orderId": 1,
  "customerId": 1,
  "items": [ 101, 102 ],
  "total": 945.0
}
```

---

## 💥 Exemple de réponse d’erreur (cas client inconnu)

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Customer with ID 99999 not found",
  "path": "/api/orders",
  "timestamp": "2025-06-01T13:45:10Z"
}
```

> 🧼 Le champ `timestamp` est ignoré à la comparaison via `CustomComparator`.

---

## 🧪 Exécution

```bash
# Clone le projet
git clone https://github.com/amrine/backend-test.git

# Lance les tests
./mvnw test
```

Le test créera automatiquement les fichiers manquants dans `src/test/resources/goldenmaster/` si besoin.

---

## 📚 Ce que tu peux faire ensuite

- 🎛️ Ajouter un flag `--regenerate` pour mettre à jour tous les Golden
- 📂 Organiser les cas par `valid/` et `error/`
- 📤 Générer aussi les fichiers d’entrée (`input/*.json`) pour audit ou débogage
- 📦 Externaliser les comparateurs dans un module `test-support`
- 📈 Intégrer dans CI/CD et comparer les diffs en cas d’échec (GitHub Actions, etc.)

---

## 🙌 À propos

Ce module est conçu pour sécuriser le comportement d’un backend métier sur des cas critiques.  
Il t’aide à **refactorer ton code sans risquer de régression** en t’assurant que l’output n’a pas changé.


## 🙌 Auteur

Ce projet est conçu pour apprendre les **tests backend propres et solides**.  
Tu peux l’adapter pour des démos, des entretiens techniques ou des formations internes.