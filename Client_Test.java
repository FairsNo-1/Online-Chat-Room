package Test;

//Server_TCPTest.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client_Test
{
	private Socket clientSk = null;// 服务器与客户端通信用套接字
	private BufferedReader input = null; // 输入流，来自客户端
	private PrintWriter output = null; // 输出流，指向客户端
	private Scanner scanner = null;
	public Client_Test(int port)
	{
		try
		{
			clientSk = new Socket("127.0.0.1", port); // 127.0.0.1 指本机
			input = new BufferedReader(
					new InputStreamReader(clientSk.getInputStream())); // 获得输入流
			output = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(clientSk.getOutputStream())),
					true); // 获得输出流
			scanner = new Scanner(System.in);//接收从键盘输入的数据
			System.out.println("成功加入聊天室！");
			System.out.print("输入你的昵称：");
			String string = getRequest();//输入一个字符串作为你的昵称
			while (true)
			{
				if (input != null && output != null) // 流建立成功
				{
					String str = scanner.nextLine(); 
					sendResponse(str);// 将键盘输入的数据发送给客户端
					if(str.equals("bye"))
						break;//如果输入退出聊天标识bye则停止发送
				}
				else
				{
					System.out.println("聊天室进入失败！");
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("错误：" + e.getMessage());
		}
		finally
		{
			try
			{
				if(output!=null)
				    output.close();
				if(input!=null)
					input.close();
				if(scanner!=null)
					scanner.close();
				if(clientSk!=null)
					clientSk.close();
				System.out.println("你已经离开聊天室！");//关闭输入输出流，显示退出状态
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getRequest()
	{// 获取从客户端发送的信息
		String frmClt = null;
		try
		{
			frmClt = input.readLine();
		}
		catch (Exception e)
		{
			System.out.println("无法获得信息");
			System.exit(0);
		}
		return frmClt;
	}

	public void sendResponse(String message)
	{// 向客户端发送信息
		try
		{
			output.println(message);
			output.flush();// 把当前缓冲区中的输出流全部刷到通信管道中。
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		Client_Test sa = new Client_Test(8000);
	}
}

