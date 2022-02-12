package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.IntType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public final class TwoBooleanOperationNode extends GraphNode<JPanel> {
    public enum TwoBooleanOperationEnum {
        AND,
        OR,
        XOR,
        EQUAL,
        NOT_EQAUL
    }

    private GraphNode<?> left;
    private GraphNode<?> right;
    private TwoBooleanOperationEnum operation;

    public TwoBooleanOperationNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    public GraphNode<?> getLeft() {
        return left;
    }

    public void setLeft(GraphNode<?> left) {
        this.left = left;
    }

    public GraphNode<?> getRight() {
        return right;
    }

    public void setRight(GraphNode<?> right) {
        this.right = right;
    }

    public TwoBooleanOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(TwoBooleanOperationEnum operation) {
        this.operation = operation;
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child) {
        return IntType.INSTANCE;
    }

    @Override
    public Type getReturnType() {
        return IntType.INSTANCE;
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

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Arrays.asList(left, right);
    }
}
