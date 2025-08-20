package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
    }

    @Test
    public void stringSchemaTest() {
        StringSchema schema = validator.string();

        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.isValid(""));

        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(false, schema.isValid(""));
        assertEquals(true, schema.isValid("what does the fox say"));

        schema.contains("Does");
        assertEquals(true, schema.isValid("what does the fox say"));
        schema.contains("dos");
        assertEquals(false, schema.isValid("what does the fox say"));

        schema.minLength(4);
        assertEquals(false, schema.isValid("wha"));

        schema.minLength(10).minLength(4).contains("He");
        assertEquals(true, schema.isValid("Hexlet"));

        assertThrows(IllegalArgumentException.class, () -> schema.contains(null));
    }

    @Test
    public void numberSchemaTest() {
        NumberSchema schema = validator.number();

        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.isValid(5));

        schema.positive();
        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.isValid(10));
        assertEquals(false, schema.isValid(-10));
        assertEquals(false, schema.isValid(0));

        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(true, schema.isValid(5));

        schema.range(5, 10);
        assertEquals(true, schema.isValid(5));
        assertEquals(true, schema.isValid(10));
        assertEquals(false, schema.isValid(4));
        assertEquals(false, schema.isValid(11));

        assertThrows(IllegalArgumentException.class, () -> schema.range(5, null));

        assertThrows(IllegalArgumentException.class, () -> schema.range(5, 4));
    }

    @Test
    public void mapSchemaTest() {
        MapSchema schema = validator.map();

        assertEquals(true, schema.isValid(null));

        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(true, schema.isValid(new HashMap<>()));

        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertEquals(true, schema.isValid(data));

        schema.sizeof(2);
        assertEquals(false, schema.isValid(data));
        data.put("key2", "value2");
        assertEquals(true, schema.isValid(data));
    }

    @Test
    public void mapSchemaTestByKeys() {
        MapSchema schema = validator.map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));
        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertEquals(true, schema.isValid(human1));
        schema.sizeof(2);
        assertEquals(true, schema.isValid(human1));
        schema.sizeof(1);
        assertEquals(false, schema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertEquals(false, schema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertEquals(false, schema.isValid(human3));

        Map<String, Integer> human4 = new HashMap<>();
        human4.put("firstName", 1);
        human4.put("lastName", 2);
        schema.sizeof(2);
        assertThrows(ClassCastException.class, () -> schema.isValid(human4));
    }
}
