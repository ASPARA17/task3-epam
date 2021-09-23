package com.epam.jwd.service.logic;

import com.epam.jwd.entity.Operator;
import com.epam.jwd.service.exception.ResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class OperatorPool<T extends Operator> {
    private static final Logger logger = LogManager.getLogger(OperatorPool.class);
    private static final String TRY_LOCK = "Attempt to block";
    private static final String UNLOCK = "Unlock thread";

    private final static int POOL_SIZE = 5;
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final ArrayList<T> resources = new ArrayList<>();

    public OperatorPool(List<T> source) {
        resources.addAll(source);
    }

    public T getResource(Client client, long maxWaitMillis) throws ResourceException {
        try {
            logger.debug(TRY_LOCK);
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                for (T resource : resources) {
                    if (!resource.isBusy()) {
                        resource.setBusy(true);
                        return resource;
                    }
                }
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e);
        }
        // TODO: 19.09.2021 add return or exception
        throw new RuntimeException();
        //return null;
    }

    public void unlockResource(Client client, T resource) {
        resource.setBusy(false);
        logger.debug(UNLOCK);
        semaphore.release();
    }
}
