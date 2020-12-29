package util;

import java.util.*;

public class MapRessourceBundle extends ResourceBundle {
    private final Map<String,Object> info;

    public MapRessourceBundle(Object[] objects){
        info = new HashMap<>();

        for(int i=0;i<objects.length;i++){
            info.put(String.valueOf(i),objects[i]);
        }
    }

    public int size(){
        return info.size();
    }




    @Override
    protected Object handleGetObject(String key) {
        return info.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(info.keySet());
    }
}
