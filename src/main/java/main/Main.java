package main;

import gui.MainFrame;
import middle.InputBuffer;
import process.Process;

/**
 * Created by ksh on 2014-05-24.
 */
public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.showGui();
        process.Process process = new Process();
        process.process();
    }
}
