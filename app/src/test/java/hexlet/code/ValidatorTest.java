package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}
