package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public final class SetVarNode extends GraphNode<JPanel> {
    private String varName = "";
    @Nullable
    private GraphNode<?> value;

    public SetVarNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        DeclareVarNode declareVar = DeclareVarNode.resolveVariable(varName, this);
        if (declareVar != null) {
            return declareVar.getType();
        }
        return null;
    }

    @Override
    public Type getReturnType() {
        return VoidType.INSTANCE;
    }

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

    @Override
    public List<GraphNode<?>> getChildren() {
        return value != null ? List.of(value) : List.of();
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Nullable
    public GraphNode<?> getValue() {
        return value;
    }

    public void setValue(@Nullable GraphNode<?> value) {
        this.value = value;
    }
}
