package util;

import java.util.HashMap;
import java.util.Map;

public class ParameterMap extends HashMap {

    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap() {
        super();
    }

    public ParameterMap(Map m) {
        super(m);
    }

    private boolean locked = false;
    private static final StringManager sm = StringManager.getManager("connector.http");

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public Object put(Object key, Object value) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.licked"));
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map m) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.licked"));
        }
        super.putAll(m);
    }

    @Override
    public Object remove(Object key) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.licked"));
        }
        return super.remove(key);
    }

    @Override
    public void clear() {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.licked"));
        }
        super.clear();
    }
}
