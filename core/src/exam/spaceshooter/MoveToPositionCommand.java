package exam.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MoveToPositionCommand implements ICommand {
    private final PlayerShip playerShip;
    private  Input input;
    private float x;
    private  float y;
    private final float deltaTime;

    private Viewport viewport = null;

    private final float TOUCH_MOVEMENT_THRESHOLD = 0.1f;

    public MoveToPositionCommand(PlayerShip ship, float deltaTime, Viewport viewport, Input input) {
        this.playerShip = ship;
        this.deltaTime = deltaTime;
        this.viewport =viewport;
        this.input= input;
    }

    @Override
    public void execute() {
        //get the screen position of the touch
        float xTouchPixels = Gdx.input.getX();
        float yTouchPixels = Gdx.input.getY();

        //convert to world position
        Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
        touchPoint = viewport.unproject(touchPoint);

        //calculate the x and y differences
        Vector2 playerShipCentre = new Vector2(
                playerShip.boundingBox.x + playerShip.boundingBox.width / 2,
                playerShip.boundingBox.y + playerShip.boundingBox.height / 2);

        float touchDistance = touchPoint.dst(playerShipCentre);

        if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {

            float xTouchDifference = touchPoint.x - playerShipCentre.x;
            float yTouchDifference = touchPoint.y - playerShipCentre.y;

            //scale to the maximum speed of the ship
            float xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
            float yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

            // limit the movement in each direction to the max distance
            float leftLimit, rightLimit, upLimit, downLimit;
            leftLimit = -playerShip.boundingBox.x;
            downLimit = -playerShip.boundingBox.y;
            rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
            upLimit = (float) WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;


            if (xMove > 0) xMove = Math.min(xMove, rightLimit);
            else xMove = Math.max(xMove, leftLimit);

            if (yMove > 0) yMove = Math.min(yMove, upLimit);
            else yMove = Math.max(yMove, downLimit);

            playerShip.translate(xMove, yMove);
        }
    }

    public void undo() {
        // Move the ship back to its original position by moving it in the opposite direction
            playerShip.translate(-x, -y);
    }
}
