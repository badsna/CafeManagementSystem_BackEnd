package com.example.cafemanagementsystem.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFileToJsonObject {

public JSONObject read() throws IOException, JSONException {
    String file="src/main/resources/openapi/response.json";
    String content=new String(Files.readAllBytes(Paths.get(file)));
    return new JSONObject(content);

}
}
