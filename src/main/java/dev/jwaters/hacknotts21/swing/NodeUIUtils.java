package dev.jwaters.hacknotts21.swing;

import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.graph.UserInputException;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class NodeUIUtils {
    public static JPanel wrapBody(JComponent body) {
        JPanel wrapper = new JPanel();
        wrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        wrapper.add(body);
        return wrapper;
    }

    @Nullable
    public static GraphNode<?> getAssociatedNode(JComponent component) {
        for (; component != null; component = (JComponent) component.getParent()) {
            GraphNode<?> node = getDirectAssociatedNode(component);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    public static GraphNode<?> getDirectAssociatedNode(JComponent component) {
        return (GraphNode<?>) component.getClientProperty("node");
    }

    @Nullable
    public static FunctionRepr getAssociatedFunction(JComponent component) {
        for (; component != null; component = (JComponent) component.getParent()) {
            FunctionRepr function = (FunctionRepr) component.getClientProperty("functionRepr");
            if (function != null) {
                return function;
            }
        }
        return null;
    }

    public static void traverseComponents(JComponent component, Consumer<JComponent> processor) {
        processor.accept(component);
        for (int i = 0; i < component.getComponentCount(); i++) {
            JComponent child = (JComponent) component.getComponent(i);
            traverseComponents(child, processor);
        }
    }

    public static void addListeners(JComponent component) {
        traverseComponents(component, c -> {
            if (c.getClientProperty("addedListeners") == Boolean.TRUE) {
                return;
            }

            if (c instanceof JTextField textField) {
                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        onPropertyChanged(c);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        onPropertyChanged(c);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        onPropertyChanged(c);
                    }
                });
            } else if (c instanceof JComboBox<?> comboBox) {
                comboBox.addActionListener(e -> onPropertyChanged(c));
            }

            c.putClientProperty("addedListeners", Boolean.TRUE);
        });
    }

    private static void onPropertyChanged(JComponent component) {
        GraphNode<?> node = getAssociatedNode(component);
        if (node == null) {
            return;
        }
        for (; component != null; component = (JComponent) component.getParent()) {
            if (getDirectAssociatedNode(component) != null) {
                break;
            }
        }
        try {
            GraphNode.doReadFromComponent(node, component);
        } catch (UserInputException e) {
            // user input error, ignore
        }
    }
}
