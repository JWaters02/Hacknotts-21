package dev.jwaters.hacknotts21.type;

import dev.jwaters.hacknotts21.graph.BlockNode;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.graph.PrintNode;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TypeChecker {
    @Nullable
    public static GraphNode<?> isValid(GraphNode<?> node) {
//        if (node instanceof BlockNode blockNode) {
//            for (var child : blockNode.getChildren()) {
//                var childValid = isValid(child);
//                if (childValid != null) return childValid;
//            }
//        } else if (node instanceof PrintNode printNode) {
//            if (!Objects.equals(
//                    printNode.getExpectedChildType(printNode.getValue()),
//                    printNode.getValue().getReturnType()
//            ));
//        }
        return null;
    }
}
