package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        checks.put("required", s -> s != null && !s.equals(""));
        return this;
    }

    public StringSchema minLength(int length) {
        checks.put("minLength", s -> s.length() >= length);
        return this;
    }

    public StringSchema contains(String subString) {
        checks.put("contains", s -> s.toLowerCase().contains(subString.toLowerCase()));
        return this;
    }
}
