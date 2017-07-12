package doc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gtp.dubbo.api.utils.AutoBuildDoc;

/**
 * 获得字段的时候，还得用
 getFields()获得某个类的所有的公共（public）的字段，包括父类。 
getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。 
 * @author gaotingping@cyberzone.cn
 *
 */
public class Test1 {

	@Test
	public void test2() throws Exception{
		
		List<String> p=new ArrayList<>();
		p.add("com.gtp.service");
		
		AutoBuildDoc.build("E:/myWord3.doc", p);
	}
}
