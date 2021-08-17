package byow.Core;

import byow.TileEngine.TETile;

public class Doge {
    TETile character;
    int x;
    int y;

    public Doge(TETile tile, int x, int y){
        character=tile;
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TETile getCharacter() {
        return character;
    }
}
