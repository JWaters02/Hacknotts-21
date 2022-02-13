package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import com.sun.jdi.IntegerType;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.IntType;
import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class ToStringNode extends GraphNode<ToStringNode.Panel> {
    @Expose
    @Nullable
    private GraphNode<?> value;

    public GraphNode<?> getValue() {
        return value;
    }

    public ToStringNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return IntType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return StringType.INSTANCE;
    }

    @Override
    protected Panel makeComponent() {
        return new Panel(
                value != null ? value.createComponent() : null
        );
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        if (value != null) {
            doReadFromComponent(value, component.value);
        }
    }

    @Override
    public void writeToComponent(Panel component) {
        if (value != null) {
            doWriteToComponent(value, component.value);
        }
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Collections.singletonList(value);
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        value = newChild;
    }

    public static class Panel extends JPanel {
        @Nullable JComponent value;

        public Panel(@Nullable JComponent value) {
            this.value = value;

            setLayout(new FlowLayout());

            if (value != null) {
                add(NodeUIUtils.wrapBody(value));
            }
            add(new JLabel("as string"));
        }
    }
}
