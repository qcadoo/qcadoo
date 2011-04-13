package com.qcadoo.tenant.internal;

import org.springframework.stereotype.Service;

import com.qcadoo.tenant.api.MultiTenantCallback;
import com.qcadoo.tenant.api.MultiTenantService;
import com.qcadoo.tenant.api.Standalone;

@Service
@Standalone
public class DefaultMultiTenantService implements MultiTenantService {

    @Override
    public void doInMultiTenantContext(final MultiTenantCallback callback) {
        callback.invoke();
    }

    @Override
    public void doInMultiTenantContext(final int tenantId, final MultiTenantCallback callback) {
        doInMultiTenantContext(callback);
    }

}
