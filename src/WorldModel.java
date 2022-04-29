import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    private final int numRows;
    private final int numCols;
    private final Background[][] background;
    private final Entity[][] occupancy;
    private final Set<Entity> entities;
    private final Parser parser;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
        this.parser = new Parser(this);
    }

    public int getNumRows(){return this.numRows;}

    public int getNumCols(){return this.numCols;}

    public Background[][] getBackground(){return this.background;}

    public Set<Entity> getEntities(){return this.entities;}

    public void load(
            Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }

    private boolean processLine(
            String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[Functions.PROPERTY_KEY]) {
                case Functions.BGND_KEY:
                    return parser.parseBackground(properties, imageStore);
                case Functions.DUDE_KEY:
                    return parser.parseDude(properties,  imageStore);
                case Functions.OBSTACLE_KEY:
                    return parser.parseObstacle(properties,  imageStore);
                case Functions.FAIRY_KEY:
                    return parser.parseFairy(properties,  imageStore);
                case Functions.HOUSE_KEY:
                    return parser.parseHouse( properties,  imageStore);
                case Functions.TREE_KEY:
                    return parser.parseTree(properties,  imageStore);
                case Functions.SAPLING_KEY:
                    return parser.parseSapling(properties,  imageStore);
            }
        }

        return false;
    }

    public void tryAddEntity( Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity( entity);
    }

    public boolean withinBounds( Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0
                && pos.getX() < this.numCols;
    }

    public boolean isOccupied( Point pos) {
        return withinBounds(pos) && this.getOccupancyCell( pos) != null;
    }


    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */
    public void addEntity( Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity( Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds( pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell( oldPos, null);
            this.removeEntityAt( pos);
            this.setOccupancyCell( pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity( Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }

    private void removeEntityAt( Point pos) {
        if (this.withinBounds( pos) && this.getOccupancyCell( pos) != null) {
            Entity entity = this.getOccupancyCell( pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell( pos, null);
        }
    }

    public Optional<PImage> getBackgroundImage( Point pos)
    {
        if (this.withinBounds( pos)) {
            return Optional.of(this.getBackgroundCell( pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<Entity> getOccupant( Point pos) {
        if (this.isOccupied( pos)) {
            return Optional.of(this.getOccupancyCell( pos));
        }
        else {
            return Optional.empty();
        }
    }

    public Entity getOccupancyCell( Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    private void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    private Background getBackgroundCell( Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }


}
