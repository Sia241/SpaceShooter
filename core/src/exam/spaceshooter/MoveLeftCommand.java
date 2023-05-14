package exam.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MoveLeftCommand implements ICommand {
    private final PlayerShip playerShip;
    private final float deltaTime;

    public MoveLeftCommand(PlayerShip playerShip, float deltaTime) {
        this.playerShip = playerShip;
        this.deltaTime = deltaTime;
    }



    @Override
    public void execute() {
        float leftLimit = -playerShip.boundingBox.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftLimit), 0f);
        }
    }
}







