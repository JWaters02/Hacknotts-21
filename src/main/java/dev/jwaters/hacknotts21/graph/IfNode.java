package dev.jwaters.hacknotts21.graph;

import org.jetbrains.annotations.Nullable;

public final class IfNode extends AbstractConditionNode {
    public IfNode(@Nullable GraphNode<?> parent) {
        super(parent, "If");
    }
}
