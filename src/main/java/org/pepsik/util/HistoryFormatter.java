package org.pepsik.util;

import org.pepsik.model.Stage;

import java.util.Deque;
import java.util.Iterator;

/**
 * Created by Berezovyi Aleksandr on 8/1/2016.
 */
public class HistoryFormatter {

    public static String format(Deque<Stage> expression) {
        StringBuilder sb = new StringBuilder();
        Iterator<Stage> iterator = expression.iterator();

        if (iterator.hasNext()) {
            sb.append(iterator.next().getResultOperation());
        }

        while (iterator.hasNext()) {
            Stage stage = iterator.next();

            sb.append(" ");
            sb.append(stage.getBinaryOperator());
            sb.append(" ");
            sb.append(stage.getOperand());
        }

        return sb.toString();
    }
}
