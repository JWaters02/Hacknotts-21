package dev.jwaters.hacknotts21.codecorrectness;

import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.thankyoujava.Pair;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TypeChecker {
    @Nullable
    public static Pair<GraphNode<?>, String> isValid(GraphNode<?> node, FunctionRepr containingFunc) {
        for (GraphNode<?> child : node.getChildren()) {
            if (child == null) return new Pair<>(node, "Child is null");

            if (
                // dont check on void types
                child.getReturnType(containingFunc) == VoidType.INSTANCE ||
                !Objects.equals(child.getReturnType(containingFunc), node.getExpectedChildType(child, containingFunc)
            )) return new Pair<>(node, String.format("expected %s got %s", node.getExpectedChildType(child, containingFunc), child.getReturnType(containingFunc)));

            var childValid = isValid(child, containingFunc);
            if (childValid != null) return childValid;
        }

        return null;
    }
}
