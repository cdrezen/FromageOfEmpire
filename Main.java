package fromageofempire;

import java.util.Arrays;
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
        scanner.close(); // Fermer le scanner à la fin de la boucle de jeu

    }

    private static void initializeGame() {
        // Ici, vous pouvez initialiser tout ce qui est nécessaire pour commencer une
        // nouvelle partie.
        // Par exemple, la configuration initiale des ressources, la mise en place du
        // terrain de jeu, etc.
        System.out.println("Initialisation du jeu...");

        // Exemple : Initialisation des ressources de base

        // Autres initialisations si nécessaire
    }

    private static void runGameLoop() {
        // La boucle de jeu principal
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Entrez une action: ('q' pour quitter, 'Entrée' pour passer le tour)");

            // Lire l'entrée de l'utilisateur
            String[] input = scanner.nextLine().split(" ");
            String command = input[0];
            String[] params = Arrays.copyOfRange(input, 1, input.length);

            switch (command) {
                case "q":
                    System.out.println("Quitting the game...");
                    isRunning = false; // Mettre fin à la boucle de jeu
                    return;
                case "build":
                    GameManager.getInstance().buildCommand(params[0]);
                    break;
                case "":
                    break;
            
                default:
                    System.out.println("Entrée non reconnue.");
                    continue;
            }

            System.out.println("Processing turn...");
            // Faire tourner un tour le jeu
            updateGame();
            // Afficher l'état actuel du jeu
            displayGameState();
            checkGameOver();
        }
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
