package fromageofempire;

public enum ResourceType {
    // Ajoutez d'autres types de ressources selon les besoins
    GOLD("\uD83D\uDCB0"),
    FOOD("\uD83C\uDF56"),
    WOOD("\uD83C\uDF32"), // Emoji pour le bois
    STONE("\uD83E\uDEA8"), // Emoji pour la pierre
    IRON("\u2699\uFE0F"),
    COAL("\u26AB"),
    STEEL("\u26D3\uFE0F"),
    CEMENT("\uD83E\uDDF1"),
    LUMBER("\uD83E\uDE9C"),
    TOOLS("\uD83D\uDEE0\uFE0F"),
    FROMAGEDOR("\uD83E\uDDC0");

    private final String emoji;

    ResourceType(String emoji) {
        this.emoji = emoji;
    }

    public String toString() {
        return this.name() + emoji+":";
    }

}
