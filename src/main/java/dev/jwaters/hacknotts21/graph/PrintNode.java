package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class PrintNode extends GraphNode<PrintNode.Panel> {
    @Expose
    private GraphNode<?> value = new StringLiteralNode(this);

    public PrintNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return StringType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    public Panel createComponent() {
        return new Panel(value.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        doReadFromComponent(this.value, component.value);
    }

    @Override
    public void writeToComponent(Panel component) {
        doWriteToComponent(this.value, component.value);
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.singletonList(value);
    }

    public GraphNode<?> getValue() {
        return value;
    }

    public void setValue(GraphNode<?> value) {
        this.value = value;
    }

    public static final class Panel extends JPanel {
        private final JComponent value;

        public Panel(JComponent value) {
            this.value = value;

            setLayout(new FlowLayout());
            add(new JLabel("Print "));
            add(value);
        }
    }

}
