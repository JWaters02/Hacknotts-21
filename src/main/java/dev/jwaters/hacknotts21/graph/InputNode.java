package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public final class InputNode extends GraphNode<JLabel> {
    public InputNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return null;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return StringType.INSTANCE;
    }

    @Override
    protected JLabel makeComponent() {
        return new JLabel("Get User Input");
    }

    @Override
    public void readFromComponent(JLabel component) throws UserInputException {
    }

    @Override
    public void writeToComponent(JLabel component) {
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
    }
}
