package exam.spaceshooter;

import static exam.spaceshooter.ICommand.WORLD_HEIGHT;
import static exam.spaceshooter.ICommand.WORLD_WIDTH;
import static exam.spaceshooter.IGameObject.textureAtlas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship implements ScoreVisitable, CollisionVisitable {

    Vector2 directionVector;
    // Intrinsic Attribute
    float timeSinceLastDirectionChange = 0 ;
    float directionChangeFrequency = 0.75f;
    private static final float laserWidth = 1;
    private static final float laserHeight = 4;
    private static final float laserMovementSpeed = 50;
    private static final float timeBetweenShots = 1;
    private FlyweightEnemyShip flyweightEnemyShip;


    public EnemyShip(float xCentre, float yCentre,
                     float width, float height,
                     float movementSpeed, int shield,
                     String textureRegionName,
                     TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, null, laserTextureRegion);
        this.flyweightEnemyShip = FlyweightEnemyShipFactory.getEnemyShip(textureRegionName);
        directionVector = new Vector2(0, -1);
    }







    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = SpaceShooterGame.random.nextDouble()*6.283185; //0 to 2*PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }

    }

    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.31f, boundingBox.y + boundingBox.height * 0.045f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox. width * 0.69f, boundingBox.y + boundingBox.height * 0.045f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0;
        return laser;
    }



    @Override
    public void draw(Batch batch) {
        batch.draw(flyweightEnemyShip.getEnemyShipTextureRegion(), boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public int getPointValue() {
        return flyweightEnemyShip.getPointValue();
    }

    @Override
    public void accept(ScoringVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public void accept(CollisionVisitor visitor, Laser laser) {
        visitor.visit(this, laser);
    }


}


