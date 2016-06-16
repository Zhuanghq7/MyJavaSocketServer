package Zhuangh7.JFrame.main;

import java.util.Vector;  

public class ChatManager {  

  private ChatManager(){}  
  private static final ChatManager cm = new ChatManager();  
  public static ChatManager getChatManager() {  
      return cm;  
  }  
    
  //用于储存管理新建的每一个socket线程  
  Vector<ChatSocket> vector = new Vector<ChatSocket>();  
    
  public void add(ChatSocket cs) {  
      vector.add(cs);  
  }  
    
    
  public void remove(ChatSocket cs) {  
      vector.remove(cs);  
      ServerListener.Client_num--;
  }  
    
  public void publish(ChatSocket cs,String out) {  
      for (int i = 0; i < vector.size(); i++) {  
          ChatSocket csChatSocket = vector.get(i);  
          if (!cs.equals(csChatSocket)&&csChatSocket!=null) {  
              csChatSocket.out(out);  
          }  
      }  
  }  
}  