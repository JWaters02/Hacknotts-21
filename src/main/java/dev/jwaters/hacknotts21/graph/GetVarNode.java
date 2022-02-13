package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.HintTextField;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class GetVarNode extends GraphNode<GetVarNode.Panel> {
    @Expose
    private String varName = "";

    public GetVarNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return null;
    }

    @Override
    public @Nullable Type getReturnType(FunctionRepr containingFunc) {
        DeclareVarNode declareVar = DeclareVarNode.resolveVariable(varName, this, containingFunc);
        if (declareVar != null) {
            return declareVar.getType();
        }
        return null;
    }

    @Override
    protected Panel makeComponent() {
        return new Panel();
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        this.varName = component.nameField.getText();
    }

    @Override
    public void writeToComponent(Panel component) {
        component.nameField.setText(this.varName);
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.emptyList();
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public static final class Panel extends JPanel {
        private final HintTextField nameField = new HintTextField("Name");

        public Panel() {
            setLayout(new FlowLayout());
            add(new JLabel("Variable "));
            add(nameField);
        }
    }
}
