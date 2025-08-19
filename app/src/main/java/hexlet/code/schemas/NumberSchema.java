package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Number> {
    public NumberSchema required() {
        checks.put("required", n -> n != null);
        return this;
    }

    public NumberSchema positive() {
        checks.put("positive", n -> n.doubleValue() > 0);
        return this;
    }

    public NumberSchema range(Number min, Number max) {
        checks.put("range", n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue());
        return this;
    }
}
