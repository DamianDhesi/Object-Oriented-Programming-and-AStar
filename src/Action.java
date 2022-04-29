import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * An action that can be taken by an entity
 */
public abstract class Action
{
    private final Entity entity;
    private final int repeatCount;

    public Action(Entity entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    protected int getRepeatCount() {
        return this.repeatCount;
    }
    protected Entity getEntity(){return this.entity;}
    protected abstract void executeAction(EventScheduler scheduler);

}
