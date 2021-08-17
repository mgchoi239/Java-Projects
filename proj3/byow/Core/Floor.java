package byow.Core;
import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Floor {
    int x;
    int y;
    int w;
    int h;
    Point center;

    public Floor(int x, int y, int w, int h) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        center = new Point(this.x + (this.w/2), this.y + (this.h/2));
    }

    public class Tree {
        Tree left;
        Tree right;
        Floor value;

        private Tree(Floor f){
            left=null;
            right=null;
            value=f;
        }
        public Floor getValue(){
            return value;
        }

        public List<Floor> resultList(){
            List<Floor> f=new ArrayList<>();
            recurHelper(this, f);
            return f;
        }

        public void recurHelper(Tree t, List<Floor> add){
            if (t==null){
                return;
            }else{
                if (t.right==null&&t.left==null)
                    add.add(t.value);
                recurHelper(t.right, add);
                recurHelper(t.left, add);
            }
        }
        public List<Hallway> hallwayList(){
            List<Hallway> h=new ArrayList<>();
            hallwayHelper(this, h);
            return h;
        }
        public void hallwayHelper(Tree t, List<Hallway> h){
            if (t.left==null||t.right==null){
                return;
            }else{
                Hallway hway=new Hallway(t.left.value.center, t.right.value.center);
                h.add(hway);
                hallwayHelper(t.left,h);
                hallwayHelper(t.right,h);
            }

        }
    }

    public Tree splitContainer(int n, Random seed, Boolean b){
        Tree root = new Tree(this);
        if (n!=0){
            if (b) {
                Floor[] sr = this.randomSplitX(seed);
                root.left = sr[0].splitContainer(n - 1, seed, !b);
                root.right = sr[1].splitContainer(n - 1, seed, !b);
            } else {
                Floor[] sr = this.randomSplitY(seed);
                root.left = sr[0].splitContainer(n - 1, seed, !b);
                root.right = sr[1].splitContainer(n - 1, seed, !b);
            }
        }
        return root;
    }

    public Floor[] randomSplitX(Random seed){
        Floor r1, r2;
        r1 = new Floor(this.x, this.y,seed.nextInt(this.w), this.h);
        r2 = new Floor(this.x + r1.w, this.y, this.w - r1.w, this.h);
        if (true) {
            float w_ratio = (float) r1.w / r2.w;
            if (w_ratio < 0.4 || w_ratio > 2.5) {
                return this.randomSplitX(seed);
            }
        }
        return new Floor[]{r1, r2};
    }

    public Floor[] randomSplitY(Random seed){
        Floor r1, r2;
        r1 = new Floor(this.x, this.y, this.w, seed.nextInt(this.h));
        r2 = new Floor(this.x, this.y+r1.h, this.w, this.h-r1.h);
        if (true) {
            float h_ratio = (float) r1.h / r2.h;
            if (h_ratio < 0.4 || h_ratio > 2.5) {
                return this.randomSplitY(seed);
            }
        }
        return new Floor[]{r1, r2};
    }
}
