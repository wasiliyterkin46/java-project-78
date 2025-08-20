package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Number> {
    public NumberSchema required() {
        checks.put("required", n -> n != null);
        return this;
    }

    public NumberSchema positive() {
        checks.put("positive", n -> n == null || n.doubleValue() > 0);
        return this;
    }

    public NumberSchema range(Number min, Number max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Граница диапазона не может иметь значение 'null'");
        }

        if (min.doubleValue() > max.doubleValue()) {
            throw new IllegalArgumentException("Нижняя граница диапазона должна быть больше верхней");
        }

        checks.put("range", n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue());
        return this;
    }
}
