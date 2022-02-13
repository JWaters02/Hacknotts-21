package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.HintTextField;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class SetVarNode extends GraphNode<SetVarNode.Panel> {
    @Expose
    private String varName = "";
    @Expose
    @Nullable
    private GraphNode<?> value;

    public SetVarNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        DeclareVarNode declareVar = DeclareVarNode.resolveVariable(varName, this, containingFunc);
        if (declareVar != null) {
            return declareVar.getType();
        }
        return null;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    protected Panel makeComponent() {
        return new Panel(value == null ? null : value.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        this.varName = component.varNameField.getText();
        if (this.value != null) {
            doReadFromComponent(this.value, component.value);
        }
    }

    @Override
    public void writeToComponent(Panel component) {
        component.varNameField.setText(varName);
        if (value != null) {
            doWriteToComponent(value, component.value);
        }
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.singletonList(value);
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        value = newChild;
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

    public static final class Panel extends JPanel {
        private final HintTextField varNameField = new HintTextField("Var name");
        @Nullable
        private final JComponent value;

        public Panel(@Nullable JComponent value) {
            this.value = value;

            setLayout(new FlowLayout());
            add(new JLabel("Set var: "));
            add(varNameField);
            add(new JLabel(" to "));

            if (value != null) {
                add(NodeUIUtils.wrapBody(value));
            }
        }
    }
}
