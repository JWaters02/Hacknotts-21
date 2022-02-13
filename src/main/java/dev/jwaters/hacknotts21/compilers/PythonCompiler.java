package dev.jwaters.hacknotts21.compilers;

import dev.jwaters.hacknotts21.graph.*;

import java.io.IOException;

class PythonCompiler extends CodeCompiler {
    private int indentationLevel;

    public PythonCompiler() {
        this.writer = new StringBuilder();
        this.indentationLevel = 0;
    }

    @Override
    void declareVar(DeclareVarNode node) {
        // meaningless in python
    }

    @Override
    void ifStatement(IfNode node) throws IOException {
        writer.append("if ");
        handleNode(node.getCondition());
        writer.append(":\n");
        indent();
        printIndent();
        handleNode(node.getBody());
        writer.append("\n");
        dedent();
        printIndent();
    }

    @Override
    void ifElseStatement(IfElseNode node) throws IOException {
        writer.append("if ");
        handleNode(node.getCondition());
        writer.append(":\n");
        indent();
        printIndent();
        handleNode(node.getIfBody());
        writer.append("\n");
        dedent();
        printIndent();
        writer.append("else:\n");
        indent();
        printIndent();
        handleNode(node.getElseBody());
        writer.append("\n");
        printIndent();
        dedent();
    }

    @Override
    void whileStatement(WhileNode node) throws IOException {
        writer.append("while ");
        handleNode(node.getCondition());
        writer.append(":\n");
        indent();
        printIndent();
        handleNode(node.getBody());
        writer.append("\n");
        dedent();
        printIndent();
    }

    @Override
    void blockStatement(BlockNode node) throws IOException {
        for (GraphNode<?> child : node.getChildren()) {
            printIndent();
            handleNode(child);
            writer.append("\n");
        }
    }

    @Override
    void getVar(GetVarNode node) {
        writer.append(node.getVarName());
    }

    @Override
    void input(InputNode node) {
        writer.append("input()");
    }

    @Override
    void print(PrintNode node) throws IOException {
        writer.append("print(");
        handleNode(node.getValue());
        writer.append(")");
    }

    @Override
    void setVar(SetVarNode node) throws IOException {
        writer.append(node.getVarName());
        writer.append(" = ");
        handleNode(node.getValue());
    }

    @Override
    void twoNumberOperation(TwoNumberOperationNode node) throws IOException {
        handleNode(node.getLeft());

        switch (node.getOperation()) {
            case ADD -> writer.append(" + ");
            case SUBTRACT -> writer.append(" - ");
            case MULTIPLY -> writer.append(" * ");
            case DIVIDE -> writer.append(" / ");
            case MODULUS -> writer.append(" % ");
            case EQUAL -> writer.append(" == ");
            case NOT_EQUAL -> writer.append(" != ");
            case GREATER_THAN -> writer.append(" > ");
            case LESS_THAN -> writer.append(" < ");
            case GREATER_THAN_OR_EQUAL -> writer.append(" >= ");
            case LESS_THAN_OR_EQUAL -> writer.append(" <= ");
        }

        handleNode(node.getRight());
    }

    @Override
    void twoBooleanOperation(TwoBooleanOperationNode node) throws IOException {
        handleNode(node.getLeft());

        switch (node.getOperation()) {
            case AND -> writer.append(" and ");
            case OR -> writer.append(" or ");
            case XOR -> writer.append(" ^ ");
            case EQUAL -> writer.append(" == ");
            case NOT_EQUAL -> writer.append(" != ");
        }

        handleNode(node.getRight());
    }

    @Override
    void integerLiteral(IntegerLiteralNode node) {
        writer.append(String.format("%d", node.getValue()));
    }

    void printIndent() {
        writer.append(" ".repeat(Math.max(0, indentationLevel)));
    }

    void indent() {
        indentationLevel += 2;
    }

    void dedent() {
        indentationLevel -= 2;
    }
}
