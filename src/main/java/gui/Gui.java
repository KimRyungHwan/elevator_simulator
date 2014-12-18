package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ksh on 2014-05-24.
 */
public class MainFrame extends Frame {

    public MainFrame() {
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

                Panel panel = new MainPanel("1b", 8, 20, 70);
                add(panel);
            }
        });
    }
}
