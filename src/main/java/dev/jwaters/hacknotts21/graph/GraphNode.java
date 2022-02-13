package dev.jwaters.hacknotts21.graph;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.jwaters.hacknotts21.swing.NodeUIUtils;
import dev.jwaters.hacknotts21.thankyoujava.JavaIsBadUtil;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

public abstract sealed class GraphNode<C extends JComponent> permits
        AbstractConditionNode,
        BlockNode,
        BooleanLiteralNode,
        DeclareVarNode,
        GetVarNode,
        IfElseNode,
        InputNode,
        IntegerLiteralNode,
        NotNode,
        PrintNode,
        SetVarNode,
        StringLiteralNode,
        TwoBooleanOperationNode,
        TwoNumberOperationNode {
    @SuppressWarnings("unchecked")
    public static final Class<? extends GraphNode<?>>[] GRAPH_NODE_TYPES = JavaIsBadUtil.getConcreteClasses((Class<GraphNode<?>>) (Class<?>) GraphNode.class);

    @Nullable
    private final GraphNode<?> parent;

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

    public final C createComponent() {
        C component = makeComponent();
        component.putClientProperty("node", this);
        NodeUIUtils.addListeners(component);
        return component;
    }
    protected abstract C makeComponent();
    public abstract void readFromComponent(C component) throws UserInputException;
    public abstract void writeToComponent(C component);

    public abstract List<@Nullable GraphNode<?>> getChildren();

    @SuppressWarnings("unchecked")
    public static <T extends JComponent> void doReadFromComponent(GraphNode<?> node, T component) throws UserInputException {
        ((GraphNode<T>) node).readFromComponent(component);
    }

    @SuppressWarnings("unchecked")
    public static <T extends JComponent> void doWriteToComponent(GraphNode<?> node, T component) {
        ((GraphNode<T>) node).writeToComponent(component);
    }

    public static class SerializerFactory implements TypeAdapterFactory, InstanceCreator<GraphNode<?>> {
        private final ThreadLocal<Deque<GraphNode<?>>> stack = ThreadLocal.withInitial(ArrayDeque::new);

        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<? super T> rawType = type.getRawType();
            if (!GraphNode.class.isAssignableFrom(rawType)) {
                return null;
            }
            return (TypeAdapter<T>) Serializer.create(rawType.asSubclass(GraphNode.class), gson, typ -> gson.getDelegateAdapter(this, typ), stack);
        }

        @Override
        public GraphNode<?> createInstance(java.lang.reflect.Type type) {
            return stack.get().getFirst();
        }
    }

    public static class Serializer<T extends GraphNode<?>> extends TypeAdapter<T> {
        private final Class<T> reifiedType;
        private final Gson gson;
        private final Function<TypeToken<T>, TypeAdapter<T>> delegate;
        private final ThreadLocal<Deque<GraphNode<?>>> stack;

        @SuppressWarnings("unchecked")
        public static <T extends GraphNode<?>> TypeAdapter<?> create(Class<T> reifiedType, Gson gson, Function<TypeToken<?>, TypeAdapter<?>> delegate, ThreadLocal<Deque<GraphNode<?>>> stack) {
            return new Serializer<>(reifiedType, gson, (Function<TypeToken<T>, TypeAdapter<T>>) (Function<?, ?>) delegate, stack);
        }

        private Serializer(Class<T> reifiedType, Gson gson, Function<TypeToken<T>, TypeAdapter<T>> delegate, ThreadLocal<Deque<GraphNode<?>>> stack) {
            this.reifiedType = reifiedType;
            this.gson = gson;
            this.delegate = delegate;
            this.stack = stack;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(JsonWriter out, T value) {
            Class<T> type = (Class<T>) value.getClass();
            JsonObject json = delegate.apply(TypeToken.get(type)).toJsonTree(value).getAsJsonObject();
            json.addProperty("_type", value.getClass().getName());
            gson.toJson(json, out);
        }

        @SuppressWarnings("unchecked")
        @Override
        public T read(JsonReader in) {
            Class<T> clazz = reifiedType;
            JsonObject json = gson.fromJson(in, JsonObject.class);
            //noinspection RedundantCast
            if (reifiedType == (Class<?>) GraphNode.class) {
                String typeStr = json.get("_type").getAsString();
                try {
                    clazz = (Class<T>) Class.forName(typeStr, false, getClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new JsonParseException("Unknown type: " + typeStr, e);
                }
                if (Modifier.isAbstract(clazz.getModifiers()) || !GraphNode.class.isAssignableFrom(clazz)) {
                    throw new JsonParseException("Cannot instantiate graph node type: " + typeStr);
                }
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
                delegate.apply(TypeToken.get(clazz)).fromJsonTree(json);
            } finally {
                stack.get().pop();
            }
            return node;
        }
    }
}
