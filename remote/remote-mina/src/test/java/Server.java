import cn.irving.zhao.util.remote.mina.MinaUtil;
import cn.irving.zhao.util.remote.mina.core.keeper.impl.DefaultMinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.method.impl.DefaultMinaMessageMethodFactory;
import modal.DemoMessage;

public class Server {
    public static void main(String[] args) {

        DefaultMinaMessageMethodFactory methodFactory = new DefaultMinaMessageMethodFactory();
        DefaultMinaSessionKeeper sessionKeeper = new DefaultMinaSessionKeeper();

        ServerMethod method = new ServerMethod();
//        methodFactory.registerMethod("test", method);


        MinaUtil minaUtil = new MinaUtil();
        minaUtil.setHost("192.168.4.129");
        minaUtil.setPort(8899);
        minaUtil.setMethodFactory(methodFactory);
        minaUtil.setSessionKeeper(sessionKeeper);
        minaUtil.setType(MinaUtil.Type.SERVER);

        minaUtil.init();

        method.callback = (data) -> {
            minaUtil.sendMessage(new DemoMessage("1", "test", data));
        };

        System.out.println("INIT");


    }
}
