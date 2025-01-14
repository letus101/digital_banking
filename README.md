# Documentation de l'API Digital Banking - Groupe 2

## Aperçu
Cette API RESTful fait partie d'un système bancaire numérique qui offre diverses opérations bancaires, notamment la gestion des clients, la gestion des comptes et les transactions.

## Authentification
L'API utilise JWT (JSON Web Token) pour l'authentification. La plupart des points d'accès nécessitent une authentification et des autorisations basées sur les rôles.

### Points d'accès d'authentification

#### Connexion
- **URL**: `/login`
- **Méthode**: POST
- **Corps de la requête**:
```json
{
    "username": "string",
    "password": "string"
}
```
- **Réponse**: Token JWT

## Gestion des Clients

### 1. Créer un Client
- **URL**: `/customers`
- **Méthode**: POST
- **Autorisation**: Requise (rôle ADMIN)
- **Corps de la requête**:
```json
{
    "name": "string",
    "email": "string",
    "phoneNumber": "string"
}
```
- **Réponse**: Détails du client

### 2. Mettre à jour un Client
- **URL**: `/customer/{customerId}`
- **Méthode**: PUT
- **Autorisation**: Requise (rôle ADMIN)

### 3. Obtenir un Client
- **URL**: `/customers/{id}`
- **Méthode**: GET
- **Réponse**: Détails du client

### 4. Lister les Clients
- **URL**: `/customers`
- **Méthode**: GET
- **Réponse**: Liste des clients

### 5. Rechercher des Clients
- **URL**: `/customers/search`
- **Méthode**: GET
- **Paramètres de requête**: 
  - `keyword` (optionnel, défaut: "")

## Gestion des Comptes

### 1. Créer un Compte Courant
- **URL**: `/accounts/current/{customerId}`
- **Méthode**: POST
- **Paramètres**:
  - `initialBalance`: double
  - `overDraft`: double

### 2. Créer un Compte Épargne
- **URL**: `/accounts/saving/{customerId}`
- **Méthode**: POST
- **Paramètres**:
  - `initialBalance`: double
  - `interestRate`: double

### 3. Obtenir les Comptes d'un Client
- **URL**: `/accounts/customer/{customerId}`
- **Méthode**: GET

## Opérations de Transaction

### 1. Opération de Débit
- **URL**: `/accounts/debit`
- **Méthode**: POST
- **Corps de la requête**:
```json
{
    "accountId": "string",
    "amount": number,
    "description": "string"
}
```

### 2. Opération de Crédit
- **URL**: `/accounts/credit`
- **Méthode**: POST
- **Corps de la requête**:
```json
{
    "accountId": "string",
    "amount": number,
    "description": "string"
}
```

### 3. Opération de Virement
- **URL**: `/accounts/transfer`
- **Méthode**: POST
- **Corps de la requête**:
```json
{
    "accountSource": "string",
    "accountDestination": "string",
    "amount": number,
    "description": "string"
}
```

## Historique des Opérations

### Obtenir l'Historique Paginé
- **URL**: `/accounts/{accountId}/pageOperations`
- **Méthode**: GET
- **Paramètres**:
  - `page` (optionnel, défaut: 0)
  - `size` (optionnel, défaut: 5)

## Sécurité
- Implémentation de CORS
- Authentification JWT requise
- Chiffrement des mots de passe
- Autorisations basées sur les rôles

## Démarrage Rapide

### Prérequis
- Java 17 ou supérieur
- Spring Boot 3.2.1
- MySQL Database

### Installation
1. Cloner le dépôt
2. Configurer la base de données dans `application.properties`
3. Lancer l'application :
```bash
./mvnw spring-boot:run
```

## Équipe - Groupe 2
- KETAJ Youssef
- MIME Abdelhakim
- TAKI Oussama
- FARID Oussama
- REZOUK Walid

## Licence
Ce projet est sous licence MIT