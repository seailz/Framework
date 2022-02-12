/*
 * MIT License
 *
 * Copyright (c) 2022 Negative
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package dev.negativekb.api.util.cache;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@SuppressWarnings("all")
public abstract class ObjectHashCache<K, V> {

    private final String path;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Save the Cache to the JSON file
     *
     * @param cacheHashMap Class Type HashMap
     */
    public void save(@NotNull LinkedHashMap<K, V> cacheHashMap) throws IOException {
        File file = getFile(path);
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);
        gson.toJson(cacheHashMap, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Load the Cache from the JSON file
     *
     * @return A new instance of an ArrayList with the new Cache
     */
    @NotNull
    public LinkedHashMap<K, V> load() throws IOException {
        File file = getFile(path);
        LinkedHashMap<K, V> loaded = new LinkedHashMap<>();
        if (file.exists()) {
            Reader reader = new FileReader(file);
            Type type = new TypeToken<LinkedHashMap<K, V>>(){}.getType();
            loaded = gson.fromJson(reader, type);
            return loaded;
        }
        return loaded;
    }

    private File getFile(@NotNull String path) {
        return new File(path);
    }

}
