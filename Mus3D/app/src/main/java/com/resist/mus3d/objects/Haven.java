package com.resist.mus3d.objects;

import java.util.HashMap;
import java.util.Map;

public class Haven {
    private static Map<String, Haven> harbours = new HashMap<>();
    private String id;
    private String name;

    private Haven(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Haven cacheHarbour(String id, String name) {
        if (!harbours.containsKey(id)) {
            harbours.put(id, new Haven(id, name));
        }
        return harbours.get(id);
    }

    public String getName() {
        return name;
    }
}
