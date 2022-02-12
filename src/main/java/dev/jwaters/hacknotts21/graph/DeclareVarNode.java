package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.swing.HintTextField;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class DeclareVarNode extends GraphNode<DeclareVarNode.Panel> {
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
    public Panel createComponent() {
         return new Panel();
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        this.name = component.nameField.getText();
        this.type = component.typeChooser.getType();
    }

    @Override
    public void writeToComponent(Panel component) {
        component.nameField.setText(name);
        component.typeChooser.setType(type);
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.emptyList();
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

    public static class Panel extends JPanel {
        private final HintTextField nameField = new HintTextField("Name");
        private final Type.TypeChooser typeChooser = new Type.TypeChooser(false);

        public Panel() {
            setLayout(new FlowLayout());
            add(new JLabel("Declare "));
            add(nameField);
            add(new JLabel(" as "));
            add(typeChooser);
        }
    }
}
