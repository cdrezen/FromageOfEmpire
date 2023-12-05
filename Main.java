package fromageofempire;

import java.util.Scanner;

public class Main {

    static Scanner scanner;

    public static void main(String[] args) {
        // Initialisation du gestionnaire de jeu
        GameManager gameManager = GameManager.getInstance();
        scanner = new Scanner(System.in);

        // Initialisation des ressources et des paramètres de départ
        initializeGame();

        // Boucle principale du jeu (peut être remplacée par votre propre logique de
        // jeu)
        runGameLoop();
    }

    private static void initializeGame() {
        // Ici, vous pouvez initialiser tout ce qui est nécessaire pour commencer une
        // nouvelle partie.
        // Par exemple, la configuration initiale des ressources, la mise en place du
        // terrain de jeu, etc.
        System.out.println("Initialisation du jeu...");

        // Exemple : Initialisation des ressources de base
        GameManager.getInstance().initializeResources();

        // Autres initialisations si nécessaire
    }

    private static void runGameLoop() {
        // La boucle de jeu principal
        boolean isRunning = true;

        while (isRunning) {
            // Mettre à jour le jeu
            updateGame();

            // Afficher l'état actuel du jeu
            displayGameState();

            scanner.nextLine();

            // Vérifier les conditions de fin de jeu
            isRunning = checkGameOver();
        }

        while (isRunning) {
            System.out.println("Entrez une action: ('q' pour quitter, 'Entrée' pour passer le tour)");

            // Lire l'entrée de l'utilisateur
            String input = scanner.nextLine();

            switch (input) {
                case "q":
                    System.out.println("Quitting the game...");
                    isRunning = false; // Mettre fin à la boucle de jeu
                    break;
                case "build":
                    
                    break;
            
                default:
                    break;
            }
            // Effectuer une action en fonction de l'entrée
            if ("q".equalsIgnoreCase(input)) {
                
            } else if ("p".equalsIgnoreCase(input)) {
                // Ici, vous pouvez ajouter la logique pour traiter un tour de jeu
                // Mettre à jour le jeu
                updateGame();

                // Afficher l'état actuel du jeu
                displayGameState();
                System.out.println("Processing turn...");
            } else if ("c".equalsIgnoreCase(input)) {

                GameManager.getInstance().addBuilding("FARM");
                BuildingFactory.updateGame();

                // Afficher l'état actuel du jeu
                displayGameState();
                System.out.println("Processing turn...");
            } else {
                System.out.println("Entrée non reconnue.");
            }

        }

        System.out.println("Fin de la partie.");
        scanner.close(); // Fermer le scanner à la fin de la boucle de jeu

        System.out.println("Fin de la partie.");
    }

    private static void updateGame() {
        // Ici, mettez à jour la logique de votre jeu.
        // Par exemple, gérer les actions des joueurs, les événements du jeu, etc.
        GameManager.getInstance().update();
    }

    private static void displayGameState() {
        // Affiche l'état actuel du jeu.
        // Par exemple, les ressources actuelles, l'état des bâtiments, etc.
        GameManager.getInstance().displayResources();
        GameManager.getInstance().displayBuildings();
    }

    private static boolean checkGameOver() {
        // Déterminer si les conditions de fin de partie sont remplies.
        // Retourner 'false' pour terminer la boucle de jeu.
        return true; // Modifier cette condition en fonction de la logique de votre jeu
    }
}
