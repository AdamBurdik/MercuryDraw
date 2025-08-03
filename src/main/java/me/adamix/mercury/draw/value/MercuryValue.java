package me.adamix.mercury.draw.value;

import org.jetbrains.annotations.NotNull;

public interface MercuryValue {
	float calculate(float parentValue);

	static Fixed px(float pixels) {
		return new Fixed(pixels, MercuryUnit.PIXELS);
	}

	static Fixed pct(float percentage) {
		return new Fixed(percentage, MercuryUnit.PERCENTAGE);
	}

	static Fixed fixed(float value, @NotNull MercuryUnit unit) {
		return new Fixed(value, unit);
	}

	static Auto auto() {
		return new Auto();
	}

	record Fixed(float value, @NotNull MercuryUnit unit) implements MercuryValue {
		@Override
		public float calculate(float parentValue) {
			return switch (unit) {
				case PIXELS -> value;
				case PERCENTAGE -> value * parentValue / 100;
			};
		}
	}

	record Auto() implements MercuryValue {
		@Override
		public float calculate(float parentValue) {
			return 0;
		}
	}
}
