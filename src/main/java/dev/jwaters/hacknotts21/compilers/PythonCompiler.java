package dev.jwaters.hacknotts21.compilers;

import dev.jwaters.hacknotts21.graph.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

class PythonCompiler extends CodeCompiler {
    private final FileWriter writer;
    private int indentationLevel;

    public PythonCompiler(File outFile) throws IOException {
        this.writer = new FileWriter(outFile);
        this.indentationLevel = 0;
    }

    @Override
    void declareVar(DeclareVarNode node) {
        // meaningless in python
    }

    @Override
    void ifStatement(IfNode node) throws IOException {
        writer.write("if ");
        handleNode(node.getCondition());
        writer.write(":\n");
        indent();
        printIndent();
        handleNode(node.getBody());
        writer.write("\n");
        dedent();
        printIndent();
    }

    @Override
    void ifElseStatement(IfElseNode node) throws IOException {
        writer.write("if ");
        handleNode(node.getCondition());
        writer.write(":\n");
        indent();
        printIndent();
        handleNode(node.getIfBody());
        writer.write("\n");
        dedent();
        printIndent();
        writer.write("else:\n");
        indent();
        printIndent();
        handleNode(node.getElseBody());
        writer.write("\n");
        printIndent();
        dedent();
    }

    @Override
    void whileStatement(WhileNode node) throws IOException {
        writer.write("while ");
        handleNode(node.getCondition());
        writer.write(":\n");
        indent();
        printIndent();
        handleNode(node.getBody());
        writer.write("\n");
        dedent();
        printIndent();
    }

    @Override
    void blockStatement(BlockNode node) throws IOException {
        for (GraphNode<?> child : node.getChildren()) {
            printIndent();
            handleNode(child);
        }
    }

    @Override
    void getVar(GetVarNode node) throws IOException {
        writer.write(node.getVarName());
    }

    @Override
    void input(InputNode node) throws IOException {
        writer.write("input()");
    }

    @Override
    void print(PrintNode node) throws IOException {
        writer.write("print(");
        handleNode(node.getValue());
        writer.write(")");
    }

    @Override
    void setVar(SetVarNode node) throws IOException {
        writer.write(node.getVarName());
        writer.write(" = ");
        handleNode(node.getValue());
    }

    @Override
    void twoNumberOperation(TwoNumberOperationNode node) throws IOException {
        handleNode(node.getLeft());

        switch (node.getOperation()) {
            case ADD -> writer.write(" + ");
            case SUBTRACT -> writer.write(" - ");
            case MULTIPLY -> writer.write(" * ");
            case DIVIDE -> writer.write(" / ");
            case MODULUS -> writer.write(" % ");
            case EQUAL -> writer.write(" == ");
            case NOT_EQUAL -> writer.write(" != ");
            case GREATER_THAN -> writer.write(" > ");
            case LESS_THAN -> writer.write(" < ");
            case GREATER_THAN_OR_EQUAL -> writer.write(" >= ");
            case LESS_THAN_OR_EQUAL -> writer.write(" <= ");
        }

        handleNode(node.getRight());
    }

    @Override
    void twoBooleanOperation(TwoBooleanOperationNode node) throws IOException {
        handleNode(node.getLeft());

        switch (node.getOperation()) {
            case AND -> writer.write(" and ");
            case OR -> writer.write(" or ");
            case XOR -> writer.write(" ^ ");
            case EQUAL -> writer.write(" == ");
            case NOT_EQAUL -> writer.write(" != ");
        }

        handleNode(node.getRight());
    }

    @Override
    void integerLiteral(IntegerLiteralNode node) throws IOException {
        writer.write(String.format("%d", node.getValue()));
    }

    @Override
    void close() throws IOException {
        writer.close();
    }

    void printIndent() throws IOException {
        for (int i = 0; i < indentationLevel; i++) {
            writer.write(" ");
        }
    }

    void indent() {
        indentationLevel += 2;
    }

    void dedent() {
        indentationLevel -= 2;
    }
}
