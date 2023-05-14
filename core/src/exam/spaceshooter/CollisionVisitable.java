package exam.spaceshooter;

public interface CollisionVisitable {
    public void accept(CollisionVisitor visitor,Laser laser);
}
