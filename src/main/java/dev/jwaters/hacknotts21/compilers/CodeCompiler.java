package dev.jwaters.hacknotts21.compilers;

import dev.jwaters.hacknotts21.DnDSerde;
import dev.jwaters.hacknotts21.MainForm;
import dev.jwaters.hacknotts21.graph.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public abstract class CodeCompiler {
    public StringBuilder writer = new StringBuilder();

    public static String compile(Collection<FunctionRepr> code, Language language) throws IOException {
        CodeCompiler compiler = switch (language) {
            case JAVA -> new JavaCompiler();
            case PYTHON -> new PythonCompiler();
        };
        compiler.compile(code);
        return compiler.writer.toString();
    }

    public void compile(Collection<FunctionRepr> code) throws IOException {
        handleNode(code.stream().filter(function -> function.getName().equals("main")).findFirst().get().getBody());
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
        } else if (node instanceof CallFunctionNode callfunctionnode) {
            callFunction(callfunctionnode);
        } else if (node instanceof NotNode notNode) {
            not(notNode);
        } else if (node instanceof StringLiteralNode stringLiteralNode) {
            stringLiteral(stringLiteralNode);
        } else if (node instanceof ToStringNode toStringNode) {
            handleToString(toStringNode);
        } else if (node instanceof TwoNumberOperationNode booleanoperationnode) {
            twoNumberOperation(booleanoperationnode);
        } else if (node instanceof IntegerLiteralNode integerliteralnode) {
            integerLiteral(integerliteralnode);
        } else if (node instanceof TwoBooleanOperationNode booleanoperationnode) {
            twoBooleanOperation(booleanoperationnode);
        } else if (node instanceof BlockNode blockNode) {
            blockStatement(blockNode);
        }
    }

    abstract void handleToString(ToStringNode node) throws IOException;

    abstract void stringLiteral(StringLiteralNode node);

    abstract void not(NotNode node) throws IOException;

    abstract void callFunction(CallFunctionNode node) throws IOException;

    abstract void declareVar(DeclareVarNode node) throws IOException;

    abstract void ifStatement(IfNode node) throws IOException;

    abstract void ifElseStatement(IfElseNode node) throws IOException;

    abstract void whileStatement(WhileNode node) throws IOException;

    abstract void blockStatement(BlockNode node) throws IOException;

    abstract void getVar(GetVarNode node) throws IOException;

    abstract void input(InputNode node) throws IOException;

    abstract void print(PrintNode node) throws IOException;

    abstract void setVar(SetVarNode node) throws IOException;

    abstract void twoNumberOperation(TwoNumberOperationNode node) throws IOException;

    abstract void twoBooleanOperation(TwoBooleanOperationNode node) throws IOException;

    abstract void integerLiteral(IntegerLiteralNode node) throws IOException;

    public static void main(String[] args) throws IOException {
        var functions = DnDSerde.readFromFile(new File("code.json"));

        JComboBox<Language> cbLangs = MainForm.getInstance().getCbSelectLang();
        CodeCompiler.compile(functions, (Language) Objects.requireNonNull(cbLangs.getSelectedItem()));
    }
}

