package exam.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MoveUpCommand implements ICommand {
    private final PlayerShip playerShip;
    private final float deltaTime;

    public MoveUpCommand(PlayerShip playerShip, float deltaTime) {
        this.playerShip = playerShip;
        this.deltaTime = deltaTime;
    }

    @Override
    public void execute() {
        float upLimit = (float) WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerShip.translate(0f, Math.min(playerShip.movementSpeed * deltaTime, upLimit));
        }
    }
}