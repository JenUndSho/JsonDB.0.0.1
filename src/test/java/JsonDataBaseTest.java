import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Assert;
import org.junit.Test;
import server.JsonDataBase;

public class JsonDataBaseTest {

    @Test
    public void testSetSuccessfullyToDataBase() {
        String key = "1";
        String value = "HelloWorld!";
        JsonDataBase jsonDataBase = new JsonDataBase();
        jsonDataBase.set(key, value);

        Assert.assertTrue(jsonDataBase.getDb().toString().contains(key) && jsonDataBase.getDb().toString().contains(value));
    }

    @Test
    public void testRemoveSuccessfullyToDataBase() {
        String key = "1";
        String value = "HelloWorld!";
        JsonDataBase jsonDataBase = new JsonDataBase();
        jsonDataBase.set(key, value);
        jsonDataBase.delete(new Gson().toJsonTree(key));

        Assert.assertFalse(jsonDataBase.getDb().toString().contains(key) && jsonDataBase.getDb().toString().contains(value));
    }

    @Test
    public void testGetSuccessfullyFromDataBase() {
        String key = "1";
        String value = "HelloWorld!";
        JsonDataBase jsonDataBase = new JsonDataBase();
        jsonDataBase.set(key, value);

        Assert.assertEquals(jsonDataBase.getDb().get(key).getAsString(), value);
    }


    @Test
    public void testFindElementWorksCorrectly() {
        String expected = "Tesla Roadster";
        JsonArray keys = new JsonArray();
        keys.add("person");
        keys.add("car");
        keys.add("model");
        JsonDataBase jsonDataBase = new JsonDataBase();
        jsonDataBase.set("person", "{\"name\":\"Evhenii\",\"car\":{\"model\":\"Tesla Roadster\",\"year\":\"2018\"},\"rocket\":{\"name\":\"Falcon 9\",\"launches\":\"88\"}}");
        String actual = jsonDataBase.findElement(keys, false).getAsString();

        Assert.assertEquals(expected, actual);
    }

}
