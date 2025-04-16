package Util;

import java.util.*;

public class IdGenerator {
    private static final Map<Class<?>, Set<Integer>> mapa_id = new HashMap<>();
    private static final Random rand = new Random();

    public static int generateUniqueId(Class<?> clase) {
        mapa_id.putIfAbsent(clase, new HashSet<>()); // Asegura que tenga Set para esa clase

        Set<Integer> list_id = mapa_id.get(clase);
        int id;

        do {
            id = rand.nextInt(1000000000);
        } while (list_id.contains(id));

        list_id.add(id);
        return id;
    }
}