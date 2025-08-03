package me.adamix.mercury.draw.layout;


import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.direction.MercuryDirection;
import me.adamix.mercury.draw.elements.MercuryElement;
import me.adamix.mercury.draw.elements.MercurySizeElement;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import static me.adamix.mercury.draw.value.MercuryValue.pct;
import static me.adamix.mercury.draw.value.MercuryValue.px;

public class FlexboxContainer extends MercuryContainer<FlexboxContainer> {
	private @NotNull MercuryDirection direction = MercuryDirection.VERTICAL;
	private float gap = 0;

	public FlexboxContainer(@NotNull MercuryValue width, @NotNull MercuryValue height) {
		super(width, height);
	}

	public @NotNull FlexboxContainer direction(@NotNull MercuryDirection direction) {
		this.direction = direction;
		return this;
	}

	public @NotNull FlexboxContainer gap(float gap) {
		this.gap = gap;
		return this;
	}

	@Override
	protected @NotNull FlexboxContainer self() {
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

		if (direction == MercuryDirection.VERTICAL) {
			float childHeight = (((float) finalHeight) - gap * (children.size() - 1)) / children.size();

			float verticalOffset = 0;
			for (MercuryElement<?> child : children) {
				if (child instanceof MercurySizeElement<?> sizeElement) {
					if (child.y() instanceof MercuryValue.Auto) {
						child.y(px(verticalOffset));
					}
					if (child.x() instanceof MercuryValue.Auto) {
						child.x(px(0));
					}

					if (sizeElement.height() instanceof MercuryValue.Auto) {
						sizeElement.height(px(childHeight));
						verticalOffset += childHeight;
						verticalOffset += gap;
					}
					if (sizeElement.width() instanceof MercuryValue.Auto) {
						sizeElement.width(pct(100));
					}

					var result = child.draw(drawContext, finalX, finalY, finalWidth, finalHeight);
				} else {
					// TODO
				}
			}
		} else {
			float childWidth = (((float) finalWidth) - gap * (children.size() - 1)) / children.size();
			System.out.println(childWidth);

			float horizontalOffset = 0;
			for (MercuryElement<?> child : children) {
				if (child instanceof MercurySizeElement<?> sizeElement) {
					if (child.x() instanceof MercuryValue.Auto) {
						child.x(px(horizontalOffset));
					}
					if (child.y() instanceof MercuryValue.Auto) {
						child.y(px(0));
					}

					if (sizeElement.width() instanceof MercuryValue.Auto) {
						sizeElement.width(px(childWidth));
						horizontalOffset += childWidth;
						horizontalOffset += gap;
					}
					if (sizeElement.height() instanceof MercuryValue.Auto) {
						sizeElement.height(pct(100));
					}

					var result = child.draw(drawContext, finalX, finalY, finalWidth, finalHeight);
				} else {
					// TODO
				}
			}
		}

		drawContext.endDrawing();
		ctx.drawImage(drawContext.image(), finalX, finalY, finalWidth, finalHeight, rotation);

		return new DrawResult(finalX, finalY, finalWidth, finalHeight);
	}
}