package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Record_Test {
	public void recordMessage(String name,String str) 
	{//�����¼����
		try
		{
			FileWriter fw=new FileWriter("D:\\����\\�����¼.txt",true);
			//����д���ļ���true��ʾ���ļ������д��
			BufferedWriter bw=new BufferedWriter(fw);
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			//����ʱ�������Ա��������ʱ����ϵͳʱ�䲻һ�µ�����
		    Date nowTime = new Date();
		    SimpleDateFormat matter = new SimpleDateFormat(
		                "yyyy��MM��dd��E HHʱmm��ss��");
		    bw.write(matter.format(nowTime)+" ");//д���������ݷ��͵�ʱ��
			bw.write(name+":"+str);//д�������¼
			bw.flush();
			bw.newLine();
			bw.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
