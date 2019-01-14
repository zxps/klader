package org.rubio.klader.core.processor;

import org.jamel.dbf.processor.DbfRowProcessor;

public class MetaProcessor implements DbfRowProcessor {

    private int rows;

    @Override
    public void processRow(Object[] objects) {
        rows++;
    }

    public int getRows() {
        return rows;
    }
}
