package fromageofempire;

public interface BuildingComponent {
    public void onBuilt();
    public void update();
    public int getUsersCount();
    @Override
    String toString();
    //public void setObserver(BuildingObserver observer);
}
