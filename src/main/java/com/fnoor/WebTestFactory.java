package com.fnoor;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class WebTestFactory {

    @Factory
    public Object[] createInstances() {
        Object[] result = new Object[10];
        for (int i = 0; i < 10; i++) {
            result[i] = new WebTest(i * 10);
        }
        return result;
    }
}


