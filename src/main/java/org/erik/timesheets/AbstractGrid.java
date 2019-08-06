package org.erik.timesheets;

import com.vaadin.flow.component.grid.Grid;

public abstract class AbstractGrid<T> extends Grid<T> {

    public AbstractGrid() {
        setSizeFull();
    }
}
