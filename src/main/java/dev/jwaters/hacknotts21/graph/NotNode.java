package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class NotNode extends GraphNode<NotNode.Panel> {
    @Expose
    @Nullable
    private GraphNode<?> value;

    public NotNode(@Nullable GraphNode<?> parent) {
        super(parent);
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

    public static final class Panel extends JPanel {
        @Nullable JComponent value;

        public Panel(@Nullable JComponent value) {
            this.value = value;

            setLayout(new FlowLayout());

            add(new JLabel("¬"));
            if (value != null) {
                add(NodeUIUtils.wrapBody(value));
            }
        }
    }
}
