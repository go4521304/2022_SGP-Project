package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class Recycle
{
    private static HashMap<Class, ArrayList<GameObject>> recycle = new HashMap<>();
    public static void init() { recycle.clear(); }
    public static GameObject get (Class clazz)
    {
        ArrayList<GameObject> bin = recycle.get(clazz);
        if (bin == null) return null;
        if (bin.size() == 0) return null;
        return bin.remove(0);
    }

    public static void add(GameObject object) {
        Class clazz = object.getClass();
        ArrayList<GameObject> bin = recycle.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycle.put(clazz, bin);
        }
        if (bin.indexOf(object) >= 0) {
            // already exists in the recycle bin
            return;
        }
        bin.add(object);
    }
}
