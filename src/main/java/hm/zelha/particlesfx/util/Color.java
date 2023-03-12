package hm.zelha.particlesfx.util;

import org.apache.commons.lang.Validate;

public class Color {

    public static final Color WHITE = new Color(0xFFFFFF).lock();
    public static final Color SILVER = new Color(0xC0C0C0).lock();
    public static final Color GRAY = new Color(0x808080).lock();
    public static final Color DARK_GRAY = new Color(0x404040).lock();
    public static final Color BLACK = new Color(0x000000).lock();
    public static final Color RED = new Color(0xFF0000).lock();
    public static final Color PINK = new Color(0xFFAFAF).lock();
    public static final Color MAROON = new Color(0x800000).lock();
    public static final Color YELLOW = new Color(0xFFFF00).lock();
    public static final Color OLIVE = new Color(0x808000).lock();
    public static final Color LIME = new Color(0x00FF00).lock();
    public static final Color GREEN = new Color(0x008000).lock();
    public static final Color AQUA = new Color(0x00FFFF).lock();
    public static final Color TEAL = new Color(0x008080).lock();
    public static final Color BLUE = new Color(0x0000FF).lock();
    public static final Color NAVY = new Color(0x000080).lock();
    public static final Color MAGENTA = new Color(0xFF00FF).lock();
    public static final Color PURPLE = new Color(0x800080).lock();
    public static final Color ORANGE = new Color(0xFFA500).lock();
    private int red;
    private int green;
    private int blue;
    private boolean locked = false;

    public Color(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public Color(int rgb) {
        setRGB(rgb);
    }

    public Color clone() {
        return new Color(red, green, blue);
    }

    /**
     * makes this object unmodifiable
     *
     * @return this object
     */
    public Color lock() {
        locked = true;

        return this;
    }

    public void setRGB(int rgb) {
        if (locked) throw new UnsupportedOperationException("Cannot modify locked Colors!");

        Validate.isTrue((rgb >> 24) == 0, "Extrenuous data in: ", rgb);
        
        setRed(rgb >> 16 & 0xFF);
        setGreen(rgb >> 8 & 0xFF);
        setBlue(rgb & 0xFF);
    }

    public void setRed(int red) {
        if (locked) throw new UnsupportedOperationException("Cannot modify locked Colors!");

        Validate.isTrue(red >= 0 && red <= 255, "Red is not between 0-255: ", red);

        this.red = red;
    }

    public void setGreen(int green) {
        if (locked) throw new UnsupportedOperationException("Cannot modify locked Colors!");

        Validate.isTrue(green >= 0 && green <= 255, "Green is not between 0-255: ", green);

        this.green = green;
    }

    public void setBlue(int blue) {
        if (locked) throw new UnsupportedOperationException("Cannot modify locked Colors!");

        Validate.isTrue(blue >= 0 && blue <= 255, "Blue is not between 0-255: ", blue);

        this.blue = blue;
    }

    public int getRGB() {
        return red << 16 | green << 8 | blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    /**
     * @return whether this object can be modified
     */
    public boolean isLocked() {
        return locked;
    }
}
