public class Activity extends Action {
    private final WorldModel world;
    private final ImageStore imageStore;
    public Activity(Entity entity,WorldModel world, ImageStore imageStore)
    {
        super(entity, 0);
        this.world = world;
        this.imageStore = imageStore;
    }

    public WorldModel getWorldModel() {
        return this.world;
    }

    public ImageStore getimageStore() {
        return this.imageStore;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        ((Animatable)getEntity()).executeActivity(this, scheduler);
    }
}
