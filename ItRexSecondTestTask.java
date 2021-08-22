package itrex;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ItRexSecondTestTask {

    private static int H;
    private static int M;
    private static int N;
    // First level
    private static int currentLevel = 0;
    // '1' coordinates
    private static int startX;
    private static int startY;
    // '2' coordinates
    private static int endX;
    private static int endY;

    static List<List<Point>> pathes = new LinkedList<>();// Collection of all pathes from '1' to '2'
    static List<Point> path = new LinkedList<>(); // Current path from '1' to '2'

    private static List<boolean[][]> levels = new LinkedList<>();
    private static String filePath = "(ClassPath)/INPUT.txt"; // TODO: 22.08.2021 Set your path to INPUT.txt

    public static void main(String[] args) {
        initializeLabyrinth();
        findPath(new Point(levels, startX, startY, currentLevel));// First point '1'
        int minSize = pathes.get(0).size();
        for (List<Point> list: pathes) {
            if(list.size() < minSize){
                minSize = list.size();
            }
        }
        System.out.println((minSize * 5) + " seconds!");
    }
    // Initialize Labyrinth '.' - true, 'o' - false
    private static void initializeLabyrinth(){
        LinkedList<String> fileData = readFromFile();
        String[] hmn = fileData.removeFirst().trim().split(" ");
        H = Integer.parseInt(hmn[0]);
        M = Integer.parseInt(hmn[1]);
        N = Integer.parseInt(hmn[2]);
        int countElement = 0;
        for (int i = 0; i < H; i++){
            boolean[][] level = new boolean[M][N];
            for (int j = 0; j < M; j++){
                for (int k = 0; k < N; k++){
                    char[] str = fileData.get(countElement).trim().toCharArray();
                    if(str[k] == '.'){
                        level[j][k] = true;
                    }else if(str[k] == 'o'){
                        level[j][k] = false;
                    }else if(str[k] == '1'){
                        startX = j;
                        startY = k;
                        level[j][k] = false;
                    }else if(str[k] == '2'){
                        endX = j;
                        endY = k;
                        level[j][k] = true;
                    }
                }
                countElement++;
            }
            levels.add(level);
        }
    }
    // Read Labyrinth from INPUT.txt
    private static LinkedList<String> readFromFile(){
        LinkedList<String> stringsFromFile = new LinkedList();
        String line = "";
        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            while((line = reader.readLine()) != null){
                if(!line.equals("")){
                    stringsFromFile.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringsFromFile;
    }
    // Find available path and add it to pathes
    private static void findPath(Point point){
        if(path.size() == 0){
            point.setPreviousPoint(point);
        }
        if(point.isPrincessPoint(endX, endY)){
            addPathToPathes(path);
            path = new LinkedList<>();
            return;
        }
        // If possible, take a step in the right direction - LEFT, RIGHT, TOP, BOTTOM, NEXT LEVEL
        if(point.hasLeft() && !point.getPreviousPoint().equals(point.getLeftPoint()) && !pathContainPoint(point.getLeftPoint())){
            Point leftPoint = point.getLeftPoint();
            leftPoint.setPreviousPoint(point);
            path.add(leftPoint);
            findPath(leftPoint);
        }
        if(point.hasTop() && !point.getPreviousPoint().equals(point.getTopPoint()) && !pathContainPoint(point.getTopPoint())){
            Point topPoint = point.getTopPoint();
            topPoint.setPreviousPoint(point);
            path.add(topPoint);
            findPath(topPoint);
        }
        if(point.hasRight() && !point.getPreviousPoint().equals(point.getRightPoint()) && !pathContainPoint(point.getRightPoint())){
            Point rightPoint = point.getRightPoint();
            rightPoint.setPreviousPoint(point);
            path.add(rightPoint);
            findPath(rightPoint);
        }
        if(point.hasBottom() && !point.getPreviousPoint().equals(point.getBottomPoint()) && !pathContainPoint(point.getBottomPoint())){
            Point bottomPoint = point.getBottomPoint();
            bottomPoint.setPreviousPoint(point);
            path.add(bottomPoint);
            findPath(bottomPoint);
        }
        if(point.hasNextLevel() && !point.getPreviousPoint().equals(point.getNextLevelPoint()) && !pathContainPoint(point.getNextLevelPoint())){
            Point nextLevelPoint = point.getNextLevelPoint();
            nextLevelPoint.setPreviousPoint(point);
            path.add(nextLevelPoint);
            findPath(nextLevelPoint);
        }
    }
    // This method is necessary to avoid infinite recursion.
    private static boolean pathContainPoint(Point point){
        for (Point pointOfPath : path){
            if(pointOfPath.equals(point)){
                return true;
            }
        }
        return false;
    }
    // Add current path to pathes.
    private static void addPathToPathes(List<Point> addPath){
        if(pathes.size() == 0){
            pathes.add(addPath);
            return;
        }
        Point firstPoint = path.get(0);
        List<Point> lastPath = pathes.get(pathes.size() - 1);
        for (int i  = lastPath.size() - 1; i >= 0; i--){
            if(firstPoint.getLeftPoint().equals(lastPath.get(i))){
                pathes.add(createNewPath(i, lastPath));
                return;
            }
            if(firstPoint.getTopPoint().equals(lastPath.get(i))){
                pathes.add(createNewPath(i, lastPath));
                return;
            }
            if(firstPoint.getRightPoint().equals(lastPath.get(i))){
                pathes.add(createNewPath(i, lastPath));
                return;
            }
            if(firstPoint.getBottomPoint().equals(lastPath.get(i))){
                pathes.add(createNewPath(i, lastPath));
                return;
            }
        }
    }
    // Create List<Point> which have all point from '1' to '2'
    private static List<Point> createNewPath(int index, List<Point> lastPath){
        List<Point> newPath = new LinkedList<>();
        for (int i = 0; i < index - 1; i++){
            newPath.add(lastPath.get(i));
        }
        for (Point addPoint: path) {
            newPath.add(addPoint);
        }
        return newPath;
    }
}

class Point{

    private int x;
    private int y;
    private int currentLevel;
    private List<boolean[][]> levels;
    private Point previousPoint;

    public Point(List<boolean[][]> levels, int x, int y, int currentLevel){
        this.levels = levels;
        this.x = x;
        this.y = y;
        this.currentLevel = currentLevel;
    }

    public boolean hasLeft(){
        if(y == 0){
            return false;
        }
        return levels.get(currentLevel)[x][y - 1];
    }

    public boolean hasRight(){
        if(y == (levels.get(0)[0].length - 1)){
            return false;
        }
        return levels.get(currentLevel)[x][y + 1];
    }

    public boolean hasTop(){
        if(x == 0){
            return false;
        }
        return levels.get(currentLevel)[x - 1][y];
    }

    public boolean hasBottom(){
        if(x == levels.get(0).length - 1){
            return false;
        }
        return levels.get(currentLevel)[x + 1][y];
    }

    public boolean hasNextLevel(){
        if(this.currentLevel == levels.size() - 1){
            return false;
        }
        return levels.get(this.currentLevel + 1)[x][y];
    }

    public Point getLeftPoint(){
        return new Point(levels, x, y - 1, currentLevel);
    }

    public Point getRightPoint(){
        return new Point(levels, x, y + 1, currentLevel);
    }

    public Point getTopPoint(){
        return new Point(levels, x - 1, y, currentLevel);
    }

    public Point getBottomPoint(){
        return new Point(levels, x + 1, y, currentLevel);
    }

    public Point getNextLevelPoint(){
        return new Point(levels, x, y, currentLevel + 1);
    }

    public boolean equals(Point point){
        return this.currentLevel == point.currentLevel && this.x == point.x && this.y == point.y;
    }

    public void setPreviousPoint(Point previousPoint){
        this.previousPoint = previousPoint;
    }

    public Point getPreviousPoint(){
        return this.previousPoint;
    }

    public boolean isPrincessPoint(int x, int y){
        return currentLevel == levels.size() - 1 && this.x == x && this.y == y;
    }

    public String toString(){
        return "\nLevel: " + currentLevel + ", x = " + x + ", y = " + y;
    }
}
