package com.qcadoo.commons.dateTime;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalTime;

import com.google.common.base.Preconditions;

/**
 * Immutable, thread-safe class representing time range. Time ranges with end time before start time are interpreted as ending in
 * the next day (from start do midnight (inclusive) and from midnight to end time).
 * 
 * @author Marcin Kubala
 * @since 1.2.1
 * 
 */
public class TimeRange implements Comparable<TimeRange> {

    private final LocalTime from;

    private final LocalTime to;

    /**
     * Get new Time range
     * 
     * Arguments order
     * 
     * @param from
     * @param to
     */
    public TimeRange(final LocalTime from, final LocalTime to) {
        Preconditions.checkArgument(from != null, "Missing lower bound for time range.");
        Preconditions.checkArgument(to != null, "Missing upper bound for time range.");
        this.from = from;
        this.to = to;
    }

    /**
     * @return true if from is greater than to.
     */
    public boolean startsDayBefore() {
        return to.isBefore(from);
    }

    /**
     * Check if given time is included in this time range. If from & to boundaries is in reversed order (time from is greater than
     * to; startsDayBefore() returns true) then it will be checked that given time is contained in range [from time = midnight
     * (inclusive)] or [midnight - to time].
     * 
     * @param time
     * @return true if this time range contains given time.
     */
    public boolean contains(final LocalTime time) {
        if (startsDayBefore()) {
            return !time.isAfter(from) && time.isBefore(to);
        }
        return !time.isAfter(to) && !time.isBefore(from);
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).append(from).append(to).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TimeRange other = (TimeRange) obj;
        return new EqualsBuilder().append(from, other.from).append(to, other.to).isEquals();
    }

    @Override
    public int compareTo(final TimeRange other) {
        return getFrom().compareTo(other.getFrom());
    }

}