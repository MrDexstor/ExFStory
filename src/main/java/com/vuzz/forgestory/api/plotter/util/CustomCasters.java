package com.vuzz.forgestory.api.plotter.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class CustomCasters {
    public static <K,V> HashMap<K,V> setToHashMap(Set<Entry<K,V>> set) {
        HashMap<K,V> mapFromSet = new HashMap<K,V>();
        for(Entry<K,V> entry : set)
        {
            mapFromSet.put(entry.getKey(), entry.getValue());
        }
        return mapFromSet;
    }
}
