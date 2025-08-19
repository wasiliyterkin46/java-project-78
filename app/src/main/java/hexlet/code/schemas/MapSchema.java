package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        checks.put("required", s -> s != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.put("sizeof", s -> s.size() == size);
        return this;
    }
}
