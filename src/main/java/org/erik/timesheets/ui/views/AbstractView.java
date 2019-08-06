package org.erik.timesheets.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractView extends VerticalLayout {

    private boolean hasAttached = false;

    protected AbstractView() {
        setSizeFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (!hasAttached) {
            hasAttached = true;
            onAttachOnce(attachEvent);
        }
    }

    /**
     * Called once and only once on the first attach
     */
    protected abstract void onAttachOnce(AttachEvent attachEvent);
}
