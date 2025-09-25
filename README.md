# Latice

**Latice** est un jeu de plateau stratégique implémenté en Java avec interface console et interface graphique JavaFX. Ce projet permet aux joueurs de placer des tuiles colorées avec des formes spécifiques sur un plateau de jeu pour marquer des points.

## Description du Jeu

Latice est un jeu de stratégie où les joueurs placent des tuiles sur un plateau de jeu hexagonal. Chaque tuile possède :
- **Une couleur** : Rouge, Vert, Jaune, Bleu, Rose, Cyan
- **Une forme** : Oiseau (🦅), Dauphin (🐬), Plume (🪶), Fleur (🌼), Lézard (🦎), Tortue (🐢)

Les joueurs doivent placer leurs tuiles de manière stratégique pour créer des connexions et marquer des points selon les règles du jeu.

## Fonctionnalités

- **Deux modes de jeu** :
  - Mode console avec affichage textuel et émojis colorés
  - Mode graphique avec interface JavaFX (assets visuels complets)
- **Système de tuiles** : 6 couleurs × 6 formes = 36 combinaisons possibles
- **Gestion des joueurs** : Support pour plusieurs joueurs avec racks individuels
- **Système de points** : Calcul automatique des scores
- **Plateau de jeu** : Grille hexagonale avec cellules spéciales (Soleil/Lune)
- **Tests unitaires** : Couverture complète avec JUnit 5

## Structure du Projet

```
src/
├── main/java/latice/
│   ├── application/        # Point d'entrée de l'application
│   ├── cell/              # Gestion des cellules du plateau
│   ├── console/           # Interface console et utilitaires d'affichage
│   ├── controller/        # Logique de jeu (Referee)
│   ├── gameboard/         # Plateau de jeu et gestion des cellules
│   ├── player/            # Joueurs, racks et pools de tuiles
│   ├── tile/              # Tuiles (couleurs et formes)
│   └── view/              # Interface graphique JavaFX
├── main/resources/assets/ # Assets graphiques (images, sons, vidéos)
└── test/java/            # Tests unitaires
```

## Prérequis

- **Java 17** ou version supérieure
- **Maven 3.6+** pour la compilation et gestion des dépendances
- **JavaFX** (pour le mode graphique - dépendance à ajouter si nécessaire)

## Installation

1. **Cloner le repository** :
   ```bash
   git clone https://github.com/Ichillow/Latice.git
   cd Latice
   ```

2. **Compiler le projet** :
   ```bash
   mvn clean compile
   ```

3. **Exécuter les tests** :
   ```bash
   mvn test
   ```

## Utilisation

### Mode Console

Pour lancer le jeu en mode console :

```bash
java -cp target/classes latice.application.LaticeApplicationConsole
```

Choisissez l'option "1" pour le mode console. Vous pourrez :
- Visualiser le plateau de jeu en mode texte
- Voir vos tuiles avec des émojis colorés
- Placer des tuiles en spécifiant les coordonnées
- Suivre les scores des joueurs

### Mode Graphique (JavaFX)

Pour le mode graphique, choisissez l'option "2" (nécessite JavaFX configuré).

**Note** : Le mode graphique nécessite l'ajout des dépendances JavaFX dans le `pom.xml`. Les assets graphiques sont déjà inclus dans `src/main/resources/assets/`.

## Architecture

### Composants Principaux

- **`Tile`** : Représente une tuile avec couleur et forme
- **`Player`** : Gère les joueurs, leurs racks et leurs pools de tuiles
- **`GameBoard`** : Plateau de jeu avec cellules hexagonales
- **`Referee`** : Contrôleur principal gérant les règles et la logique du jeu
- **`Console`** : Utilitaires d'affichage pour l'interface console
- **`MainWindow`** : Interface graphique JavaFX

### Classes de Données

- **`Color`** : Énumération des couleurs avec codes ANSI pour l'affichage console
- **`Shape`** : Énumération des formes avec émojis Unicode
- **`Position`** : Coordonnées sur le plateau
- **`Cell`** : Cellule du plateau avec type (normal, soleil, lune)

## Tests

Le projet inclut une suite de tests complète :

```bash
# Exécuter tous les tests
mvn test

# Tests par catégorie
mvn test -Dtest=latice.tile.*Test    # Tests des tuiles
mvn test -Dtest=latice.player.*Test  # Tests des joueurs
mvn test -Dtest=latice.console.*Test # Tests de l'interface console
```

**Couverture des tests** : 60 tests répartis sur 9 classes de test, couvrant :
- Création et manipulation des tuiles
- Gestion des joueurs et de leurs racks
- Logique du plateau de jeu
- Interface console
- Contrôleur de jeu (Referee)

## Configuration Maven

Le projet utilise Maven avec les dépendances suivantes :
- **JUnit 5.8.2** pour les tests
- **Java 17** comme version cible
- **Maven Surefire Plugin** pour l'exécution des tests

## Assets

Le projet inclut des assets graphiques complets :
- **Tuiles** : Images PNG pour chaque combinaison couleur/forme (36 images)
- **Arrière-plans** : Thèmes visuels (océan, soleil, lune)
- **Audio** : Musique de fond et effets sonores
- **Interface** : Icônes de contrôle de volume

## Contribution

1. Fork le repository
2. Créez une branche pour votre fonctionnalité : `git checkout -b feature/ma-fonctionnalite`
3. Committez vos changements : `git commit -am 'Ajout de ma fonctionnalité'`
4. Pushez la branche : `git push origin feature/ma-fonctionnalite`
5. Créez une Pull Request

## Problèmes Connus

- Le mode graphique nécessite l'ajout explicite des dépendances JavaFX dans le `pom.xml`
- Certaines fonctionnalités du mode console peuvent nécessiter des ajustements selon le terminal utilisé

## Auteurs

- **Ichillow** - Développeur principal

## Licence

Ce projet est développé dans un cadre éducatif à l'IUT de Limoges (fr.unilim.iut).