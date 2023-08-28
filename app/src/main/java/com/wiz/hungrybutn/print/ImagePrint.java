package com.wiz.hungrybutn.print;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorTreeAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.rt.printerlibrary.bean.LableSizeBean;
import com.rt.printerlibrary.bean.Position;
import com.rt.printerlibrary.bean.UsbConfigBean;
import com.rt.printerlibrary.cmd.Cmd;
import com.rt.printerlibrary.cmd.CpclFactory;
import com.rt.printerlibrary.cmd.EscFactory;
import com.rt.printerlibrary.cmd.PinFactory;
import com.rt.printerlibrary.cmd.TscFactory;
import com.rt.printerlibrary.cmd.ZplFactory;
import com.rt.printerlibrary.connect.PrinterInterface;
import com.rt.printerlibrary.enumerate.BmpPrintMode;
import com.rt.printerlibrary.enumerate.CommonEnum;
import com.rt.printerlibrary.enumerate.ConnectStateEnum;
import com.rt.printerlibrary.enumerate.PrintDirection;
import com.rt.printerlibrary.exception.SdkException;
import com.rt.printerlibrary.factory.cmd.CmdFactory;
import com.rt.printerlibrary.factory.connect.PIFactory;
import com.rt.printerlibrary.factory.connect.UsbFactory;
import com.rt.printerlibrary.factory.printer.PrinterFactory;
import com.rt.printerlibrary.factory.printer.ThermalPrinterFactory;
import com.rt.printerlibrary.printer.RTPrinter;
import com.rt.printerlibrary.setting.BitmapSetting;
import com.rt.printerlibrary.setting.CommonSetting;
import com.rt.printerlibrary.utils.BitmapConvertUtil;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.app.BaseApplication;
import com.wiz.hungrybutn.app.BaseEnum;
import com.wiz.hungrybutn.main.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePrint   {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0xd0;
    private static final int ALBUM_IMAGE_ACTIVITY_REQUEST_CODE = 0xd1;

    private LinearLayout llUploadImage;
    private Button btn_print;
    private FrameLayout flContent;
    private ImageView ivImage;
    private EditText et_pic_width;

    private RTPrinter rtPrinter;
    private Uri imageUri;
    private Bitmap mBitmap, mTempBmp;
    private final static String TAG = "ImagePrint";
    private int bmpPrintWidth = 72;
    private List<String> NO_PERMISSION = new ArrayList<String>();
    private static final int REQUEST_CAMERA = 0;
    private PrinterFactory printerFactory;


    private Object configObj;
    private String[] NEED_PERMISSION = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
   private  Activity  activity ;
   private  static ImagePrint  imagePrint ;

    public ImagePrint(Activity activity){
        this.activity = activity ;
        BaseApplication.instance.setCurrentCmdType(BaseEnum.CMD_ESC);
        printerFactory = new ThermalPrinterFactory();
        rtPrinter = printerFactory.create();




    }
    public static  ImagePrint getInstance(Activity activity){
        if (imagePrint == null){
            imagePrint = new ImagePrint(activity) ;
        }
        return  imagePrint ;
    }





    void usbConnect(){
        UsbConfigBean usbConfigBean = (UsbConfigBean) configObj;
        connectUSB(usbConfigBean);

    }

    private void connectUSB(UsbConfigBean usbConfigBean) {
        UsbManager mUsbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        PIFactory piFactory = new UsbFactory();
        PrinterInterface printerInterface = piFactory.create();
        printerInterface.setConfigObject(usbConfigBean);
        rtPrinter.setPrinterInterface(printerInterface);

        if (mUsbManager.hasPermission(usbConfigBean.usbDevice)) {
            try {
                rtPrinter.connect(usbConfigBean);
                BaseApplication.instance.setRtPrinter(rtPrinter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mUsbManager.requestPermission(usbConfigBean.usbDevice, usbConfigBean.pendingIntent);
        }

    }

    public void showUSBDeviceChooseDialog() {
        final UsbDeviceChooseDialog usbDeviceChooseDialog = new UsbDeviceChooseDialog();
        usbDeviceChooseDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UsbDevice mUsbDevice = (UsbDevice) parent.getAdapter().getItem(position);
                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(
                        activity,
                        0,
                        new Intent(activity.getApplicationInfo().packageName),
                        0);
                configObj = new UsbConfigBean(BaseApplication.getInstance(), mUsbDevice, mPermissionIntent);
                usbDeviceChooseDialog.dismiss();
                init();
            }
        });
        usbDeviceChooseDialog.show(activity.getFragmentManager(), null);
    }




    public void init() {
        usbConnect() ;
        rtPrinter = BaseApplication.getInstance().getRtPrinter();
    }



    public void CheckAllPermission() {
        NO_PERMISSION.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < NEED_PERMISSION.length; i++) {
                if (activity.checkSelfPermission(NEED_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                    NO_PERMISSION.add(NEED_PERMISSION[i]);
                }
            }
            if (NO_PERMISSION.size() == 0) {
            } else {
                activity.requestPermissions(NO_PERMISSION.toArray(new String[NO_PERMISSION.size()]), REQUEST_CAMERA);
            }
        } else {

        }

    }



    public void print( Bitmap bitmap) throws SdkException {
        mBitmap = bitmap ;
//        mBitmap = BitmapFactory.decodeResource(activity.getResources(),
//                R.drawable.f11);
        if (mBitmap == null) {//未选择图片
            return;
        }
        try {
            bmpPrintWidth = 72 ;
        } catch (Exception e) {
            e.printStackTrace();
        }
                escPrint();

        }

    private void escPrint() throws SdkException {

        new Thread(new Runnable() {
            @Override
            public void run() {


                CmdFactory cmdFactory = new EscFactory();
                Cmd cmd = cmdFactory.create();
                cmd.append(cmd.getHeaderCmd());

                CommonSetting commonSetting = new CommonSetting();
                commonSetting.setAlign(CommonEnum.ALIGN_MIDDLE);
                cmd.append(cmd.getCommonSettingCmd(commonSetting));

                BitmapSetting bitmapSetting = new BitmapSetting();

                /**
                 * MODE_MULTI_COLOR - 适合多阶灰度打印<br/> Suitable for multi-level grayscale printing<br/>
                 * MODE_SINGLE_COLOR-适合白纸黑字打印<br/>Suitable for printing black and white paper
                 */
                bitmapSetting.setBmpPrintMode(BmpPrintMode.MODE_SINGLE_COLOR);
//                bitmapSetting.setBmpPrintMode(BmpPrintMode.MODE_MULTI_COLOR);


                if (bmpPrintWidth > 72) {
                    bmpPrintWidth = 72;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_pic_width.setText(bmpPrintWidth + "");
                        }
                    });
                }
                bitmapSetting.setBimtapLimitWidth(bmpPrintWidth * 8);
                try {
                    cmd.append(cmd.getBitmapCmd(bitmapSetting, mBitmap));
                } catch (SdkException e) {
                    e.printStackTrace();
                }
                cmd.append(cmd.getLFCRCmd());
                cmd.append(cmd.getLFCRCmd());
//                cmd.append(cmd.getLFCRCmd());
//                cmd.append(cmd.getLFCRCmd());
//                cmd.append(cmd.getLFCRCmd());
//                cmd.append(cmd.getLFCRCmd());
                if (rtPrinter != null) {
                    rtPrinter.writeMsg(cmd.getAppendCmds());//Sync Write
                }

            }
        }).start();



    }



}
