package dev.jwaters.hacknotts21;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JList lsBlocks;
    private JButton btnTestDrag;

    public MainForm() {
        btnNewFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodeOutput.setText("blah");
            }
        });
        addDraggableListItem(pnlCodeCreator, lsBlocks, btnTestDrag);
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
        // TODO: place custom component creation code here
        spnCodeOutput = new JScrollPane();
        spnCodeCreator = new JScrollPane();
        txtCodeOutput = new JTextArea();
        tbBlockStore = new JToolBar();
        tbMain = new JToolBar();
        pnlCodeCreator = new JPanel();
        lsBlocks = new JList();
        btnTestDrag = new JButton();

        // Set padding for lsBlocks
        lsBlocks.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        spnCodeOutput.setPreferredSize(new Dimension(200, 200));

        // Make new list model to store the draggable options
        makeListModel(lsBlocks);

//        addDraggableListItem(pnlCodeCreator, lsBlocks, btnTestDrag);
    }

    /**
     * Make a new list model with all the block types
     * @param lsBlocks the list to add the block types to
     */
    private void makeListModel(JList lsBlocks) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Define Variable");
        listModel.addElement("Set Variable");
        listModel.addElement("Read In");
        listModel.addElement("Print");
        listModel.addElement("If");
        listModel.addElement("Else");
        listModel.addElement("While");
        lsBlocks.setModel(listModel);
    }

    /**
     * Use JSwing library to implement draggable list items from lsBlocks into pnlCodeCreator
     * @param pnlCodeCreator
     * @return
     */
    public void addDraggableListItem(JPanel pnlCodeCreator, JList lsBlocks, JButton btnTestDrag) {
        var listener = new DragMouseAdapter();
        pnlCodeCreator.addMouseListener(listener);
        btnTestDrag.addMouseListener(listener);
        lsBlocks.setFocusable(false);
        TransferHandler handler = new TransferHandler("text");
        pnlCodeCreator.setTransferHandler(handler);
        btnTestDrag.setTransferHandler(handler);
        lsBlocks.setTransferHandler(handler);
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
}
