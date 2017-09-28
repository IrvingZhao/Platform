package client;

import cn.irving.zhao.util.remote.mina.client.MinaClient;
import common.CustomMethodFactory;
import common.CustomSerialExecutor;
import common.CustomSignExecutor;

public class ClientMain {
    public static void main(String[] args) {
        CustomMethodFactory methodFactory = new CustomMethodFactory();
        ClientMethod method = new ClientMethod();
        methodFactory.registerMethod("method", method);

        CustomSerialExecutor serialExecutor = new CustomSerialExecutor();

        CustomSignExecutor signExecutor = new CustomSignExecutor();

        MinaClient client = new MinaClient("192.168.4.129", 8899);
        client.setClientId("ef5ea89d-f80d-4846-b387-cbfe22142310");
        client.setSalt("EWuqCA0zjEka4qrVujAnJvWlScTjGqeFfGqQMDEYsFM=");

        client.setMethodFactory(methodFactory);

        client.setSerialExecutor(serialExecutor);

        client.setSignExecutor(signExecutor);

        client.setAutoRegisterClient(true);

        client.init();

//        client.setSalt("111231231");
        client.sendMessage("111", new ClientModel("我是客户端", "客户端sign异常消息"));

        //TODO 发送消息

//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                try {
//                    Thread.sleep(3000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                client.sendMessage("method", new ClientModel("我是客户端", "第" + i + "次发送消息"));
//            }
//
//        }).start();

    }
}
