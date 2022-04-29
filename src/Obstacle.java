import processing.core.PImage;

import java.util.List;

public class Obstacle extends Animatable{

    public Obstacle(String id, Point position, int animationPeriod, List<PImage> images){
        super(id, position, images,0, animationPeriod);
    }

    @Override
    public void scheduleActions(
            WorldModel world,
            ImageStore imageStore, EventScheduler scheduler)
    {
        Event event;
        event = new Event(new Animation(this, 0), ((Animatable) this).getAnimationPeriod(),
                this);
        event.scheduleEvent(scheduler);
    }

}
