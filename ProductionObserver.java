package fromageofempire;

public interface ProductionObserver {
    public void OnEmptyFactory(ProductionComponent source);
    public void OnBuiltFactory(ProductionComponent source);
    public void OnProducedResource(ProductionComponent source, Production production);
}
