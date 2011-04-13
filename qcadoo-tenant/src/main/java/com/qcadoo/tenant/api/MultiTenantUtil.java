package com.qcadoo.tenant.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiTenantUtil {

    @Autowired
    private MultiTenantService multiTenantService;

    private static MultiTenantUtil instance;

    @PostConstruct
    public void init() {
        initialise(this);
    }

    private static void initialise(final MultiTenantUtil multiTenantUtil) {
        MultiTenantUtil.instance = multiTenantUtil;
    }

    public static void doInMultiTenantContext(final MultiTenantCallback callback) {
        MultiTenantUtil.instance.multiTenantService.doInMultiTenantContext(callback);
    }

    public static void doInMultiTenantContext(final int tenantId, final MultiTenantCallback callback) {
        MultiTenantUtil.instance.multiTenantService.doInMultiTenantContext(tenantId, callback);
    }

}
