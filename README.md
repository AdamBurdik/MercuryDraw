# MercuryDraw
Simple library that provides drawing API

> [!NOTE]
> This library was written for my personal use case. It includes stuff I needed in the time of writing.

# Include as dependency

You can use the library using jitpack
```kotlin
maven { url = uri("https://www.jitpack.io") }

dependencies {
    // Get the newest commit from https://www.jitpack.io/#AdamBurdik/MercuryDraw
    implementation("com.github.AdamBurdik:MercuryDraw:498418193e")
}
```

# Usage

For drawing anything, you need a canvas.

You can create one like this, with arguments **WIDTH** and **HEIGHT**
```java
MercuryCanvas canvas = new MercuryCanvas(1000, 1000); // 1000x1000 pixels
```

*Canvas is just root instance of MercuryContainer.*

*So following code is used on MercuryContainer but works for the canvas.*

## 1. Filling container with solid color

```java
// RGB
container.background(255,0,0);

// RGBA
container.background(255,0,0,255);

// Instance of java.awt.Color
container.background(Color.RED);
```

### Value System

The library uses a flexible value system that supports both absolute and relative measurements:

- **`px(value)`** - Absolute pixel values
- **`pct(value)`** - Percentage of parent container
- **`auto()`** - Automatic sizing (often used for text)

```java
container.rect(pct(50), pct(50)) // 50% of parent width and height
        .color(100, 100, 100);

container.rect(px(200), pct(30)) // 200px wide, 30% of parent height
        .color(200, 100, 100);
```
## 2. Drawing rectangle

Rectangle has few builder parameters:
- X, Y = Position (0 by default)
- Color = Fill color
- Rounded = Corner radius
- Antialiasing = If the rounded corners are smoothed
- Align = Alignment to the parent
- Rotation = Rotation around its center (in degrees)

```java
container.rect(px(100), px(100)) // 100x100 pixels
        .x(px(0))
        .y(px(0))
        .color(255, 255, 255)
        .rounded(15, 15)
        .antialiasing(true)
        .align(MercuryAlignment.CENTER)
        .rotation(45);
```

## 3. Drawing circles/ovals

Circles and ovals are also rectangles with rounded corners.

Circle has the following builder parameters:
- Width, Height = Size (equal for perfect circle)
- Color = Fill color
- Rounded = Corner radius (50% of width/height for circle)
- Antialiasing = If the circle edges are smoothed
- X, Y = Position (0 by default)
- Align = Alignment to the parent
- Rotation = Rotation around its center (in degrees)

```java
container.rect(px(100), px(100)) // Perfect circle since width == height
        .color(0, 150, 255)
        .rounded(50, 50) // Half of width/height to make a circle
        .x(px(50))
        .y(px(50))
        .antialiasing(true)
        .align(MercuryAlignment.CENTER)
        .rotation(45);
```

## 4. Drawing text

Text can be drawn with fonts.

Text has the following builder parameters:
- Text = The string to render
- Font = MercuryFont object (loaded from TTF file)
- FontSize = Size of the text in pixels
- Color = Text color (white by default)
- Antialiasing = If text rendering is smoothed (true by default)
- X, Y = Position (0 by default)
- Align = Alignment to the parent
- Rotation = Rotation around its center (in degrees)

```java
// First, load a font
MercuryFont font = new MercuryFont(Path.of("fonts/roboto-regular.ttf"));

// Then add text to container
container.text("Hello, World!", font, 24)
        .color(255, 255, 255)
        .x(px(50))
        .y(px(50))
        .align(MercuryAlignment.CENTER)
        .antialiasing(true)
        .rotation(0);
```

## 5. Drawing images

Images can be loaded and displayed.

Image has the following builder parameters:
- Path = File path to the image (PNG, JPG, etc.)
- Width = Image width (uses original width if not set)
- Height = Image height (uses original height if not set)
- X, Y = Position (0 by default)
- Align = Alignment to the parent
- Rotation = Rotation around its center (in degrees)

```java
container.image(Path.of("path/to/image.png"))
        .width(px(200))
        .height(px(200))
        .x(px(50))
        .y(px(50))
        .align(MercuryAlignment.CENTER)
        .rotation(0);
```

## 6. Layout Systems

MercuryDraw provides multiple layout systems for organizing elements:

### Flexbox Layout

The flexbox container works similarly to CSS Flexbox.

Flexbox has the following builder parameters:
- Width, Height = Container dimensions
- Direction = Layout direction (VERTICAL or HORIZONTAL)
- Gap = Space between items (0 by default)
- X, Y = Position (0 by default)
- Align = Alignment to the parent
- Background = Background color (transparent by default)
- Clip = Enable clipping of overflow content (false by default)

```java
var flexbox = canvas.flexbox(px(400), px(300))
        .direction(MercuryDirection.VERTICAL)
        .gap(20)
        .background(50, 50, 50)
        .x(px(50))
        .y(px(50));

flexbox.rect(px(100), px(50)).color(255, 0, 0);
flexbox.rect(px(100), px(50)).color(0, 255, 0);
flexbox.rect(px(100), px(50)).color(0, 0, 255);
```

### Grid Layout

The GridContainer provides a grid-based layout:

```java
var grid = canvas.grid(px(400), px(300), 3, 3); // 3 rows, 3 columns

for (int i = 0; i < 9; i++) {
    grid.rect(px(100), px(100))
            .color((i * 30) % 255, (i * 60) % 255, (i * 90) % 255);
}
```

### Basic Container

You can also create nested containers for more complex layouts:

```java
var innerContainer = canvas.container(px(300), px(300))
        .background(50, 50, 50)
        .x(px(50))
        .y(px(50));

innerContainer.rect(px(100), px(100))
        .color(255, 100, 100)
        .x(px(100))
        .y(px(100));
```

# Advanced Features

## Positioning and Alignment

All elements support:
- **X/Y positioning** - Absolute positioning within parent
- **Alignment** - Align element to parent (TOP_LEFT, CENTER, BOTTOM_RIGHT, etc.)
- **Rotation** - Rotate element around its center (in degrees)

```java
container.rect(px(100), px(100))
        .color(100, 150, 200)
        .x(px(50))
        .y(px(50))
        .align(MercuryAlignment.CENTER_LEFT)
        .rotation(45);
```

## Clipping

Containers support clipping to prevent child elements from overflowing:

```java
var clippedContainer = canvas.container(px(200), px(200))
        .background(200, 200, 200)
        .clip(true) // Enable clipping
        .rounded(30, 30);
```

## Antialiasing

Enable antialiasing for smoother rendering of text and rounded shapes:

```java
container.rect(px(100), px(100))
        .color(255, 0, 0)
        .rounded(50, 50)
        .antialiasing(true);

container.text("Smooth Text", font, 24)
        .antialiasing(true);
```

# Creating and Saving Images

After the layout is completed, you need to create it.

```java
MercuryCanvas canvas = new MercuryCanvas(1000, 800);

// ... build your layout ...

MercuryImage result = canvas.create();
ImageIO.write(result, "png", Path.of("").resolve("output.png").toFile());
```