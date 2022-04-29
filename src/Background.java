import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background
{
    private final String id;
    private final List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public void setBackground(
            WorldModel world, Point pos)
    {
        if (world.withinBounds( pos)) {
            this.setBackgroundCell(world, pos);
        }
    }

    private void setBackgroundCell(
            WorldModel world, Point pos)
    {
        world.getBackground()[pos.getY()][pos.getX()] = this;
    }

    public List<PImage> getImages(){
        return this.images;
    }

    public int getImageIndex(){
        return this.imageIndex;
    }

    public PImage getCurrentImage() {
            return this.getImages().get(this.getImageIndex());


    }

}
