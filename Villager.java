package fromageofempire;
public class Villager 
{
    /**
     * sassieté du villageois
     */
    int full;
    HousingComponent home;
    ProductionComponent workplace;
    //String name;

    VillagerObserver observer;

    public Villager(VillagerObserver observer) 
    {
        this.observer = observer;
        this.full = 2;
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

    public void update()
    {
        this.full -= 1;
        if(full == 0) observer.OnStarving(this);
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
        home.addInhabitant(this);
    }

    public ProductionComponent getWorkplace() {
        return workplace;
    }

    public void setWorkplace(ProductionComponent workplace) {
        if(isWorker()) this.workplace.removeWorker(this);
        this.workplace = workplace;
        workplace.addWorker(this);
    }
}

