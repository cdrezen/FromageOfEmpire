package fromageofempire;

public class Production 
{
    Resource[] input;
    Resource[] output;
    double tmp_efficiency = 0;
    boolean requiresMoreEfficiency = false;

    public Production(Resource[] input, Resource[] output) 
    {
        this.input = input;
        this.output = output;
    }

    public Production(Resource ... output) 
    {
        this.input = null;
        this.output = output;
    }

    public Resource[] getInput() {
        return input;
    }

    public Resource[] getOutput() {
        return output;
    }

    public boolean hasInput() { return (input != null); }

    private Resource[] calculate(Resource[] resources, double efficiency)
    {
        Resource[] copy = new Resource[resources.length];

        for (int i = 0; i < resources.length; i++) 
        {   
            ResourceType type = resources[i].getType();
            int quantity = resources[i].getQuantity();

            quantity *= tmp_efficiency + efficiency;
            //System.err.println("qty:"+quantity +"base:"+resources[i].getQuantity());
            if(!requiresMoreEfficiency && quantity == 0)
            {
                requiresMoreEfficiency = true;
                return null;
            }
            copy[i] = new Resource(type, quantity);
        }

        return copy;
    }

    public Production produce(double efficiency)
    {
        Resource[] produced = calculate(output, efficiency);
        Resource[] consumed = null;

        if(produced != null && hasInput()) consumed = calculate(input, efficiency);

        if(requiresMoreEfficiency) 
        { 
            tmp_efficiency += efficiency;
            requiresMoreEfficiency = false;
        }
        else tmp_efficiency = 0;

        return new Production(consumed, produced);
    }
}
