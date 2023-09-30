package team.starworld.realisticmc.util;

import lombok.Getter;
import lombok.Setter;

public class MathUtils {

    public static class TypedFloatNumber {

        public static TypedFloatNumber zero () {
            return new TypedFloatNumber(0);
        }

        public static TypedFloatNumber proportionZero () {
            return new TypedFloatNumber(Type.PROPORTION, 0);
        }

        public enum Type {
            PROPORTION, NUMBER
        }

        private @Getter @Setter Type type;
        private @Getter @Setter float value;

        public TypedFloatNumber (Type type, float value) {
            this.type = type;
            this.value = value;
        }

        public TypedFloatNumber (int numerator, int denominator) {
            this.type = Type.PROPORTION;
            this.value = (float) numerator / denominator;
        }

        public TypedFloatNumber (float value) {
            this.type = Type.NUMBER;
            this.value = value;
        }

        @Override
        public String toString () {
            return "TypedNumber (type=%s, value=%s)".formatted(this.type, this.value);
        }
    }

}
