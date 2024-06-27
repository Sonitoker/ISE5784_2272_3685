package Scene;

import com.google.gson.*;
import geometries.*;
import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

class InterfaceAdapter implements JsonSerializer<Geometries>, JsonDeserializer<Geometries> {

    @Override
    public JsonElement serialize(Geometries src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        try {
            Field geometryField = Geometries.class.getDeclaredField("geometries");
            geometryField.setAccessible(true);
            List<Intersectable> geometries = (List<Intersectable>) geometryField.get(src);
            for (Intersectable intersectable : geometries) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("type", intersectable.getClass().getSimpleName());
                jsonObject.add("attributes", context.serialize(intersectable));
                jsonArray.add(jsonObject);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public Geometries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Intersectable> geometries = new LinkedList<>();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement attributes = jsonObject.get("attributes");
            Intersectable intersectable = switch (type) {
                case "Sphere" -> context.deserialize(attributes, Sphere.class);
                case "Triangle" -> context.deserialize(attributes, Triangle.class);
                case "Cylinder" -> context.deserialize(attributes, Cylinder.class);
                case "Tube" -> context.deserialize(attributes, Tube.class);
                case "Polygon" -> context.deserialize(attributes, Polygon.class);
                case "Plane" -> context.deserialize(attributes, Plane.class);
                default -> null;
            };
            if (intersectable != null) {
                geometries.add(intersectable);
            }
        }
        Geometries geometriesObject = new Geometries();
        try {
            Field geometryField = Geometries.class.getDeclaredField("geometries");
            geometryField.setAccessible(true);
            geometryField.set(geometriesObject, geometries);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return geometriesObject;
    }

}


//public class InterfaceAdapter implements JsonSerializer<Object>, JsonDeserializer<Object> {
//
//    private static final String CLASSNAME = "CLASSNAME";
//    private static final String DATA = "DATA";
//
//    /****** Helper method to get the className of the object to be deserialized *****/
//    public Class<?> getObjectClass(String className) {
//        try {
//            return Class.forName(className);
//        } catch (ClassNotFoundException e) {
//            throw new JsonParseException("Class not found: " + className, e);
//        }
//    }
//
//    /**
//        * This is the implementation of the JsonDeserializer interface. It generates the object from the JSON element.
//     * convert the JsonElement to the object of the specified type.
//     */
//    @Override
//    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        JsonPrimitive prim = jsonObject.getAsJsonPrimitive(CLASSNAME);
//        String className = prim.getAsString();
//        Class<?> klass = getObjectClass(className);
//        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
//    }
//
//    /**
//        * This is the implementation of the JsonSerializer interface. It generates the JSON element from the object.
//     * convert the object to JsonElement.
//     */
//    @Override
//    public JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(CLASSNAME, obj.getClass().getName());
//        jsonObject.add(DATA, jsonSerializationContext.serialize(obj));
//        return jsonObject;
//    }
//}