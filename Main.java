package fromageofempire;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner scanner;

    public static void main(String[] args) {
        // Initialisation du gestionnaire de jeu
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
        System.out.println(" ________ ________  ________  _____ ______   ________  ________  _______           ________  ________      _______   _____ ______   ________  ___  ________  _______      \n" +
                "|\\  _____\\\\   __  \\|\\   __  \\|\\   _ \\  _   \\|\\   __  \\|\\   ____\\|\\  ___ \\         |\\   __  \\|\\  _____\\    |\\  ___ \\ |\\   _ \\  _   \\|\\   __  \\|\\  \\|\\   __  \\|\\  ___ \\     \n" +
                "\\ \\  \\__/\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\\\\\__\\ \\  \\ \\  \\|\\  \\ \\  \\___|\\ \\   __/|        \\ \\  \\|\\  \\ \\  \\__/     \\ \\   __/|\\ \\  \\\\\\__\\ \\  \\ \\  \\|\\  \\ \\  \\ \\  \\|\\  \\ \\   __/|    \n" +
                " \\ \\   __\\\\ \\   _  _\\ \\  \\\\\\  \\ \\  \\\\|__| \\  \\ \\   __  \\ \\  \\  __\\ \\  \\_|/__       \\ \\  \\\\\\  \\ \\   __\\     \\ \\  \\_|/_\\ \\  \\\\|__| \\  \\ \\   ____\\ \\  \\ \\   _  _\\ \\  \\_|/__  \n" +
                "  \\ \\  \\_| \\ \\  \\\\  \\\\ \\  \\\\\\  \\ \\  \\    \\ \\  \\ \\  \\ \\  \\ \\  \\|\\  \\ \\  \\_|\\ \\       \\ \\  \\\\\\  \\ \\  \\_|      \\ \\  \\_|\\ \\ \\  \\    \\ \\  \\ \\  \\___|\\ \\  \\ \\  \\\\  \\\\ \\  \\_|\\ \\ \n" +
                "   \\ \\__\\   \\ \\__\\\\ _\\\\ \\_______\\ \\__\\    \\ \\__\\ \\__\\ \\__\\ \\_______\\ \\_______\\       \\ \\_______\\ \\__\\        \\ \\_______\\ \\__\\    \\ \\__\\ \\__\\    \\ \\__\\ \\__\\\\ _\\\\ \\_______\\\n" +
                "    \\|__|    \\|__|\\|__|\\|_______|\\|__|     \\|__|\\|__|\\|__|\\|_______|\\|_______|        \\|_______|\\|__|         \\|_______|\\|__|     \\|__|\\|__|     \\|__|\\|__|\\|__|\\|_______|\n" +
                "                                                                                                                                                                          \n" +
                "                                                                                                                                                                          \n" +
                "                                                                                                                                                                          ");

        System.out.print("Bienvenue dans \"Fromage of Empire\" !\n" +
                "\n" +
                "Dans ce jeu de stratégie et de gestion, vous êtes le chef d'un village et votre objectif est de développer votre village, de gérer ses ressources et de veiller au bien-être de ses habitants.\n"
            +   "\nBut du jeu :\n" +
                    "- Construire et améliorer des bâtiments pour développer le village.\n" +
                    "- Gérer les ressources telles que l'or, la nourriture, le bois et la pierre.\n" +
                    "- Assurer la survie et le bonheur de vos villageois.\n" +
                    "- Prendre des décisions stratégiques pour équilibrer la croissance, les ressources et la population.\n\n" +
                    "Pour finir le jeu, vous devrez obtenir le FROMAGE D'OR.\n\n" +
                "Caractéristiques principales :\n" +
                    "- Construction : Bâtissez des maisons, des fermes, des carrières et bien d'autres structures.\n" +
                    "- Gestion des Ressources : Collectez et gérez les ressources essentielles pour la survie de votre village.\n" +
                    "- Durabilité : Surveillez l'indice de durabilité de votre village pour éviter la famine et d'autres crises.\n" +
                    "\n" +
                "Conseils pour commencer :\n" +
                    "- Commencez par construire des maisons pour loger vos villageois.\n" +
                    "- Assurez une production constante de nourriture pour éviter la famine.\n" +
                    "- Nous vous conseillons (FORTEMENT) d'utiliser en premier lieu la commande \"help\" pour comprendre.\n"+
                "Bonne chance dans la construction et la gestion de votre empire !");

    }

    private static void runGameLoop() {
        // La boucle de jeu principal
        GameManager gameManager = GameManager.getInstance();
        gameManager.start();

        while (gameManager.isRunning()) {
            System.out.printf("\n");
            System.out.println("Entrez une action: ('q' pour quitter, 'Entrée' pour passer le tour)");

            // Lire l'entrée de l'utilisateur
            String[] input = scanner.nextLine().split(" ");
            String command = input[0];

            if(command.equals(""))
            {
                System.out.println("Processing turn...");
                // Faire tourner un tour le jeu
                updateGame();
                // Afficher l'état actuel du jeu
                displayGameState();
                if(checkGameOver()) gameManager.stop();
            }
            else 
            {
                String[] params = Arrays.copyOfRange(input, 1, input.length);
                interpret(command, params);
            }
            System.out.printf("\n");

        }
    }

    private static boolean interpret(String str_cmd, String[] params) 
    {
        for (CommandType type : CommandType.values()) 
        {
            if (str_cmd.equalsIgnoreCase(type.toString().toLowerCase())) {
                type.execute(params);
                return true;
            }
        }

        System.out.println("Entrée non reconnue.");
        return false;
    }

    private static void updateGame() {
        // Ici, mettez à jour la logique de votre jeu.
        // Par exemple, gérer les actions des joueurs, les événements du jeu, etc.
        GameManager.getInstance().update();
    }

    private static void displayGameState() {
        // Affiche l'état actuel du jeu.
        // Par exemple, les ressources actuelles, l'état des bâtiments, etc.
        GameManager.getInstance().displayVillagers();
        GameManager.getInstance().displayResources();
        GameManager.getInstance().displayBuildings();
    }

    private static boolean checkGameOver() {
        // Déterminer si les conditions de fin de partie sont remplies.
        boolean lost = GameManager.getInstance().isGameLost();
        if(lost) System.out.println("Game over, you lose!");
        boolean won = GameManager.getInstance().isGameWon();
        if(won) System.out.println("Game over, you won!");

        return lost || won;
    }
}
