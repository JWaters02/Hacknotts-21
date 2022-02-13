package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.HintTextField;
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

    public void readFromPanel(Panel panel) {
        this.name = panel.nameField.getText();
    }

    public void writeToPanel(Panel panel) {
        panel.nameField.setText(this.name);
    }

    public static final class Panel extends JPanel {
        private final HintTextField nameField = new HintTextField("Name");

        public Panel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            setBorder(BorderFactory.createLineBorder(Color.black));
            setOpaque(true);
            setBackground(Color.darkGray);
            add(new JLabel("Function: "));
            add(nameField);
        }
    }
}
