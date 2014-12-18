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
        setVisible(true);
        setLayout(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                Panel panel = new Buliding("1b", 8, 20, 70);
                add(panel);
            }
        });
    }
}
