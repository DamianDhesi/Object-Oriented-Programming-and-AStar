import processing.core.PImage;

import java.util.List;

public class Sapling extends TransformablePlant{
    private final int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, int health) {
        super(id, position, images, health, Functions.SAPLING_ACTION_ANIMATION_PERIOD, Functions.SAPLING_ACTION_ANIMATION_PERIOD);
        this.healthLimit = Functions.SAPLING_HEALTH_LIMIT;
    }

    public int getHealthLimit(){
        return this.healthLimit;
    }

    @Override
    public void executeActivity(Activity activity, EventScheduler scheduler) {
        this.changetHealth(1);
        super.executeActivity(activity, scheduler);
    }

    @Override
    public boolean transform(Activity activity, EventScheduler scheduler) {
        if(super.transform(activity, scheduler)){
            return true;
        }
        else if (this.getHealth() >= this.getHealthLimit())
        {
            Animatable tree = new Tree("tree_" + this.getId(),
                    this.getPosition(),
                    getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                   getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    activity.getimageStore().getImageList( Functions.TREE_KEY));

            activity.getWorldModel().removeEntity( this);
            scheduler.unscheduleAllEvents(this);

            activity.getWorldModel().addEntity( tree);
            tree.scheduleActions(activity.getWorldModel(), activity.getimageStore(), scheduler);

            return true;
        }

        return false;
    }

}
