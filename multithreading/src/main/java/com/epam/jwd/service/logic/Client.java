package com.epam.jwd.service.logic;

import com.epam.jwd.service.exception.ResourceException;
import com.epam.jwd.service.impl.CallOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client implements Runnable {
    private static final Logger logger = LogManager.getLogger(Client.class);
    private OperatorPool<CallOperator> pool;

    public Client(OperatorPool<CallOperator> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        CallOperator operator = null;
        try {
            operator = pool.getResource(this, 500);
            operator.service();
        } catch (ResourceException e) {
            logger.error(e.getMessage());
        } finally {
            if (operator != null) {
                pool.unlockResource(this, operator);
            }
        }
    }
}
