package exam.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MoveDownCommand implements ICommand {
    private final PlayerShip playerShip;
    private final float deltaTime;

    public MoveDownCommand(PlayerShip playerShip, float deltaTime) {
        this.playerShip = playerShip;
        this.deltaTime = deltaTime;
    }

    @Override
    public void execute() {
        float downLimit = -playerShip.boundingBox.y;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerShip.translate(0f, Math.max(-playerShip.movementSpeed * deltaTime, downLimit));
        }
    }
}