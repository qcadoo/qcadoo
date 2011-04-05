package com.qcadoo.view.internal.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qcadoo.plugin.api.Module;

public abstract class ResourceModule extends Module {

    private final ResourceService resourceService;

    public ResourceModule(final ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        resourceService.addResourceModule(this);
    }

    @Override
    public void disable() {
        resourceService.removeResourceModule(this);
    }

    /**
     * Serves resource to response
     * 
     * @param request
     * @param response
     * @return true when resource was served
     */
    public abstract boolean serveResource(final HttpServletRequest request, final HttpServletResponse response);
}
