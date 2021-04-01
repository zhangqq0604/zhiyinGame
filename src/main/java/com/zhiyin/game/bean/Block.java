package com.zhiyin.game.bean;

import java.io.Serializable;

public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean topWall;
    private boolean bottomWall;
    private boolean leftWall;
    private boolean rightWall;
    //分数 非零代表银行
    private int score;

    private int code;

    private int wallCount = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Block(int code) {
        this.code = code;
    }

    public boolean isTopWall() {
        return topWall;
    }

    public void setTopWall(boolean topWall) {
        if (topWall && !this.topWall) {
            wallCount += 1;
        }
        if (!topWall && this.topWall) {
            wallCount -= 1;
        }
        this.topWall = topWall;
    }

    public boolean isBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        if (bottomWall && !this.bottomWall) {
            wallCount += 1;
        }
        if (!bottomWall && this.bottomWall) {
            wallCount -= 1;
        }
        this.bottomWall = bottomWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        if (leftWall && !this.leftWall) {
            wallCount += 1;
        }
        if (!leftWall && this.leftWall) {
            wallCount -= 1;
        }
        this.leftWall = leftWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        if (rightWall && !this.rightWall) {
            wallCount += 1;
        }
        if (!rightWall && this.rightWall) {
            wallCount -= 1;
        }
        this.rightWall = rightWall;
    }

    public int getScore() {
        return score;
    }

    public int getWallCount() {
        return wallCount;
    }

    public void setWallCount(int wallCount) {
        this.wallCount = wallCount;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
