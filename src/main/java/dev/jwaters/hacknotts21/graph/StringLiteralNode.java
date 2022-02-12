package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.swing.HintTextField;
import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class StringLiteralNode extends GraphNode<HintTextField> {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringLiteralNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        return null;
    }

    @Override
    public Type getReturnType() {
        return StringType.INSTANCE;
    }

    @Override
    public HintTextField createComponent() {
        return new HintTextField("Your Text Here");
    }

    @Override
    public void readFromComponent(HintTextField component) {
        this.value = component.getText();
    }

    @Override
    public void writeToComponent(HintTextField component) {
        component.setText(value);
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Collections.emptyList();
    }
}
