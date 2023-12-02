package fromageofempire;
public class Villager 
{
    /**
     * sassieté du villageois
     */
    int full;

    Building home;
    Building workplace;

    VillagerObserver observer;

    public Villager() 
    {
        this.full = 1000;
    }

    public Villager(Building home) 
    {
        this.full = 1000;
        this.home = home;
    }

    public Villager(Building home, Building workplace) 
    {
        this.full = 1000;
        this.home = home;
        this.workplace = workplace;
    }

    public void update()
    {
        this.full -= 1;
        if(full == 0) observer.OnStarving();
    }

    /**
     * au chomage?
     */
    public boolean isWorker()
    {
        return (this.workplace != null);
    }

    public Building getHome() {
        return home;
    }

    public void setHome(Building home) {
        this.home = home;
    }

    public Building getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Building workplace) {
        this.workplace = workplace;
    }
}

