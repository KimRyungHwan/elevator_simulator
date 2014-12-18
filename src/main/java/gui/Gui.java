package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by ksh on 2014-05-24.
 */
public class Gui extends Frame {

    public Gui() {
    }

    public void showGui() {
        init();
    }

    private void init() {
        setSize(600, 600);
        setVisible(true);
        setLayout(null);

        //   createMenu();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose();
                System.exit(0);
            }
        });

        Panel panel = new Buliding("1b", 8, 20, 70);
        add(panel);
    }

    public void createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu settingMenu = new Menu("환경설정");
        settingMenu.add("설정");

        menuBar.add(settingMenu);

        setMenuBar(menuBar);

    }
}
