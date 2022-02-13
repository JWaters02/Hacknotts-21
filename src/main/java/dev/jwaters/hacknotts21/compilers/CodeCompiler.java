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
    public static void compile(Collection<FunctionRepr> code, Language language) throws IOException {
        switch (language) {
            case PYTHON -> {
                PythonCompiler pythonCompiler = new PythonCompiler();
                pythonCompiler.compile(code);
            }
            case JAVA -> {
                JavaCompiler javaCompiler = new JavaCompiler();
                javaCompiler.compile(code);
            }
            case LOLCODE -> {
                LOLCODECompiler lolcodeCompiler = new LOLCODECompiler();
                lolcodeCompiler.compile(code);
            }
        }
    }

    public void compile(Collection<FunctionRepr> code) throws IOException {
        handleNode(code.stream().filter(function -> function.getName().equals("main")).findFirst().get().getBody());
    }

    public void constructOutput(StringBuilder outSB) {
        MainForm.getInstance().getTxtCodeOutput().setText(outSB.toString());
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
            twoNumberOperation(booleanoperationnode);
        } else if (node instanceof IntegerLiteralNode integerliteralnode) {
            integerLiteral(integerliteralnode);
        } else if (node instanceof TwoBooleanOperationNode booleanoperationnode) {
            twoBooleanOperation(booleanoperationnode);
        } else if (node instanceof BlockNode blockNode) {
            blockStatement(blockNode);
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

    abstract void twoNumberOperation(TwoNumberOperationNode node) throws IOException;

    abstract void twoBooleanOperation(TwoBooleanOperationNode node) throws IOException;

    abstract void integerLiteral(IntegerLiteralNode node) throws IOException;

    public static void main(String[] args) throws IOException {
        var functions = DnDSerde.readFromFile(new File("code.json"));

        JComboBox<Language> cbLangs = MainForm.getInstance().getCbSelectLang();
        CodeCompiler.compile(functions, (Language) Objects.requireNonNull(cbLangs.getSelectedItem()));
    }
}

