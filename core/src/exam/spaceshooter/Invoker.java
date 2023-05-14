package exam.spaceshooter;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public Map<String, ICommand> commandes = new HashMap<>();

    public void addCommand(String ref, ICommand cmd){
           commandes.put(ref,cmd);
    }

    public void invoquer(String ref) {
        ICommand cmd = commandes.get(ref);
        if(cmd!=null) cmd.execute();
    }

}
