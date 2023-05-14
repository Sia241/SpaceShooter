package exam.spaceshooter;

import static exam.spaceshooter.ICommand.WORLD_HEIGHT;
import static exam.spaceshooter.ICommand.WORLD_WIDTH;
import static exam.spaceshooter.GameScreen.asteroidTexture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Ship implements ScoreVisitable,CollisionVisitable {

    boolean a = true;
    private TextureRegion texture;
    private Vector2 velocity;
    private float angularVelocity,asteroidWidth,asteroidHeight;
    Vector2 position = new Vector2(MathUtils.random(-WORLD_WIDTH/2, WORLD_WIDTH/2),
            MathUtils.random(Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight())); // set initial position at the top of the screen


    public Asteroid(TextureRegion texture,float width,float height) {
        super(WORLD_WIDTH /2, WORLD_HEIGHT/4, 7,7 );
        this.texture = texture;
        super.xPosition = MathUtils.random(-WORLD_WIDTH/2, WORLD_WIDTH/2);
        super.yPosition = Gdx.graphics.getHeight();
        this.asteroidWidth = width;
        this.asteroidHeight = height;
       super.shield= 1;
       super.shipTextureRegion= asteroidTexture;
        // set initial velocity for asteroid to fall straight down
        velocity = new Vector2(0, -30); // adjust this value as necessary to control the speed of the asteroid
    }


    public void updatee(float delta) {
        // update asteroid's position based on its velocity and elapsed time
        position.mulAdd(velocity, delta);

        // check if asteroid is outside the screen and adjust its position if necessary
        float asteroidWidth = 7; // assuming the asteroid texture region is 10x10 pixels
        float asteroidHeight = 7;
      /*  if (position.y + asteroidHeight / 2 < 0) {
            // asteroid has moved off the bottom of the screen, wrap it to the top
            position.y = WORLD_HEIGHT + asteroidHeight / 2;
            position.x = MathUtils.random(-WORLD_WIDTH + asteroidWidth / 2, WORLD_WIDTH - asteroidWidth / 2);
        }*/

        timeSinceLastShot += delta;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] l = null;
        return l;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), position.x, position.y, this.asteroidWidth,this.asteroidHeight);
       // this.draw(batch);
    }

    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }

    @Override
    public void accept(ScoringVisitor visitor) {
            visitor.visit(this);

    }

    public int getPointValue() {
        return 50;
    }

    public boolean collidesWith(Laser laser) {
        Rectangle laserBounds = new Rectangle(laser.boundingBox);
        Rectangle asteroidBounds = new Rectangle(position.x, position.y, 4, 7);

        return laserBounds.overlaps(asteroidBounds);
    }

    public boolean collidesWith(PlayerShip playerShip) {
        Rectangle laserBounds = new Rectangle(playerShip.boundingBox);
        Rectangle asteroidBounds = new Rectangle(position.x, position.y, 4, 7);

        return laserBounds.overlaps(asteroidBounds);
    }
    @Override
    public void accept(CollisionVisitor visitor, Laser laser) {
        visitor.visit(this, laser);
    }



    // add getters and setters for position and velocity
}
