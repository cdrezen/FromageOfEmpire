package fromageofempire;

import java.io.Serializable;

public class Resource implements Serializable {
    private ResourceType type;
    private int quantity;

    public Resource(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public Resource(Resource resource)
    {
        this.type = resource.type;
        this.quantity = resource.quantity;
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

    public boolean setQuantity(int quantity) {
        this.quantity = quantity;
        return this.quantity != 0;
    }

    public void addQuantity(int quantity)
    {
        this.quantity += quantity;
    }

    public boolean removeQuantity(int quantity)
    {
        this.quantity -= quantity;
        if(this.quantity < 0) this.quantity = 0;
        return this.quantity != 0;
    }

    public static Resource[] asArray(Resource ... resources)
    {
        return resources;
    }

    @Override
    public String toString() {
        return type.toString() + ':' + quantity;
    }
}
