package com.epam.jwd.service.logic;

import com.epam.jwd.service.exception.ResourceException;
import com.epam.jwd.service.impl.CallOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Client extends Thread {
    private static final Logger logger = LogManager.getLogger(Client.class);
    private static final String CONNECT_OPERATOR = "Client connected to operator";
    private static final String DISCONNECT_OPERATOR = "Client disconnected to operator";
    private final int MAX_WAIT_MILLIS = 500;
    private OperatorPool<CallOperator> pool;
    private Long id;

    public Client(OperatorPool<CallOperator> pool) {
        this.pool = pool;
        this.id = IdGenerator.generateId();
    }

    public long getId() {
        return id;
    }

    @Override
    public void run() {
        CallOperator operator = null;
        try {
            logger.debug(CONNECT_OPERATOR);
            operator = pool.getOperator(this, MAX_WAIT_MILLIS);
            operator.service();
        } catch (ResourceException e) {
            logger.error(e);
            System.err.println("Client #" + this.getId() + " not wait ->"+ e.getMessage());
        } finally {
            if (operator != null) {
                logger.debug(DISCONNECT_OPERATOR);
                pool.releaseOperator(this, operator);
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
