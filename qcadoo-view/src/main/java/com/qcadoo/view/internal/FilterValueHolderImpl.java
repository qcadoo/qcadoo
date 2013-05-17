package com.qcadoo.view.internal;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;

public class FilterValueHolderImpl implements FilterValueHolder {

    private static final String VALUE_DOES_NOT_EXISTS = "Value of key '%s' does not exists.";

    private static final String JSON_ERROR = "Can't read JSON initiaization parameters.";

    private static final String DIFFARENT_VALUE_TYPE = "Value associated with key '%s' is not '%s' type.";

    private static final String INVALID_TYPE = "Value is not '%s' type.";

    private final JSONObject filterValue;

    private boolean empty;

    public FilterValueHolderImpl() {
        this.filterValue = new JSONObject();
        empty = true;
    }

    public FilterValueHolderImpl(FilterValueHolder holder) {
        this.filterValue = holder.toJSON();
        empty = true;
    }

    @Override
    public JSONObject toJSON() {

        try {
            String[] keys = JSONObject.getNames(filterValue);
            return keys != null ? new JSONObject(filterValue, keys) : new JSONObject();
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);

        }
    }

    @Override
    public void put(String key, Integer value) {
        try {
            filterValue.put(key, value.toString());
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void put(String key, BigDecimal value) {
        try {
            filterValue.put(key, value.toString());
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void put(String key, Long value) {
        try {
            filterValue.put(key, value);
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void put(String key, String value) {
        try {
            filterValue.put(key, value);
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void put(String key, boolean value) {
        try {
            filterValue.put(key, value);
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean getBoolean(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            return filterValue.getBoolean(key);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Integer getInteger(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            return new Integer(filterValue.getString(key));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(String.format(INVALID_TYPE, Integer.class.getName()));
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            return new BigDecimal(filterValue.getString(key));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(String.format(INVALID_TYPE, BigDecimal.class.getName()));
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Long getLong(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            return filterValue.getLong(key);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public String getString(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            return filterValue.getString(key);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean has(String key) {
        return filterValue.has(key);
    }

    @Override
    public Object remove(String key) {
        return filterValue.remove(key);
    }

    @Override
    public void put(String key, List<Long> value) {
        try {
            JSONArray array = new JSONArray(value);
            filterValue.put(key, array);
            empty = false;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<Long> getListOfLongs(String key) {
        if (!filterValue.has(key)) {
            throw new IllegalArgumentException(String.format(VALUE_DOES_NOT_EXISTS, key));
        }

        try {
            Object value = filterValue.get(key);
            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                int length = array.length();

                List<Long> list = Lists.newArrayListWithCapacity(length);
                for (int i = 0; i < length; ++i) {
                    list.add(array.getLong(i));
                }

                return list;
            } else {
                throw new IllegalStateException(String.format(DIFFARENT_VALUE_TYPE, key, List.class.getName()));
            }
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void initialize(final JSONObject jsonObject) {
        clearHolder();
        Iterator<?> iterator = jsonObject.keys();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                filterValue.put(key, jsonObject.get(key));
                empty = false;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException(JSON_ERROR, e);
        }
    }

    private void clearHolder() {
        Iterator<?> iterator = filterValue.keys();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }
}
