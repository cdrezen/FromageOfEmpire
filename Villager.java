package fromageofempire;
public class Villager 
{
    /**
     * sassieté du villageois
     */
    int full;
    Building home;
    Building workplace;
    //String name;

    VillagerObserver observer;

    public Villager(VillagerObserver observer) 
    {
        this.observer = observer;
        this.full = 10;
    }

    public Villager(VillagerObserver observer, Building home) 
    {
        this.observer = observer;
        this.full = 10;
        this.home = home;
    }

    public Villager(VillagerObserver observer, Building home, Building workplace) 
    {
        this.observer = observer;
        this.full = 10;
        this.home = home;
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

    public Building getHome() {
        return home;
    }

    public void setHome(Building home) {
        if(isHoused()) this.home.removeUser(this);
        this.home = home;
        home.addUser(this);
    }

    public Building getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Building workplace) {
        if(isWorker()) this.workplace.removeUser(this);
        this.workplace = workplace;
        workplace.addUser(this);
    }
}

