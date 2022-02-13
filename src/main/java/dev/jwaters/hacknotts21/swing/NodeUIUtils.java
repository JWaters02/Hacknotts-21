package dev.jwaters.hacknotts21.swing;

import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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
            GraphNode<?> node = (GraphNode<?>) component.getClientProperty("node");
            if (node != null) {
                return node;
            }
        }
        return null;
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
}
