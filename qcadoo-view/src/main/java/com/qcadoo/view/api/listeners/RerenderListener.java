package com.qcadoo.view.api.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;

@Service
public class RerenderListener {

    private static final Logger LOG = LoggerFactory.getLogger(RerenderListener.class);

    public void onChangeRerender(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rerender invoked on change component {} to value {}.", state.getName(), state.getFieldValue());
        }
    }
}
