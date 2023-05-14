package exam.spaceshooter;

public interface ICommand {
    static final float WORLD_WIDTH = 72;
    static final float WORLD_HEIGHT = 128;
    void execute();
}