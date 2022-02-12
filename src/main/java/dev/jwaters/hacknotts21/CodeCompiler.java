package dev.jwaters.hacknotts21;

import dev.jwaters.hacknotts21.graph.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

enum Language {
    PYTHON
}

public abstract class CodeCompiler {
    public static void compile(Map<String, BlockNode> code, Language language, File outFile) throws IOException {
        switch (language) {
            case PYTHON -> {
                PythonCompiler pythonCompiler = new PythonCompiler(outFile);
                var declarenode = new DeclareVarNode();
                declarenode.setName("testva");
                var ifnode = new IfNode();
                ifnode.setCondition(declarenode);
                ifnode.getBody().getChildren().add(declarenode);
                var blocknode = new BlockNode();
                blocknode.getChildren().add(ifnode);
                pythonCompiler.compile(Map.of("main", blocknode));
            }
        }
    }

    void handleNode(GraphNode<?> node) throws IOException {
        if (node instanceof DeclareVarNode declarenode) {
            declareVar(declarenode);
        } else if (node instanceof IfNode ifnode) {
            ifStatement(ifnode);
        } else if (node instanceof IfElseNode ifelsenode) {
            ifElseStatement(ifelsenode);
        } else if (node instanceof WhileNode whileNode) {
            whileStatement(whileNode);
        } else if (node instanceof BlockNode blockNode) {
            for (GraphNode<?> child : blockNode.getChildren()) {
                handleNode(child);
            }
        }
    }

    abstract void setVar();

    abstract void declareVar(DeclareVarNode node) throws IOException;

    abstract void ifStatement(IfNode node) throws IOException;

    abstract void ifElseStatement(IfElseNode node) throws IOException;

    abstract void whileStatement(WhileNode node) throws IOException;

    abstract void blockStatement(BlockNode node) throws IOException;

    abstract void close() throws IOException;

    public static void main(String[] args) throws IOException {
        compile(null, Language.PYTHON, new File("test.py"));
    }
}

class PythonCompiler extends CodeCompiler {
    private final FileWriter writer;
    private int indentationLevel;

    public PythonCompiler(File outFile) throws IOException {
        this.writer = new FileWriter(outFile);
        this.indentationLevel = 0;
    }

    public void compile(Map<String, BlockNode> code) throws IOException {
        handleNode(code.get("main"));
        close();
    }

    @Override
    void setVar() {

    }

    @Override
    void declareVar(DeclareVarNode node) throws IOException {
        // meaningless in python

        writer.write("wowie");
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
