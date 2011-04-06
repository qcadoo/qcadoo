package com.qcadoo.tenant.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiTenantUtil {

    private static MultiTenantService multiTenantService;

    @Autowired
    public MultiTenantUtil(final MultiTenantService multiTenantService) {
        MultiTenantUtil.multiTenantService = multiTenantService;
    }

    public static void doInMultiTenantContext(final MultiTenantCallback callback) {
        MultiTenantUtil.multiTenantService.doInMultiTenantContext(callback);
    }

}
