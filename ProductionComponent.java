package fromageofempire;

import java.util.ArrayList;

public class ProductionComponent implements BuildingComponent {

    int capacity;
    Production production;
    ArrayList<Villager> workers;
    static ProductionObserver productionObserver;

    public ProductionComponent(int capacity, Production production) {
        this.capacity = capacity;
        this.production = production;
        workers = new ArrayList<Villager>();
    }

    public static void setProductionObserver(ProductionObserver _productionObserver)
    {
        productionObserver = _productionObserver;
    }

    public int getCapacity() { return this.capacity; }

    public boolean isAtMaxCapacity() { return getUsersCount() > this.capacity; }

    public void addWorker(Villager villager) {
        this.workers.add(villager);
        //villager.setWorkplace(this);
    }

    public void removeWorker(Villager villager) {
        this.workers.remove(villager);
        //villager.setWorkplace(null);
    }

    @Override
    public void update() {
        
        if(workers.isEmpty()) 
        {
            productionObserver.OnEmptyFactory(this);
            return;
        }

        if(isAtMaxCapacity()) 
        {
            productionObserver.OnProducedResource(this, production);
            return;
        }
        else
        {
            productionObserver.OnEmptyFactory(this);
        }

        double efficiency = (double)getUsersCount()/getCapacity();
        Production produced = production.produce(efficiency);
        if(produced.getOutput() != null) productionObserver.OnProducedResource(this, produced);
    }

    @Override
    public int getUsersCount() {
        return workers.size();
    }

    @Override
    public void onBuilt() {
        // TODO Auto-generated method stub
        productionObserver.OnBuiltFactory(this);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "workers: " + getUsersCount();
    }
}
