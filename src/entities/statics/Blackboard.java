package entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import game.Handler;
import graphics.Assets;
import tiles.Tile;

public class Blackboard extends StaticEntity{

    public Blackboard(Handler handler, float x, float y) {
        super(handler, x, y, (int) (Tile.TILE_WIDTH * 1.75), (int) (Tile.TILE_HEIGHT * 1));

        // SPECIFIC TO BLACKBOARD
        bounds.x = 0;
        bounds.y = 10;
        bounds.width = 60;
        bounds.height = 40;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.blackBoard, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.RED);
        //g.fillRect((int) (x - handler.getGameCamera().getxOffset() + bounds.x), (int) (y - handler.getGameCamera().getyOffset() + bounds.y), bounds.width, bounds.height);
    }

}
