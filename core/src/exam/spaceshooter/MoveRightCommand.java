package exam.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MoveRightCommand implements ICommand {

    private final PlayerShip playerShip;
    private final float deltaTime;

    public MoveRightCommand(PlayerShip playerShip, float deltaTime) {
        this.playerShip = playerShip;
        this.deltaTime = deltaTime;
    }

    @Override
    public void execute() {
        float rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightLimit), 0f);
        }
    }
}