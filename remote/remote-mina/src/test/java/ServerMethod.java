import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import modal.DemoMessage;
import org.apache.mina.core.session.IoSession;

import java.util.function.Consumer;

public class ServerMethod implements MinaMessageMethod<DemoMessage, DemoMessage> {

    public Consumer<String> callback;

    @Override
    public DemoMessage execute(DemoMessage data, IoSession session) {
        System.out.println("======================== server method =======================");
        System.out.println(data.getData());
        callback.accept("我是服务器");
        return null;
    }
}
