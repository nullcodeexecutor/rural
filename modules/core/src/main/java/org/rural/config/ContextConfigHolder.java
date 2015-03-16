package org.rural.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuantao on 2015/3/15.
 */
public class ContextConfigHolder {
    private Map<String, Object> contextConfigs;

    public ContextConfigHolder() {
        this.contextConfigs = new HashMap<String, Object>();
    }

    public void add(String key, Object value) {
        contextConfigs.put(key, value);
    }

    public Object get(String key) {
       return contextConfigs.get(key);
    }

}
