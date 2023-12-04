package fromageofempire;

import java.util.ArrayList;

public class ProductionComponent implements BuildingComponent {

    int capacity;
    Resource[] input;
    Resource[] output;
    ArrayList<Villager> workers;
    static ProductionObserver productionObserver;

    public ProductionComponent(int capacity, Resource[] input, Resource[] output) {
        this.capacity = capacity;
        this.input = input;
        this.output = output;
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
            productionObserver.OnProducedResource(this, input, output);
            return;
        }

        double efficiency = getUsersCount()/getCapacity();

        Resource[] produced = output.clone();

        for (Resource product : produced) 
        {
            product.setQuantity((int)(efficiency * product.getQuantity()));
        }

        if(input == null) {
            productionObserver.OnProducedResource(this, produced, input);
            return;
        }

        Resource[] consumed = input.clone();

        for (Resource loss : consumed) 
        {
            loss.setQuantity((int)(efficiency * loss.getQuantity()));
        }

        productionObserver.OnProducedResource(this, produced, consumed);
    }

    @Override
    public int getUsersCount() {
        return workers.size();
    }
    
}
