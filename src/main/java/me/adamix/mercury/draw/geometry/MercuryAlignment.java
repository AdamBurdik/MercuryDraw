package me.adamix.mercury.draw.geometry;

public enum MercuryAlignment {
	TOP_LEFT      { public int offsetX(int w, int parentWidth) { return 0;                      } public int offsetY(int h, int parentHeight) { return 0;     } },
	TOP_CENTER    { public int offsetX(int w, int parentWidth) { return -w/2 + parentWidth / 2; } public int offsetY(int h, int parentHeight) { return 0;     } },
	TOP_RIGHT     { public int offsetX(int w, int parentWidth) { return -w + parentWidth;       } public int offsetY(int h, int parentHeight) { return 0;     } },
	CENTER_LEFT   { public int offsetX(int w, int parentWidth) { return 0;                } public int offsetY(int h, int parentHeight) { return -h/2 + parentHeight / 2; } },
	CENTER        { public int offsetX(int w, int parentWidth) { return -w/2 + parentWidth / 2; } public int offsetY(int h, int parentHeight) { return -h/2 + parentHeight / 2; } },
	CENTER_RIGHT  { public int offsetX(int w, int parentWidth) { return -w + parentWidth; } public int offsetY(int h, int parentHeight) { return -h/2 + parentHeight / 2; } },
	BOTTOM_LEFT   { public int offsetX(int w, int parentWidth) { return 0;                } public int offsetY(int h, int parentHeight) { return -h + parentHeight; } },
	BOTTOM_CENTER { public int offsetX(int w, int parentWidth) { return -w/2 + parentWidth / 2;             } public int offsetY(int h, int parentHeight) { return -h + parentHeight;    } },
	BOTTOM_RIGHT  { public int offsetX(int w, int parentWidth) { return -w + parentWidth; } public int offsetY(int h, int parentHeight) { return -h + parentHeight;    } };

	public abstract int offsetX(int width, int parentWidth);
	public abstract int offsetY(int height, int parentHeight);
}
