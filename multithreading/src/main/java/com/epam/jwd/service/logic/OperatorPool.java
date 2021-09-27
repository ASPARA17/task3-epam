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
    private static final String LOCK_THREAD = "Blocking a thread";

    private final static int POOL_SIZE = 5;
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final ArrayList<T> operators = new ArrayList<>();

    public OperatorPool(List<T> source) {
        operators.addAll(source);
    }

    public T getOperator(Client client, long maxWaitMillis) throws ResourceException {
        try {
            logger.debug(TRY_LOCK);
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                for (T operator : operators) {
                    if (!operator.isBusy()) {
                        logger.debug(LOCK_THREAD + operator);
                        operator.setBusy(true);
                        System.out.println("Client #" + client.getId()
                                + " connected to operator " + operator);
                        return operator;
                    }
                }
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e);
        }
        throw new ResourceException("timed out " + maxWaitMillis);
    }

    public void releaseOperator(Client client, T operator) {
        operator.setBusy(false);
        logger.debug(UNLOCK + operator);
        System.out.println("Client #" + client.getId() + ": " + operator
                + " --> disconnected");
        semaphore.release();
    }
}
