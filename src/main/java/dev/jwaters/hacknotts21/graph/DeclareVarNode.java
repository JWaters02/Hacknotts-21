package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DeclareVarNode implements GraphNode<JPanel> {
    private String name = "";
    @Nullable
    private Type type;

    @Override
    public JPanel createComponent() {
        return null;
    }

    @Override
    public void readFromComponent(JPanel component) {

    }

    @Override
    public void writeToComponent(JPanel component) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Type getType() {
        return type;
    }

    public void setType(@Nullable Type type) {
        this.type = type;
    }
}
