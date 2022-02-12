package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;

import java.util.ArrayList;
import java.util.List;

public class FunctionRepr {
    @Expose
    private final String name;
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
}
