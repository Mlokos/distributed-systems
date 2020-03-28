package com.mlokos.online_nutritionist.view;

public class HtmlSearchRow {
	public static String getTemplate() {
		return template;
	}
	
	private static String template = "\n" + 
			"    <form action=\"search\" method=\"POST\">\n" + 
			"        <div class=\"columns\">\n" + 
			"            <div class=\"column is-6\">\n" + 
			"                <div class=\"field is-horizontal\">\n" + 
			"                    <div class=\"field-label is-normal\">\n" + 
			"                        <!-- &nbsp used as a space without splitting words -->\n" + 
			"                        <label class=\"label\">Meal&nbspname</label>\n" + 
			"                    </div>\n" + 
			"                    <div class=\"field-body\">\n" + 
			"                        <div class=\"field\">\n" + 
			"                            <p class=\"control\">\n" + 
			"                                <input class=\"input\" type=\"text\" name=\"meal\" placeholder=\"ramen\">\n" + 
			"                            </p>\n" + 
			"                        </div>\n" + 
			"                    </div>\n" + 
			"                </div>\n" + 
			"                <div class=\"field is-horizontal\">\n" + 
			"                    <div class=\"field-label is-normal\">\n" + 
			"                        <label class=\"label\">Ingredients</label>\n" + 
			"                    </div>\n" + 
			"                    <div class=\"field-body\">\n" + 
			"                        <div class=\"field\">\n" + 
			"                            <p class=\"control\">\n" + 
			"                                <input class=\"input\" type=\"text\" name=\"ingredients\" placeholder=\"eggs, becon, . . .\">\n" + 
			"                            </p>\n" + 
			"                        </div>\n" + 
			"                    </div>\n" + 
			"                </div>\n" + 
			"            </div>\n" + 
			"            <div class=\"column is-3\">\n" + 
			"                <div class=\"field is-vertical\">\n" + 
			"                    <input type=\"submit\" class=\"button is-primary\" value=\"Search\">\n" + 
			"                </div>\n" + 
			"            </div>\n" + 
			"        </div>\n" + 
			"    </form>\n" + 
			"    <br>\n" + 
			"    <form action=\"recepies\" method=\"GET\">\n" + 
			"        <div class=\"columns\">\n" + 
			"            <div class=\"column is-3\">\n" + 
			"                <div class=\"field is-vertical\">\n" + 
			"                    <input type=\"submit\" class=\"button is-primary\" value=\"My Recepies\">\n" + 
			"                </input>\n" + 
			"                </div>\n" + 
			"            </div>\n" + 
			"        </div>\n" + 
			"    </form>\n" + 
			"    <br>";
}
