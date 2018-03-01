package com.pi2star.tirecare.controller.decode;


import com.pi2star.tirecare.entity.GPSMessage;
import com.pi2star.tirecare.entity.MPUMessage;
import com.pi2star.tirecare.entity.TireMessage;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
public class CodeProcessor {

    //------------------------------------------------------------------------

    public static final int UNIT = 1; //(1 byte)
    public static final int GPS_UNIT = 20 * UNIT; //19 * UNIT;//TODO LM 传递数据有误？单个GPS结构体抓取后是20个字节，年月日部分是8个字节，正确该是6个吧？
    public static final int TIRE_UNIT = 16 * UNIT;
    public static final int BOX_ID_IDX = 0;
    public static final int CHARACTERISTIC_IDX = 1;
    public static final int TIRE_PACK_COUNT_IDX = 2;
    public static final int GPS_PACK_COUNT_IDX = 3;

    //------------------------------------------------------------------------

    public static final int MPU_UNIT = 12*UNIT;
    public static final int MPU_YEAR_IDX = 2;//年一个字节
    public static final int MPU_MONTH_IDX = 3;
    public static final int MPU_DAY_IDX = 4;
    public static final int MPU_HOUR_IDX = 5;
    public static final int MPU_MINUTE_IDX = 6;
    public static final int MPU_SECOND_IDX = 7;
    public static final int MPUPACK_BEGIN_IDX = 8;
    public static final int MPUPACK_COUNT = 80;
    public static final int MPUPACK_TOTAL_LEN =  MPUPACK_COUNT * MPU_UNIT + 8;

    //------------------------------------------------------------------------

    public static final int TIRE_PACK_BEGIN_IDX = 4;
    public static final int TIRE_IDH_IDX = 0;
    public static final int TIRE_IDL_IDX = 1;

    public static final int TIRE_PRESSURE_IDX = 2;
    public static final int TIRE_TEMPERATURE_IDX = 4;
    public static final int TIRE_ACCELERATE_IDX = 6;
    public static final int TIRE_VOLTAGE_IDX = 8;

    public static final int TIRE_YEAR_IDX = 10;//年一个字节
    public static final int TIRE_MONTH_IDX = 11;
    public static final int TIRE_DAY_IDX = 12;
    public static final int TIRE_HOUR_IDX = 13;
    public static final int TIRE_MINUTE_IDX = 14;
    public static final int TIRE_SECOND_IDX = 15;

    public static final float TIRE_PRESSURE_CONVERT_NUM = 16.0f;
    public static final float TIRE_TEMPERATURE_CONVERT_NUM = 128.0f;
    public static final float TIRE_ACCELERATE_CONVERT_NUM = 16.0f;
    public static final float TIRE_VOLTAGE_CONVERT_NUM = 8000.0f;

    //------------------------------------------------------------------------

    public static final int GPS_HOUR_IDX = 0;
    public static final int GPS_MINUTE_IDX = 1;
    public static final int GPS_SECOND_IDX = 2;

    public static final int GPS_STATE_IDX = 3;
    public static final int GPS_NSHEMI_IDX = 4;
    public static final int GPS_EWHEMI_IDX = 5;

    public static final int GPS_YEAR_IDX = 6;/** GPS数据包年月日部分是8个字节，年部分占两个字节*/
    public static final int GPS_MONTH_IDX = 8;
    public static final int GPS_DAY_IDX = 9;

    public static final int GPS_SPEED_IDX = 10;//9;

    public static final int GPS_LATITUDE_IDX = 12;//10;
    public static final int GPS_LONGITUDE_IDX = 16;//14;

    //------------------------------------------------------------------------


    public enum Characteristic {

        STARTUP((byte)0xff), SHUTDOWN((byte)0xee), TIREMSG((byte)0x00), MPUMSG((byte)0x06), UNKNOWN((byte)-1);

        public byte _value;

        private Characteristic(byte value) {

            _value = value;

        }

        public static Characteristic getType(byte value) {

            Characteristic type = UNKNOWN;

            if(value == STARTUP._value) {

                type = STARTUP;

            } else if(value== SHUTDOWN._value) {

                type = SHUTDOWN;

            } else if(value == TIREMSG._value) {

                type = TIREMSG;

            } else if(value > TIREMSG._value && value <= MPUMSG._value) {

                type = MPUMSG;

            } else {

                type = UNKNOWN;

            }

            return type;

        }

    }

    public ArrayList<Byte> mOriginBytes;

    public Characteristic mParseType;

    public int tirePackCount;
    public int tirePackBeginIdx = TIRE_PACK_BEGIN_IDX;

    public int gpsPackCount;
    public int gpsPackBeginIdx;

    public int mpuPackBeginIndex;


    public List<TireMessage> parseTireMsg() {

        if(mOriginBytes == null || mOriginBytes.size() == 0) return null;

        ArrayList<TireMessage> tList = null;

        try {

            Logger.logMsg(Logger.DEBUG, "start tire_msg parse --------------------------------------------------------------------");
            System.out.println("start tire_msg parse --------------------------------------------------------------------" + tirePackCount);

            List<Byte> tireBytes = mOriginBytes.subList(tirePackBeginIdx, tirePackCount * TIRE_UNIT + tirePackBeginIdx);

            if(tireBytes == null && tireBytes.size() == 0) return null;

            tList = new ArrayList<>();

            for(int i = 0; i < tirePackCount; i ++) {

                int startIndxBase = (i * TIRE_UNIT);

                int idH = tireBytes.get(startIndxBase + TIRE_IDH_IDX) & 0xFF;//防止最高位是F时，转int出现负值的情况，&0xFF使高24位归零(java 将byte转int时，如果byte最高为是1（比如：0xF~ 到 0x8），会将高24位也自动补1)
                int idL = tireBytes.get(startIndxBase + TIRE_IDL_IDX) & 0xFF;
                String hexStr = Integer.toHexString(idL);
                int tirePlace = -1;

                if(hexStr.length() > 1) {

                    tirePlace = Integer.parseInt(hexStr.substring(1), 16);

                } else {

                    tirePlace = idL;

                }


                byte[] pressure = new byte[4];
                pressure[0] = 0x00;
                pressure[1] = 0x00;
                pressure[2] = tireBytes.get(startIndxBase + TIRE_PRESSURE_IDX);
                pressure[3] = tireBytes.get(startIndxBase + TIRE_PRESSURE_IDX + 1);


                byte[] temperature = new byte[4];

                byte hByte = tireBytes.get(startIndxBase + TIRE_TEMPERATURE_IDX);

                byte flagJudge = (byte) (hByte & 0x80);//&0x80用来判断最高位是0 还是 1，如果是1说明（符号位是1）传上来的是负数，前面16位应该补1

                if(flagJudge != 0) {

                    temperature[0] = (byte) 0xFF;
                    temperature[1] = (byte) 0xFF;

                } else {

                    temperature[0] = (byte) 0x00;
                    temperature[1] = (byte) 0x00;

                }

                temperature[2] = tireBytes.get(startIndxBase + TIRE_TEMPERATURE_IDX);
                temperature[3] = tireBytes.get(startIndxBase + TIRE_TEMPERATURE_IDX + 1);

                System.out.println("==========================" + bytesToHexString(temperature));


                byte[] accelerate = new byte[4];


                hByte = tireBytes.get(startIndxBase + TIRE_ACCELERATE_IDX);

                flagJudge = (byte) (hByte & 0x80);

                if(flagJudge != 0) {

                    accelerate[0] = (byte) 0xFF;
                    accelerate[1] = (byte) 0xFF;

                } else {

                    accelerate[0] = (byte) 0x00;
                    accelerate[1] = (byte) 0x00;

                }

                accelerate[2] = tireBytes.get(startIndxBase + TIRE_ACCELERATE_IDX);
                accelerate[3] = tireBytes.get(startIndxBase + TIRE_ACCELERATE_IDX + 1);

                System.out.println("==========================" + bytesToHexString(accelerate));


                byte[] voltage = new byte[4];
                voltage[0] = 0x00;
                voltage[1] = 0x00;
                voltage[2] = tireBytes.get(startIndxBase + TIRE_VOLTAGE_IDX);
                voltage[3] = tireBytes.get(startIndxBase + TIRE_VOLTAGE_IDX + 1);


                int year = 2000 + (tireBytes.get(startIndxBase + TIRE_YEAR_IDX) & 0xFF);
                int month = tireBytes.get(startIndxBase + TIRE_MONTH_IDX) & 0xFF;
                int day = tireBytes.get(startIndxBase + TIRE_DAY_IDX) & 0xFF;


                int hour = tireBytes.get(startIndxBase + TIRE_HOUR_IDX) & 0xFF;
                int minute = tireBytes.get(startIndxBase + TIRE_MINUTE_IDX) & 0xFF;
                int second = tireBytes.get(startIndxBase + TIRE_SECOND_IDX) & 0xFF;

                String dateStr = year + "-" + month + "-" + day;

                String timeStr = hour + ":" + minute + ":" + second;

                long timestamp = -1l;

                try {

                    LocalDate date = LocalDate.of(year, month, day);

                    dateStr = date.toString();

                    LocalTime time = LocalTime.of(hour, minute, second);

                    timeStr = time.toString();

                    LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute, second);

                    timestamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

                } catch(Exception e) {

                    e.printStackTrace();
                    System.out.println(e.getMessage());


                }

                Logger.logMsg(Logger.DEBUG, "IDH : " + idH);
                Logger.logMsg(Logger.DEBUG, "IDL : " + idL);
                Logger.logMsg(Logger.DEBUG, "tire place : " + tirePlace);
                Logger.logMsg(Logger.DEBUG, "pressure : " + pressure[0] + " - " + pressure[1]);
                Logger.logMsg(Logger.DEBUG, "temperature : " + temperature[0] + " - " + temperature[1]);
                Logger.logMsg(Logger.DEBUG, "accelerate : " + accelerate[0] + " - " + accelerate[1]);
                Logger.logMsg(Logger.DEBUG, "voltage : " + voltage[0] + " - " + voltage[1]);
                Logger.logMsg(Logger.DEBUG, "date : " + dateStr);
                Logger.logMsg(Logger.DEBUG, "time : " + timeStr);

                TireMessage tireMsg = new TireMessage();

                tireMsg.setIdh(idH);
                tireMsg.setIdl(idL);
                tireMsg.setPlace(tirePlace);
                tireMsg.setPressure((float) bytes2Int(pressure) / TIRE_PRESSURE_CONVERT_NUM);
                tireMsg.setTemperature((float) bytes2Int(temperature) / TIRE_TEMPERATURE_CONVERT_NUM);
                tireMsg.setAccelerate((float) bytes2Int(accelerate) / TIRE_ACCELERATE_CONVERT_NUM);
                tireMsg.setVoltage((float) bytes2Int(voltage) / TIRE_VOLTAGE_CONVERT_NUM);

                tireMsg.setUtc_time_year(dateStr);
                tireMsg.setUtc_time_day(timeStr);
                tireMsg.setTimestamp(timestamp);

                tList.add(tireMsg);

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            Logger.logMsg(Logger.ERROR, e.getMessage());
            tList = null;

        } finally {

            Logger.logMsg(Logger.DEBUG, "end  tire_msg parse --------------------------------------------------------------------");
            System.out.println("end  tire_msg parse --------------------------------------------------------------------");

        }

        return tList;

    }

    public ArrayList<GPSMessage> parseGPSMsg() {

        if(mOriginBytes == null || mOriginBytes.size() == 0) return null;

        ArrayList<GPSMessage> gList = null;

        try {

            Logger.logMsg(Logger.DEBUG, "start gps_msg parse --------------------------------------------------------------------");
            System.out.println("start gps_msg parse --------------------------------------------------------------------" + gpsPackCount);

            List<Byte> gpsBytes = mOriginBytes.subList(gpsPackBeginIdx, gpsPackCount * GPS_UNIT + gpsPackBeginIdx);


            //TODO
            byte[] bytes = new byte[gpsBytes.size()];

            for(int i = 0; i < bytes.length; i ++) {

                bytes[i] = gpsBytes.get(i);

            }

            System.out.println(">>>>>>>>>>>> gps bytes : " + bytesToHexString(bytes));

            if(gpsBytes == null && gpsBytes.size() == 0) return null;

            gList = new ArrayList<>();

            for(int i = 0; i < gpsPackCount; i ++) {

                int startIndexStep = (i * GPS_UNIT);

                int hour = gpsBytes.get(startIndexStep + GPS_HOUR_IDX) & 0xFF;
                int minute = gpsBytes.get(startIndexStep + GPS_MINUTE_IDX) & 0xFF;
                int second = gpsBytes.get(startIndexStep + GPS_SECOND_IDX) & 0xFF;

                char gpsState = (char)((int)(gpsBytes.get(startIndexStep + GPS_STATE_IDX) & 0xFF));

                int nshemi = gpsBytes.get(startIndexStep + GPS_NSHEMI_IDX) & 0xFF;

                int ewhemi = gpsBytes.get(startIndexStep + GPS_EWHEMI_IDX & 0xFF);

                byte[] yearBytes = new byte[4];

                yearBytes[0] = 0x00;
                yearBytes[1] = 0x00;
                yearBytes[2] = gpsBytes.get(startIndexStep + GPS_YEAR_IDX);
                yearBytes[3] = gpsBytes.get(startIndexStep + GPS_YEAR_IDX + 1);

                int year = bytes2Int(yearBytes);
                int month = gpsBytes.get(startIndexStep + GPS_MONTH_IDX) & 0xFF;
                int day = gpsBytes.get(startIndexStep + GPS_DAY_IDX) & 0xFF;

                byte[] speedData = new byte[4];
                speedData[0] = 0x00;
                speedData[1] = 0x00;
                speedData[2] = gpsBytes.get(startIndexStep + GPS_SPEED_IDX);
                speedData[3] = gpsBytes.get(startIndexStep + GPS_SPEED_IDX + 1);
                int speed = bytes2Int(speedData);

                byte[] latData = new byte[4];
                latData[0] = gpsBytes.get(startIndexStep + GPS_LATITUDE_IDX);
                latData[1] = gpsBytes.get(startIndexStep + GPS_LATITUDE_IDX + 1);
                latData[2] = gpsBytes.get(startIndexStep + GPS_LATITUDE_IDX + 2);
                latData[3] = gpsBytes.get(startIndexStep + GPS_LATITUDE_IDX + 3);
                //float latitude = bytes2float(latData, 0);

                byte[] longData = new byte[4];
                longData[0] = gpsBytes.get(startIndexStep + GPS_LONGITUDE_IDX);
                longData[1] = gpsBytes.get(startIndexStep + GPS_LONGITUDE_IDX + 1);
                longData[2] = gpsBytes.get(startIndexStep + GPS_LONGITUDE_IDX + 2);
                longData[3] = gpsBytes.get(startIndexStep + GPS_LONGITUDE_IDX + 3);
                //float longitude = bytes2float(longData, 0);

                System.out.println(bytesToHexString(latData) + "+++++++++++++++++++++++++++++++++++" + bytesToHexString(longData));

                String dateStr = year + "-" + month + "-" + day;

                String timeStr = hour + ":" + minute + ":" + second;

                long timeStamp = -1l;

                try {

                    LocalTime time = LocalTime.of(hour, minute, second);
                    LocalDate date = LocalDate.of(year, month, day);
                    LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute, second);

                    timeStr = time.toString();
                    dateStr = date.toString();

                    timeStamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println(e.getMessage());

                }

                GPSMessage gpsMsg = new GPSMessage();
                gpsMsg.setGpssta(gpsState);
                gpsMsg.setLatitude((float)bytes2Int(latData) / 100000f);
                gpsMsg.setNshemi(nshemi);
                gpsMsg.setLongitude((float)bytes2Int(longData) / 100000f);
                gpsMsg.setEwhemi(ewhemi);
                gpsMsg.setUtc_time_day(timeStr);
                gpsMsg.setUtc_time_year(dateStr);
                gpsMsg.setSpeed(speed / 1000.0f);
                gpsMsg.setTimestamp(timeStamp);

                System.out.println(gpsMsg);

                gList.add(gpsMsg);

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            Logger.logMsg(Logger.ERROR, e.getMessage());
            gList = null;

        } finally {

            Logger.logMsg(Logger.DEBUG, "end  gps_msg parse --------------------------------------------------------------------");
            System.out.println("end  gps_msg parse --------------------------------------------------------------------");

        }

        return gList;

    }

    public MPUMessage parseMPUMsg() {

        if(mOriginBytes == null || mOriginBytes.size() == 0) return null;

        MPUMessage mpuMsg = null;

        try {

            Logger.logMsg(Logger.DEBUG, "start mpu_msg parse --------------------------------------------------------------------");
            System.out.println("start mpu_msg parse --------------------------------------------------------------------");

            List<Byte> mpuDataList = mOriginBytes.subList(MPUPACK_BEGIN_IDX, MPUPACK_TOTAL_LEN);

            if(mpuDataList == null && mpuDataList.size() == 0) return null;

            mpuMsg = new MPUMessage();

            int year = 2000 + (mOriginBytes.get(MPU_YEAR_IDX) & 0xFF);
            int month = mOriginBytes.get(MPU_MONTH_IDX) & 0xFF;
            int day = mOriginBytes.get(MPU_DAY_IDX) & 0xFF;


            int hour = mOriginBytes.get(MPU_HOUR_IDX) & 0xFF;
            int minute = mOriginBytes.get(MPU_MINUTE_IDX) & 0xFF;
            int second = mOriginBytes.get(MPU_SECOND_IDX) & 0xFF;


            String dateStr = year + "-" + month + "-" + day;

            String timeStr = hour + ":" + minute + ":" + second;

            long timeStamp = -1l;

            try {

                LocalTime time = LocalTime.of(hour, minute, second);
                LocalDate date = LocalDate.of(year, month, day);
                LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute, second);

                timeStr = time.toString();
                dateStr = date.toString();
                timeStamp = ldt.toEpochSecond(ZoneOffset.of("+8"));

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println(e.getMessage());

            }


            Float[] acXArr = new Float[MPUPACK_COUNT];
            Float[] acYArr = new Float[MPUPACK_COUNT];
            Float[] acZArr = new Float[MPUPACK_COUNT];

            Float[] gyXArr = new Float[MPUPACK_COUNT];
            Float[] gyYArr = new Float[MPUPACK_COUNT];
            Float[] gyZArr = new Float[MPUPACK_COUNT];

            for(int i = 0; i < MPUPACK_COUNT; i ++) {

                int startIndexStep = (i * MPU_UNIT);

                byte[] bytes = new byte[12];

                for(int k = 0; k < bytes.length; k ++) {

                    bytes[k] = mpuDataList.get(startIndexStep + k);

                }

                byte packPos = 0x00;

                packPos = ((byte)(bytes[0] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float acX = (float) bytes2Int(new byte[] {packPos, packPos, bytes[0], bytes[1]}) / 16384.0f;

                packPos = ((byte)(bytes[2] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float acY = (float) bytes2Int(new byte[] {packPos, packPos, bytes[2], bytes[3]}) / 16384.0f;

                packPos = ((byte)(bytes[4] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float acZ = (float) bytes2Int(new byte[] {packPos, packPos, bytes[4], bytes[5]}) / 16384.0f;

                packPos = ((byte)(bytes[6] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float gyX = (float) bytes2Int(new byte[] {packPos, packPos, bytes[6], bytes[7]}) / 16.40f;

                packPos = ((byte)(bytes[8] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float gyY = (float) bytes2Int(new byte[] {packPos, packPos, bytes[8], bytes[9]}) / 16.40f;

                packPos = ((byte)(bytes[10] & 0x80) == 0 ? 0x00 : (byte) 0xFF);
                float gyZ = (float) bytes2Int(new byte[] {packPos, packPos, bytes[10], bytes[11]}) / 16.40f;

                acXArr[i] = acX;
                acYArr[i] = acY;
                acZArr[i] = acZ;

                gyXArr[i] = gyX;
                gyYArr[i] = gyY;
                gyZArr[i] = gyZ;


            }


            mpuMsg.setUtc_time_year(dateStr);
            mpuMsg.setUtc_time_day(timeStr);
            mpuMsg.setTimestamp(timeStamp);

            mpuMsg.setAcX(acXArr);
            mpuMsg.setAcY(acYArr);
            mpuMsg.setAcZ(acZArr);

            mpuMsg.setGyX(gyXArr);
            mpuMsg.setGyY(gyYArr);
            mpuMsg.setGyZ(gyZArr);

            System.out.println(acXArr[0] + "_" + acYArr[0] + "_" + acZArr[0] + "||" + gyXArr[0] + "_" + gyYArr[0] + "_" + gyZArr[0]);


        } catch(Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            Logger.logMsg(Logger.ERROR, e.getMessage());
            mpuMsg = null;

        } finally {

            Logger.logMsg(Logger.DEBUG, "end  mpu_msg parse --------------------------------------------------------------------");
            System.out.println("end  mpu_msg parse --------------------------------------------------------------------");

        }


        return mpuMsg;

    }

    public static String bytesToHexString(byte[] bArray) {

        StringBuffer sb = new StringBuffer(bArray.length);

        String sTemp;

        for (int i = 0; i < bArray.length; i++) {

            sTemp = Integer.toHexString(0xFF & bArray[i]);

            if (sTemp.length() < 2)
                sb.append(0);

            sb.append(sTemp.toUpperCase());

        }

        return sb.toString();

    }

    public double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

/*    public int bytesToInt(byte[] b) {

        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;

    }*/

    public static float bytes2float(byte[] b) {

        int l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);

        return Float.intBitsToFloat(l);
    }

    public static byte[] float2Bytes(float f) {

        int value = Float.floatToIntBits(f);
        byte[] byteRet = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }

        return byteRet;

    }

    public static int bytes2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }


}
