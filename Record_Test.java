package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Record_Test {
	public void recordMessage(String name,String str) 
	{//聊天记录保存
		try
		{
			FileWriter fw=new FileWriter("D:\\桌面\\聊天记录.txt",true);
			//构建写入文件，true表示从文件最后面写入
			BufferedWriter bw=new BufferedWriter(fw);
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			//定义时区，可以避免虚拟机时间与系统时间不一致的问题
		    Date nowTime = new Date();
		    SimpleDateFormat matter = new SimpleDateFormat(
		                "yyyy年MM月dd日E HH时mm分ss秒");
		    bw.write(matter.format(nowTime)+" ");//写入聊天内容发送的时间
			bw.write(name+":"+str);//写入聊天记录
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
