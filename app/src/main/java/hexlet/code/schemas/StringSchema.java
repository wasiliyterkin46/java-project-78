package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        checks.put("required", s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        checks.put("minLength", s -> s != null && s.length() >= length);
        return this;
    }

    public StringSchema contains(String subString) throws IllegalArgumentException {
        if (subString == null) {
            throw new IllegalArgumentException("Подстрока не может иметь значение 'null'");
        }
        checks.put("contains", s -> s != null && s.toLowerCase().contains(subString.toLowerCase()));
        return this;
    }
}
