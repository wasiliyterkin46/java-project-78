package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    public void numberSchemaTest() {
        NumberSchema schema = validator.number();

        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.isValid(5));

        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(true, schema.isValid(5));

        schema.positive();
        assertEquals(true, schema.isValid(10));
        assertEquals(false, schema.isValid(-10));
        assertEquals(false, schema.isValid(0));

        schema.range(5, 10);

        schema.isValid(5); // true
        schema.isValid(10); // true
        schema.isValid(4); // false
        schema.isValid(11); // false
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
}
