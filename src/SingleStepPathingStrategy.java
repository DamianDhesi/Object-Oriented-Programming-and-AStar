import processing.core.PImage;

import java.util.List;
import java.util.function.*;
import java.util.stream.*;

public class SingleStepPathingStrategy extends Animatable implements PathingStrategy
{
    public SingleStepPathingStrategy(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        /* Does not check withinReach.  Since only a single step is taken
         * on each call, the caller will need to check if the destination
         * has been reached.
         */
        return potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)
                                && Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                                && Math.abs(end.getY() - pt.getY()) <= Math.abs(end.getY() - start.getY()))
                .limit(1)
                .collect(Collectors.toList());
    }
}
