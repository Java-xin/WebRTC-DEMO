import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Vector;

// 指定一个URL客户端可以通过这个url来链接webSocket
// 当一个客户端访问onlineServer连接的时候，创建一个OnlineServer的实例
@ServerEndpoint("/onlineServer")
public class OnlineServer {

    // 当前会话对象，通过session，服务器可以向客户端主动发送消息
    private Session session;

    public static Vector<OnlineServer> clientServer = new Vector();
    // 当客户单与服务器建立连接的时候触发方法
  @OnOpen
  public void onOpen(Session session) {
      this.session = session;
      // 将当前客户端加入客户端列表
      clientServer.add(this);
      System.out.println("onOpen新连接连接");
  }

  @OnClose
    public void onClose() {
      clientServer.remove(this);
      System.out.println("onClose有一个连接");
  }

  @OnMessage
   public void OnMessage(String message, Session session){
    // 群发消息 广播消息
      for (OnlineServer onlineServer : clientServer) {
          try {
              // 往客户端发送消息
              onlineServer.session.getBasicRemote().sendText(message);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
}
