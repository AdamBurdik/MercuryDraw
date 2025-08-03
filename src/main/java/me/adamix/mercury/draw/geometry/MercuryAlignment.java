package me.adamix.mercury.draw.geometry;

public enum MercuryAlignment {
	TOP_LEFT      { public int offsetX(int w) { return 0;     } public int offsetY(int h) { return 0;     } },
	TOP_CENTER    { public int offsetX(int w) { return -w/2;  } public int offsetY(int h) { return 0;     } },
	TOP_RIGHT     { public int offsetX(int w) { return -w;    } public int offsetY(int h) { return 0;     } },
	CENTER_LEFT   { public int offsetX(int w) { return 0;     } public int offsetY(int h) { return -h/2;  } },
	CENTER        { public int offsetX(int w) { return -w/2;  } public int offsetY(int h) { return -h/2;  } },
	CENTER_RIGHT  { public int offsetX(int w) { return -w;    } public int offsetY(int h) { return -h/2;  } },
	BOTTOM_LEFT   { public int offsetX(int w) { return 0;     } public int offsetY(int h) { return -h;    } },
	BOTTOM_CENTER { public int offsetX(int w) { return -w/2;  } public int offsetY(int h) { return -h;    } },
	BOTTOM_RIGHT  { public int offsetX(int w) { return -w;    } public int offsetY(int h) { return -h;    } };

	public abstract int offsetX(int width);
	public abstract int offsetY(int height);
}
