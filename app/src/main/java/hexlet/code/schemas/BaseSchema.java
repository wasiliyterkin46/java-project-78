package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new HashMap<>();

    public final boolean isValid(T valueForCheck) {
        for (Map.Entry<String, Predicate<T>> check : checks.entrySet()) {
            if (!check.getValue().test(valueForCheck)) {
                return false;
            }
        }
        return true;
    }
}
