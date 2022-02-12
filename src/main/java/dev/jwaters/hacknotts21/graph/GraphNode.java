package dev.jwaters.hacknotts21.graph;

import javax.swing.*;

public sealed interface GraphNode<C extends JComponent> permits BlockNode, DeclareVarNode, IfElseNode, IfNode, WhileNode {
    C createComponent();
    void readFromComponent(C component);
    void writeToComponent(C component);
}
