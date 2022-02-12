package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.BooleanType;
import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.type.IntType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public final class TwoNumberOperationNode extends GraphNode<TwoNumberOperationNode.Panel> {
    public enum TwoNumberOperationEnum {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        MODULUS("%"),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        GREATER_THAN_OR_EQUAL("≥"),
        LESS_THAN_OR_EQUAL("≤"),
        EQUAL("="),
        NOT_EQUAL("≠"),
        ;

        private final String symbol;
        TwoNumberOperationEnum(String symbol) {
            this.symbol = symbol;
        }
    }

    @Expose
    private GraphNode<?> left = new IntegerLiteralNode(this);
    @Expose
    private GraphNode<?> right = new IntegerLiteralNode(this);
    @Expose
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
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return IntType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return switch (operation) {
            case ADD, SUBTRACT, GREATER_THAN, MULTIPLY, DIVIDE, MODULUS, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL -> IntType.INSTANCE;
            case EQUAL, NOT_EQUAL -> BooleanType.INSTANCE;
        };
    }

    @Override
    public Panel createComponent() {
        return new Panel(left.createComponent(), operation.symbol, right.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        doReadFromComponent(this.left, component.left);
        doReadFromComponent(this.right, component.right);
    }

    @Override
    public void writeToComponent(Panel component) {
        doWriteToComponent(this.left, component.left);
        doWriteToComponent(this.right, component.right);
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Arrays.asList(left, right);
    }

    public static final class Panel extends JPanel {
        private final JComponent left;
        private final JComponent right;

        public Panel(JComponent left, String operation, JComponent right) {
            this.left = left;
            this.right = right;

            setLayout(new FlowLayout());
            add(left);
            add(new JLabel(" " + operation + " "));
            add(right);
        }
    }
}
