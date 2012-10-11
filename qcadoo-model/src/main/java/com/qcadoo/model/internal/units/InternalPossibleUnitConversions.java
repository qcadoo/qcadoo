package com.qcadoo.model.internal.units;

import com.qcadoo.model.api.units.PossibleUnitConversions;
import com.qcadoo.model.api.units.UnitConversion;

public interface InternalPossibleUnitConversions extends PossibleUnitConversions {

    void addConversion(final UnitConversion unitConversion);
}
