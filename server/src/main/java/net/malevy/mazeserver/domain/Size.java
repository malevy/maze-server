package net.malevy.mazeserver.domain;

import org.springframework.util.Assert;

public class Size {
    private int height;
    private int width;

    public Size(int height, int width) {
        Assert.isTrue(height > 0, "must provide a positive value for height");
        Assert.isTrue(width > 0, "must provide a positive value for width");
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
