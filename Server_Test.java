package Test;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Date;
import java.util.TimeZone;

public class Server_Test
{
	ServerSocket svrSkt = null;// �������׽���
	Socket cltSkt = null;// ��������ͻ���ͨ�����׽���
	public Server_Test(int port) throws Exception
	{
		System.out.println("�������Ѿ�����������");
		svrSkt = new ServerSocket(port); // ��ʼ����
		while (true) // ����ѭ������
		{
			cltSkt = svrSkt.accept(); // ������������
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
						new InputStreamReader(socket.getInputStream())); // ���������
				pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true); // ��������
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
				sendResponse("��������ϢΪ:" + InetAddress.getLocalHost().getHostName());
			}
			catch (UnknownHostException e1)
			{
				e1.printStackTrace();
			}
			String name =getRequest();
			System.out.println(name+"������������~");
			while (true)
			{
				if (br != null && pw != null) // �������ɹ�
				{
					
					String str = getRequest();//��ȡ����ĵ�һ��������Ϊ�ǳ�
					if (str.equals("bye"))    //��bye��Ϊ�˳������ʶ
					{
						try
						{
							pw.close();
							br.close();
							socket.close();
							System.out.println(name+"�뿪��������~");
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						break;
					}
					else
					{
						System.out.println(name+":"+str);//��ӡ��������Ϣ
						Record_Test rec=new Record_Test();
						rec.recordMessage(name,str);//��������Ϣд�������¼�ļ�
					}
				}
				else {
					System.out.println("�����ѶϿ���");
				}
			}
		}

		public String getRequest()
		{// ��ȡ�ӿͻ��˷��͵���Ϣ
			String frmClt = null;
			try
			{
				frmClt = br.readLine();//ͨ���������е�readLine������ÿͻ��˵�����
			}
			catch (Exception e)
			{
				System.out.println("�޷���ȡ��Ϣ.....");
				System.exit(0);
			}
			return frmClt;
		}

		public void sendResponse(String message)
		{// ��ͻ��˷�����Ϣ
			try
			{
				pw.println(message);
				pw.flush();// �ѵ�ǰ�������е������ȫ��ˢ��ͨ�Źܵ��С�
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
