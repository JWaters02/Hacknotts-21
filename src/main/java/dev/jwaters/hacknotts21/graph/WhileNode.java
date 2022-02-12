package dev.jwaters.hacknotts21.graph;

import org.jetbrains.annotations.Nullable;

public final class WhileNode extends AbstractConditionNode {
    public WhileNode(@Nullable GraphNode<?> parent) {
        super(parent, "While");
    }
}
