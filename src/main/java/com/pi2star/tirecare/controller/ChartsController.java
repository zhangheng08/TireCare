package com.pi2star.tirecare.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.pi2star.tirecare.dao.GPSRepository;
import com.pi2star.tirecare.dao.TireRepository;
import com.pi2star.tirecare.entity.GPSMessage;
import com.pi2star.tirecare.entity.TireMessage;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;


@Controller
@RequestMapping(path="/charts")
public class ChartsController {

    @Autowired
    private GPSRepository mGPSRepository;

    @Autowired
    private TireRepository mTireRepository;

    @RequestMapping("/test")
    public String showTest(Model model) {


        return "test";

    }

    @RequestMapping("/global")
    public String showGlobalState(Model model) {

        int count = mGPSRepository.countDistinctByBoxId();

        model.addAttribute("serverCount", count);

        return "global";

    }

    @GetMapping("/ajaxPreGPSLocation")
    public @ResponseBody ArrayList<GPSMessage> gpsPreFetch() {

        ArrayList<GPSMessage> list = mGPSRepository.findAllByGroup();

        return list;

    }

    @GetMapping("/ajaxGPSLocation")
    public @ResponseBody ArrayList<GPSMessage> gpsUpdateFetch(@RequestParam("tmsp")long timestamp) {


        ArrayList<GPSMessage> list = mGPSRepository.findByTimestamp(timestamp);

        return list;

    }

    @GetMapping("/ajaxGPSLocationSingle")
    public @ResponseBody GPSMessage gpsUpdateSingalFetch(@RequestParam("id")int boxId, @RequestParam("tmsp")long timestamp) {

        GPSMessage gpsMessage = mGPSRepository.findByBoxIdAndTimestamp(boxId, timestamp);

        if(gpsMessage == null) {

/*            gpsMessage = new GPSMessage();
            gpsMessage.setSpeed(100 + (int)(Math.random() * 35));*/

            System.out.println("null");

        } else {

            System.out.println(gpsMessage);

        }



        return gpsMessage;

    }



    @RequestMapping("/details")
    public String showBoxDetail(@RequestParam("id")int boxId, @RequestParam("lg")float longitude, @RequestParam("la")float latitude,  Model model) {

        final LocalDateTime ldt = LocalDateTime.of(2018, 1, 1, 0, 0, 0);

        long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

        model.addAttribute("boxId", boxId);
        model.addAttribute("longitude", longitude);
        model.addAttribute("latitude", latitude);

        return "details";

    }

    @PostMapping("/ajaxTirePast3min")
    public @ResponseBody ArrayList<TireMessage> fetchTirePost3min(@RequestParam("id")int boxId, @RequestParam("tp")long timestamp, @RequestParam("pl")int place) {

        ArrayList<TireMessage> list = mTireRepository.findTireMessagePast3min(boxId, place, timestamp);

        return list;

    }

    @PostMapping("/ajaxTireInfo")
    public @ResponseBody TireMessage fetchTireMessage(@RequestParam("id")int boxId, @RequestParam("tp")long timestamp, @RequestParam("pl")int place) {

        System.out.println("query tire info: " + boxId + " -- " + timestamp + "--" + place);

        TireMessage tireMessage = mTireRepository.findTireMessageByBoxIdAndTimestampAndPlace(boxId, timestamp, place);

        if(tireMessage == null) tireMessage = new TireMessage();

        System.out.println(tireMessage);

        return tireMessage;

    }


}
