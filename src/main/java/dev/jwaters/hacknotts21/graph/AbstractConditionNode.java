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

public abstract sealed class AbstractConditionNode extends GraphNode<AbstractConditionNode.Panel> permits IfNode, WhileNode {
    private final String conditionTypeName;
    @Expose
    private GraphNode<?> condition = new BooleanLiteralNode(this);
    @Expose
    private final BlockNode body = new BlockNode(this);

    public AbstractConditionNode(@Nullable GraphNode<?> parent, String conditionTypeName) {
        super(parent);
        this.conditionTypeName = conditionTypeName;
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        if (child == condition) {
            return BooleanType.INSTANCE;
        } else if (child == body) {
            return VoidType.INSTANCE;
        }
        return null;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    public Panel createComponent() {
        return new Panel(conditionTypeName, condition.createComponent(), body.createComponent());
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        doReadFromComponent(this.condition, component.condition);
        doReadFromComponent(this.body, component.body);
    }

    @Override
    public void writeToComponent(Panel component) {
        doWriteToComponent(this.condition, component.condition);
        doWriteToComponent(this.body, component.body);
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return Arrays.asList(condition, body);
    }

    public void setCondition(GraphNode<?> condition) {
        this.condition = condition;
    }

    public GraphNode<?> getCondition() {
        return condition;
    }

    public BlockNode getBody() {
        return body;
    }

    public static final class Panel extends JPanel {
        private final JComponent condition;
        private final JComponent body;

        public Panel(String conditionTypeName, JComponent condition, JComponent body) {
            this.condition = condition;
            this.body = body;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JPanel conditionPanel = new JPanel();
            conditionPanel.setLayout(new FlowLayout());
            conditionPanel.add(new JLabel(conditionTypeName + " "));
            conditionPanel.add(condition);
            conditionPanel.add(new JLabel(":"));
            add(NodeUIUtils.wrapBody(conditionPanel));

            add(NodeUIUtils.wrapBody(body));
        }
    }
}
