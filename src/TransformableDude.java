import processing.core.PImage;

import java.util.List;

public abstract class TransformableDude extends Moveable{

    private final int resourceLimit;
    public TransformableDude(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit(){return this.resourceLimit;}

    protected boolean transform(Activity activity, EventScheduler scheduler, Entity miner){
        activity.getWorldModel().removeEntity( this);
        scheduler.unscheduleAllEvents( this);

        activity.getWorldModel().addEntity( miner);
        ((Animatable)miner).scheduleActions(activity.getWorldModel(), activity.getimageStore(), scheduler);
        return true;
    }

    protected abstract Entity makeMiner();

    @Override
    boolean canPass(Point pt, WorldModel world){
        return world.isOccupied(pt) && world.getOccupancyCell(pt).getClass() == Stump.class;
    }

}
