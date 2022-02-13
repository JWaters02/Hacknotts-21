package dev.jwaters.hacknotts21.codecorrectness;

import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.thankyoujava.Pair;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class TypeChecker {
    /**
     * Checks if the given graph is valid
     * @param functions list of functions in the program
     * @return null if all functions are valid, otherwise a pair of the offending function and the error message
     */
    @Nullable
    public static Pair<GraphNode<?>, String> isValid(List<FunctionRepr> functions) {
        for (FunctionRepr function : functions) {
            var error = isValid(function.getBody(), function);
            if (error != null) return error;
        }
        return null;
    }

    @Nullable
    private static Pair<GraphNode<?>, String> isValid(GraphNode<?> node, FunctionRepr containingFunc) {
        for (GraphNode<?> child : node.getChildren()) {
            if (child == null) return new Pair<>(node, "Child is null");

            if (
                // dont check on void types
                child.getReturnType(containingFunc) != VoidType.INSTANCE &&
                !Objects.equals(child.getReturnType(containingFunc), node.getExpectedChildType(child, containingFunc)
            )) return new Pair<>(node, String.format("expected %s got %s", node.getExpectedChildType(child, containingFunc), child.getReturnType(containingFunc)));

            var childValid = isValid(child, containingFunc);
            if (childValid != null) return childValid;
        }

        return null;
    }
}
