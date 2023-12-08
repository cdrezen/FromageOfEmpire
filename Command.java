package fromageofempire;

/**
 * Command
 */
@FunctionalInterface
public interface Command 
{
    void execute(String[] args);
}