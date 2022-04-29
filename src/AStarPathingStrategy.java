import processing.core.PImage;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy extends Animatable implements PathingStrategy{
    public AStarPathingStrategy(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        class Node{
            private final int f;
            private final Point cameFrom;
            public Node(int g, int h, Point cf){
                this.f = g + h;
                this.cameFrom = cf;
            }
        }
        Function<Point, Integer> getH = point -> Math.abs(end.getX() - point.getX())
                + Math.abs(end.getY() - point.getY());
        Function<Point, Integer> getG = point -> Math.abs(start.getX() - point.getX())
                + Math.abs(start.getY() - point.getY());

        HashMap<Point, Node> values = new HashMap<>();
        values.put(start, new Node(getG.apply(start), getH.apply(start), null));
        Comparator<Point> sortLowestF = (p1, p2) -> values.get(p1).f - values.get(p2).f;

        PriorityQueue<Point> openSet = new PriorityQueue<>(sortLowestF);
        openSet.add(start);

        List<Point> closedList = new ArrayList<>();

        if(withinReach.test(start, end)){
            return Collections.singletonList(start);
        }

        while(!openSet.isEmpty()){
            Point current = openSet.remove();
            if(!closedList.contains(current)){
                closedList.add(current);
            }

            if(withinReach.test(current, end)){
                List<Point> path = new ArrayList<>();

                while(values.containsKey(current) && values.get(current).cameFrom != null){
                    path.add(0, current);
                    current = values.get(current).cameFrom;
                }
                return path;
            }

            for(Point neighbor : potentialNeighbors.apply(current)
                    .filter(canPassThrough)
                    .toList()){
                if(!closedList.contains(neighbor)){
                    if(!values.containsKey(neighbor)){
                        values.put(neighbor, new Node(getG.apply(neighbor), getH.apply(neighbor), current));
                    }
                    if(!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

}
