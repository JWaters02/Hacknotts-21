package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.HintTextField;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionRepr {
    @Expose
    private String name;
    @Expose
    private Type returnType = VoidType.INSTANCE;
    @Expose
    private final List<DeclareVarNode> params = new ArrayList<>();
    @Expose
    private final BlockNode body = new BlockNode(null);

    public FunctionRepr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<DeclareVarNode> getParams() {
        return params;
    }

    public BlockNode getBody() {
        return body;
    }

    public Panel createComponent() {
        Panel panel = new Panel(body.createComponent());
        panel.putClientProperty("functionRepr", this);
        return panel;
    }

    public void readFromPanel(Panel panel) throws UserInputException {
        this.name = panel.nameField.getText();
        this.body.readFromComponent(panel.body);
    }

    public void writeToPanel(Panel panel) {
        panel.nameField.setText(this.name);
        this.body.writeToComponent(panel.body);
    }

    public static final class Panel extends JPanel {
        private final HintTextField nameField = new HintTextField("Name");
        private final JPanel body;

        public Panel(JPanel body) {
            this.body = body;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.black));
            setOpaque(true);
            setBackground(Color.darkGray);

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            headerPanel.add(new JLabel("Function: "));
            headerPanel.add(nameField);
            add(headerPanel);

            add(NodeUIUtils.wrapBody(body));
        }
    }
}
