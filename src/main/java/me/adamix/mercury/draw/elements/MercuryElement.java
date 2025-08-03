package me.adamix.mercury.draw.elements;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.geometry.MercuryAlignment;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import static me.adamix.mercury.draw.value.MercuryValue.px;

public abstract class MercuryElement<T extends MercuryElement<T>> {
	protected @NotNull MercuryValue xPosition = px(0);
	protected @NotNull MercuryValue yPosition = px(0);
	protected int rotation = 0;
	protected @NotNull MercuryAlignment alignment = MercuryAlignment.TOP_LEFT;

	public @NotNull T rotation(int rotation) {
		this.rotation = rotation;
		return self();
	}

	public @NotNull T x(@NotNull MercuryValue value) {
		this.xPosition = value;
		return self();
	}

	public @NotNull T y(@NotNull MercuryValue value) {
		this.yPosition = value;
		return self();
	}

	public @NotNull T align(@NotNull MercuryAlignment alignment) {
		this.alignment = alignment;
		return self();
	}

	public @NotNull MercuryValue x() {
		return xPosition;
	}

	public @NotNull MercuryValue y() {
		return yPosition;
	}

	public @NotNull MercuryAlignment alignment() {
		return alignment;
	}

	protected abstract @NotNull T self();

	protected float calculateX(int width, float parentWidth) {
		return xPosition.calculate(parentWidth) + alignment.offsetX(width);
	}

	protected float calculateY(int height, float parentHeight) {
		return yPosition.calculate(parentHeight) + alignment.offsetY(height);
	}

	public abstract @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight);
}