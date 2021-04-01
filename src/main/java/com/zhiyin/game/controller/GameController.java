package com.zhiyin.game.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyin.game.Service.GameService;
import com.zhiyin.game.bean.Cell;
import com.zhiyin.game.utils.BuildMapUtils;
import com.zhiyin.game.utils.CellTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CellTools cellTools;

    /**
     *  获取地图数据11111
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMapInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String getMapInfo(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        cellTools.setsize(10);
        cellTools.createCells();
        Cell[][] cells = BuildMapUtils.build(10, 10);
        cellTools.createCells(cells);
        Cell[][] bank = cellTools.createBank();
        Map<String, Integer> thief = new HashMap<>();
        Map<String, Integer> police = new HashMap<>();
        do {
            thief = cellTools.getThief(bank);
            police = cellTools.getPolice(bank);
        }while (thief.get("x").equals(police.get("x")));
        Map resultMap = new HashMap();
        resultMap.put("mapInfo",bank);
        resultMap.put("thief",thief);
        resultMap.put("police",police);
        return JSONObject.toJSONString(resultMap);
    }
}
