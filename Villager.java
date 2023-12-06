package fromageofempire;
public class Villager 
{
    /**
     * sassieté du villageois
     */
    static final int FOOD_CONSUMPTION = 1;
    static final int MAX_STARVATION_DURATION = 4;

    static Resource food;
    int starvation;
    HousingComponent home;
    ProductionComponent workplace;
    //String name;


    VillagerObserver observer;

    public Villager(VillagerObserver observer) 
    {
        this.observer = observer;
        this.starvation = 0;
    }

    public Villager(VillagerObserver observer, HousingComponent home) 
    {
        this(observer);
        this.home = home;
    }

    public Villager(VillagerObserver observer, HousingComponent home, ProductionComponent workplace) 
    {
        this(observer, home);
        this.workplace = workplace;
    }

    public static void setFoodSource(Resource source)
    {
        food = source;
    }

    public void eat()
    {
        if(food.getQuantity() == 0)
        {
            starvation++;
            observer.OnStarving(this);
            if(starvation == MAX_STARVATION_DURATION) observer.OnDeath(this);
        }
        else food.removeQuantity(FOOD_CONSUMPTION);
    }

    public void build()
    {

    }

    public void work()
    {

    }

    public void update()
    {
        eat();
    }

    /**
     * au chomage?
     */
    public boolean isWorker()
    {
        return (this.workplace != null);
    }

    public boolean isHoused()
    {
        return (this.home != null);
    }

    public HousingComponent getHome() {
        return home;
    }

    public void setHome(HousingComponent home) {
        if(isHoused()) this.home.removeInhabitant(this);
        this.home = home;
        if(home != null) home.addInhabitant(this);
    }

    public ProductionComponent getWorkplace() {
        return workplace;
    }

    public void setWorkplace(ProductionComponent workplace) {
        if(isWorker()) this.workplace.removeWorker(this);
        this.workplace = workplace;
        if(workplace != null) workplace.addWorker(this);
    }
}

