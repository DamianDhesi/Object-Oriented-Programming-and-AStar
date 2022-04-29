import processing.core.PImage;

import java.util.*;

public class Dude_full extends TransformableDude{

    public Dude_full(String id, Point position, int actionPeriod, int animationPeriod,
                     int resourceLimit, List<PImage> images){
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(Activity activity, EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                this.getPosition().findNearest(activity.getWorldModel(),  new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && moveTo(activity,
                fullTarget.get(), scheduler,
                nextPosition(fullTarget.get().getPosition(), activity.getWorldModel())))
        {
            transform(activity,scheduler, makeMiner());
        }
        else {
            super.executeActivity(activity, scheduler);
        }
    }

    @Override
    protected Entity makeMiner(){
        return new Dude_not_full(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());
    }

    @Override
    public boolean moveTo(Activity activity, Entity target, EventScheduler scheduler, Point nextPos) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            return true;
        }
        else {
            //Point nextPos = nextPositionDude(target.getPosition(), activity.getWorldModel());
            return super.moveTo(activity, target, scheduler, nextPos);
        }
    }

}
