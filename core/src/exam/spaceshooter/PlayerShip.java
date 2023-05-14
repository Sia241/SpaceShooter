package exam.spaceshooter;

import static exam.spaceshooter.ICommand.WORLD_HEIGHT;
import static exam.spaceshooter.ICommand.WORLD_WIDTH;
import static exam.spaceshooter.IGameObject.textureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {


    TextureRegion  playerShipTextureRegion = IGameObject.textureAtlas.findRegion("player_ship");

    TextureRegion playerLaserTextureRegion = textureAtlas.findRegion("player_laser");

    public int getLives() {
        return lives;
    }

    private int lives = 10;
    public PlayerShip(float xCentre, float yCentre,
                      float width, float height,
                      float movementSpeed, int shield,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, laserTextureRegion);
    }
    public PlayerShip() {
        super(WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10, 40, 1,
                0.4f, 4, 45, 0.5f);


        super.shipTextureRegion= this.playerShipTextureRegion;
        super.laserTextureRegion = this.playerLaserTextureRegion;
    }


    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.19f, boundingBox.y + boundingBox.height * 0.56f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox. width * 0.81f, boundingBox.y + boundingBox.height * 0.44f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
