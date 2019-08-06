package org.erik.timesheets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.erik.timesheets.ui.views.projects.ProjectsView;
import org.erik.timesheets.ui.views.statistics.StatisticsView;
import org.erik.timesheets.ui.views.timesheet.TimesheetView;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Timesheets", shortName = "Timesheets")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private final Tabs menu;
    private Map<Class<?>, Tab> viewToTabMap = new HashMap<>();

    private Tab tabToSelect;

    public MainLayout() {
        menu = createMenuTabs();
        addToNavbar(menu);
    }

    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Timesheet", TimesheetView.class));
        tabs.add(createTab("Statistics", StatisticsView.class));
        tabs.add(createTab("Projects", ProjectsView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private Tab createTab(String title,
            Class<? extends Component> viewClass) {
        Tab tab = createTab(populateLink(new RouterLink(null, viewClass), title));
        viewToTabMap.put(viewClass, tab);
        return tab;
    }

    private Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Tab selectedTab = menu.getSelectedTab();
        Tab targetTab = viewToTabMap.get(event.getNavigationTarget());
        if (targetTab != null && targetTab != selectedTab) {
            tabToSelect = targetTab;
        } else {
            tabToSelect = null;
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (tabToSelect != null) {
            menu.setSelectedTab(tabToSelect);
        }
    }
}
