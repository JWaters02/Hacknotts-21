package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public final class IfElseNode extends GraphNode<IfElseNode.Panel> {
    @Expose
    private GraphNode<?> condition = new BooleanLiteralNode(this);
    @Expose
    private final BlockNode ifBody = new BlockNode(this);
    @Expose
    private final BlockNode elseBody = new BlockNode(this);

    public IfElseNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        if (child == condition) {
            return BooleanType.INSTANCE;
        } else if (child == ifBody || child == elseBody) {
            return VoidType.INSTANCE;
        }
        return null;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    protected Panel makeComponent() {
        return new Panel(condition.createComponent(), ifBody.createComponent(), elseBody.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        doReadFromComponent(this.condition, component.condition);
        doReadFromComponent(this.ifBody, component.ifBody);
        doReadFromComponent(this.elseBody, component.elseBody);
    }

    @Override
    public void writeToComponent(Panel component) {
        doWriteToComponent(this.condition, component.condition);
        doWriteToComponent(this.ifBody, component.ifBody);
        doWriteToComponent(this.elseBody, component.elseBody);
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Arrays.asList(condition, ifBody, elseBody);
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        if (index == 0) {
            condition = newChild;
        }
    }

    public GraphNode<?> getCondition() {
        return condition;
    }

    public void setCondition(GraphNode<?> condition) {
        this.condition = condition;
    }

    public BlockNode getIfBody() {
        return ifBody;
    }

    public BlockNode getElseBody() {
        return elseBody;
    }

    public static final class Panel extends JPanel {
        private final JComponent condition;
        private final JComponent ifBody;
        private final JComponent elseBody;

        public Panel(JComponent condition, JComponent ifBody, JComponent elseBody) {
            this.condition = condition;
            this.ifBody = ifBody;
            this.elseBody = elseBody;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JPanel conditionPanel = new JPanel();
            conditionPanel.setLayout(new FlowLayout());
            conditionPanel.add(new JLabel("If "));
            conditionPanel.add(condition);
            conditionPanel.add(new JLabel(":"));
            add(NodeUIUtils.wrapBody(conditionPanel));

            add(NodeUIUtils.wrapBody(ifBody));
            add(new JLabel("Else:"));
            add(NodeUIUtils.wrapBody(elseBody));

        }
    }
}
