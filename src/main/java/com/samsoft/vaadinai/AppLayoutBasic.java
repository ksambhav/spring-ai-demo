package com.samsoft.vaadinai;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

@Layout
public class AppLayoutBasic extends AppLayout {

    public AppLayoutBasic() {
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("My GPT");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        SideNav nav = getSideNav();
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.NONE);
        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> sideNav.addItem(new SideNavItem(entry.title(), entry.menuClass())));
        return sideNav;
    }
}