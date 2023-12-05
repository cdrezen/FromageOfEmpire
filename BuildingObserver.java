package fromageofempire;

public interface BuildingObserver {
    public void OnEmpty(Building source);
    public void OnBuilt(Building source);
}
