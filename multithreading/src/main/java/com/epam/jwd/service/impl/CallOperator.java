package com.epam.jwd.service.impl;

import com.epam.jwd.entity.Operator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallOperator extends Operator {
    private static final Logger logger = LogManager.getLogger(CallOperator.class);
    private static final String SERVICE_CLIENT = "Service client";
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
            logger.debug(SERVICE_CLIENT);
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CallOperator{");
        sb.append("id=").append(operatorId);
        sb.append(", busy=").append(isBusy()).append('}');
        return sb.toString();
    }

}
