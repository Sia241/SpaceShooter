package exam.spaceshooter;


public class MoveCommand implements ICommand {
    private EnemyShip enemyShip;
    private float xMove;
    private float yMove;
    private float deltaTime;

    public MoveCommand(EnemyShip enemyShip, float xMove, float yMove, float deltaTime) {
        this.enemyShip = enemyShip;
        this.xMove = xMove;
        this.yMove = yMove;
        this.deltaTime= deltaTime;
    }

        @Override
        public void execute() {
            float leftLimit, rightLimit, upLimit, downLimit;
            leftLimit = -enemyShip.boundingBox.x;
            downLimit = (float) WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
            rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
            upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

            float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
            float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

            if (xMove > 0) {
                xMove = Math.min(xMove, rightLimit);
            } else {
                xMove = Math.max(xMove, leftLimit);
            }

            if (yMove > 0) {
                yMove = Math.min(yMove, upLimit);
            } else {
                yMove = Math.max(yMove, downLimit);
            }

            enemyShip.translate(xMove, yMove);
        }


}
