package fromageofempire;

public class ProductionBuilding extends Building {

    static ProductionObserver productionObserver;

    public ProductionBuilding(int capacity) {
        super(capacity);
        //TODO Auto-generated constructor stub
    }

    public static void setProductionObserver(ProductionObserver _productionObserver)
    {
        productionObserver = _productionObserver;
    }

    @Override
    public void addUser(Villager villager) {
        this.users.add(villager);
        //villager.setWorkplace(this);
    }

    @Override
    public void removeUser(Villager villager) {
        this.users.remove(villager);
        //villager.setWorkplace(null);
    }

    @Override
    public void update() {
        
        if(users.isEmpty()) 
        {
            observer.OnEmpty(this);
            productionObserver.OnEmptyFactory(this);
        }
    }
    
}
