package dev.jwaters.hacknotts21;

import dev.jwaters.hacknotts21.graph.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

enum Language {
    PYTHON
}

public abstract class CodeCompiler {
    public static void compile(Map<String, BlockNode> code, Language language, File outFile) throws IOException {
        switch (language) {
            case PYTHON -> {
                PythonCompiler pythonCompiler = new PythonCompiler(outFile);
                var blocknode = new BlockNode(null);
                var ifnode = new IfNode(blocknode);
                var declarenode = new DeclareVarNode(ifnode);
                declarenode.setName("testva");
                ifnode.setCondition(declarenode);
                ifnode.getBody().getChildren().add(declarenode);
                blocknode.getChildren().add(ifnode);
                pythonCompiler.compile(code);
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
        } else if (node instanceof WhileNode whilenode) {
            whileStatement(whilenode);
        } else if (node instanceof GetVarNode getvarnode) {
            getVar(getvarnode);
        } else if (node instanceof InputNode inputnode) {
            input(inputnode);
        } else if (node instanceof PrintNode printnode) {
            print(printnode);
        } else if (node instanceof SetVarNode setvarnode) {
            setVar(setvarnode);
        } else if (node instanceof TwoNumberOperationNode booleanoperationnode) {
            twoNumberbooleanOperation(booleanoperationnode);
        } else if (node instanceof IntegerLiteralNode integerliteralnode) {
            integerLiteral(integerliteralnode);
        } else if (node instanceof BlockNode blockNode) {
            for (GraphNode<?> child : blockNode.getChildren()) {
                handleNode(child);
            }
        }
    }

    abstract void declareVar(DeclareVarNode node) throws IOException;

    abstract void ifStatement(IfNode node) throws IOException;

    abstract void ifElseStatement(IfElseNode node) throws IOException;

    abstract void whileStatement(WhileNode node) throws IOException;

    abstract void blockStatement(BlockNode node) throws IOException;

    abstract void getVar(GetVarNode node) throws IOException;

    abstract void input(InputNode node) throws IOException;

    abstract void print(PrintNode node) throws IOException;

    abstract void setVar(SetVarNode node) throws IOException;

    abstract void twoNumberbooleanOperation(TwoNumberOperationNode node) throws IOException;

    abstract void integerLiteral(IntegerLiteralNode node) throws IOException;

    abstract void close() throws IOException;

    public static void main(String[] args) throws IOException {
        var blocknode = new BlockNode(null);
        var ifnode = new IfNode(blocknode);
        var getvar1 = new GetVarNode(ifnode);
        getvar1.setVarName("test");
        var setvar = new SetVarNode(ifnode);
        setvar.setVarName("test");
        var add = new TwoNumberOperationNode(setvar);
        add.setOperation(TwoNumberOperationNode.TwoNumberOperationEnum.ADD);
        var getvar2 = new GetVarNode(add);
        getvar2.setVarName("test");
        var literal = new IntegerLiteralNode(add);
        literal.setValue(1);

        add.setLeft(getvar2);
        add.setRight(literal);
        setvar.setValue(add);
        ifnode.setCondition(getvar1);
        ifnode.getBody().getChildren().add(setvar);
        blocknode.getChildren().add(ifnode);
        compile(Map.of("main", blocknode), Language.PYTHON, new File("test.py"));
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
    void twoNumberbooleanOperation(TwoNumberOperationNode node) throws IOException {
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
