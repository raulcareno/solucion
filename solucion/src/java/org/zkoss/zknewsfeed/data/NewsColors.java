package org.zkoss.zknewsfeed.data;

public final class NewsColors {
	public static String[] _colors = {"#FF6F6F", "#6D8EFE", "#00E874","#FF9900",};
	public static String[] _type = {"Académica", "Reunión", "Recordatorio","Otro"};
	
	public static int getColorPosition(String color) {
		for(int i=0; i<_colors.length; i++) {
			if(color.equals(_colors[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static String getColorType(String color) {
		String ret = "";
		for(int i=0; i<_colors.length; i++) {
			if(color.equals(_colors[i])) {
				ret= _type[i];
			}
		}
		
		return ret;
	}
}
