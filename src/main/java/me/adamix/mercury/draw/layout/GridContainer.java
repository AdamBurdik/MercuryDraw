package me.adamix.mercury.draw.layout;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.direction.MercuryDirection;
import me.adamix.mercury.draw.elements.MercuryElement;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import static me.adamix.mercury.draw.value.MercuryValue.pct;
import static me.adamix.mercury.draw.value.MercuryValue.px;

public class GridContainer extends MercuryContainer<GridContainer> {
	private @NotNull MercuryDirection direction = MercuryDirection.VERTICAL;
	private final int rowCount;
	private final int columnCount;

	private float rowGap = 0;
	private float columnGap = 0;

	public GridContainer(@NotNull MercuryValue width, @NotNull MercuryValue height, int rowCount, int columnCount) {
		super(width, height);
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}

	public @NotNull GridContainer direction(@NotNull MercuryDirection direction) {
		this.direction = direction;
		return this;
	}

	public @NotNull GridContainer gap(float rowGap, float columnGap) {
		this.rowGap = rowGap;
		this.columnGap = columnGap;
		return this;
	}

	@Override
	protected @NotNull GridContainer self() {
		return this;
	}

	@Override
	public @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight) {
		final int finalWidth = calculateWidth(parentWidth);
		final int finalHeight = calculateHeight(parentHeight);
		final float finalX = calculateX(finalWidth, parentWidth);
		final float finalY = calculateY(finalHeight, parentHeight);

		DrawContext drawContext = new DrawContext(finalWidth, finalHeight);
		drawContext.beginDrawing();
		drawContext.drawBackground(background);

//		float rowHeight = (float) finalHeight / rowCount;
		float rowHeight = (((float) finalHeight) - rowGap * (rowCount - 1)) / rowCount;
		System.out.println("row height: " + rowHeight);

		float rowOffset = 0f;

		int i = 0;
		for (int y = 0; y < rowCount; y++) {
			FlexboxContainer rowFlexbox = new FlexboxContainer(pct(100), px(rowHeight))
					.gap(columnGap)
					.background(0, 0, 255, 0)
					.direction(MercuryDirection.HORIZONTAL)
				    .y(px(rowOffset));
			System.out.println("Row: " + rowFlexbox);

			for (int x = 0; x < columnCount; x++) {
				MercuryElement<?> child = children.get(i);

				rowFlexbox.elemenet(child);
				i++;
			}

			var result = rowFlexbox.draw(drawContext, finalX, finalY, finalWidth, finalHeight);
			System.out.println("result: " + result);
			rowOffset += result.height() + rowGap;
		}

		drawContext.endDrawing();
		ctx.drawImage(drawContext.image(), finalX, finalY, finalWidth, finalHeight, rotation);

		return new DrawResult(finalX, finalY, finalWidth, finalHeight);
	}
}
