package com.qcadoo.model.internal.api;

import java.util.Collection;

public interface DefaultValidatorsProvider {

    Collection<FieldHookDefinition> getMissingValidators(final Iterable<FieldHookDefinition> validators);

}
