package com.epam.jwd.service.logic;

import com.epam.jwd.service.impl.CallOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IdGenerator{
    private static final Logger logger = LogManager.getLogger(IdGenerator.class);
    private static final String GENERATE_ID = "Generate id";
    private static Long id = 1L;

    public static Long generateId() {
        logger.debug(GENERATE_ID);
        return ++id;
    }
}
