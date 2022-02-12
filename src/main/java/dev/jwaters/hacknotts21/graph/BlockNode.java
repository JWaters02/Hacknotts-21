package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class BlockNode extends GraphNode<JPanel> {
    @Expose
    private final List<GraphNode<?>> children = new ArrayList<>();

    public BlockNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    public JPanel createComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (GraphNode<?> child : children) {
            JComponent childComponent = child.createComponent();
            childComponent.putClientProperty("node", child);
            panel.add(childComponent);
        }
        return panel;
    }

    @Override
    public void readFromComponent(JPanel component) throws UserInputException {
        for (int i = 0; i < children.size(); i++) {
            GraphNode<?> child = children.get(i);
            JComponent childComponent = (JComponent) component.getComponent(i);
            doReadFromComponent(child, childComponent);
        }
    }

    @Override
    public void writeToComponent(JPanel component) {
        for (int i = 0; i < children.size(); i++) {
            GraphNode<?> child = children.get(i);
            JComponent childComponent = (JComponent) component.getComponent(i);
            doWriteToComponent(child, childComponent);
        }
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return children;
    }
}
