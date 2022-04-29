import processing.core.PImage;

import java.util.List;
import java.util.Random;

public abstract class TransformablePlant extends Animatable {
    private int health;

    public TransformablePlant(String id, Point position, List<PImage> images, int health, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
    }

    protected int getHealth(){return this.health;}
    protected void changetHealth(int i){this.health += i;}

    @Override
    protected void executeActivity(Activity activity, EventScheduler scheduler) {
        if (!transform(activity,scheduler)) {
            super.executeActivity(activity, scheduler);
        }
    }

    protected boolean transform(Activity activity, EventScheduler scheduler){
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(this.getId(),
                    this.getPosition(),
                    activity.getimageStore().getImageList( Functions.STUMP_KEY));

            activity.getWorldModel().removeEntity(this);
            scheduler.unscheduleAllEvents( this);

            activity.getWorldModel().addEntity( stump);
            //stump.scheduleActions(activity.getWorldModel(), activity.getimageStore(), scheduler);

            return true;
        }

        return false;
    }

    protected int getNumFromRange(int max, int min) // for animations, transform
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }

}
