package dev.jwaters.hacknotts21;

import com.formdev.flatlaf.FlatDarculaLaf;
import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

enum FileTypes {
    Blocks,
    Code
}

@SuppressWarnings("unused")
public class MainForm {
    private static MainForm instance;

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
    private JTextField txtfIf;
    private JTextField txtfElse;
    private JTextField txtfWhile;
    private JTextField txtfBoolOperation;
    private JButton btnSaveBlocks;
    private JButton btnLoadBlocks;
    private JTextField txtfGetVariable;
    private JTextField txtfNumOperation;
    private JTextField txtfNotNode;

    private List<FunctionRepr> functions = new ArrayList<>();

    public static MainForm getInstance() {
        return instance;
    }

    private void loadFunctions(List<FunctionRepr> functions) {
        this.functions = functions;
        pnlCodeCreator.removeAll();
        for (FunctionRepr function : functions) {
            FunctionRepr.Panel functionPanel = function.createComponent();
            function.writeToPanel(functionPanel);
            pnlCodeCreator.add(functionPanel);
        }
        pnlCodeCreator.revalidate();
    }

    public MainForm() {
        instance = this;

        // Listeners
        btnNewFunction.addActionListener(e -> {
            FunctionRepr function = new FunctionRepr("");
            functions.add(function);
            pnlCodeCreator.add(function.createComponent());
            pnlCodeCreator.revalidate();
        });
        btnSaveCode.addActionListener(e -> {
            File file = chooseFile(FileTypes.Code, true);
            if (file != null) {
                try (var writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(txtCodeOutput.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(pnlMainWindow, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSaveBlocks.addActionListener(e -> {
            File file = chooseFile(FileTypes.Blocks, true);
            if (file != null) {
                try {
                    DnDSerde.writeToFile(file, functions);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(pnlMainWindow, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLoadBlocks.addActionListener(e -> {
            File file = chooseFile(FileTypes.Blocks, false);
            if (file != null) {
                try {
                    Collection<FunctionRepr> functions = DnDSerde.readFromFile(file);
                    loadFunctions(new ArrayList<>(functions));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(pnlMainWindow, "Error loading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create list of JTextFields from the txtf's
        List<JTextField> txtfList = new ArrayList<>();
        txtfList.add(txtfDefineVar);
        txtfList.add(txtfSetVar);
        txtfList.add(txtfReadInput);
        txtfList.add(txtfPrint);
        txtfList.add(txtfIf);
        txtfList.add(txtfElse);
        txtfList.add(txtfWhile);
        txtfList.add(txtfBoolOperation);
        txtfList.add(txtfGetVariable);
        txtfList.add(txtfNumOperation);
        txtfList.add(txtfNotNode);
        addDraggableListItem(pnlCodeCreator, txtfList);

        spnCodeOutput.setPreferredSize(new Dimension(pnlMainWindow.getWidth(), 200));
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
        txtfSetVar = new JTextField();
        txtfReadInput = new JTextField();
        txtfPrint = new JTextField();
        txtfDefineVar = new JTextField();
        txtfIf = new JTextField();
        txtfElse = new JTextField();
        txtfWhile = new JTextField();
        txtfBoolOperation = new JTextField();
        txtfNumOperation = new JTextField();
        txtfGetVariable = new JTextField();
        txtfNotNode = new JTextField();
    }

    public void addDraggableListItem(JPanel pnlCodeCreator, List<JTextField> txtfList) {
        var listener = new DragMouseAdapter();
        TransferHandler handler = new TransferHandler("name");
        DragTransferHandler dragHandler = new DragTransferHandler();
        pnlCodeCreator.setTransferHandler(dragHandler);
        pnlCodeCreator.addMouseListener(listener);
        for (JTextField txtf : txtfList) {
            txtf.addMouseListener(listener);
            txtf.setFocusable(false);
            txtf.setTransferHandler(handler);
        }
    }

    private static class DragMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            var c = (JComponent) e.getSource();
            var handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }

    private class DragTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            return isStringDataSupported(support);
        }

        protected boolean isStringDataSupported(TransferSupport support) {
            if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) return true;
            DataFlavor[] flavors = support.getDataFlavors();
            for (DataFlavor dataFlavor : flavors) {
                if (dataFlavor.getRepresentationClass() == String.class) return true;
            }
            return false;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            String line;
            try {
                line = getStringData(support);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                Class<?> blockClass = Class.forName(line);
                if (GraphNode.class.isAssignableFrom(blockClass)) {
                    Constructor<? extends GraphNode<?>> constructor = (Constructor<? extends GraphNode<?>>) blockClass.asSubclass(GraphNode.class).getConstructor(GraphNode.class);
                    GraphNode<?> node = constructor.newInstance((Object) null);
                    addJPanelToCodeCreator(node.createComponent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(line);
            return true;
        }

        protected String getStringData(TransferSupport support)
                throws UnsupportedFlavorException, IOException {
            if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            }
            DataFlavor[] flavors = support.getDataFlavors();
            for (DataFlavor dataFlavor : flavors) {
                if (dataFlavor.getRepresentationClass() == String.class) {
                    return (String) support.getTransferable().getTransferData(dataFlavor);
                }
            }
            return "";
        }
    }

    private void addJPanelToCodeCreator(JComponent component) {
        pnlMainWindow.add(component);
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

    @Nullable
    private File chooseFile(FileTypes fileType, boolean save) {
        JFileChooser fc = new JFileChooser();
        String out = "";
        int returnVal = save ? fc.showSaveDialog(MainForm.this.pnlMainWindow) : fc.showOpenDialog(MainForm.this.pnlMainWindow);
        if (fileType == FileTypes.Blocks) {
            fc.setFileFilter(new FileNameExtensionFilter("JSON", "json"));
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
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

    public void removeFunction(Component component) {
        pnlCodeCreator.remove(component);
        pnlCodeCreator.revalidate();
        pnlCodeCreator.repaint();
    }
}