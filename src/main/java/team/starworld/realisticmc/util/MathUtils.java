package team.starworld.realisticmc.util;

import lombok.Getter;
import lombok.Setter;

public class MathUtils {

    public static class TypedNumber {

        public enum Type {
            PROPORTION, NUMBER
        }

        private @Getter @Setter Type type;
        private @Getter @Setter Number value;

        public TypedNumber (Type type, Number value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString () {
            return "TypedNumber (type=%s, value=%s)".formatted(this.type, this.value);
        }
    }

}
