package me.adamix.mercury.draw.elements;

import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import static me.adamix.mercury.draw.value.MercuryValue.px;

public abstract class MercurySizeElement<T extends MercurySizeElement<T>> extends MercuryElement<T> {
	protected @NotNull MercuryValue width = px(0);
	protected @NotNull MercuryValue height = px(0);

	public @NotNull T width(@NotNull MercuryValue width) {
		this.width = width;
		return self();
	}

	public @NotNull T height(@NotNull MercuryValue height) {
		this.height = height;
		return self();
	}

	public @NotNull MercuryValue width() {
		return width;
	}

	public @NotNull MercuryValue height() {
		return height;
	}

	protected int calculateWidth(int parentWidth) {
		return Math.round(width.calculate(parentWidth));
	}

	protected int calculateHeight(int parentHeight) {
		return Math.round(height.calculate(parentHeight));
	}
}
