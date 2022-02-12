package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.IntType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public final class IntegerLiteralNode extends GraphNode<JSpinner> {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public IntegerLiteralNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        return null;
    }

    @Override
    public Type getReturnType() {
        return IntType.INSTANCE;
    }

    @Override
    public JSpinner createComponent() {
        return new JSpinner();
    }

    @Override
    public void readFromComponent(JSpinner component) throws UserInputException {
        Integer value = (Integer) component.getValue();
        if (value == null) {
            throw new UserInputException(component);
        }
        this.value = value;
    }

    @Override
    public void writeToComponent(JSpinner component) {
        component.setValue(value);
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Collections.emptyList();
    }
}
