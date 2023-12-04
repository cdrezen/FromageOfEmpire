package fromageofempire;

import java.util.ArrayList;

public class HousingComponent implements BuildingComponent {

    static HousingObserver housingObserver;
    ArrayList<Villager> inhabitants;
    int capacity;

    public HousingComponent(int capacity) {
        this.capacity = capacity;
        this.inhabitants = new ArrayList<Villager>();
    }

    public static void setHousingObserver(HousingObserver _housingObserver)
    {
        housingObserver = _housingObserver;
    }
    
    public void addInhabitant(Villager villager) {
        this.inhabitants.add(villager);
        //villager.setHome(this);
    }

    public void removeInhabitant(Villager villager) {
        this.inhabitants.remove(villager);
        //villager.setHome(null);
    }

    @Override
    public void update() {
        
        if(inhabitants.isEmpty()) 
        {
            housingObserver.OnEmptyHousing(this);
        }
    }

    @Override
    public int getUsersCount() {
        return inhabitants.size();
    }
}
