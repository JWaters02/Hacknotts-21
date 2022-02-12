package dev.jwaters.hacknotts21.codecorrectness;

import dev.jwaters.hacknotts21.graph.BlockNode;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.graph.InputNode;
import dev.jwaters.hacknotts21.graph.PrintNode;
import dev.jwaters.hacknotts21.thankyoujava.Pair;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TypeChecker {
    @Nullable
    public static Pair<GraphNode<?>, String> isValid(GraphNode<?> node) {
        for (GraphNode<?> child : node.getChildren()) {
            if (child == null) return new Pair<>(node, "Child is null");

            if (
                // dont check on void types
                child.getReturnType() == VoidType.INSTANCE ||
                !Objects.equals(child.getReturnType(), node.getExpectedChildType(child)
            )) return new Pair<>(node, String.format("expected %s got %s", node.getExpectedChildType(child), child.getReturnType()));

            var childValid = isValid(child);
            if (childValid != null) return childValid;
        }

        return null;
    }
}
