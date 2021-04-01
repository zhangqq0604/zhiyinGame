package com.zhiyin.game.bean;

import java.util.*;

public class GameMap {
    static final int DEFAULT_MAP_WIDTH = 10;
    static final int DEFAULT_BANK_COUNT = 3;
    static final int DEFAULT_LEVEL = 8; //墙的最大生成数, 等级/10*保证连通的最大墙数， 保证连通的最大墙数 = 2*mapWidth*(mapWidth-1) - (mapWidth*mapWidth-1) = (mapWidth-1)^2
    int mapWidth;
    int bankCount;
    int level;
    Block[] blocks;
    int policePos;
    int thiefPos;

    public GameMap(){
        this(DEFAULT_MAP_WIDTH);
    }

    public GameMap(int mapWidth){
        this(mapWidth, DEFAULT_BANK_COUNT);
    }

    public GameMap(int mapWidth, int bankCount){
        this(mapWidth, bankCount, DEFAULT_LEVEL);
    }

    public GameMap(int mapWidth, int bankCount, int level){
        this.mapWidth = mapWidth;
        this.bankCount = bankCount;
        this.level = level;
        blocks = new Block[mapWidth*mapWidth];
        createMap();
    }

    public Block[][] getMapInfo(){

        Block[][] map = new Block[mapWidth][mapWidth];
        for(int i = 0;i<mapWidth;i++){
            for(int j = 0;j<mapWidth;j++){
                map[i][j]= blocks[i*mapWidth+j];
            }
        }
        return map;
    }

    private void createMap(){
        int maxWall = Math.round(level*(mapWidth-1)*(mapWidth-1)/10) ;
        ArrayList<int[]> roadList = new ArrayList<>();
        ArrayList<int[]> wallList = new ArrayList<>();
        for (int i = 0; i < mapWidth * mapWidth; i++) {
            blocks[i] = new Block(i);
            if(i<mapWidth){
                blocks[i].setTopWall(true);
            }
            if(i%10 == 0){
                blocks[i].setLeftWall(true);
            }
            if((i+1)%10 == 0){
                blocks[i].setRightWall(true);
            }
            if(i>=(mapWidth-1)*mapWidth){
                blocks[i].setBottomWall(true);
            }
        }
        List<Integer>[] graph= new ArrayList[mapWidth*mapWidth];
        initGraph(graph, roadList);
        Collections.shuffle(roadList);
        Iterator<int[]> roadIter = roadList.iterator();
        while(roadIter.hasNext()){
            int[] connect = roadIter.next();
            if(checkNodeConnect(graph, connect) && wallList.size() < maxWall){
                roadIter.remove();
                generateWall(connect);
                graph[connect[0]].remove(Integer.valueOf(connect[1]));
                graph[connect[1]].remove(Integer.valueOf(connect[0]));
                wallList.add(connect);
            }
        }
        generateBank();
        generateThief();
        generatePolice();
//        wallList.forEach(e-> System.out.println(e[0] + "-" +e[1]));
//        System.out.println(wallList.size());
//        System.out.println(roadList.size());
    }
    private void initGraph(List<Integer>[] graph,ArrayList<int[]> roadList){
        for (int i = 0; i < mapWidth * mapWidth; i++) {
            if (i + 1 < (i / mapWidth + 1) * mapWidth) {
                roadList.add(new int[]{i, i + 1});
                initGraph(graph, i,i + 1);

            }
            if (i + mapWidth < mapWidth * mapWidth) {
                roadList.add(new int[]{i, i + mapWidth});
                initGraph(graph, i,i + mapWidth);
            }
        }
    }

    private void initGraph(List<Integer>[] graph, int code0, int code1){
        if (graph[code0]==null) {
            graph[code0]=new ArrayList<>();
        }
        graph[code0].add(code1);
        if (graph[code1]==null) {
            graph[code1]=new ArrayList<>();
        }
        graph[code1].add(code0);
    }

    private void generateWall(int[]connect){
        if(connect[0] == connect[1] - 1){
            blocks[connect[0]].setRightWall(true);
            blocks[connect[1]].setLeftWall(true);
        }
        if(connect[0] == connect[1] + mapWidth){
            blocks[connect[0]].setBottomWall(true);
            blocks[connect[1]].setTopWall(true);
        }

    }

    private boolean checkNodeConnect(List<Integer>[] graph, int[] connect){
        LinkedList<Integer> queue = new LinkedList<> ();
        queue.offer(connect[0]);
        boolean[] visited = new boolean [graph.length];
        visited[connect[0]]=true;
        while(!queue.isEmpty()){
            int size=queue.size();
            for(int i = 0; i < size; i++){
                int node = queue.poll();
                List<Integer> nextList = graph[node];
                if (nextList==null){
                    continue;
                }
                for (Integer next : nextList){
                    if(node == connect[0] && next == connect[1]){
                        continue;
                    }
                    if (next==connect[1]){
                        return true;
                    }
                    if (visited[next]){
                        continue;
                    }
                    visited[next] =true;
                    queue.add(next);
                }
            }
        }
        return false;
    }

    private void generateBank(){
        List<Block> tempList = new ArrayList(Arrays.asList(blocks));
        tempList.sort((o1, o2)->o2.getWallCount() - o1.getWallCount());
        tempList.subList(0, bankCount).forEach(e -> blocks[e.getCode()].setScore(e.getWallCount()));
    }

    private void generateThief(){
        thiefPos = (int)(Math.random()*(mapWidth*mapWidth));
        while(blocks[thiefPos].getScore() != 0){
            thiefPos = (int)(Math.random()*(mapWidth*mapWidth));
        }
    }

    private void generatePolice(){
        policePos = (int)(Math.random()*(mapWidth*mapWidth));
        while(policePos == thiefPos){
            policePos = (int)(Math.random()*(mapWidth*mapWidth));
        }
    }

    public int[] getPolicePos() {
        return new int[]{policePos/mapWidth, policePos%mapWidth};
    }

    public int[] getThiefPos() {
        return new int[]{thiefPos/mapWidth, thiefPos%mapWidth};
    }
}
