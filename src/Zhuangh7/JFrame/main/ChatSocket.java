package Zhuangh7.JFrame.main;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;  
import java.net.Socket;  
  
public class ChatSocket extends Thread {  
      
    Socket socket;  
    public ChatSocket(Socket s){  
        this.socket = s;  
    }  
      
    public void out(String out) {  
        try {  
        	//PrintWriter os=new PrintWriter(socket.getOutputStream());
            socket.getOutputStream().write((out+"\n").getBytes("UTF-8"));  
        	//os.println(out);
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            MainClass.print("断开了一个客户端链接_out");  
            ChatManager.getChatManager().remove(this);  
            e.printStackTrace();  
        }  
    }  
  
    @Override  
    public void run() {  
    	if(ServerListener.Client_num<=ServerListener.max_client){
    		out("你已经连接到本服务器了"); 
    		out("你是第"+ServerListener.Client_num+"个客户");
    		if(ServerListener.Client_num==1){
    			out("等待客户2");
    			while(ServerListener.Client_num!=2){
    			//	out(""+ServerListener.Client_num);
    				try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			out("客户2加入到聊天");
    		}
    		else{
    			out("与客户1配对");
    		}
    		try {  
    			BufferedReader br = new BufferedReader(  
    					new InputStreamReader(  
    							socket.getInputStream(),"UTF-8"));  
    			String line = null;  
    			boolean tag = true;
    			while ((line = br.readLine()) != null && tag ) {
    				MainClass.print(line);  
    				ChatManager.getChatManager().publish(this, line);
    				if(line.equals("close")){
    					tag = false;
    				}
    			}  
	            br.close();
	            socket.close();
	            MainClass.print("断开了一个客户端链接_1");
	            ChatManager.getChatManager().remove(this);  
	            MainClass.print("剩余客户端"+ServerListener.Client_num);
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	        	MainClass.print("断开了一个客户端链接_ioerror");  
	            ChatManager.getChatManager().remove(this);  
	            e.printStackTrace();  
	        }  
    	}
    	else{
    		out("服务器已满！");
    		try {
				socket.close();//关闭连接
				ChatManager.getChatManager().remove(this);  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }  
}  