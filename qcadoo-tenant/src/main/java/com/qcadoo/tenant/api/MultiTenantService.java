package com.qcadoo.tenant.api;

public interface MultiTenantService {

    void doInMultiTenantContext(final MultiTenantCallback callback);

}