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
    protected JPanel makeComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (GraphNode<?> child : children) {
            panel.add(child.createComponent());
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

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        children.set(index, newChild);
    }

    @Override
    public void replace(GraphNode<?> newNode) {
        BlockNode blockNode = (BlockNode) newNode;
        children.clear();
        children.addAll(blockNode.children);
    }
}
