package com.mlokos.online_nutritionist.view;

public class HtmlRecipeRow {
	public static String getTemplate() {
		return template;
	}
	
	private static String template = "" +
			"<form action=\"search/$row_id\" method=\"POST\">\n" + 
			"    <div class=\"columns\">\n" + 
			"        <div class=\"column is-3\">\n" + 
			"            <div class=\"field is-vertical\">\n" + 
			"                <div class=\"field-label is-flex is-horizontal-center\">\n" + 
			"                    <figure class=\"image is-128x128 is-horizontal-center\">\n" + 
			"                        <img src=\"$imageURL\">\n" + 
			"                    </figure>\n" + 
			"                </div>\n" + 
			"                <div class=\"field-body is-flex is-horizontal-center has-text-centered\">\n" + 
			"                    <div class=\"label\">\n" + 
			"                        <label class=\"label\">$name</label>\n" + 
			"                    </div>\n" + 
			"                </div>\n" + 
			"            </div>\n" + 
			"        </div>\n" + 
			"        <div class=\"column is-5\">\n" + 
			"            <div class=\"field is-vertical\">\n" + 
			"                <div class=\"field-label is-normal level-left\">\n" + 
			"                    <label class=\"label\">Ingredients</label>\n" + 
			"                </div>\n" + 
			"                <div class=\"field-body\">\n" + 
			"                    <div class=\"field\">\n" + 
			"                        $ingredients\n" + 
			"                    </div>\n" + 
			"                </div>\n" + 
			"            </div>\n" + 
			"        </div>\n" + 
			"        <div class=\"column is-3\">\n" + 
			"            <div class=\"field is-vertical\">\n" + 
			"                <a href=\"$linkURL\">$linkURL</a>\n" + 
			"            </div>\n" + 
			"        </div>\n" + 
			"        <div class=\"column is-1\">\n" + 
			"            <input type=\"submit\" class=\"button is-primary\" value=\"Save\">\n" + 
			"        </div>\n" + 
			"    </div>\n" + 
			"</form>";
}
