import processing.core.PImage;

import java.util.List;

public class Tree extends TransformablePlant{

    public Tree(String id, Point position, int actionPeriod, int animationPeriod,
                int health, List<PImage> images){
        super(id, position, images, health, actionPeriod, animationPeriod);
    }

}
