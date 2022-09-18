package de.thomas.utils.config.context;

import com.google.gson.JsonObject;
import de.thomas.utils.Variables;
import de.thomas.utils.config.base.JsonConfigSerializable;

public record BaguetteContext(Integer totalBaguetteCounter) implements JsonConfigSerializable {

    public BaguetteContext() {
        this(0);
    }

    @Override
    public BaguetteContext deserialize(JsonObject object) {
        return new BaguetteContext(object.get(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME).getAsInt());
    }

    @Override
    public void serialize(JsonObject object) {
        object.addProperty(Variables.TOTAL_BAGUETTE_COUNTER_CONFIG_NAME, this.totalBaguetteCounter);
    }
}
