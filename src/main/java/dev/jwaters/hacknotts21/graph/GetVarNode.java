package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class GetVarNode extends GraphNode<JPanel> {
    private String varName = "";

    public GetVarNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        return null;
    }

    @Override
    public @Nullable Type getReturnType() {
        DeclareVarNode declareVar = DeclareVarNode.resolveVariable(varName, this);
        if (declareVar != null) {
            return declareVar.getType();
        }
        return null;
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

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
