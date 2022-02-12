package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public abstract sealed class GraphNode<C extends JComponent> permits
        AbstractConditionNode,
        BlockNode,
        BooleanLiteralNode,
        DeclareVarNode,
        GetVarNode,
        IfElseNode,
        InputNode,
        IntegerLiteralNode,
        PrintNode,
        SetVarNode,
        StringLiteralNode,
        TwoBooleanOperationNode,
        TwoNumberOperationNode {
    @Nullable
    private final GraphNode<?> parent;

    public GraphNode(@Nullable GraphNode<?> parent) {
        this.parent = parent;
    }

    @Nullable
    public GraphNode<?> getParent() {
        return parent;
    }

    @Nullable
    public abstract Type getExpectedChildType(GraphNode<?> child);

    @Nullable
    public abstract Type getReturnType();

    public abstract C createComponent();
    public abstract void readFromComponent(C component) throws UserInputException;
    public abstract void writeToComponent(C component);

    public abstract List<@Nullable GraphNode<?>> getChildren();

    @SuppressWarnings("unchecked")
    protected static <T extends JComponent> void doReadFromComponent(GraphNode<?> node, T component) throws UserInputException {
        ((GraphNode<T>) node).readFromComponent(component);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends JComponent> void doWriteToComponent(GraphNode<?> node, T component) {
        ((GraphNode<T>) node).writeToComponent(component);
    }
}
