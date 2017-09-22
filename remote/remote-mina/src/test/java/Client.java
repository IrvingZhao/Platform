import cn.irving.zhao.util.remote.mina.MinaUtil;
import cn.irving.zhao.util.remote.mina.core.keeper.impl.DefaultMinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.method.impl.DefaultMinaMessageMethodFactory;
import modal.DemoMessage;

public class Client {
    public static void main(String[] args) {
        DefaultMinaMessageMethodFactory methodFactory = new DefaultMinaMessageMethodFactory();
        DefaultMinaSessionKeeper sessionKeeper = new DefaultMinaSessionKeeper();

//        methodFactory.registerMethod("test", new ClientMethod());

        MinaUtil minaUtil = new MinaUtil();
        minaUtil.setHost("192.168.4.129");
        minaUtil.setPort(8899);
        minaUtil.setMethodFactory(methodFactory);
        minaUtil.setSessionKeeper(sessionKeeper);
        minaUtil.setType(MinaUtil.Type.CLIENT);

        minaUtil.init();

        System.out.println("client INIT");

        DemoMessage message = new DemoMessage("1", "test", "我是客户端");

        minaUtil.sendMessage(message);
    }
}
