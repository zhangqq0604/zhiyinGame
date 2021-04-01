package com.zhiyin.game.utils;

import com.zhiyin.game.bean.Cell;

import java.util.*;

public class BuildMapUtils {

    //数值越高，墙越多
    private static float flexible = 0.7f;

    public static void setFlexible(float flexible) {
        BuildMapUtils.flexible = flexible;
    }

    private static Cell getCell(Cell[][] map, int x, int y){
        return map[y][x];
    }

    public static Cell[][] build(int width, int height){
        Random random = new Random();
        Cell[][] map = new Cell[height][width];
        for(int i=0;i<height;i++){
            Cell[] column = new Cell[width];
            for(int j=0;j<width;j++){
                Cell cell = new Cell();
                column[j] = cell;
            }
            map[i] = column;
        }
        int posX = 0;
        int posY = 0;
        getCell(map, posX, posY).setVisited(true);
        Stack<Vector2> stack = new Stack();
        stack.push(new Vector2(posX,posY));
        while (stack.size()>0){
            List<String> moveDir = new ArrayList<>();
            List<String> removeDir = new ArrayList<>();
            if(posX>0 && !getCell(map, posX-1, posY).isVisited())
                moveDir.add("L");
            if(posX<height-1 && !getCell(map, posX+1, posY).isVisited())
                moveDir.add("R");
            if(posY<width-1 && !getCell(map, posX, posY+1).isVisited())
                moveDir.add("D");
            if(posY>0 && !getCell(map, posX, posY-1).isVisited())
                moveDir.add("U");

            if(posX>0)
                removeDir.add("L");
            if(posX<width-1)
                removeDir.add("R");
            if(posY<height-1)
                removeDir.add("D");
            if(posY>0)
                removeDir.add("U");

            if(moveDir.size()>0){
                String dir = moveDir.get(random.nextInt(moveDir.size()));
                stack.push(new Vector2(posX,posY));
                switch (dir){
                    case "L":
                        getCell(map, posX, posY).setLeftWall(false);
                        getCell(map, posX, posY).setVisited(true);
                        getCell(map, posX-1, posY).setRightWall(false);
                        posX -= 1;
                        break;
                    case "R":
                        getCell(map, posX, posY).setRightWall(false);
                        getCell(map, posX, posY).setVisited(true);
                        getCell(map, posX+1, posY).setLeftWall(false);
                        posX += 1;
                        break;
                    case "D":
                        getCell(map, posX, posY).setBottomWall(false);
                        getCell(map, posX, posY).setVisited(true);
                        getCell(map, posX, posY+1).setTopWall(false);
                        posY += 1;
                        break;
                    case "U":
                        getCell(map, posX, posY).setTopWall(false);
                        getCell(map, posX, posY).setVisited(true);
                        getCell(map, posX, posY-1).setBottomWall(false);
                        posY -= 1;
                        break;
                }
            }else{
                getCell(map, posX, posY).setVisited(true);
                Vector2 lastPos = stack.pop();
                if(Math.random()>flexible){
                    for(int i=0;i<random.nextInt(2)+1;i++){
                        switch (removeDir.get(random.nextInt(removeDir.size()))){
                            case "L":
                                getCell(map, posX, posY).setLeftWall(false);
                                getCell(map, posX-1, posY).setRightWall(false);
                                break;
                            case "R":
                                getCell(map, posX, posY).setRightWall(false);
                                getCell(map, posX+1, posY).setLeftWall(false);
                                break;
                            case "D":
                                getCell(map, posX, posY).setBottomWall(false);
                                getCell(map, posX, posY+1).setTopWall(false);
                                break;
                            case "U":
                                getCell(map, posX, posY).setTopWall(false);
                                getCell(map, posX, posY-1).setBottomWall(false);
                                break;
                        }
                    }

                }

                posX = lastPos.getX();
                posY = lastPos.getY();
            }
        }
        return map;
    }

    public static class Vector2{
        private int x;

        private int y;

        public Vector2(int x,int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector2 vector2 = (Vector2) o;
            return x == vector2.x &&
                    y == vector2.y;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    public static String printMap(Cell[][] map){
        StringBuilder sb = new StringBuilder();
        sb.append("<div><table>");
        for(int i=0;i<map[0].length;i++){
            sb.append("<tr>");
            for(int j=0; j<map.length;j++){
                String style = "";
                if(map[i][j].isTopWall()){
                    style += "border-top: 1px solid black;";
                }
                if(map[i][j].isBottomWall()){
                    style += "border-bottom: 1px solid black;";
                }
                if(map[i][j].isLeftWall()){
                    style += "border-left: 1px solid black;";
                }
                if(map[i][j].isRightWall()){
                    style += "border-right: 1px solid black;";
                }
                sb.append("<td style='width: 100px;height:100px;"+style+"'></td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table></div>");
        return sb.toString();
    }

    public static void main(String[] args) {
        Cell[][] build = build(10, 10);
        System.out.println(printMap(build));
    }
}
