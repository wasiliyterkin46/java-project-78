package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        checks.put("required", s -> s != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.put("sizeof", s -> s != null && s.size() == size);
        return this;
    }

    public <T, M> MapSchema shape(Map<T, BaseSchema<M>> schemas) {
        checks.put("shape", map -> {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                boolean keyExist = schemas.containsKey(entry.getKey());
                if (keyExist) {
                    try {
                        M checkedValue = (M) entry.getValue();
                    } catch (ClassCastException e) {
                        return false;
                    }

                    BaseSchema<M> schema = schemas.get(entry.getKey());
                    M checkedValue = (M) entry.getValue();
                    if (!schema.isValid(checkedValue)) {
                        return false;
                    }
                }
            }
            return true;
        });

        return this;
    }
}
