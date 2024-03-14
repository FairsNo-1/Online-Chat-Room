package Test;

//Server_TCPTest.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client_Test
{
	private Socket clientSk = null;// ��������ͻ���ͨ�����׽���
	private BufferedReader input = null; // �����������Կͻ���
	private PrintWriter output = null; // �������ָ��ͻ���
	private Scanner scanner = null;
	public Client_Test(int port)
	{
		try
		{
			clientSk = new Socket("127.0.0.1", port); // 127.0.0.1 ָ����
			input = new BufferedReader(
					new InputStreamReader(clientSk.getInputStream())); // ���������
			output = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(clientSk.getOutputStream())),
					true); // ��������
			scanner = new Scanner(System.in);//���մӼ������������
			System.out.println("�ɹ����������ң�");
			System.out.print("��������ǳƣ�");
			String string = getRequest();//����һ���ַ�����Ϊ����ǳ�
			while (true)
			{
				if (input != null && output != null) // �������ɹ�
				{
					String str = scanner.nextLine(); 
					sendResponse(str);// ��������������ݷ��͸��ͻ���
					if(str.equals("bye"))
						break;//��������˳������ʶbye��ֹͣ����
				}
				else
				{
					System.out.println("�����ҽ���ʧ�ܣ�");
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("����" + e.getMessage());
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
				System.out.println("���Ѿ��뿪�����ң�");//�ر��������������ʾ�˳�״̬
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getRequest()
	{// ��ȡ�ӿͻ��˷��͵���Ϣ
		String frmClt = null;
		try
		{
			frmClt = input.readLine();
		}
		catch (Exception e)
		{
			System.out.println("�޷������Ϣ");
			System.exit(0);
		}
		return frmClt;
	}

	public void sendResponse(String message)
	{// ��ͻ��˷�����Ϣ
		try
		{
			output.println(message);
			output.flush();// �ѵ�ǰ�������е������ȫ��ˢ��ͨ�Źܵ��С�
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

