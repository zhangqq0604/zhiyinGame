package com.zhiyin.game.Service;

import com.zhiyin.game.bean.GameMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameService {

    public Map getMapInfo() {
        GameMap map = new GameMap();
        Map retMap = new HashMap<>();
        retMap.put("mapInfo", map.getMapInfo());
        retMap.put("policePos", map.getPolicePos());
        retMap.put("thiefPos", map.getThiefPos());
        return retMap;
    }
}
