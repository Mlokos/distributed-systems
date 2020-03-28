package com.mlokos.online_nutritionist.view;

public class HtmlBackbone {
	public static String getTemplate() {
		return template;
	}
	
	private static String template = "" +
			"<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"    <meta charset=\"utf-8\" />\n" + 
			"    <title>Online Nutritionist</title>\n" + 
			"    <link rel=\"stylesheet\" href=\"../css/bulma-0.8.0/css/bulma.min.css\">\n" + 
			"    <style>\n" + 
			HtmlCssStyle.getTemplate() +
			"        body {\n" + 
			"            margin: 30px 0;\n" + 
			"        }\n" + 
			"        .columns {\n" + 
			"            margin-left: 0;\n" + 
			"            margin-right: 0;\n" + 
			"        }\n" + 
			"        .is-horizontal-center {\n" + 
			"            justify-content: center;\n" + 
			"        }\n" + 
			"    </style>\n" + 
			"</head>\n" + 
			"<body>\n" + 
			"$search_row\n" + 
			"$recipe_rows\n" +
			"</body>\n" + 
			"</html>";
}
