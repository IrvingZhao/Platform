import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import modal.DemoMessage;
import org.apache.mina.core.session.IoSession;

public class ClientMethod implements MinaMessageMethod<DemoMessage, DemoMessage> {
    @Override
    public DemoMessage execute(DemoMessage data, IoSession session) {
        System.out.println("======================== client method =======================");
        System.out.println(data.getData());
        return null;
    }
}
