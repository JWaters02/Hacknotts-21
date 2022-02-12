package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class DeclareVarNode extends GraphNode<JPanel> {
    private String name = "";
    @Nullable
    private Type type;

    public DeclareVarNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
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

    @Nullable
    public static DeclareVarNode resolveVariable(String name, GraphNode<?> atNode) {
        for (; atNode != null; atNode = atNode.getParent()) {
            GraphNode<?> parent = atNode.getParent();
            if (parent == null) {
                return null;
            }
            if (parent instanceof DeclareVarNode declareVarNode) {
                if (declareVarNode.getName().equals(name)) {
                    return declareVarNode;
                }
            }
            if (parent instanceof BlockNode blockNode) {
                for (GraphNode<?> child : blockNode.getChildren()) {
                    if (child instanceof DeclareVarNode declareVarNode) {
                        if (declareVarNode.getName().equals(name)) {
                            return declareVarNode;
                        }
                    }
                    if (child == atNode) {
                        break;
                    }
                }
            }
        }
        return null;
    }
}
