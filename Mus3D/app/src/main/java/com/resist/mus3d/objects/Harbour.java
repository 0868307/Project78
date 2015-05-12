package com.resist.mus3d.objects;

import java.util.HashMap;
import java.util.Map;

public class Harbour {
    private static Map<String, Harbour> harbours = new HashMap<>();
	private String id;
	private String name;

	private Harbour(String id, String name) {
		this.id = id;
		this.name = name;
	}

    public static Harbour cacheHarbour(String id, String name) {
        if(!harbours.containsKey(id)) {
            harbours.put(id, new Harbour(id, name));
        }
        return harbours.get(id);
    }

    public String getName() {
        return name;
    }
}
