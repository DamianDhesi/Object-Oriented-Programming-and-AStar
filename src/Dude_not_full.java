import processing.core.PImage;

import java.util.*;

public class Dude_not_full extends TransformableDude{

    private int resourceCount;
    public Dude_not_full(String id, Point position, int actionPeriod, int animationPeriod,
                     int resourceLimit, List<PImage> images){
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
        this.resourceCount = 0;
    }


    public void changeResourceCount(int i){
        this.resourceCount += i;
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    @Override
    public void executeActivity(Activity activity, EventScheduler scheduler) {
        Optional<Entity> target =
                this.getPosition().findNearest(activity.getWorldModel(),
                        new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo(activity,
                target.get(),
                scheduler,
                nextPosition(target.get().getPosition(), activity.getWorldModel()))
                || !transform(activity, scheduler, makeMiner()))
        {
            super.executeActivity(activity, scheduler);
        }
    }

    @Override
    public boolean transform(Activity activity, EventScheduler scheduler, Entity miner) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            super.transform(activity, scheduler, miner);
        }

        return false;
    }

    @Override
    protected Entity makeMiner(){
        return new Dude_full(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());
    }

    @Override
    public boolean moveTo(Activity activity, Entity target, EventScheduler scheduler, Point nextPos) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            this.changeResourceCount(1);
            ((TransformablePlant)target).changetHealth(-1);
            return true;
        }
        else {
            //Point nextPos = nextPositionDude(target.getPosition(), activity.getWorldModel());
            return super.moveTo(activity, target, scheduler, nextPos);
        }
    }

}
