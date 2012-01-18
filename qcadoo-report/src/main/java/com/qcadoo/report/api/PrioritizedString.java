package com.qcadoo.report.api;

/**
 * A String with priority option for sorting
 * 
 */
public class PrioritizedString implements Comparable<PrioritizedString> {

    private Pair<String, Integer> pair;

    public PrioritizedString(final String string) {
        pair = new Pair<String, Integer>(string, 0);
    }

    public PrioritizedString(final String string, final Integer priority) {
        pair = new Pair<String, Integer>(string, priority);
    }

    public String getString() {
        return pair.getKey();
    }

    public void setString(final String string) {
        pair.setKey(string);
    }

    public Integer getPriority() {
        return pair.getValue();
    }

    public void setPriority(final Integer priority) {
        pair.setValue(priority);
    }

    @Override
    public boolean equals(final Object obj) {
        return pair.equals(obj);
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }

    @Override
    public String toString() {
        return pair.toString();
    }

    @Override
    public int compareTo(final PrioritizedString o) {
        String a = getPriority() + getString();
        String b = o.getPriority() + o.getString();
        return a.compareTo(b);
    }
}
