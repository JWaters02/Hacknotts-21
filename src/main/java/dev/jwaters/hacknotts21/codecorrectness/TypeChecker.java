package dev.jwaters.hacknotts21.codecorrectness;

import dev.jwaters.hacknotts21.graph.BlockNode;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.graph.InputNode;
import dev.jwaters.hacknotts21.graph.PrintNode;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TypeChecker {
    @Nullable
    public static GraphNode<?> isValid(GraphNode<?> node) {
        for (GraphNode<?> child : node.getChildren()) {
            if (
                // dont check on null types
                child.getChildren() == null ||
                !Objects.equals(child.getReturnType(), node.getExpectedChildType(child)
            )) return node;

            var childValid = isValid(child);
            if (childValid != null) return childValid;
        }

        return null;
    }
}
