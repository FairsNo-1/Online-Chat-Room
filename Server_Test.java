package Test;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Date;
import java.util.TimeZone;

public class Server_Test
{
	ServerSocket svrSkt = null;// 服务器套接字
	Socket cltSkt = null;// 服务器与客户端通信用套接字
	public Server_Test(int port) throws Exception
	{
		System.out.println("聊天室已经开启！！！");
		svrSkt = new ServerSocket(port); // 开始监听
		while (true) // 保持循环监听
		{
			cltSkt = svrSkt.accept(); // 接收连接请求
			new Thread(new MyRunnable(cltSkt)).start();
		}
	}
	
	class MyRunnable implements Runnable
	{
		BufferedReader br;
		PrintWriter pw;
		Socket socket;
		MyRunnable(Socket socket)
		{
			this.socket = socket;
			try
			{
				br = new BufferedReader(
						new InputStreamReader(socket.getInputStream())); // 获得输入流
				pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true); // 获得输出流
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{
			try
			{
				sendResponse("服务器信息为:" + InetAddress.getLocalHost().getHostName());
			}
			catch (UnknownHostException e1)
			{
				e1.printStackTrace();
			}
			String name =getRequest();
			System.out.println(name+"进入了聊天室~");
			while (true)
			{
				if (br != null && pw != null) // 流建立成功
				{
					
					String str = getRequest();//获取输入的第一个数据作为昵称
					if (str.equals("bye"))    //以bye作为退出聊天标识
					{
						try
						{
							pw.close();
							br.close();
							socket.close();
							System.out.println(name+"离开了聊天室~");
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						break;
					}
					else
					{
						System.out.println(name+":"+str);//打印出聊天信息
						Record_Test rec=new Record_Test();
						rec.recordMessage(name,str);//将聊天信息写入聊天记录文件
					}
				}
				else {
					System.out.println("连接已断开！");
				}
			}
		}

		public String getRequest()
		{// 获取从客户端发送的信息
			String frmClt = null;
			try
			{
				frmClt = br.readLine();//通过输入流中的readLine函数获得客户端的数据
			}
			catch (Exception e)
			{
				System.out.println("无法读取信息.....");
				System.exit(0);
			}
			return frmClt;
		}

		public void sendResponse(String message)
		{// 向客户端发送信息
			try
			{
				pw.println(message);
				pw.flush();// 把当前缓冲区中的输出流全部刷到通信管道中。
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		Server_Test sa = new Server_Test(8000);
	}
}
