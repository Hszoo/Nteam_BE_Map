package com.Nteam.backend.map.controller;

import com.Nteam.backend.map.dto.MapDto;
import com.Nteam.backend.map.service.MapService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    MapService mapService;
    @GetMapping("/{address}")
    public MapDto convertToCoordinate(@PathVariable String address) throws ParseException {
        MapDto mapDto = new MapDto();
        String json = mapService.getKakaoApiFromAddress(address);
        MapDto pos = mapService.changeToJSON(json);
        mapDto.setAddress_name(pos.getAddress_name());
        mapDto.setLongitude(pos.getLongitude());
        mapDto.setLatitude(pos.getLatitude());

        return mapDto;
    }
}
