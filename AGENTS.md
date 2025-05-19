# AGENTS.md

## 🎯 Objectif

Ce fichier définit les **rôles et responsabilités** des membres impliqués dans la **relecture de code** (code review) pour garantir une qualité de développement homogène, constructive et efficace.

---

## 👤 Rôles des agents

### 🔍 Reviewer
**Responsable de relire et commenter le code proposé.**

#### Responsabilités :
- Lire **tout** le code modifié (pas juste les parties familières)
- Vérifier :
    - Lisibilité, nommage, clarté
    - Respect des conventions (formatage, style, architecture)
    - Qualité des tests (présence, couverture, pertinence)
    - Robustesse (gestion des erreurs, nulls, cas limites)
    - Impact sur le code existant (effets de bord, compatibilité)
- Poser des questions si quelque chose n’est pas clair
- Suggérer des améliorations **sans imposer**
- Donner un **Go / Demande de changement** explicite

---

### 🧑‍💻 Author
**Responsable de soumettre du code propre, testable et compréhensible.**

#### Responsabilités :
- Pousser du code lisible, clair, testé
- Préparer une **PR bien décrite** :
    - Résumé du besoin
    - Ce qui a été modifié
    - Ce qui reste à valider/tester
- Anticiper les questions (ajouter des commentaires si besoin)
- Répondre aux retours de review avec ouverture et réactivité
- Ne pas merger sans validation explicite

---

## ✅ Check-list de review (rapide)

- [ ] Le code fait ce qu’il est censé faire
- [ ] Il est clair et bien structuré
- [ ] Il est couvert par des tests pertinents
- [ ] Il suit les conventions du projet
- [ ] Il ne casse pas d’autres fonctionnalités
- [ ] Il n’introduit pas de dette technique inutile
- [ ] La PR est bien documentée (titre, description, commits)

---

## 🧠 Bonnes pratiques

- Toujours assumer la **bienveillance et l’intention positive**
- Un bon review ne cherche pas la perfection, il cherche la clarté
- Le but n’est pas d’avoir raison, mais d’améliorer le code ensemble
- Si un point bloque, on en discute **en vocal ou en pairing**

---

## 📆 Fréquence & timing

- Une PR ne devrait **pas rester ouverte plus de 48h**
- Reviewers désignés dès la création de la PR
- Les petites PR sont prioritaires

---

## 🤝 Règle d’or

> **“On shippe ensemble, on apprend ensemble.”**

---

