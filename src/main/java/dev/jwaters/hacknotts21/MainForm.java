package dev.jwaters.hacknotts21;

import com.formdev.flatlaf.FlatDarculaLaf;
import dev.jwaters.hacknotts21.compilers.CodeCompiler;
import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.compilers.Language;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    private JComboBox cbSelectLang;
    private JTextField txtfToString;
    private JButton btnCompile;

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

    @SuppressWarnings("unchecked")
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
            saveCode();
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
        btnCompile.addActionListener(e -> {
            try {
                var functions = DnDSerde.readFromFile(new File("code.json"));
                System.out.print(functions);
                JComboBox<Language> cbLangs = MainForm.getInstance().getCbSelectLang();
                CodeCompiler.compile(functions, (Language) Objects.requireNonNull(cbLangs.getSelectedItem()));
            } catch (IOException ex) {
                ex.printStackTrace();
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
        txtfList.add(txtfToString);
        addDraggableListItem(pnlCodeCreator, txtfList);

        spnCodeOutput.setPreferredSize(new Dimension(pnlMainWindow.getWidth(), 200));

        // Set up the language combobox
        cbSelectLang.addItem(Language.JAVA);
        cbSelectLang.addItem(Language.PYTHON);
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
        cbSelectLang = new JComboBox<>();
        txtfToString = new JTextField();
        btnCompile = new JButton();
    }

    public void addDraggableListItem(JPanel pnlCodeCreator, List<JTextField> txtfList) {
        var listener = new DragMouseAdapter();
        TransferHandler handler = new TransferHandler("name");
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

    public JTextArea getTxtCodeOutput() {
        return txtCodeOutput;
    }

    public JComboBox<Language> getCbSelectLang() { return cbSelectLang; }

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

    public void removeFunction(Component component) {
        pnlCodeCreator.remove(component);
        pnlCodeCreator.revalidate();
        pnlCodeCreator.repaint();
    }

    public List<FunctionRepr> getFunctions() {
        return functions;
    }

    public void saveCode() {
        File file = chooseFile(FileTypes.Code, true);
        if (file != null) {
            try (var writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(txtCodeOutput.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(pnlMainWindow, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}