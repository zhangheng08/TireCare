package com.pi2star.tirecare.controller;

import com.pi2star.tirecare.controller.decode.CodeProcessor;
import com.pi2star.tirecare.dao.*;
import com.pi2star.tirecare.entity.*;
import com.sun.media.jfxmedia.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(path="/trml")
public class CollectController {

    @Value("${elasticsearch.clustername}")
    private String clustername;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    private CodeProcessor mDataManager;

    @Autowired
    private MPURepository mpuRepository;

    @Autowired
    private GPSRepository gpsRepository;

    @Autowired
    private TireRepository tirRepository;

    @Autowired
    private BytesInHexRepository bytesInHexRepository;

    @GetMapping("/gpsFakeSave")
    public @ResponseBody String gpsFakeSave(HttpServletRequest request) {

        String ret = "";

        LocalDateTime ldt = LocalDateTime.of(2018, 1, 1, 0, 0, 0);

        float[][] pos = new float[100][2];

        for(int i = 0; i < 100 ; i ++) {

            pos[i][0] = 116.448265f + (i % 2 == 0 ? -1 : 1) * (float)(Math.random());
            pos[i][1] = 39.949146f + (i % 2 == 0 ? -1 : 1) * (float)(Math.random());

        }

        int _id = 0;

        for(int k = 0; k < pos.length; k ++) {

            long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

            //ArrayList<GPSMessage> list = new ArrayList<>();

            final int boxId = k;

            for(int i = 0; i < 300; i ++) {

                GPSMessage gpsMsg = new GPSMessage();

                gpsMsg.setBoxId(k);
                gpsMsg.setCharacteristic(0);
                gpsMsg.setEwhemi('E');
                gpsMsg.setGpssta('A');

                gpsMsg.setLongitude(pos[k][0]);
                gpsMsg.setLatitude(pos[k][1]);

                gpsMsg.setNshemi('N');
                gpsMsg.setSpeed(35 + (int)(Math.random() * 200));
                gpsMsg.setTimestamp(timestamp);

                LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                LocalTime lt = LocalTime.of(ldt2.getHour() , ldt2.getMinute() , ldt2.getSecond());
                gpsMsg.setUtc_time_day(lt.toString());
                gpsMsg.setUtc_time_year("2018-01-01");

                //list.add(gpsMsg);

                timestamp ++;

                elasticSaveGPS(gpsMsg, _id ++);

            }

            //list.parallelStream().forEach(g -> {g.setBoxId(boxId + 1); gpsRepository.save(g);});

        }

        return "all data saved success";

    }

    @GetMapping("/tireFakeSave")
    public @ResponseBody String tireFakeSave(HttpServletRequest request) {

        final LocalDateTime ldt = LocalDateTime.of(2018, 1, 1, 0, 0, 0);

        int _id = 0;

        for(int k = 0; k < 100; k ++) {

            long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

            //ArrayList<TireMessage> list = new ArrayList<>();

            final int boxId = k;

            for(int i = 0; i < 300; i ++) {

                TireMessage tireMessage = new TireMessage();

                tireMessage.setBoxId(boxId);
                tireMessage.setCharacteristic(0);
                tireMessage.setIdh(0x1111);
                tireMessage.setIdl(0x1100);
                tireMessage.setPlace(0);
                tireMessage.setPressure(100f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                tireMessage.setTemperature(4f + (float)(Math.random() * 10) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                tireMessage.setAccelerate(1f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                tireMessage.setVoltage(2f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                tireMessage.setTimestamp(timestamp);

                LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                LocalTime lt = LocalTime.of(ldt2.getHour() , ldt2.getMinute() , ldt2.getSecond());
                tireMessage.setUtc_time_day(lt.toString());
                tireMessage.setUtc_time_year("2018-01-01");

                //list.add(tireMessage);

                timestamp ++;

                elasticSaveTire(tireMessage, _id ++);

            }

            //list.parallelStream().forEach(t -> {t.setBoxId(boxId + 1); tirRepository.save(t);});

        }

        for(int k = 0; k < 100; k ++) {

                long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

                //ArrayList<TireMessage> list = new ArrayList<>();

                final int boxId = k;

                for(int i = 0; i < 300; i ++) {

                    TireMessage tireMessage = new TireMessage();

                    tireMessage.setBoxId(boxId);
                    tireMessage.setCharacteristic(0);
                    tireMessage.setIdh(0x1111);
                    tireMessage.setIdl(0x1101);
                    tireMessage.setPlace(1);
                    tireMessage.setPressure(100f + (float)(Math.random() * 2) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTemperature(4f + (float)(Math.random() * 10) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setAccelerate(1f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setVoltage(2f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTimestamp(timestamp);

                    LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                    LocalTime lt = LocalTime.of(ldt2.getHour() , ldt2.getMinute() , ldt2.getSecond());
                    tireMessage.setUtc_time_day(lt.toString());
                    tireMessage.setUtc_time_year("2018-01-01");

                    //list.add(tireMessage);

                    timestamp ++;

                    elasticSaveTire(tireMessage, _id ++);

                }

                //list.parallelStream().forEach(t -> {t.setBoxId(boxId + 1); tirRepository.save(t);});

            }

            /*for(int k = 0; k < 100; k ++) {

                long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

                //ArrayList<TireMessage> list = new ArrayList<>();

                final int boxId = k;

                for(int i = 0; i < 300; i ++) {

                    TireMessage tireMessage = new TireMessage();

                    tireMessage.setBoxId(boxId);
                    tireMessage.setCharacteristic(0);
                    tireMessage.setIdh(0x1111);
                    tireMessage.setIdl(0x1102);
                    tireMessage.setPlace(2);
                    tireMessage.setPressure(100f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTemperature(4f + (float)(Math.random() * 10) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setAccelerate(1f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setVoltage(2f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTimestamp(timestamp);

                    LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                    LocalTime lt = LocalTime.of(ldt2.getHour() , ldt2.getMinute() , ldt2.getSecond());
                    tireMessage.setUtc_time_day(lt.toString());
                    tireMessage.setUtc_time_year("2018-01-01");

                    //list.add(tireMessage);

                    timestamp ++;

                    elasticSaveTire(tireMessage, _id ++);

                }

                //list.parallelStream().forEach(t -> {t.setBoxId(boxId + 1); tirRepository.save(t);});

            }

            for(int k = 0; k < 100; k ++) {

                long timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

                //ArrayList<TireMessage> list = new ArrayList<>();

                final int boxId = k;

                for(int i = 0; i < 300; i ++) {

                    TireMessage tireMessage = new TireMessage();

                    tireMessage.setBoxId(boxId);
                    tireMessage.setCharacteristic(0);
                    tireMessage.setIdh(0x1111);
                    tireMessage.setIdl(0x1103);
                    tireMessage.setPlace(3);
                    tireMessage.setPressure(100f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTemperature(4f + (float)(Math.random() * 10) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setAccelerate(1f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setVoltage(2f + (float)(Math.random()) * (((int)(Math.random() * 100)) > 50 ? -1 : 1));
                    tireMessage.setTimestamp(timestamp);

                    LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                    LocalTime lt = LocalTime.of(ldt2.getHour() , ldt2.getMinute() , ldt2.getSecond());
                    tireMessage.setUtc_time_day(lt.toString());
                    tireMessage.setUtc_time_year("2018-01-01");

                    //list.add(tireMessage);

                    timestamp ++;

                    elasticSaveTire(tireMessage, _id ++);

                }

            //list.parallelStream().forEach(t -> {t.setBoxId(boxId + 1); tirRepository.save(t);});

        }*/

        return "ok";

    }

    @PostMapping("/codeTrans")
    public @ResponseBody byte handleUploadData(HttpServletRequest request) {

        InputStream byteIs = null;
        BufferedInputStream bufferedIs = null;
        int length = 0;

        try {

            CodeProcessor processor = new CodeProcessor();

            byteIs = request.getInputStream();

            bufferedIs = new BufferedInputStream(byteIs, 1024);

            final ArrayList<Byte> dataByteList = new ArrayList<Byte>();

            byte[] buffer = new byte[1024];

            int len = 0;

            int idx = 0;

            while ((len = bufferedIs.read(buffer)) != -1) {

                for(int i = 0; i < len; i ++) {

                    dataByteList.add(buffer[i]);

                }

            }

            //TODO
            byte[] allB = new byte[dataByteList.size()];

            for(int i = 0; i < dataByteList.size(); i ++) {

                allB[i] = dataByteList.get(i);

            }

            String bytesInHexStr = CodeProcessor.bytesToHexString(allB);
            System.out.println(bytesInHexStr);

            processor.mOriginBytes = dataByteList;

            System.out.println("all byte contains: " + dataByteList.size() + " | bytes: ..." /*+ bytesToHexString(tireData).substring(0, 10240)*/);

            final int boxId = dataByteList.get(CodeProcessor.BOX_ID_IDX);

            final byte charact = dataByteList.get(CodeProcessor.CHARACTERISTIC_IDX);

            BytesInHex bytesInHex = new BytesInHex();
            bytesInHex.setHexString(bytesInHexStr);
            bytesInHex.setCharacteristic(charact);
//            bytesInHexRepository.(bytesInHex);

            switch (CodeProcessor.Characteristic.getType(charact)) {

                case STARTUP:
                case SHUTDOWN:
                case TIREMSG:

                    processor.tirePackCount = dataByteList.get(CodeProcessor.TIRE_PACK_COUNT_IDX);

                    processor.gpsPackCount = dataByteList.get(CodeProcessor.GPS_PACK_COUNT_IDX);

                    processor.gpsPackBeginIdx = processor.tirePackBeginIdx + /*processor.tirePackCount*/25 * CodeProcessor.TIRE_UNIT; /**胎压包数量即便是0，也会留有25byte的0在占位*/

                    List<TireMessage> tireMsgList = processor.parseTireMsg();

                    if(tireMsgList != null && tireMsgList.size() != 0) {
                        tireMsgList.parallelStream().forEach(t -> {t.setBoxId(boxId); t.setCharacteristic(charact & 0xff); tirRepository.save(t); System.out.println(t); System.out.println();});
                    }

                    List<GPSMessage> gpsMsgList = processor.parseGPSMsg();

                    if(gpsMsgList != null && gpsMsgList.size() != 0) {
                        gpsMsgList.parallelStream().forEach(g -> {g.setBoxId(boxId); g.setCharacteristic(charact & 0xff); gpsRepository.save(g); System.out.println(g);});
                    }


                    break;
                case MPUMSG:

                    MPUMessage mpuMsg = processor.parseMPUMsg();

                    mpuMsg.setBoxId(boxId);

                    mpuMsg.setCharacteristic(charact & 0xff);

                    System.out.println(mpuMsg);

                    mpuRepository.save(mpuMsg);

                    break;
                case UNKNOWN:
                default:
                    break;

            }

        } catch(Exception e) {

            e.printStackTrace();
            Logger.logMsg(Logger.ERROR, e.getMessage());

        } finally {

            try {

                if(bufferedIs != null) bufferedIs.close();
                if(byteIs != null) byteIs.close();

                bufferedIs = null;
                byteIs = null;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return 0xF;

    }

    private String elasticSaveGPS(GPSMessage g, int _id) {

        RestClient restClient = null;

        String ret = "empty!";

        try {

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "Janhoo08"));

            restClient = RestClient.builder(new HttpHost(host, port))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(12000).setSocketTimeout(12000);
                        }
                    })
                    .setMaxRetryTimeoutMillis(12000)
                    .build();

            HttpEntity entity = new NStringEntity("" +
                    "{\n" +
                    "    \"box_id\" : " + g.getBoxId() + ",\n" +
                    "    \"characteristic\" : " + g.getCharacteristic() + ",\n" +
                    "    \"ewhemi\" : \"" + (char)g.getEwhemi() + "\",\n" +
                    "    \"gpssta\" : \"" + g.getGpssta() + "\",\n" +
                    "    \"lat\": " + g.getLatitude() + ",\n" +
                    "    \"lon\": " + g.getLongitude() + ",\n" +
                    "    \"location\": [" + g.getLongitude() + "," + g.getLatitude() + "],\n" +
                    "    \"nshemi\": \"" +(char)g.getNshemi() + "\",\n" +
                    "    \"speed\": " + g.getSpeed() + ",\n" +
                    "    \"timestamp\": " + g.getTimestamp() + ",\n" +
                    "    \"date\": \"" + g.getUtc_time_year() + "T" + (g.getUtc_time_day().length() < 8 ? g.getUtc_time_day() + ":00" : g.getUtc_time_day()) + "\"\n" +
                    "}", ContentType.APPLICATION_JSON);

            Response resp = restClient.performRequest("PUT", "/tire-data/gps-msg/" + _id, Collections.<String, String>emptyMap(), entity);

            ret = EntityUtils.toString(resp.getEntity());

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {

            try {
                if(restClient != null) restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }

        return ret;

    }

    private String elasticSaveTire(TireMessage t, int _id) {

        RestClient restClient = null;

        String ret = "empty!";

        try {

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "Janhoo08"));

            restClient = RestClient.builder(new HttpHost(host, port))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(12000).setSocketTimeout(12000);
                        }
                    })
                    .setMaxRetryTimeoutMillis(12000)
                    .build();

            HttpEntity entity = new NStringEntity("" +
                    "{\n" +
                    "    \"box_id\" : " + t.getBoxId() + ",\n" +
                    "    \"characteristic\" : " + t.getCharacteristic() + ",\n" +
                    "    \"accelerate\" : " + t.getAccelerate() + ",\n" +
                    "    \"idh\" : " + t.getIdh() + ",\n" +
                    "    \"idl\": " + t.getIdl() + ",\n" +
                    "    \"place\": " + t.getPlace() + ",\n" +
                    "    \"pressure\": " + t.getPressure() + ",\n" +
                    "    \"voltage\": " + t.getVoltage() + ",\n" +
                    "    \"temperature\": " + t.getTemperature() + ",\n" +
                    "    \"timestamp\": " + t.getTimestamp() + ",\n" +
                    "    \"date\": \"" + t.getUtc_time_year() + "T" + (t.getUtc_time_day().length() < 8 ? t.getUtc_time_day() + ":00" : t.getUtc_time_day()) + "\"\n" +
                    "}", ContentType.APPLICATION_JSON);

            Response resp = restClient.performRequest("PUT", "/tire-data/tire-msg/" + _id, Collections.<String, String>emptyMap(), entity);

            ret = EntityUtils.toString(resp.getEntity());

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {

            try {
                if(restClient != null) restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }

        return ret;

    }


}
