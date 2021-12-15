package dev.negativekb.api.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class UtilHTTP {

    @SneakyThrows
    public JSONObject getJSONObjectFromMojang(@NotNull String url) {
        URL rawURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) rawURL.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        if (content.toString().isEmpty()) { // Mojang API 204 fix
            return null;
        }

        return new JSONObject(content.toString());
    }
}
