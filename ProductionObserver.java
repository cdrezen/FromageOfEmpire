package fromageofempire;

public interface ProductionObserver {
    public void OnEmptyFactory(ProductionComponent source);
    public void OnProducedResource(ProductionComponent source, Resource[] produced, Resource[] cost);
}
