import java.util.List;
import java.util.Objects;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity
{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;


    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }


    protected void nextImage(){this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    protected String getId(){return this.id;}

    protected Point getPosition(){return this.position;}

    protected List<PImage> getImages(){return this.images;}

    protected int getImageIndex(){return this.imageIndex;}

    protected PImage getCurrentImage(){return this.getImages().get(this.getImageIndex());}

    protected void setPosition(Point pos){this.position = pos;}

}
