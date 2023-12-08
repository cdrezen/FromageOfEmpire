package fromageofempire;

import java.util.Arrays;

public enum CommandType implements Command
{
    BUILD(CommandType::buildCommand),
    DESTROY(CommandType::destroyCommand),
    HIRE(CommandType::hireCommand),
    FIRE(CommandType::fireCommand),
    AUTOFILL(CommandType::autofill),
    Q(CommandType::quit);

    Command command;

    CommandType(Command command)
    {
        this.command = command;
    }

    @Override
    public void execute(String[] args) {
        command.execute(args);
    }

    static void buildCommand(String[] params)
    {
        String buildingName = params[0];

        if(buildingName.equals("help"))
        {
            System.out.println("list of buildings:");
            for (BuildingType type : BuildingType.values()) {
                System.out.printf("%s %s\n", type.toString().toLowerCase(), Arrays.toString(type.getRecipe()));
            }
            return;
        }

        BuildingType type = null;
        for (BuildingType t : BuildingType.values()) {
            if(t.toString().toLowerCase().equalsIgnoreCase(buildingName)) { 
                type = t;
                break;
            }
        }

        if(type != null) GameManager.getInstance().build(type);
        else buildCommand(new String[]{"help"});
    }

    static int parseInt(String str)
    {
        int res = -1;
        try {
            res = Integer.parseInt(str);
        } catch (Exception e) { System.err.println("Invalid param.");}
        return res;
    }

    static int[] parseIndexAndSize(String[] params)
    {
        int index = parseInt(params[0]);
        if(index < 0) return null;
        int nb = -1;
        if(params.length > 1) nb = parseInt(params[1]);
        return new int[]{ index, nb };
    }

    static void destroyCommand(String[] params)
    {
        int index = parseInt(params[0]);
        if(index > 0) GameManager.getInstance().destroy(index);
    }

    static void hireCommand(String[] params)
    {
        int[] parsed = parseIndexAndSize(params);
        if(parsed != null) GameManager.getInstance().hireWorkers(parsed[0], parsed[1]);
    }

    static void fireCommand(String[] params)
    {
        int[] parsed = parseIndexAndSize(params);
        if(parsed != null) GameManager.getInstance().fireWorkers(parsed[0], parsed[1]);
    }

    static void autofill(String[] params)
    {
        GameManager.getInstance().switch_autofill();
    }

    static void quit(String[] params)
    {
        System.out.println("Quitting the game...");
        GameManager.getInstance().stop();
    }
}
