import processing.core.PImage;

import java.util.List;

public abstract class Animatable extends Entity {

    private final int actionPeriod;
    private final int animationPeriod;
    public Animatable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position,images);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    protected int getAnimationPeriod(){return this.animationPeriod;}
    protected int getActionPeriod(){return this.actionPeriod;}
    protected void executeActivity(Activity activity, EventScheduler scheduler){
        Event event = new Event(new Activity(this, activity.getWorldModel(), activity.getimageStore()),
                this.getActionPeriod(), this);
        event.scheduleEvent(scheduler);
    }

    protected void scheduleActions(
            WorldModel world,
            ImageStore imageStore, EventScheduler scheduler)
    {
        Event event;
        event = new Event(new Activity(this, world, imageStore),
                ((Animatable) this).getActionPeriod(), this);
        event.scheduleEvent(scheduler);
        event = new Event(new Animation(this, 0), ((Animatable) this).getAnimationPeriod(),
                this);
        event.scheduleEvent(scheduler);
    }

}
