package com.pi2star.tirecare.controller;

import com.pi2star.tirecare.dao.GPSRepository;
import com.pi2star.tirecare.dao.MPURepository;
import com.pi2star.tirecare.dao.TireRepository;
import com.pi2star.tirecare.entity.GPSMessage;
import com.pi2star.tirecare.entity.MPUMessage;
import com.pi2star.tirecare.entity.TireMessage;
import com.pi2star.tirecare.utils.Statices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;


@Controller
@RequestMapping(path="/charts")
public class ChartsController {

    @Autowired
    private GPSRepository mGPSRepository;

    @Autowired
    private TireRepository mTireRepository;

    @Autowired
    private MPURepository mMpuRepository;

    @RequestMapping("/test")
    public String showTest(Model model) {


        return "test";

    }

    @RequestMapping("/global")
    public String showGlobalState(Model model) {

        int count = mGPSRepository.countDistinctByBoxId();

        long timeSec = Statices.getTimeUTC();

        model.addAttribute("serverCount", count);

        return "global";

    }

    @GetMapping("/ajaxPreGPSLocation")
    public @ResponseBody ArrayList<GPSMessage> gpsPreFetch() {

        //ArrayList<GPSMessage> list = mGPSRepository.findAllByGroup();

        ArrayList<GPSMessage> list = new ArrayList<>();

        int[] boxIds = mGPSRepository.findDistinctBoxIds();

        if(boxIds != null) {

            for(int boxId : boxIds) {

                GPSMessage gm = mGPSRepository.findLatestGpsMsg(boxId);

                list.add(gm);

            }

        }

        return list;

    }

    @GetMapping("/ajaxGPSLocation")
    public @ResponseBody ArrayList<GPSMessage> gpsUpdateFetch() {

        long timestamp = Statices.getTimeUTC() - 180;

        ArrayList<GPSMessage> list = mGPSRepository.findByTimestamp(timestamp);

        return list;

    }

    @GetMapping("/ajaxGPSLocationSingle")
    public @ResponseBody GPSMessage gpsUpdateSingalFetch(@RequestParam("id")int boxId) {

        long timestamp = Statices.getTimeUTC() - 180;

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

    @GetMapping("/ajaxOrigin")
    public @ResponseBody HashMap<String, ArrayList> originFetch() {

        ArrayList<TireMessage> tlist = mTireRepository.findTopTireMessage();
        ArrayList<GPSMessage> glist = mGPSRepository.findTopGpsMessage();
        ArrayList<MPUMessage> mlist = mMpuRepository.findTopMpuMessage();

        HashMap<String, ArrayList> map = new HashMap<>();

        map.put("Tire", tlist);
        map.put("gps", glist);
        map.put("mpu", mlist);

        return map;


    }

    @GetMapping("/Origin")
    public String showOrigin(Model model, HttpSession session) {

        ArrayList<TireMessage> tlist = mTireRepository.findTopTireMessage();
        ArrayList<GPSMessage> glist = mGPSRepository.findTopGpsMessage();
        ArrayList<MPUMessage> mlist = mMpuRepository.findTopMpuMessage();

        HashMap<String, ArrayList> map = new HashMap<>();

        map.put("tire", tlist == null ? new ArrayList<TireMessage>() : tlist);
        map.put("gps", glist == null ? new ArrayList<GPSMessage>() : glist);
        map.put("mpu", mlist == null ? new ArrayList<MPUMessage>() : mlist);

        model.addAttribute("top100", map);

        Object obj = session.getAttribute("tab");

        int idx = 0;

        if(obj != null) idx = (int) obj;

        model.addAttribute("selectedTab", idx);

        return "origin";

    }

    @GetMapping("/ajaxSendSelected")
    public @ResponseBody int saveTagIndex(@RequestParam("index")int index, HttpSession session) {

        session.setAttribute("tab", index);

        int idx = (int) session.getAttribute("tab");

        return idx;

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
    public @ResponseBody TireMessage fetchTireMessage(@RequestParam("id")int boxId, @RequestParam("pl")int place) {

        long timestamp = Statices.getTimeUTC() - 180;

        System.out.println("query tire info: " + boxId + " -- " + timestamp + "--" + place);

        TireMessage tireMessage = mTireRepository.findTireMessageByBoxIdAndTimestampAndPlace(boxId, timestamp, place);

        if(tireMessage == null) {
            tireMessage = new TireMessage();
            tireMessage.setTimestamp(timestamp);
            tireMessage.setPressure(-1);
            tireMessage.setTemperature(-274);
        }

        System.out.println(tireMessage);

        return tireMessage;

    }

    @GetMapping("/ajaxTimeslice")
    public @ResponseBody HashMap<String, HashMap<Long, Object>> fetchTireMessageWithTimeslice(@RequestParam("from")long timeFrom, @RequestParam("to")int timeTo, @RequestParam("index")int index) {

        HashMap<String, HashMap<Long, Object>> map = new HashMap<>();

        HashMap<Long, Object> innerTireMap = new HashMap<>();

        ArrayList<TireMessage> list = mTireRepository.findBetweenTimeslice(index, timeFrom, timeTo);

        if(list != null) {

            for(TireMessage tm : list) {

                innerTireMap.put(tm.getTimestamp(), tm);

            }

        }

        map.put("tire", innerTireMap);

        return map;
    }


}
