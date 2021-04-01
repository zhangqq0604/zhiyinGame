package com.zhiyin.game.utils;


import com.zhiyin.game.bean.Cell;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Ryan
 */
@Component
public class CellTools {

    private Cell[]cells[] ;

    private int size;

    public int getsize() {
        return size;
    }

    public void setsize(int size) {
        this.size = size;
    }

    public Cell[][] createCells(){
        cells = new Cell[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Cell cell = new Cell();
                cells[i][j] = cell;
            }
        }
        return cells;
    }


    public Cell[][] createCells(Cell[][] map){
        this.cells = map;
        return map;
    }

    public Cell[][] randomBank1(){
        Set<Integer> infeed = new TreeSet<>();
        List<Integer> endwiseList = new ArrayList<>();
        List<Integer>  markList = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            int random = (int)(Math.random()*this.size)%this.size;
            if (infeed.size() == this.size){
                break;
            }else {
                infeed.add(random);
            }
        }
        for (int i = 0; i < infeed.size(); i++) {
            int randomEndWise = (int)(Math.random()*this.size)%this.size;
            int randomMark = (int)(Math.random()*this.size)%this.size;
            endwiseList.add(randomEndWise);
            markList.add(randomMark);
        }

        List infeedList = Arrays.asList(infeed.toArray());

        for (int i = 0; i < markList.size(); i++) {
            cells[(int)infeedList.get(i)][endwiseList.get(i)].setBank(true);
            cells[(int)infeedList.get(i)][endwiseList.get(i)].setMark(markList.get(i) + 1);
        }
        return cells;
    }

    public Cell[][] randomBank2(){
        Set<Integer>  endwiseSet= new TreeSet<>();
        List<Integer> infeedList = new ArrayList<>();
        List<Integer>  markList = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            int random = (int)(Math.random()*this.size)%this.size;
            if (endwiseSet.size() == this.size){
                break;
            }else {
                endwiseSet.add(random);
            }
        }
        for (int i = 0; i < endwiseSet.size(); i++) {
            int randomInfeed = (int)(Math.random()*this.size)%this.size;
            int randomMark = (int)(Math.random()*this.size)%this.size;
            infeedList.add(randomInfeed);
            markList.add(randomMark);
        }

        List endwiseList = Arrays.asList(endwiseSet.toArray());

        for (int i = 0; i < markList.size(); i++) {
            cells[infeedList.get(i)][(int)endwiseList.get(i)].setBank(true);
            cells[infeedList.get(i)][(int)endwiseList.get(i)].setMark(markList.get(i));
        }
        return cells;
    }

    public Cell[][] createBank(){
        int random = 1+ (int)(Math.random()*10);
        if (random > 5){
            randomBank1();
        }else {
            randomBank2();
        }
        return cells;
    }
    
    public Cell[][] createWall(){
        return cells;
    }

    public Map<String,Integer> getThief(Cell[][] cells){
        Map<String,Integer> thief = new HashMap<>();
        int x = (int)(Math.random()*10);
        int y = (int)(Math.random()*10);
        Cell cells1 = cells[x][y];
        if (!cells1.isBank()){
            thief.put("x",x);
            thief.put("y",y);
            return thief;
        }else {
            return getThief(cells);
        }
    }

    public Map<String,Integer> getPolice(Cell[][] cells){
        Map<String,Integer> police = new HashMap<>();
        int x = (int)(Math.random()*10);
        int y = (int)(Math.random()*10);
        Cell cells1 = cells[x][y];
        if (!cells1.isBank()){
            police.put("x",x);
            police.put("y",y);
            return police;
        }else {
            return getPolice(cells);
        }
    }


    public static void main(String[] args) {

    }
}
