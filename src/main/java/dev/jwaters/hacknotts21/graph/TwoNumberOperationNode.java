package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.swing.NodeUIUtils;
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
    @Nullable
    private GraphNode<?> left = new IntegerLiteralNode(this);
    @Expose
    @Nullable
    private GraphNode<?> right = new IntegerLiteralNode(this);
    @Expose
    private TwoNumberOperationEnum operation;

    public TwoNumberOperationNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Nullable
    public GraphNode<?> getLeft() {
        return left;
    }

    public void setLeft(@Nullable GraphNode<?> left) {
        this.left = left;
    }

    @Nullable
    public GraphNode<?> getRight() {
        return right;
    }

    public void setRight(@Nullable GraphNode<?> right) {
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
    protected Panel makeComponent() {
        return new Panel(left == null ? null : left.createComponent(), operation.symbol, right == null ? null : right.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        if (left != null) {
            doReadFromComponent(this.left, component.left);
        }
        if (right != null) {
            doReadFromComponent(this.right, component.right);
        }
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
        @Nullable
        private final JComponent left;
        @Nullable
        private final JComponent right;

        public Panel(@Nullable JComponent left, String operation, @Nullable JComponent right) {
            this.left = left;
            this.right = right;

            setLayout(new FlowLayout());
            if (left != null) {
                add(NodeUIUtils.wrapBody(left));
            }
            add(new JLabel(" " + operation + " "));
            if (right != null) {
                add(NodeUIUtils.wrapBody(right));
            }
        }
    }
}
