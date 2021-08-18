package byow.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    int x;
    int y;
    int w;
    int h;
    Point center;

    private Room (Floor f, Random r) {
        this.x = f.x + r.nextInt(f.w / 3);
        this.y = f.y + r.nextInt(f.h / 3);
        this.w = f.w - (this.x - f.x);
        this.h = f.h - (this.y - f.y);
        this.w -= r.nextInt(this.w/2);
        this.h -= r.nextInt(this.w/2);
        center= new Point(this.x + (this.w/2), this.y + (this.h/2));
        if (y+this.h==Engine.HEIGHT){
            this.h--;
        }
        if (x+this.w==Engine.WIDTH){
            this.w--;
        }
    }

    public Room (int w, int h) {
        this.w = w;
        this.h = h;
        this.x=Engine.WIDTH/4;
        this.y=Engine.HEIGHT/4;
        center= new Point(this.x + (this.w/2), this.y + (this.h/2));
    }

    public static List<Room> constructRooms(Long seed, List<Floor> floorList) {
        Random r = new Random(seed);
        List<Room> allRoom= new ArrayList<>();
        for (Floor f: floorList){
            allRoom.add(new Room(f, r));
        }
        return allRoom;
    }
}
