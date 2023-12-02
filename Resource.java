package fromageofempire;

public class Resource {
    private ResourceType type;
    private int quantity;

    public Resource(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    // Getters et setters
    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity)
    {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity)
    {
        this.quantity -= quantity;
        //if(this.quantity < 0) this.quantity = 0;
    }
}
