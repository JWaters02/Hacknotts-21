package dev.jwaters.hacknotts21;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private JToolBar tbMain;
    private JPanel pnlMainWindow;
    private JTextArea txtCodeOutput;
    private JToolBar tbBlockStore;
    private JPanel pnlCodeCreator;
    private JScrollPane spnCodeCreator;
    private JScrollPane spnCodeOutput;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().pnlMainWindow);
        frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        spnCodeOutput = new JScrollPane();
        spnCodeCreator = new JScrollPane();
        txtCodeOutput = new JTextArea();
        tbBlockStore = new JToolBar();
        tbMain = new JToolBar();
        pnlCodeCreator = new JPanel();
        spnCodeOutput.setPreferredSize(new Dimension(200, 200));
    }

    private void setupCodeCreator() {
        // Needs to store multiple JPanels inside of it when added

    }
}
