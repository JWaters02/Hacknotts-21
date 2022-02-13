package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public final class TwoBooleanOperationNode extends GraphNode<TwoBooleanOperationNode.Panel> {
    public enum TwoBooleanOperationEnum {
        AND("AND"),
        OR("OR"),
        XOR("XOR"),
        EQUAL("="),
        NOT_EQUAL("≠"),
        ;

        private final String symbol;
        TwoBooleanOperationEnum(String symbol) {
            this.symbol = symbol;
        }
    }

    @Expose
    @Nullable
    private GraphNode<?> left;
    @Expose
    @Nullable
    private GraphNode<?> right;
    @Expose
    private TwoBooleanOperationEnum operation;

    public TwoBooleanOperationNode(@Nullable GraphNode<?> parent) {
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

    public TwoBooleanOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(TwoBooleanOperationEnum operation) {
        this.operation = operation;
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return BooleanType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return BooleanType.INSTANCE;
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
        if (left != null) {
            doWriteToComponent(this.left, component.left);
        }
        if (right != null) {
            doWriteToComponent(this.right, component.right);
        }
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Arrays.asList(left, right);
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        switch (index) {
            case 0 -> left = newChild;
            case 1 -> right = newChild;
        }
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
