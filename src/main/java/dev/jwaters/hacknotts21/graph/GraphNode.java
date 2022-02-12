package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public abstract sealed class GraphNode<C extends JComponent> permits BlockNode, DeclareVarNode, GetVarNode, IfElseNode, IfNode, InputNode, IntegerLiteralNode, PrintNode, SetVarNode, TwoNumberOperationNode, WhileNode {
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
    public abstract void readFromComponent(C component);
    public abstract void writeToComponent(C component);

    public abstract List<@Nullable GraphNode<?>> getChildren();
}
