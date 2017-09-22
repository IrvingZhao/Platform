import cn.irving.zhao.util.base.security.CryptoUtils;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
//        String message = "我是消息";
//        String salt = "hash";
//        String hash = CryptoUtils.getHash(message, salt);
//        System.out.println(hash);
//        System.out.println(CryptoUtils.verify(hash, message, salt));
//
//        System.out.println(ObjectStringSerialUtil.getSerialUtil().serial("1", ObjectStringSerialUtil.SerialType.JSON));
//        System.out.println(ObjectStringSerialUtil.getSerialUtil().parse("1", String.class, ObjectStringSerialUtil.SerialType.JSON));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 2000);

    }
}
