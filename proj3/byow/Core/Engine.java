package byow.Core;

import byow.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final File CWD = new File(System.getProperty("user.dir"));
    Boolean gameStarted=false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public TETile[][] startGame() {

        this.drawFrame("CS61B: THE GAME");
        StdDraw.pause(1000);
        String seed = "";


        File save=new File(CWD, "seed.txt");
        File dogeXFile = new File(CWD, "dogeX.txt");
        File dogeYFile = new File(CWD, "dogeY.txt");
        File prevX=new File(CWD, "previousX.txt");
        File prevY=new File(CWD, "previousY.txt");
        File rocX=new File(CWD, "rocketX.txt");
        File rocY=new File(CWD, "rocketY.txt");
        File teslaX=new File(CWD, "teslaX.txt");
        File teslaY=new File(CWD, "teslaY.txt");
        File elonPoint = new File(CWD, "elonCoord.txt");

        int dogeX = Integer.valueOf(Utils.readContentsAsString(dogeXFile));
        int dogeY = Integer.valueOf(Utils.readContentsAsString(dogeYFile));

        TETile[][] oldFrame;
        boolean load = false;
        TETile currState =Tileset.LOCKED_DOOR;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char menu = StdDraw.nextKeyTyped();
                if (menu=='n') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char temp = StdDraw.nextKeyTyped();
                            if (Character.isDigit(temp)) {
                                seed += temp;
                                StdDraw.clear(Color.BLACK);
                                this.loadSeed("ENTER THE WALLSTREET ADDRESS (SEED)", seed);
                            }
                            if (temp == 's') {
                                ter.initialize(WIDTH, HEIGHT+HEIGHT/12, 0, 0);
                                oldFrame = this.interactWithInputString("-s " + seed);
                                ter.renderFrame(oldFrame);
                                break;
                            }
                        }
                    }
                    break;
                }
                if (menu == 'l') {
                    if (Utils.readContentsAsString(new File("newWorld.txt")).equals("true")) {
                        String sed= Utils.readContentsAsString(save);
                        char[] arr=sed.toCharArray();
                        for (char c :arr) {
                            if (Character.isDigit(c)) {
                                seed += c;
                            }
                        }
                        encounter(seed, new Random(Long.valueOf(seed)), true);
                    }
                    ter.initialize(WIDTH, HEIGHT + HEIGHT / 12, 0, 0);
                    String oldSeed = Utils.readContentsAsString(save);
                    oldSeed = "-s " + oldSeed;
                    oldFrame = this.interactWithInputString(oldSeed);
                    load=true;
                    //ter.renderFrame(oldFrame);
                    break;
                }
                if (menu=='q') {
                    System.exit(0);
                }
            }
        }
        Random ran;
        if (load) {
            String inputSeed = Utils.readContentsAsString(save);
            for (char c : inputSeed.toCharArray()) {
                if (Character.isDigit(c)) {
                    seed += c;
                }
            }
        }

        ran = new Random(Long.valueOf(seed));
        Floor f = new Floor(0, 0, WIDTH, HEIGHT);
        Floor.Tree container = f.splitContainer(4, ran, true);
        List<Floor> containerList = container.resultList();
        List<Hallway> hallwayList = container.hallwayList();
        List<Room> roomList = Room.constructRooms(Long.valueOf(seed), containerList);

        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i) == null) {
                break;
            }
            if (roomList.get(i).h <= 3 || roomList.get(i).w <= 3) {
                roomList.remove(i);
            }
        }
        if (roomList.size() >= 14) {
            int random = ran.nextInt(roomList.size() - 10);
            int index = 0;
            while (random != 0) {
                roomList.remove(index);
                index = index + 2;
                random--;
            }
        }

        int rocketX = Integer.valueOf(Utils.readContentsAsString(rocX));
        int rocketY = Integer.valueOf(Utils.readContentsAsString(rocY));

        int tesX = Integer.valueOf(Utils.readContentsAsString(teslaX));
        int tesY = Integer.valueOf(Utils.readContentsAsString(teslaY));

        dogeX = Integer.valueOf(Utils.readContentsAsString(dogeXFile));
        dogeY = Integer.valueOf(Utils.readContentsAsString(dogeYFile));
        Doge newDoge = new Doge(Tileset.LOCKED_DOOR, dogeX, dogeY);

        boolean spotlight = false;
        List<Point> elonList = new ArrayList<>();
        boolean createRocket = false;
        TETile[][] saveWorldFrame =interactWithInputString(seed);
        String seedResult =seed;

        if (load) {
            String reloadCoord = Utils.readContentsAsString(elonPoint);
            while (reloadCoord.length() != 0) {
                String coordinatesX = "";
                while (Character.isDigit(reloadCoord.charAt(0))) {
                    coordinatesX += reloadCoord.charAt(0);
                    reloadCoord = reloadCoord.substring(1);
                }
                int elonX = Integer.valueOf(coordinatesX);
                if (reloadCoord.charAt(0) == ',') {
                    reloadCoord = reloadCoord.substring(1);
                }
                String coordinatesY = "";
                while (Character.isDigit(reloadCoord.charAt(0))) {
                    coordinatesY += reloadCoord.charAt(0);
                    reloadCoord = reloadCoord.substring(1);
                }
                int elonY = Integer.valueOf(coordinatesY);
                if (reloadCoord.charAt(0) == '/') {
                    reloadCoord = reloadCoord.substring(1);
                }
                elonList.add(new Point(elonX, elonY));
            }
            for (Point P: elonList) {
                System.out.println(P.x + " " + P.y);
            }
            for (Room r: roomList) {
                System.out.println(r.center.x + " " + r.center.y);
            }
            ter.renderFrame((oldFrame));
            for (Room r : roomList) {
                for (Point p : elonList) {
                    if (r.center.x == p.x && r.center.y == p.y) {
                        ter.singleTileChange(oldFrame, r.center, Tileset.FLOWER);
                    }
                }
            }
        } else {
            ter.renderFrame((oldFrame));
            for (Room r : roomList) {
                elonList.add(new Point(r.center.x,r.center.y));
                ter.singleTileChange(oldFrame, r.center, Tileset.FLOWER);
            }
        }

//        for (int i = 0; i < ran.nextInt(roomList.size()-5)+5; i++) {
//            Point newPoint = new Point(roomList.get(i).center.x, roomList.get(i).center.y);
//            elonList.add(newPoint);
//            oldFrame = this.interactWithInputString(seed);
//            ter.singleTileChange(oldFrame, newPoint, Tileset.FLOWER);
////            System.out.println(roomList.get(i).center.x);
//        }

        while (true) {
            Utils.writeContents(new File(CWD, "newWorld.txt"), "false");
            time();
            if (rocketX== newDoge.getX() && rocketY== newDoge.getY()){
                /** to the moon */
                prompt("You've found the secret Rocket!");
                StdDraw.pause(2000);
                encounter(seed, ran, false);
            }
            if (elonList.isEmpty() && !createRocket) {
                prompt("Elon Musk: Thank you! Go to the Rocket!");
                StdDraw.pause(1000);
                StdDraw.clear();
                ter.initialize(WIDTH,HEIGHT);
                ter.renderFrame(interactWithInputString("-s"+seedResult));
                ter.singleTileChange(interactWithInputString("-s"+seedResult), new Point(rocketX,rocketY), Tileset.ROCKET);
                createRocket = true;
            }
            if (tesX== newDoge.getX() && tesY== newDoge.getY()){
                currState=Tileset.DOGE_IN_TESLA;
            }
            Point mouse= new Point((int)Math.floor(StdDraw.mouseX()), (int)Math.floor(StdDraw.mouseY()));
            if ((int)Math.floor(StdDraw.mouseX())==newDoge.getX()&&(int)Math.floor(StdDraw.mouseY())==newDoge.getY()){
                headsUp("DOGE");
            }
            else if (mouse.onCenter(roomList)) {
                headsUp("MUSK");
            }
            else if (mouse.onHallway(hallwayList)){
                headsUp("floor");
            }
            else if (mouse.onFloor(roomList)) {
                headsUp("floor");
            }
            else if (mouse.onSidewalk(hallwayList)){
                headsUp("side");
            }
            else if (mouse.onEdge(hallwayList)) {
                headsUp("edge");
            }
            else if (mouse.onRoadEnd(roomList,hallwayList)) {
                headsUp("roadend");
            }
            /** ------------------------- */

            else if (mouse.onRoom(roomList)) {
                headsUp("wall");
            }else{
                headsUp("void");
            }

            if (StdDraw.hasNextKeyTyped()) {
                for (Point p : elonList) {
                    if (newDoge.x == p.x && newDoge.y == p.y) {
                        elonList.remove(p);
                        break;
                    }
                }
                char wasd = StdDraw.nextKeyTyped();

                if (wasd == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char quit = StdDraw.nextKeyTyped();

                            if (quit == 'q') {
                                String finalString = "";
                                while (!elonList.isEmpty()) {
                                    String x = String.valueOf(elonList.get(0).getX());
                                    String y = String.valueOf(elonList.get(0).getY());
                                    finalString = finalString + x + "," + y + "/";
                                    elonList.remove(0);
                                }
                                Utils.writeContents(elonPoint, finalString);
                                Utils.writeContents(rocX, String.valueOf(rocketX));
                                Utils.writeContents(rocY, String.valueOf(rocketY));
                                Utils.writeContents(dogeXFile, String.valueOf(dogeX));
                                Utils.writeContents(dogeYFile, String.valueOf(dogeY));
                                Utils.writeContents(save, seedResult);
                                System.exit(0);
                            } else {
                                break;
                            }
                        }
                    }
                }
                else if (wasd=='f'){
                    List<Point> elonFlash = new ArrayList<>();
                    String reloadCoord = Utils.readContentsAsString(elonPoint);
                    while (reloadCoord.length() != 0) {
                        String coordinatesX = "";
                        while (Character.isDigit(reloadCoord.charAt(0))) {
                            coordinatesX += reloadCoord.charAt(0);
                            reloadCoord = reloadCoord.substring(1);
                        }
                        int elonX = Integer.valueOf(coordinatesX);
                        if (reloadCoord.charAt(0) == ',') {
                            reloadCoord = reloadCoord.substring(1);
                        }
                        String coordinatesY = "";
                        while (Character.isDigit(reloadCoord.charAt(0))) {
                            coordinatesY += reloadCoord.charAt(0);
                            reloadCoord = reloadCoord.substring(1);
                        }
                        int elonY = Integer.valueOf(coordinatesY);
                        if (reloadCoord.charAt(0) == '/') {
                            reloadCoord = reloadCoord.substring(1);
                        }
                        elonFlash.add(new Point(elonX, elonY));
                    }
                    if (spotlight){
                        ter.renderFrame(interactWithInputString("-s"+seedResult));
                        ter.update(saveWorldFrame, newDoge, ' ', !spotlight);
                        for (Room r : roomList) {
                            if (!elonList.contains(r.center)) {
                                ter.singleTileChange(interactWithInputString("-s"+seedResult), r.center, Tileset.FLOOR);
                            }
                        }
                    }else{
                        ter.update(saveWorldFrame, newDoge, ' ', !spotlight);
                        ter.renderFrame(saveWorldFrame);
                    }
                    for (Room r : roomList) {
                        for (Point p : elonList) {
                            if (r.center.x == p.x && r.center.y == p.y) {
                                ter.singleTileChange(interactWithInputString("-s"+seedResult), r.center, Tileset.FLOWER);
                            }
                        }
                    }
                    spotlight=!spotlight;
                }
                else if (wasd == 'w') {
                    seedResult+=wasd;
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString("-s"+seedResult);
                    Point check = new Point(newDoge.getX(), newDoge.getY()+1);

                    for (Point p : elonList) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            elonList.remove(p);
                            ter.renderFrame(interactWithInputString("-s"+seedResult));
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList) || check.onHallway(hallwayList)) {
                        newDoge = new Doge(currState, newDoge.getX(), newDoge.getY() + 1);
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }

                }
                else if (wasd == 'a') {
                    seedResult+=wasd;
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString("-s"+seedResult);
                    Point check = new Point(newDoge.getX()-1, newDoge.getY());

                    if (currState==Tileset.DOGE_IN_TESLA_Right){
                        currState=Tileset.DOGE_IN_TESLA;
                    }

                    for (Point p : elonList) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            elonList.remove(p);
                            ter.renderFrame(interactWithInputString("-s"+seedResult));
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList) || check.onHallway(hallwayList)) {
                        newDoge = new Doge(currState, newDoge.getX()-1, newDoge.getY());
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }

                }
                else if (wasd == 's') {
                    seedResult+=wasd;
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString("-s"+seedResult);
                    Point check = new Point(newDoge.getX(), newDoge.getY()-1);
                    for (Point p : elonList) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            elonList.remove(p);
                            ter.renderFrame(interactWithInputString("-s"+seedResult));
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList) || check.onHallway(hallwayList)) {
                        newDoge = new Doge(currState, newDoge.getX(), newDoge.getY()-1);
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }

                }
                else if (wasd == 'd') {
                    seedResult+=wasd;
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString("-s"+seedResult);
                    if (currState==Tileset.DOGE_IN_TESLA){
                        currState=Tileset.DOGE_IN_TESLA_Right;
                    }
                    for (Point p : elonList) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            elonList.remove(p);
                            ter.renderFrame(interactWithInputString("-s"+seedResult));
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    Point check = new Point(newDoge.getX()+1, newDoge.getY());
                    if (check.onFloor(roomList) || check.onHallway(hallwayList)) {
                        newDoge = new Doge(currState, newDoge.getX()+1, newDoge.getY());
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }

                }
            }
        }
    }

    public static TETile[][] generateSingleRoom(Room r){
        TETile[][] miniMap = new TETile[WIDTH][HEIGHT];
        List<Room> roomList = new ArrayList<>();
        roomList.add(r);

        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                miniMap[x][y] = Tileset.DIAMOND;
            }
        }
        for (int i=0; i<miniMap.length; i++){
            for (int j=0; j<miniMap[0].length; j++){
                Point p = new Point(i, j);
                if (p.onRoom(roomList)){
                    miniMap[i][j] = Tileset.WALL;
                }
                if (p.onFloor(roomList)) {
                    miniMap[i][j] = Tileset.FLOOR;
                }
            }
        }
        return miniMap;
    }

    public void encounter(String seed, Random ran, boolean load){

        File newMapDogeX = new File("dogeX.txt");
        File newMapDogeY = new File("dogeY.txt");

        String move = "";

        prompt("Don't pick up the DogeCoin!");
        StdDraw.pause(1000);

        List<Room> roomList = new ArrayList<>();
        Room r = new Room(WIDTH/2, HEIGHT/2);
        roomList.add(r);

        int dogeX = r.center.x;
        int dogeY = r.center.y;

        ter.initialize(WIDTH, HEIGHT);
        Doge newDoge = new Doge(Tileset.LOCKED_DOOR, dogeX, dogeY);
        if (load) {
            newDoge.x = Integer.valueOf(Utils.readContentsAsString(newMapDogeX));
            newDoge.y = Integer.valueOf(Utils.readContentsAsString(newMapDogeY));
        }

        TETile[][] result = generateSingleRoom(r);
        int numBitCoin = ran.nextInt(5) + 5;
        List<Point> bitCoord = new ArrayList<>();
        Point dogeCoord;
        for (int i = 0; i < numBitCoin; i++) {
            bitCoord.add(new Point(ran.nextInt(r.w - 6) + r.x + 3, ran.nextInt(r.h - 6) + r.y + 3));
            result[bitCoord.get(i).x][bitCoord.get(i).y] = Tileset.BITCOIN;
        }
        dogeCoord = new Point(ran.nextInt(r.w - 1) + r.x + 1, ran.nextInt(r.h - 1) + r.y + 1);
        result[dogeCoord.x][dogeCoord.y] = Tileset.DOGECOIN;

        Utils.writeContents(new File("newWorld.txt"), "true");

        result[newDoge.x][newDoge.y] = newDoge.character;
        ter.renderFrame(result);
        boolean spotlight = false;
        TETile[][] saveWorldFrame =generateSingleRoom(r);
        String seedResult = seed;

        while(true) {
            if (bitCoord.isEmpty()) {
                promptsmall("Victory! Bitcoin worth 50 semester tuitions of Berkeley!");
                StdDraw.pause(5000);
                System.exit(0);
            }
            if (newDoge.x == dogeCoord.x && newDoge.y == dogeCoord.y) {
                promptsmall("DogeCoin has crashed. Your greed has defeated you.");
                StdDraw.pause(5000);
                System.exit(0);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char wasd = StdDraw.nextKeyTyped();
                if (wasd == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char quit = StdDraw.nextKeyTyped();
                            if (quit == 'q') {
                                /** change later */
                                Utils.writeContents(newMapDogeX, String.valueOf(dogeX));
                                Utils.writeContents(newMapDogeY, String.valueOf(dogeY));
                                System.exit(0);
                            } else {
                                break;
                            }
                        }
                    }
                }
                else if (wasd == 'w') {
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString(seed);
                    Point check = new Point(newDoge.getX(), newDoge.getY()+1);
                    for (Point p : bitCoord) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            bitCoord.remove(p);
                            ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                            saveWorldFrame=interactWithInputString("-s"+seedResult);
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList)) {
                        seedResult += wasd;

                        newDoge = new Doge(Tileset.LOCKED_DOOR, newDoge.getX(), newDoge.getY() + 1);
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }
                }
                else if (wasd == 'a') {
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString(seed);
                    Point check = new Point(newDoge.getX()-1, newDoge.getY());
                    for (Point p : bitCoord) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            bitCoord.remove(p);
                            ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                            saveWorldFrame=interactWithInputString("-s"+seedResult);
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList)) {
                        seedResult += wasd;
                        newDoge = new Doge(Tileset.LOCKED_DOOR, newDoge.getX()-1, newDoge.getY());
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }
                }
                else if (wasd == 's') {
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString(seed);
                    Point check = new Point(newDoge.getX(), newDoge.getY()-1);
                    for (Point p : bitCoord) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            bitCoord.remove(p);
                            ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                            saveWorldFrame=interactWithInputString("-s"+seedResult);
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList)) {
                        seedResult += wasd;

                        newDoge = new Doge(Tileset.LOCKED_DOOR, newDoge.getX(), newDoge.getY()-1);
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }
                }
                else if (wasd == 'd') {
                    TETile[][] finalWorldFrame;
                    finalWorldFrame = interactWithInputString(seed);
                    Point check = new Point(newDoge.getX()+1, newDoge.getY());
                    for (Point p : bitCoord) {
                        if (newDoge.x == p.x && newDoge.y == p.y) {
                            bitCoord.remove(p);
                            ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                            saveWorldFrame=interactWithInputString("-s"+seedResult);
                            ter.singleTileChange(interactWithInputString("-s"+seedResult), p, Tileset.FLOOR);
                            break;
                        }
                    }
                    if (check.onFloor(roomList)) {
                        seedResult += wasd;

                        newDoge = new Doge(Tileset.LOCKED_DOOR, newDoge.getX()+1, newDoge.getY());
                        ter.update(finalWorldFrame, newDoge, wasd, spotlight);
                        saveWorldFrame=interactWithInputString("-s"+seedResult);
                    }
                }
            }
        }
    }

    public void time(){
        Date date= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MMMM.dd GGG hh:mm aaa");
        String formattedDate = sdf.format(date);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH-5, HEIGHT+2,15, 2);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig);
        StdDraw.text( WIDTH-10, HEIGHT+1, formattedDate);
        StdDraw.show();
    }

    public void headsUp(String s){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(1, HEIGHT+2,5, 2);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig);
        StdDraw.text( 2, HEIGHT+2, s);
        StdDraw.show();
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4, s);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-2, "Return of the DOGE");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game(N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2-2, "Load Game(L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2-4, "Quit Game(S)");
        StdDraw.show();
    }
    public void loadSeed(String s, String s2) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.enableDoubleBuffering();
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4, "Mission:");
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-2, "Save Musk from getting sent to jail by the SEC!");
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-8, s);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-10, s2);
        StdDraw.show();
    }
    public void prompt(String s) {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-2, s);
        StdDraw.show();
    }

    public void promptsmall(String s) {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Comic Sans", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT *3/4-2, s);
        StdDraw.show();
    }

    public void interactWithKeyboard() {
        this.startGame();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputStringNoDoge(String input) {
        File save=new File(CWD, "seed.txt");
        File dogeX=new File(CWD, "dogeX.txt");
        File dogeY=new File(CWD, "dogeY.txt");
        File prevX=new File(CWD, "previousX.txt");
        File prevY=new File(CWD, "previousY.txt");
        File rocketX=new File(CWD, "rocketX.txt");
        File rocketY=new File(CWD, "rocketY.txt");
        File teslaX=new File(CWD, "teslaX.txt");
        File teslaY=new File(CWD, "teslaY.txt");


        /** turn off and on */

        if (input.charAt(0)=='l'){
            String oldSeed=Utils.readContentsAsString(save);
            input.substring(1);
            input=oldSeed+input;
        }
        String seed = "";
        String move="";
        List<Room> roomList;
        char[] arr=input.toCharArray();
        for (char c :arr) {
            if (Character.isDigit(c)) {
                seed += c;
            }
        }

        for (int i=0; i<arr.length; i++) {
            if (Character.isLetter(arr[i])) {
                if (arr[i]=='w'||arr[i]=='a'||arr[i]=='s'||arr[i]=='d')
                    move += arr[i];
            }
            if (i<arr.length-1&&arr[i]==':'&&arr[i+1]=='q'){
                move+=':';
            }
        }

        Random ran = new Random(Long.valueOf(seed));
        Floor f = new Floor(0, 0, WIDTH, HEIGHT);
        Floor.Tree container=f.splitContainer(4,ran, true);

        List<Floor> containerList=container.resultList();
        List<Hallway> hallwayList=container.hallwayList();

        roomList = Room.constructRooms(Long.valueOf(seed), containerList);

        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i) == null) {
                break;
            }
            if (roomList.get(i).h <= 3 || roomList.get(i).w <= 3) {
                roomList.remove(i);
                i--;
            }
        }
        if (roomList.size() >= 14) {
            int random = ran.nextInt(roomList.size()-10);
            int index = 0;
            while (random != 0) {
                roomList.remove(index);
                index = index +2;
                random --;
            }
        }

        Room roomSpawn = roomList.get(ran.nextInt(roomList.size()));
        Doge d = new Doge(Tileset.LOCKED_DOOR, roomSpawn.center.x, roomSpawn.center.y);
        Utils.writeContents(prevX, String.valueOf(roomSpawn.center.x));
        Utils.writeContents(prevY, String.valueOf(roomSpawn.center.y));

        TETile[][] finalWorldFrame = new TETile[Engine.WIDTH][Engine.HEIGHT];

        Room teleportRoom = roomList.get(ran.nextInt(roomList.size()));
        while (roomSpawn == teleportRoom) {
            teleportRoom = roomList.get(ran.nextInt(roomList.size()));
        }

        Room teslaRoom= roomList.get(ran.nextInt(roomList.size()));
        while (roomSpawn == teslaRoom||teslaRoom == teleportRoom) {
            teslaRoom = roomList.get(ran.nextInt(roomList.size()));
        }

        while (!move.isEmpty()){
            char m =move.charAt(0);
            if (m=='a'){
                Point p = new Point(d.x-1, d.y);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.x--;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'a');
                }
            }
            if (m=='w'){
                Point p = new Point(d.x, d.y+1);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.y++;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'w');
                }
            }
            if (m=='s'){
                Point p = new Point(d.x, d.y-1);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.y--;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'s');
                }
            }
            if (m=='d'){
                Point p = new Point(d.x+1, d.y);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.x++;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'d');
                }
            }
            if (m==':'){
                Utils.writeContents(save, input.substring(0,input.length()-2));
                System.exit(0);
            }
            move=move.substring(1);
        }

        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                Point p = new Point(x, y);

                if (p.onRoom(roomList)) {
                    finalWorldFrame[x][y] = Tileset.WALL;
                }
                /** move these three together */
                /** Dollar Signs */

                if (p.onSidewalk(hallwayList)){
                    finalWorldFrame[x][y]=Tileset.WALL;
                }
                if (p.onEdge(hallwayList)) {
                    finalWorldFrame[x][y] = Tileset.WALL;
                }
                if (p.onRoadEnd(roomList,hallwayList)) {
                    finalWorldFrame[x][y]=Tileset.WALL;
                }
                /** ------------------------- */


                if (p.onHallway(hallwayList)){
                    finalWorldFrame[x][y]=Tileset.FLOOR;
                }
                if (p.onFloor(roomList)) {
                    finalWorldFrame[x][y] = Tileset.FLOOR;
                }

                /** Elon */
//                if (p.onCenter(roomList)) {
//                    finalWorldFrame[x][y] = Tileset.FLOWER;
//                }

            }
        }

        finalWorldFrame[teslaRoom.center.x][teslaRoom.center.y] = Tileset.TESLA;
        //finalWorldFrame[d.x][d.y]=d.character;
        Utils.writeContents(rocketX, String.valueOf(teleportRoom.center.x+ran.nextInt(teleportRoom.w/2)));
        Utils.writeContents(rocketY, String.valueOf(teleportRoom.center.y+ran.nextInt(teleportRoom.h/2)));

        Utils.writeContents(teslaX, String.valueOf(teslaRoom.center.x));
        Utils.writeContents(teslaY, String.valueOf(teslaRoom.center.y));
        Utils.writeContents(dogeX, String.valueOf(d.x));
        Utils.writeContents(dogeY, String.valueOf(d.y));

        /** turn off and on */
//        ter.initialize(WIDTH,HEIGHT);
//
//        ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }
    public TETile[][] interactWithInputString(String input) {
        File save=new File(CWD, "seed.txt");
        File dogeX=new File(CWD, "dogeX.txt");
        File dogeY=new File(CWD, "dogeY.txt");
        File prevX=new File(CWD, "previousX.txt");
        File prevY=new File(CWD, "previousY.txt");
        File rocketX=new File(CWD, "rocketX.txt");
        File rocketY=new File(CWD, "rocketY.txt");
        File teslaX=new File(CWD, "teslaX.txt");
        File teslaY=new File(CWD, "teslaY.txt");


        /** turn off and on */

        if (input.charAt(0)=='l'){
            String oldSeed=Utils.readContentsAsString(save);
            input.substring(1);
            input=oldSeed+input;
        }
        String seed = "";
        String move="";
        List<Room> roomList;
        char[] arr=input.toCharArray();
        for (char c :arr) {
            if (Character.isDigit(c)) {
                seed += c;
            }
        }

        for (int i=0; i<arr.length; i++) {
            if (Character.isLetter(arr[i])) {
                if (arr[i]=='w'||arr[i]=='a'||arr[i]=='s'||arr[i]=='d')
                    move += arr[i];
            }
            if (i<arr.length-1&&arr[i]==':'&&arr[i+1]=='q'){
                move+=':';
            }
        }

        Random ran = new Random(Long.valueOf(seed));
        Floor f = new Floor(0, 0, WIDTH, HEIGHT);
        Floor.Tree container=f.splitContainer(4,ran, true);

        List<Floor> containerList=container.resultList();
        List<Hallway> hallwayList=container.hallwayList();

        roomList = Room.constructRooms(Long.valueOf(seed), containerList);

        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i) == null) {
                break;
            }
            if (roomList.get(i).h <= 3 || roomList.get(i).w <= 3) {
                roomList.remove(i);
                i--;
            }
        }
        if (roomList.size() >= 14) {
            int random = ran.nextInt(roomList.size()-10);
            int index = 0;
            while (random != 0) {
                roomList.remove(index);
                index = index +2;
                random --;
            }
        }

        Room roomSpawn = roomList.get(ran.nextInt(roomList.size()));
        Doge d = new Doge(Tileset.LOCKED_DOOR, roomSpawn.center.x, roomSpawn.center.y);
        Utils.writeContents(prevX, String.valueOf(roomSpawn.center.x));
        Utils.writeContents(prevY, String.valueOf(roomSpawn.center.y));

        TETile[][] finalWorldFrame = new TETile[Engine.WIDTH][Engine.HEIGHT];

        Room teleportRoom = roomList.get(ran.nextInt(roomList.size()));
        while (roomSpawn == teleportRoom) {
            teleportRoom = roomList.get(ran.nextInt(roomList.size()));
        }

        Room teslaRoom= roomList.get(ran.nextInt(roomList.size()));
        while (roomSpawn == teslaRoom||teslaRoom == teleportRoom) {
            teslaRoom = roomList.get(ran.nextInt(roomList.size()));
        }

        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                Point p = new Point(x, y);

                if (p.onRoom(roomList)) {
                    finalWorldFrame[x][y] = Tileset.WALL;
                }
                /** move these three together */
                /** Dollar Signs */

                if (p.onSidewalk(hallwayList)){
                    finalWorldFrame[x][y]=Tileset.WALL;
                }
                if (p.onEdge(hallwayList)) {
                    finalWorldFrame[x][y] = Tileset.WALL;
                }
                if (p.onRoadEnd(roomList,hallwayList)) {
                    finalWorldFrame[x][y]=Tileset.WALL;
                }
                /** ------------------------- */


                if (p.onHallway(hallwayList)){
                    finalWorldFrame[x][y]=Tileset.FLOOR;
                }
                if (p.onFloor(roomList)) {
                    finalWorldFrame[x][y] = Tileset.FLOOR;
                }

//                /** Elon */
                if (p.onCenter(roomList)) {
                    finalWorldFrame[x][y] = Tileset.FLOWER;
                }

            }
        }

        while (!move.isEmpty()){
            char m =move.charAt(0);
            if (m=='a'){
                Point p = new Point(d.x-1, d.y);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.x--;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'a');
                }
            }
            if (m=='w'){
                Point p = new Point(d.x, d.y+1);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.y++;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'w');
                }
            }
            if (m=='s'){
                Point p = new Point(d.x, d.y-1);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.y--;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'s');
                }
            }
            if (m=='d'){
                Point p = new Point(d.x+1, d.y);
                if (p.onHallway(hallwayList)||p.onFloor(roomList)){
                    d.x++;
                    TERenderer.updateNORENDER(finalWorldFrame, d,'d');
                }
            }
            if (m==':'){
                Utils.writeContents(save, input.substring(0,input.length()-2));
                System.exit(0);
            }
            move=move.substring(1);
        }

        finalWorldFrame[teslaRoom.center.x][teslaRoom.center.y] = Tileset.TESLA;
        finalWorldFrame[d.x][d.y]=d.character;
        Utils.writeContents(rocketX, String.valueOf(teleportRoom.center.x+ran.nextInt(teleportRoom.w/2)));
        Utils.writeContents(rocketY, String.valueOf(teleportRoom.center.y+ran.nextInt(teleportRoom.h/2)));

        Utils.writeContents(teslaX, String.valueOf(teslaRoom.center.x));
        Utils.writeContents(teslaY, String.valueOf(teslaRoom.center.y));
        Utils.writeContents(dogeX, String.valueOf(d.x));
        Utils.writeContents(dogeY, String.valueOf(d.y));

        /** turn off and on */
//        ter.initialize(WIDTH,HEIGHT);
//
//        ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }
}
