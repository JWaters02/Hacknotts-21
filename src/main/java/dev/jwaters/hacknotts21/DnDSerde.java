package dev.jwaters.hacknotts21;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import dev.jwaters.hacknotts21.graph.FunctionRepr;
import dev.jwaters.hacknotts21.graph.GraphNode;
import dev.jwaters.hacknotts21.type.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DnDSerde {
    private static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(GraphNode.class, new GraphNode.Serializer())
            .registerTypeAdapter(Type.class, new Type.Serializer())
            .create();

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
