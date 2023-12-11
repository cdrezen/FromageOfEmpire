package fromageofempire;

public enum BuildingType 
{
    WoodenCabin(2, 
        new Production(new Resource(ResourceType.WOOD, 2), new Resource(ResourceType.FOOD, 2)), 
        new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 1)),

    House(4, new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 2), new Resource(ResourceType.STONE, 2)),

    ApartmentBuilding(6, new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50), new Resource(ResourceType.STONE, 50)),

    Farm(2, 
        new Production(new Resource(ResourceType.FOOD, 10)),
        new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 5), new Resource(ResourceType.STONE, 5)),

    Quarry(2, 
        new Production(new Resource(ResourceType.STONE, 4), new Resource(ResourceType.IRON, 4), new Resource(ResourceType.COAL, 4), new Resource(ResourceType.GOLD, 2)),
        new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50)),

    LumberMill(4, 
        new Production(Resource.asArray(new Resource(ResourceType.WOOD, 4)), 
                        Resource.asArray(new Resource(ResourceType.LUMBER, 4))),
        new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50), new Resource(ResourceType.STONE, 50)),

    CementPlant(4, 
        new Production(Resource.asArray(new Resource(ResourceType.STONE, 4), new Resource(ResourceType.COAL, 4)), 
                        Resource.asArray(new Resource(ResourceType.CEMENT, 4))),
        new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50), new Resource(ResourceType.STONE, 50)),

    SteelMill(6, 
        new Production(Resource.asArray(new Resource(ResourceType.IRON, 4), new Resource(ResourceType.COAL, 2)), 
                        Resource.asArray(new Resource(ResourceType.STEEL, 4))),
        new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 100), new Resource(ResourceType.STONE, 50)),
    
    ToolFactory(8, 
        new Production(Resource.asArray(new Resource(ResourceType.STEEL, 4), new Resource(ResourceType.COAL, 4)), 
                        Resource.asArray(new Resource(ResourceType.TOOLS, 4))),
        new Resource(ResourceType.GOLD, 8), new Resource(ResourceType.WOOD, 50), new Resource(ResourceType.STONE, 50)),

    SmallQuarry(2, 
        new Production(new Resource(ResourceType.STONE, 4)),
        new Resource(ResourceType.GOLD, 2), new Resource(ResourceType.WOOD, 25)),
    
    Fromagerie(4, 
        new Production(new Resource(ResourceType.FOOD, 10), new Resource(ResourceType.FOOD, 50)),
        new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50), new Resource(ResourceType.STONE, 50)),

    CoalMine(4, 
        new Production(Resource.asArray(new Resource(ResourceType.LUMBER, 2), new Resource(ResourceType.TOOLS, 2)), 
                        new Resource(ResourceType.COAL, 10)),
        new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.LUMBER, 5), new Resource(ResourceType.STONE, 50), new Resource(ResourceType.CEMENT, 50)),

    GoldFromagerie(10,
            new Production(new Resource(ResourceType.FROMAGEDOR, 1)),
            new Resource(ResourceType.GOLD, 50), new Resource(ResourceType.LUMBER, 50), new Resource(ResourceType.STEEL, 25)),
    ;
    
    int buildTime;
    Resource[] recipe;
    Production production;

    BuildingType(int buildTime, Resource ... recipe) {
        this.buildTime = buildTime;
        this.recipe = recipe;
    }

    BuildingType(int buildTime, Production production, Resource ... recipe) {
        this.buildTime = buildTime;
        this.recipe = recipe;
        this.production = production;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public Resource[] getRecipe() {
        return recipe;
    }

    public Production getProduction() {
        return production;
    }

    public boolean containsProduction() { return (production != null); }
}
