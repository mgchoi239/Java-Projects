package byow.Core;

import java.util.List;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        /** convert Ran into respective x and y */
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean onRoom(List<Room> roomList){
        for (Room compare: roomList) {
            if (((compare.x <= this.x) && (this.x <= (compare.x + compare.w)) && (compare.y == this.y)) ||
                    ((compare.x <= this.x) && (this.x <= (compare.x + compare.w)) && (compare.y+compare.h == this.y)) ||
                    (compare.y <= this.y && this.y <= compare.y + compare.h && compare.x == this.x)||
                    (compare.y <= this.y && this.y <= compare.y + compare.h && compare.x+compare.w == this.x)) {
                return true;
            }
        }
        return false;
    }

    public boolean onFloor(List<Room> roomList){
        for (Room compare: roomList) {
            if ((compare.x < this.x) && (this.x < (compare.x + compare.w) && (compare.y < this.y) && (compare.y+compare.h > this.y))) {
                return true;
            }
        }
        return false;
    }

    public boolean onCenter(List<Room> roomList){
        for (Room compare: roomList) {
            if (this.x == compare.center.x && this.y == compare.center.y) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDistance(Doge d){
        if (Math.pow(Math.abs(this.x-d.x),2)+Math.pow(Math.abs(this.y-d.y),2) <= 49) {
            return true;
        }
        return false;
    }

    public boolean onTeleport(List<Room> roomList){
        for (Room compare: roomList) {
            if (this.x == compare.center.x && this.y == compare.center.y) {
                return true;
            }
        }
        return false;
    }

    public boolean onHallway(List<Hallway> hallwaysList){
        for (Hallway h: hallwaysList) {
            if (h.p1.x==h.p2.x&&h.p1.x==this.x){
                if (this.y>=h.p1.y&&this.y<=h.p2.y){
                    return true;
                }
            }else if (h.p1.y==h.p2.y&&h.p1.y==this.y){
                if (this.x>=h.p1.x&&this.x<=h.p2.x){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onRoadEnd(List<Room> roomList, List<Hallway> hallwayList){
        if ((new Point(this.x + 1, this.y).onEdge(hallwayList) && new Point(this.x - 1, this.y).onEdge(hallwayList))
                || (new Point(this.x, this.y+1).onEdge(hallwayList) && new Point(this.x, this.y-1).onEdge(hallwayList))) {
            return true;
        }
        return false;
    }

    public boolean onContainer(List<Floor> floorList){
        for (Floor compare: floorList) {
            if (((compare.x <= this.x) && (this.x <= (compare.x + compare.w)) && (compare.y == this.y)) ||
                    ((compare.x <= this.x) && (this.x <= (compare.x + compare.w)) && (compare.y+compare.h == this.y)) ||
                    (compare.y <= this.y && this.y <= compare.y + compare.h && compare.x == this.x)||
                    (compare.y <= this.y && this.y <= compare.y + compare.h && compare.x+compare.w == this.x)) {
                return true;
            }
        }
        return false;
    }

    public boolean onSidewalk(List<Hallway> hallwaysList){
        for (Hallway h: hallwaysList) {
            if ((h.p1.x==h.p2.x)&&((h.p1.x+1==this.x) || (h.p1.x-1==this.x))){
                if (this.y>=h.p1.y&&this.y<=h.p2.y){
                    return true;
                }
            }else if ((h.p1.y==h.p2.y)&&((h.p1.y-1==this.y) || (h.p1.y+1==this.y))){
                if (this.x>=h.p1.x&&this.x<=h.p2.x){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onEdge(List<Hallway> hallwaysList){
        Point edgeRT = new Point(this.x+1, this.y+1);
        Point edgeLT = new Point(this.x-1, this.y+1);
        Point edgeRB = new Point(this.x+1, this.y-1);
        Point edgeLB = new Point(this.x-1, this.y-1);
        if (edgeRT.onHallway(hallwaysList) || edgeLT.onHallway(hallwaysList) || edgeRB.onHallway(hallwaysList) || edgeLB.onHallway(hallwaysList)) {
            return true;
        }
        return false;
    }
}
