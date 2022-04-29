import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Moveable extends AStarPathingStrategy{
    // Have Moveable extend SingleStep to use the single step pathing strategy

    public Moveable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected boolean adjacent(Point p1, Point p2){
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    protected boolean moveTo(Activity activity, Entity target, EventScheduler scheduler, Point nextPos){
        if (!this.getPosition().equals(nextPos)) {
            Optional<Entity> occupant = activity.getWorldModel().getOccupant( nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents( occupant.get());
            }

            activity.getWorldModel().moveEntity( this, nextPos);
        }
        return false;
    }

    protected Point nextPosition(
            Point pt, WorldModel world)
    {
        List<Point> newPos = computePath(this.getPosition(), pt,
                point -> world.withinBounds(point) && (!world.isOccupied(point)  || this.canPass(point, world)),
                this::adjacent, CARDINAL_NEIGHBORS);
        if(newPos.isEmpty()){
            return this.getPosition();
        }
        return newPos.get(0);
    }

    abstract boolean canPass(Point pt, WorldModel world);

}
