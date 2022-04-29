import processing.core.PImage;

import java.util.*;

public class Fairy extends Moveable{

    public Fairy(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(Activity activity, EventScheduler scheduler) {
        Optional<Entity> fairyTarget =
                this.getPosition().findNearest(activity.getWorldModel(),  new ArrayList<>(Arrays.asList(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(activity,fairyTarget.get(), scheduler,
                    nextPosition(fairyTarget.get().getPosition(), activity.getWorldModel()))) {
                Animatable sapling = new Sapling("sapling_" + this.getId(), tgtPos,
                        activity.getimageStore().getImageList( Functions.SAPLING_KEY), 0);

                activity.getWorldModel().addEntity( sapling);
                sapling.scheduleActions(activity.getWorldModel(), activity.getimageStore(), scheduler);
            }
        }
        super.executeActivity(activity, scheduler);
    }

    @Override
    public boolean moveTo(Activity activity, Entity target, EventScheduler scheduler, Point nextPos) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            activity.getWorldModel().removeEntity( target);
            scheduler.unscheduleAllEvents( target);
            return true;
        }
        else {
            //Point nextPos = nextPositionFairy(target.getPosition(), activity.getWorldModel());
            return super.moveTo(activity, target, scheduler, nextPos);
        }
    }

    @Override
    boolean canPass(Point pt, WorldModel world){
        return false;
    }

}
