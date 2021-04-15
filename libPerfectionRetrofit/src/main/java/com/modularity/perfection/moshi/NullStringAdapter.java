package com.modularity.perfection.moshi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;

import static com.squareup.moshi.JsonReader.Token.NULL;

public class NullStringAdapter extends JsonAdapter<String> {
    @FromJson
    @Override
    public String fromJson(JsonReader reader) throws IOException {
        if (reader.peek() != NULL) {
            return reader.nextString();
        }
        reader.nextNull();
        return "";
    }

    @ToJson
    @Override
    public void toJson(JsonWriter writer, String value) throws IOException {
        writer.value(value);
    }
}
