package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class StringSchema {
    private Map<String, Predicate<String>> checks = new HashMap<>();

    public boolean isValid(String valueForCheck) {
        for (Map.Entry<String, Predicate<String>> check : checks.entrySet()) {
            if (!check.getValue().test(valueForCheck)) {
                return false;
            }
        }
        return true;
    }

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
