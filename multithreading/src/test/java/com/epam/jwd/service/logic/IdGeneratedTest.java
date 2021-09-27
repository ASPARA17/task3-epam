package com.epam.jwd.service.logic;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class IdGeneratedTest {
    @Test
    public void generatedIdNotEquals() {
        assertNotEquals(IdGenerator.generateId(), IdGenerator.generateId());
    }
}
