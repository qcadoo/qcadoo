package com.qcadoo.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class TransactionMockAwareTest {

    private static final TransactionManagerStubbingUtil TX_MANAGER_STUBBING_UTIL = new TransactionManagerStubbingUtil();

    @BeforeClass
    public static void classSuperInit() {
        TX_MANAGER_STUBBING_UTIL.mockAndStubTxManager();
    }

    @AfterClass
    public static void classDestroy() {
        TX_MANAGER_STUBBING_UTIL.restorePrevTxManager();
    }

}
