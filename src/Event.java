import java.util.LinkedList;
import java.util.List;

/**
 * An event is made up of an Entity that is taking an
 * Action a specified time.
 */
public final class Event
{
    private final Action action;
    private final long time;
    private final Entity entity;

    public Event(Action action, long time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    public void scheduleEvent(EventScheduler scheduler)
    {
        long time = System.currentTimeMillis() + (long)(this.time
                * scheduler.getTimeScale());
        Event event = new Event(this.action, time, this.entity);

        scheduler.getEventQueue().add(event);

        // update list of pending events for the given entity
        List<Event> pending = scheduler.getPendingEvents().getOrDefault(this.entity,
                new LinkedList<>());
        pending.add(event);
        scheduler.getPendingEvents().put(this.entity, pending);
    }

    public Action getAction(){return this.action;}

    public long getTime(){return this.time;}

    public Entity getEntity(){return this.entity;}

}
