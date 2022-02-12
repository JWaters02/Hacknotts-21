package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.IntType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public final class TwoNumberOperationNode extends GraphNode<JPanel> {
    public enum TwoNumberOperationEnum {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        MODULUS,
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_OR_EQUAL,
        LESS_THAN_OR_EQUAL,
        EQUAL,
        NOT_EQUAL
    }

    private GraphNode<?> left;
    private GraphNode<?> right;
    private TwoNumberOperationEnum operation;

    public TwoNumberOperationNode(@Nullable GraphNode<?> parent) {
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

    public TwoNumberOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(TwoNumberOperationEnum operation) {
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
