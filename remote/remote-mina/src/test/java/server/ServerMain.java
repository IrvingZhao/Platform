package server;

import cn.irving.zhao.util.remote.mina.server.MinaServer;
import common.CustomMethodFactory;
import common.CustomSerialExecutor;
import common.CustomServerSaltHolder;
import common.CustomSignExecutor;

public class ServerMain {
    public static void main(String[] args) {

        CustomMethodFactory methodFactory = new CustomMethodFactory();
        ServerMethod method = new ServerMethod();
        methodFactory.registerMethod("method", method);

        CustomSerialExecutor serialExecutor = new CustomSerialExecutor();

        CustomSignExecutor signExecutor = new CustomSignExecutor();

        CustomServerSaltHolder saltHolder = new CustomServerSaltHolder();

        MinaServer server = new MinaServer("192.168.4.129", 8899);

        server.setSerialExecutor(serialExecutor);
        server.setSignExecutor(signExecutor);
        server.setSaltHolder(saltHolder);
        server.setMethodFactory(methodFactory);

        server.init();

//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                try {
//                    Thread.sleep(2000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                server.sendMessage("ef5ea89d-f80d-4846-b387-cbfe22142310", "method", new ServerModel("我是server", "第" + i + "次发送消息"));
//            }
//        }).start();


    }
}
