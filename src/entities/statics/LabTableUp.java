package entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import game.Handler;
import graphics.Assets;
import tiles.Tile;

public class LabTableUp extends StaticEntity{

    public LabTableUp(Handler handler, float x, float y) {
        super(handler, x, y, (int) (Tile.TILE_WIDTH * 2), (int) (Tile.TILE_HEIGHT * 1));

        // SPECIFIC TO LAB TABLE UP
        bounds.x = 0;
        bounds.y = 10;
        bounds.width = 60;
        bounds.height = 30;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.labTableUp, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.RED);
        //g.fillRect((int) (x - handler.getGameCamera().getxOffset() + bounds.x), (int) (y - handler.getGameCamera().getyOffset() + bounds.y), bounds.width, bounds.height);
    }

}
