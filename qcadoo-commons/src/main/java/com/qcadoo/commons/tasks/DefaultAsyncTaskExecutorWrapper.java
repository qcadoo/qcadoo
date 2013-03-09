package com.qcadoo.commons.tasks;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * The decorator (wrapper) for AsyncTaskExecutor which wraps given tasks execution in try-catch statement. This is workaround for
 * missing functionality in Spring Framework 3.x (https://jira.springsource.org/browse/SPR-8995)
 * 
 * @since 1.2.1
 */
public class DefaultAsyncTaskExecutorWrapper implements AsyncTaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAsyncTaskExecutorWrapper.class);

    private final AsyncTaskExecutor asyncTaskExecutor;

    /**
     * Creates new instance with given underlying executor.
     * 
     * @param asyncTaskExecutor
     *            executor to be wrapped.
     */
    public DefaultAsyncTaskExecutorWrapper(final AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void execute(final Runnable task) {
        asyncTaskExecutor.execute(createWrappedTask(task));
    }

    @Override
    public void execute(final Runnable task, final long startTimeout) {
        asyncTaskExecutor.execute(createWrappedTask(task), startTimeout);
    }

    @Override
    public Future<?> submit(final Runnable task) {
        return asyncTaskExecutor.submit(createWrappedTask(task));
    }

    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return asyncTaskExecutor.submit(createWrappedTask(task));
    }

    /**
     * This method is performed whenever executed task throws uncaught exception.
     * 
     * @param ex
     *            exception thrown by task
     */
    protected void onException(final Exception ex) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("An unexpected exception occured", ex);
        }
    }

    private <T> Callable<T> createWrappedTask(final Callable<T> task) {
        return new Callable<T>() {

            @Override
            public T call() throws Exception {
                try {
                    return task.call();
                } catch (Exception ex) {
                    onException(ex);
                    throw ex;
                }
            }
        };
    }

    private Runnable createWrappedTask(final Runnable task) {
        return new Runnable() {

            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception ex) {
                    onException(ex);
                    throw new RuntimeException(ex);
                }
            }
        };
    }

}
