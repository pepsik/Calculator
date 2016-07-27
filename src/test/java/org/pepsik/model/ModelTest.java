package org.pepsik.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class ModelTest {

    private Model model = new Model();

    @Test
    public void validValues() {
        setInput(0, 0);
        setInput(1, 1);
        setInput(2, 2);
        setInput(9, 9);
        setInput(10, 1, 0);
        setInput(20, 2, 0);
        setInput(90, 9, 0);
        setInput(123456, 1, 2, 3, 4, 5, 6);
    }

    private void setInput(int expected, int... values) {
        for (int value : values) {
            model.addInputNumber(String.valueOf(value));
        }

        assertEquals(String.valueOf(expected), model.getInput());
    }
}