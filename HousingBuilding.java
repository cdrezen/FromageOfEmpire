package fromageofempire;

public class HousingBuilding extends Building {

    protected static HousingObserver housingObserver;

    public HousingBuilding(int capacity) {
        super(capacity);
        //TODO Auto-generated constructor stub
    }

    public static void setHousingObserver(HousingObserver _housingObserver)
    {
        housingObserver = _housingObserver;
    }
    
    @Override
    public void addUser(Villager villager) {
        this.users.add(villager);
        //villager.setHome(this);
    }

    @Override
    public void removeUser(Villager villager) {
        this.users.remove(villager);
        //villager.setHome(null);
    }

    @Override
    public void update() {
        
        if(users.isEmpty()) 
        {
            observer.OnEmpty(this);
            housingObserver.OnEmptyHousing(this);
        }
    }
}
