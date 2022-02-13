package dev.jwaters.hacknotts21;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import dev.jwaters.hacknotts21.graph.*;
import dev.jwaters.hacknotts21.type.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class DnDSerde {
    private static final Gson GSON = createGson();

    private static Gson createGson() {
        GraphNode.SerializerFactory factory = new GraphNode.SerializerFactory();
        GsonBuilder gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapterFactory(factory)
                .registerTypeAdapter(Type.class, new Type.Serializer());
        for (Class<? extends GraphNode<?>> clazz : GraphNode.GRAPH_NODE_TYPES) {
            gson.registerTypeAdapter(clazz, factory);
        }
        return gson.create();
    }

    public static Collection<FunctionRepr> readFromFile(File file) throws IOException, JsonParseException {
        try (var reader = new BufferedReader(new FileReader(file))) {
            return GSON.fromJson(reader, new TypeToken<ArrayList<FunctionRepr>>(){}.getType());
        }
    }

    public static void writeToFile(File file, Collection<FunctionRepr> functions) throws IOException {
        try (var writer = new BufferedWriter(new FileWriter(file))) {
            GSON.toJson(functions, writer);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneWithSerde(T obj) {
        return (T) GSON.fromJson(GSON.toJsonTree(obj), obj.getClass());
    }
}
