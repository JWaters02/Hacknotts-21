package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public final class BooleanLiteralNode extends GraphNode<JComboBox<Boolean>> {
    private boolean value;

    public BooleanLiteralNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        return null;
    }

    @Override
    public Type getReturnType() {
        return BooleanType.INSTANCE;
    }

    @Override
    public JComboBox<Boolean> createComponent() {
        return new JComboBox<>(new Boolean[]{true, false});
    }

    @Override
    public void readFromComponent(JComboBox<Boolean> component) throws UserInputException {
        Boolean value = (Boolean) component.getSelectedItem();
        if (value != null) {
            this.value = value;
        }
    }

    @Override
    public void writeToComponent(JComboBox<Boolean> component) {
        component.setSelectedItem(this.value);
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Collections.emptyList();
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
