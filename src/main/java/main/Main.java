package main;

import gui.MainFrame;
import middle.InputBuffer;
import process.Process;

/**
 * Created by ksh on 2014-05-24.
 */
public class Main {
    private InputBuffer inputBuffer;
    private MainFrame gui;
    private process.Process process;

    public Main() {
        inputBuffer = InputBuffer.getInstance();
        process = new Process();
        mainFrame = new MainFrame();
    }

    public static void main(String[] args) {
        new Main().mainProcess();
    }

    public void mainProcess() {
        mainFrame.showGui();
        process.process();
    }
}
