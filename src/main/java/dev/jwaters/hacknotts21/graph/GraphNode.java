package dev.jwaters.hacknotts21.graph;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public abstract sealed class GraphNode<C extends JComponent> permits
        AbstractConditionNode,
        BlockNode,
        BooleanLiteralNode,
        DeclareVarNode,
        GetVarNode,
        IfElseNode,
        InputNode,
        IntegerLiteralNode,
        PrintNode,
        SetVarNode,
        StringLiteralNode,
        TwoNumberOperationNode {
    @Nullable
    private final GraphNode<?> parent;
    @SuppressWarnings("unused") // used for serialization
    @Expose(deserialize = false)
    private final String _type = getClass().getName();

    public GraphNode(@Nullable GraphNode<?> parent) {
        this.parent = parent;
    }

    @Nullable
    public GraphNode<?> getParent() {
        return parent;
    }

    @Nullable
    public abstract Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc);

    @Nullable
    public abstract Type getReturnType(FunctionRepr containingFunc);

    public abstract C createComponent();
    public abstract void readFromComponent(C component) throws UserInputException;
    public abstract void writeToComponent(C component);

    public abstract List<@Nullable GraphNode<?>> getChildren();

    @SuppressWarnings("unchecked")
    protected static <T extends JComponent> void doReadFromComponent(GraphNode<?> node, T component) throws UserInputException {
        ((GraphNode<T>) node).readFromComponent(component);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends JComponent> void doWriteToComponent(GraphNode<?> node, T component) {
        ((GraphNode<T>) node).writeToComponent(component);
    }

    public static class Serializer implements JsonDeserializer<GraphNode<?>>, InstanceCreator<GraphNode<?>> {
        private final ThreadLocal<Deque<GraphNode<?>>> stack = ThreadLocal.withInitial(ArrayDeque::new);

        @Override
        public GraphNode<?> deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return deserialize0(json, context);
        }

        @SuppressWarnings("unchecked")
        private <T extends GraphNode<?>> T deserialize0(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
            String typeStr = json.getAsJsonObject().get("_type").getAsString();
            Class<T> clazz;
            try {
                clazz = (Class<T>) Class.forName(typeStr, false, getClass().getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown type: " + typeStr, e);
            }
            if (Modifier.isAbstract(clazz.getModifiers()) || !GraphNode.class.isAssignableFrom(clazz)) {
                throw new JsonParseException("Cannot instantiate graph node type: " + typeStr);
            }
            Constructor<T> ctor;
            try {
                ctor = clazz.getConstructor(GraphNode.class);
            } catch (NoSuchMethodException e) {
                throw new AssertionError("GraphNode has missing constructor taking a parent");
            }
            T node;
            try {
                node = ctor.newInstance(stack.get().peek());
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Exception creating GraphNode", e);
            }
            stack.get().push(node);
            try {
                context.deserialize(json, clazz);
            } finally {
                stack.get().pop();
            }
            return node;
        }

        @Override
        public GraphNode<?> createInstance(java.lang.reflect.Type type) {
            return stack.get().getFirst();
        }
    }
}
