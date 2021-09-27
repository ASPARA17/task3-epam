package com.epam.jwd.controller;

import com.epam.jwd.service.impl.CallOperator;
import com.epam.jwd.service.logic.Client;
import com.epam.jwd.service.logic.OperatorPool;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<CallOperator> audioChannels = new ArrayList<>() {
            {
                this.add(new CallOperator(771));
                this.add(new CallOperator(883));
                this.add(new CallOperator(550));
                this.add(new CallOperator(337));
                this.add(new CallOperator(442));
            }
        };
        OperatorPool<CallOperator> pool = new OperatorPool<>(audioChannels);
        for (int i = 0; i < 20; i++) {
            new Client(pool).start();
        }
    }
}
