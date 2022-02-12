package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class IfElseNode extends GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> condition;
    private final BlockNode ifBody = new BlockNode(this);
    private final BlockNode elseBody = new BlockNode(this);

    public IfElseNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        if (child == condition) {
            return BooleanType.INSTANCE;
        } else if (child == ifBody || child == elseBody) {
            return VoidType.INSTANCE;
        }
        return null;
    }

    @Override
    public Type getReturnType() {
        return VoidType.INSTANCE;
    }

    @Override
    public JPanel createComponent() {
        return null;
    }

    @Override
    public void readFromComponent(JPanel component) {

    }

    @Override
    public void writeToComponent(JPanel component) {

    }

    @Nullable
    public GraphNode<?> getCondition() {
        return condition;
    }

    public void setCondition(@Nullable GraphNode<?> condition) {
        this.condition = condition;
    }

    public BlockNode getIfBody() {
        return ifBody;
    }

    public BlockNode getElseBody() {
        return elseBody;
    }
}
