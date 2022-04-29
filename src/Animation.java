public class Animation extends Action {

    public Animation(Entity entity, int repeatCount){
        super(entity, repeatCount);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (getRepeatCount() != 1) {
            Event event = new Event(new Animation(getEntity(),
                    Math.max(getRepeatCount() - 1,
                            0)),((Animatable)getEntity()).getAnimationPeriod(), getEntity());
            event.scheduleEvent(scheduler);
        }
    }
}
