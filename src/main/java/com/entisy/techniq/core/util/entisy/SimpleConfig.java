package com.entisy.techniq.core.util.entisy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class SimpleConfig {
    private SimpleMap<String, String> map = new SimpleMap<>();
    public static final SimpleConfig EMPTY = new SimpleConfig();
    private static JsonObject json = new JsonObject();
    private File file;

    public SimpleConfig(String fileName) {
        try {
            file = new File("config\\" + fileName + ".json");

            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setJSON(JsonObject jsonObject) {
        json = jsonObject;
    }

    private SimpleConfig() {}

    public String get(String key) {
        return json.get(key).getAsString() != null ? json.get(key).getAsString() : "VALUE TO KEY " + key + " IS NULL";
    }

    public void add(String key, String value) {
        if (!map.getKeys().contains(key)) {
            map.append(key, value);
            addToJSON(key, value);
        }
    }

    public void remove(String key) {
        map.remove(key);
        removeFromJSON(key);
    }

    private void addToJSON(String key, String value) {
        for (int i = 0; i < map.size(); i++) {
            json.addProperty(key, value);
        }
        FileHelper.writeContent(file, json.toString());
    }

    private void removeFromJSON(String key) {
        for (int i = 0; i < map.size(); i++) {
            json.remove(key);
        }
        FileHelper.writeContent(file, json.toString());
    }

    public static SimpleConfig getConfig(String name) {
        File dir = new File("config");
        String fileName = "";
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().replace(".json", "").equals(name)) fileName = name;
        }

        SimpleConfig ret = new SimpleConfig(fileName);
        ret.setJSON(json);
        return ret;
    }

    private void read(File file) {
        try {
            FileReader reader = new FileReader(file);
            JsonParser parser = new JsonParser();

            Object object = parser.parse(reader);
            JsonObject json = (JsonObject) object;

            for (int i = 0; i < json.size(); i++) {
                json.entrySet().stream().forEach(e -> {
                    map.append(e.getKey(), e.getValue().toString());
                });
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
