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

    public boolean isAtMaxCapacity() { return getUsersCount() >= this.capacity; }

    @Override
    public ArrayList<Villager> getWorkers() {
        return this.workers;
    }
    public boolean addWorker(Villager villager) 
    {
        if(isAtMaxCapacity()) return false;
        boolean success = this.workers.add(villager);
        if(success) villager.setWorkplace(this);
        return success;
    }

    public boolean removeWorker(Villager villager) {
        boolean success = this.workers.remove(villager);
        if(success) villager.setWorkplace(null);
        return success;
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
    public void clear() {
        for (Villager villager : workers) {
            villager.setWorkplace(null);
        }
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

    @Override
    public boolean addInhabitant(Villager villager) {
        return false;
    }

    @Override
    public boolean removeInhabitant(Villager villager) {
        // TODO Auto-generated method stub
        return false;
    }
}
