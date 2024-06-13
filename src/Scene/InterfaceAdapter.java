package Scene;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter implements JsonSerializer<Object>, JsonDeserializer<Object> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    /****** Helper method to get the className of the object to be deserialized *****/
    public Class<?> getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class not found: " + className, e);
        }
    }

    /**
        * This is the implementation of the JsonDeserializer interface. It generates the object from the JSON element.
     * convert the JsonElement to the object of the specified type.
     */
    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = jsonObject.getAsJsonPrimitive(CLASSNAME);
        String className = prim.getAsString();
        Class<?> klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    /**
        * This is the implementation of the JsonSerializer interface. It generates the JSON element from the object.
     * convert the object to JsonElement.
     */
    @Override
    public JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, obj.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(obj));
        return jsonObject;
    }
}