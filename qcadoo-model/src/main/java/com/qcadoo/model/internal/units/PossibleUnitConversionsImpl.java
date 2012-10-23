package com.qcadoo.model.internal.units;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.units.UnitConversion;
import com.qcadoo.model.constants.UnitConversionItemFields;

public final class PossibleUnitConversionsImpl implements InternalPossibleUnitConversions {

    private final Map<String, BigDecimal> targetUnitToFactor;

    private final String unitFrom;

    private final MathContext mathContext;

    private final DataDefinition unitConversionItemDD;

    public PossibleUnitConversionsImpl(final String unitFrom, final MathContext mathContext,
            final DataDefinition unitConversionItemDD) {
        Preconditions.checkNotNull(unitFrom);
        Preconditions.checkNotNull(mathContext);
        Preconditions.checkNotNull(unitConversionItemDD);

        this.unitFrom = unitFrom;
        this.mathContext = mathContext;
        this.unitConversionItemDD = unitConversionItemDD;
        this.targetUnitToFactor = Maps.newHashMap();
    }

    @Override
    public void addConversion(final UnitConversion unitConversion) {
        Preconditions.checkArgument(unitFrom.equals(unitConversion.getUnitFrom()), "Wrong source unit!");
        final String unit = unitConversion.getUnitTo();
        if (!isDefinedFor(unit)) {
            targetUnitToFactor.put(unitConversion.getUnitTo(), applyMathContext(unitConversion.getRatio()));
        }
    }

    private BigDecimal applyMathContext(final BigDecimal value) {
        return value.setScale(mathContext.getPrecision(), mathContext.getRoundingMode());
    }

    @Override
    public boolean isEmpty() {
        return targetUnitToFactor.isEmpty();
    }

    @Override
    public boolean isDefinedFor(final String unit) {
        return targetUnitToFactor.containsKey(unit);
    }

    @Override
    public Set<String> getSupportedUnits() {
        return Collections.unmodifiableSet(targetUnitToFactor.keySet());
    }

    @Override
    public BigDecimal convertTo(final BigDecimal quantityFrom, final String unitTo) {
        final BigDecimal ratio = targetUnitToFactor.get(unitTo);
        Preconditions.checkNotNull(ratio, "Conversion " + unitFrom + " --> " + unitTo + " is not defined!");
        return quantityFrom.multiply(ratio);
    }

    @Override
    public String getUnitFrom() {
        return unitFrom;
    }

    @Override
    public Map<String, BigDecimal> asUnitToConversionMap() {
        return Collections.unmodifiableMap(targetUnitToFactor);
    }

    @Override
    public List<Entity> asEntities(final String ownerFieldName, final Entity ownerEntity) {
        final List<Entity> entities = Lists.newArrayList();
        for (final Map.Entry<String, BigDecimal> unitToFactorMapEntry : targetUnitToFactor.entrySet()) {
            final Entity entity = buildUnitConversionItem(unitToFactorMapEntry.getKey(), unitToFactorMapEntry.getValue());
            entity.setField(ownerFieldName, ownerEntity);
            entities.add(entity);
        }
        return entities;
    }

    private Entity buildUnitConversionItem(final String unitTo, final BigDecimal ratio) {
        final Entity unitConversionItem = unitConversionItemDD.create();
        unitConversionItem.setField(UnitConversionItemFields.UNIT_FROM, unitFrom);
        unitConversionItem.setField(UnitConversionItemFields.UNIT_TO, unitTo);
        unitConversionItem.setField(UnitConversionItemFields.QUANTITY_FROM, BigDecimal.ONE);
        unitConversionItem.setField(UnitConversionItemFields.QUANTITY_TO, BigDecimal.ONE.multiply(ratio, mathContext));
        return unitConversionItem;
    }

}
