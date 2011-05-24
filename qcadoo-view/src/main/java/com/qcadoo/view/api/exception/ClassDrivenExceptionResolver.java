package com.qcadoo.view.api.exception;

public interface ClassDrivenExceptionResolver {

    <T extends Exception> void addExceptionInfoResolver(Class<T> exceptionClass, ExceptionInfoResolver<T> exceptionInfoResolver);

    <T extends Exception> void removeExceptionInfoResolver(Class<T> exceptionClass);

}
