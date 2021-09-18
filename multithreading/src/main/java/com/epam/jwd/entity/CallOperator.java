package com.epam.jwd.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallOperator extends Operator{
    private static final Logger logger = LogManager.getLogger(CallOperator.class);
    private static final String ERROR_MESSAGE = "Error";
    private int operatorId;

    public CallOperator(int id) {
        this.operatorId = id;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public void service() {
        try {
            // service client
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            logger.error(ERROR_MESSAGE + e);
        }
    }
}
