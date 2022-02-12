package dev.jwaters.hacknotts21;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JToolBar tbMain;
    private JPanel pnlMainWindow;
    private JTextArea txtCodeOutput;
    private JToolBar tbBlockStore;
    private JPanel pnlCodeCreator;
    private JScrollPane spnCodeCreator;
    private JScrollPane spnCodeOutput;
    private JButton btnNewFunction;
    private JTextField txtfSetVar;
    private JTextField txtfReadInput;
    private JTextField txtfPrint;
    private JTextField txtfDefineVar;
    private JButton btnSaveCode;
    private JButton btnLoadCode;
    private JTextField txtfIf;
    private JTextField txtfElse;
    private JTextField txtfWhile;
    private JButton btnTestDrag;

    public MainForm() {
        btnNewFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodeOutput.setText("blah");
            }
        });
        // Create list of JTextFields from the txtf's
        List<JTextField> txtfList = new ArrayList<>();
        txtfList.add(txtfDefineVar);
        txtfList.add(txtfSetVar);
        txtfList.add(txtfReadInput);
        txtfList.add(txtfPrint);
        addDraggableListItem(pnlCodeCreator, txtfList);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame("Drag'on 'Drop");
        frame.setContentPane(new MainForm().pnlMainWindow);
        frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        spnCodeOutput = new JScrollPane();
        spnCodeCreator = new JScrollPane();
        txtCodeOutput = new JTextArea();
        tbBlockStore = new JToolBar();
        tbMain = new JToolBar();
        pnlCodeCreator = new JPanel();
        btnTestDrag = new JButton();
        txtfSetVar = new JTextField();
        txtfReadInput = new JTextField();
        txtfPrint = new JTextField();
        txtfDefineVar = new JTextField();

        spnCodeOutput.setPreferredSize(new Dimension(200, 200));
    }

    /**
     * Use JSwing library to implement draggable list items from lsBlocks into pnlCodeCreator
     * @param pnlCodeCreator
     * @return
     */
    public void addDraggableListItem(JPanel pnlCodeCreator, List<JTextField> txtfList) {
        var listener = new DragMouseAdapter();
        TransferHandler handler = new TransferHandler("text");
        pnlCodeCreator.setTransferHandler(handler);
        pnlCodeCreator.addMouseListener(listener);
        for (JTextField txtf : txtfList) {
            txtf.addMouseListener(listener);
            txtf.setFocusable(false);
            txtf.setTransferHandler(handler);
        }
    }

    /**
     * Since JPanel does not natively support drag and drop, we need to implement it ourselves
     */
    private class DragMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            var c = (JComponent) e.getSource();
            var handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }

    private void addJPanelToCodeCreator() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlCodeCreator.add(panel);
    }

    private List<JPanel> getJPanelsInCodeCreator() {
        // Everytime this is called, we want to get JPanels in the pnlCodeCreator
        Component[] panels = pnlCodeCreator.getComponents();
        txtCodeOutput = new JTextArea();
        List<JPanel> panelList = new ArrayList<>();
        try {
            for (Component panel : panels) {
                panelList.add((JPanel) panel);
            }
        } catch (Exception e) {
            txtCodeOutput.append("Error: " + e.getMessage());
        }
        return panelList;
    }

    public JPanel getPnlCodeCreator() {
        return pnlCodeCreator;
    }

    public void outputCompiledCode(File file) {
        String code = "";
        // Read text from file into code
        try {
            code = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtCodeOutput.setText(code);
    }
}
