package com.qcadoo.model;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

public final class TransactionManagerStubbingUtil {

    private PlatformTransactionManager originalTransactionManager;

    public void mockAndStubTxManager() {
        final TransactionStatus txStatusMock = mock(TransactionStatus.class);
        given(txStatusMock.isRollbackOnly()).willReturn(false);
        final PlatformTransactionManager txManagerMock = mock(PlatformTransactionManager.class);
        given(txManagerMock.getTransaction((TransactionDefinition) Mockito.anyObject())).willReturn(txStatusMock);

        final AnnotationTransactionAspect txAspect = AnnotationTransactionAspect.aspectOf();
        originalTransactionManager = txAspect.getTransactionManager();
        txAspect.setTransactionManager(txManagerMock);
    }

    public void restorePrevTxManager() {
        final AnnotationTransactionAspect txAspect = AnnotationTransactionAspect.aspectOf();
        txAspect.setTransactionManager(originalTransactionManager);
    }

}
