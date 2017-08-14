package com.gtp.dubbo.api.utils;

import java.awt.Color;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.annotation.ApiMethod;
import com.gtp.dubbo.api.annotation.ApiService;
import com.gtp.dubbo.api.metadata.ApiParamInfo;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfParagraphStyle;

public class AutoBuildDoc {

	public static void build(String docPath, List<String> p) throws Exception {

		Document doc = new Document(PageSize.A4);
		RtfWriter2.getInstance(doc, new FileOutputStream(docPath));
		doc.open();
		
		//写全局约束
		writerGlobalRule(doc);

		Font font10 = getFont(12, Font.NORMAL,Color.BLACK);

		List<Class<?>> list = ScannerUtils.scan(p,ApiService.class);

		for (int i = 0; i < list.size(); i++) {

			Class<?> c = list.get(i);

			// h1
			ApiService apiService = c.getAnnotation(ApiService.class);
			doc.add(new Paragraph((i + 1) + " " + apiService.value(), RtfParagraphStyle.STYLE_HEADING_1));

			Method[] m = c.getDeclaredMethods();
			for (int j = 0; j < m.length; j++) {
				Method tm = m[j];
				if (Modifier.isPublic(tm.getModifiers()) && tm.getDeclaringClass() != Object.class) {

					// h2
					doc.add(new Paragraph(""));
					ApiMethod apiMethod = tm.getAnnotation(ApiMethod.class);
					if (apiMethod != null) {

						doc.add(new Paragraph((i + 1) + "." + (j + 1) + " " + apiMethod.desc(),RtfParagraphStyle.STYLE_HEADING_2));
						doc.add(new Paragraph(""));

						Table table = new Table(2);
						table.setWidth(32 * 3);
						table.setAlignment(Element.ALIGN_LEFT);
						table.setWidths(new float[] { 20f, 76f });

						// 方法编码
						Cell c1 = new Cell(new Phrase("服务编码", font10));
						table.addCell(c1);

						Cell c2 = new Cell(new Phrase(apiMethod.value(), font10));
						table.addCell(c2);

						// 输入						
						JSONObject args = new JSONObject();
						List<ApiParamInfo> params = ReflectUtils.getParameterInfo(tm);
						if(params!=null){
							for(ApiParamInfo p3:params){
								if(p3.getApiParam()!=null){
									if(p3.getIsList()){
										JSONArray tmpData=new JSONArray();
										tmpData.add(ReflectUtils.allFields(p3.getType()));
										args.put(p3.getApiParam().value(),tmpData);
									}else if(ReflectUtils.isBaseType(p3.getType())){
										args.put(p3.getApiParam().value(),p3.getApiParam().desc());
									}else{
										args.put(p3.getApiParam().value(), ReflectUtils.allFields(p3.getType()));
									}
								}
							}
						}
						String paramStr = JsonFormatUtils.formatJson(args.toJSONString());
						Cell c3 = new Cell(new Phrase("输入", font10));
						table.addCell(c3);

						Cell c4 = new Cell(new Phrase(paramStr, font10));
						table.addCell(c4);

						// 输出
						Cell c5 = new Cell(new Phrase("输出", font10));
						table.addCell(c5);
						
						ApiParamInfo returnParams = ReflectUtils.getReturnInfo(tm);
						if(returnParams.getIsList()){
							
							JSONObject returnJson = ReflectUtils.allFields(returnParams.getType());
							JSONArray returnData=new JSONArray();
							returnData.add(returnJson);
							String returnStr = JsonFormatUtils.formatJson(returnData.toJSONString());
							Cell c6 = new Cell(new Phrase(returnStr, font10));
							table.addCell(c6);
							
						}else{
							JSONObject returnJson = ReflectUtils.allFields(returnParams.getType());
							String returnStr = JsonFormatUtils.formatJson(returnJson.toJSONString());
							Cell c6 = new Cell(new Phrase(returnStr, font10));
							table.addCell(c6);
						}
						
						doc.add(table);
					}
				}
			}
		}

		doc.close();
	}

	private static void writerGlobalRule(Document doc) throws DocumentException {
		
		//标题
		doc.add(new Paragraph("文档约定(说明)", RtfParagraphStyle.STYLE_HEADING_1));
		doc.add(new Paragraph(""));
		Font font10 = getFont(12, Font.NORMAL,Color.BLACK);
		
		//输入
		doc.add(new Paragraph("#输入格式",font10));
		
		JSONObject p1=new JSONObject();
		p1.put("serviceModule", "模块名称");
		p1.put("serviceNumber", "方法编码");
		p1.put("token","登录后获得的访问凭证");
		p1.put("args", "业务逻辑约定参数");
		
		doc.add(new Paragraph(JsonFormatUtils.formatJson(p1.toJSONString()),font10));
		doc.add(new Paragraph(""));
		
		JSONObject args=new JSONObject();
		args.put("xx1","参数1");
		args.put("xxN","参数N");
		args.put("token","登录后获得的访问凭证");
		
		doc.add(new Paragraph("*args说明:",font10));
		doc.add(new Paragraph(JsonFormatUtils.formatJson(args.toJSONString()),font10));
		
		//输出
		doc.add(new Paragraph(""));
		doc.add(new Paragraph("#输出格式",font10));
		
		JSONObject p2=new JSONObject();
		p2.put("errorMessage", "发生错误时的说明");
		p2.put("opFlag", "true/false接口逻辑处理结果");
		p2.put("serviceResult","业务逻辑返回数据");

		doc.add(new Paragraph(JsonFormatUtils.formatJson(p2.toJSONString()),font10));
		doc.add(new Paragraph(""));
		
		JSONObject serviceResult=new JSONObject();
		serviceResult.put("flag","true/false接口逻辑处理结果");
		serviceResult.put("error","错误时的说明");
		serviceResult.put("result","业务数据");
		serviceResult.put("stime","服务器当前时间戳");
				
		doc.add(new Paragraph("*serviceResult说明:",font10));
		doc.add(new Paragraph(JsonFormatUtils.formatJson(serviceResult.toJSONString()),font10));
		doc.add(new Paragraph(""));
		
		JSONObject result=new JSONObject();
		result.put("total","总记录数");
		result.put("data","业务数据");
		result.put("currentPage","当前页码");
		result.put("pageSize","每页显示条数");
		
		doc.add(new Paragraph("*分页result说明:",font10));
		doc.add(new Paragraph(JsonFormatUtils.formatJson(result.toJSONString()),font10));
		doc.add(new Paragraph(""));
		
		//说明
		Font redFont = getFont(12, Font.NORMAL,Color.RED);
		doc.add(new Paragraph("#注意:",redFont));
		doc.add(new Paragraph("*下文文档中为了简练和减少冗余,只列出必要部分",redFont));
		doc.add(new Paragraph("1.输入只列出args部分",redFont));
		doc.add(new Paragraph("2.输出只列出serviceResult.result部分",redFont));
		doc.add(new Paragraph("3.方法编码会列出",redFont));
		doc.add(new Paragraph(""));
	}

	private static Font getFont(int fontSize,int fontStyle,Color c) {
		Font chineseFont = null;
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			chineseFont = new Font(bfChinese, fontSize, fontStyle,c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chineseFont;
	}
}
