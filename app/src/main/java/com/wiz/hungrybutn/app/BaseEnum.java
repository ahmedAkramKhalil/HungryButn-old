package com.wiz.hungrybutn.app;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tony on 2017/12/3.
 */

public class BaseEnum {

    public static final int NONE = -1;
    public static final int CMD_ESC = 1, CMD_TSC = 2, CMD_CPCL = 3, CMD_ZPL = 4, CMD_PIN = 5;
    public static final int CON_BLUETOOTH = 1, CON_BLUETOOTH_BLE = 2, CON_WIFI = 3, CON_USB = 4, CON_COM = 5;
    public static final int NO_DEVICE = -1, HAS_DEVICE = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CmdType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectType {
    }


    @Retention(RetentionPolicy.SOURCE)
    public @interface ChooseDevice {
    }



}
