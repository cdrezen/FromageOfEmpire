package fromageofempire;

public class Production 
{
    Resource[] input;
    Resource[] output;

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

    public Production produce(double efficiency)
    {
        Resource[] produced = output.clone();

        for (Resource out : produced) 
        {
            int quantity = (int)efficiency * out.getQuantity();
            //if(quantity == 0 && out.getQuantity() > 1) quantity = 1;
            out.setQuantity(quantity);
        }

        if(!hasInput()) return new Production(produced);

        Resource[] consumed = input.clone();

        for (Resource loss : consumed) 
        {
            int quantity = (int)efficiency * loss.getQuantity();
            //if(quantity == 0 && loss.getQuantity() > 1) quantity = 1;
            loss.setQuantity(quantity);
        }

        return new Production(produced, consumed);
    }
}
