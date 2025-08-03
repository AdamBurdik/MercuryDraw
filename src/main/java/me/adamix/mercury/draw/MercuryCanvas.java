package me.adamix.mercury.draw;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.layout.MercuryContainer;
import org.jetbrains.annotations.NotNull;

import static me.adamix.mercury.draw.value.MercuryValue.px;

public class MercuryCanvas extends MercuryContainer<MercuryCanvas> {
	private final int width;
	private final int height;

	public MercuryCanvas(int width, int height) {
		super(px(width), px(height));
		this.width = width;
		this.height = height;
	}

	@Override
	protected @NotNull MercuryCanvas self() {
		return this;
	}

	public @NotNull MercuryImage create() {
		DrawContext ctx = new DrawContext(width, height);
		ctx.beginDrawing();

		super.draw(ctx, 0, 0, 0, 0);

		ctx.endDrawing();

		return new MercuryImage(ctx.image());
	}
}