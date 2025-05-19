Feature: Passer une commande

  Scenario: ShouldPlaceOrderWithDiscount
    Given un client avec l'id 1 et les articles [101, 102]
    When je passe une commande via l'API
    Then la réponse doit avoir un statut 201
    And le total doit être égal à 945.0

  Scenario: ShouldPlaceOrderWithoutDiscount
    Given un client avec l'id 2 et les articles [101, 102]
    When je passe une commande via l'API
    Then la réponse doit avoir un statut 201
    And le total doit être égal à 1050.0

  Scenario: ShouldThrowCustomerNotFoundException
    Given un client avec l'id 4 et les articles [101, 102]
    When je passe une commande via l'API
    Then la réponse doit avoir un statut 404
    And l'erreur "Not Found" et le message d'erreur est : "Customer not found"

  Scenario: ShouldThrowExceptionWhenItemsAreMissing
    Given un client avec l'id 1 et les articles [101, 202]
    When je passe une commande via l'API
    Then la réponse doit avoir un statut 400
    And l'erreur "Bad Request" et le message d'erreur est : "Missing item(s): [202]"
