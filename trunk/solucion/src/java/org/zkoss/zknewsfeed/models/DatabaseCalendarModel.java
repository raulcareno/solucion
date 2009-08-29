package org.zkoss.zknewsfeed.models;

import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zknewsfeed.data.NewsColors;
import org.zkoss.zknewsfeed.database.NewsDAO;
import org.zkoss.zul.SimplePieModel;

public final class DatabaseCalendarModel {
	
	//a static variable to the database functions
	public static NewsDAO dao = new NewsDAO();
	
	
	private SimpleCalendarModel _simpleCalendarModel;
	private SimplePieModel _simplePieModel;
	
	public DatabaseCalendarModel() {
		_simpleCalendarModel = new SimpleCalendarModel();
		_simplePieModel = new SimplePieModel();
		int counts[] = {0, 0, 0};
		java.util.List lst = dao.selectAll();
		for(int i=0; i<lst.size(); i++) {
			NewsItem ni = (NewsItem) lst.get(i);
			_simpleCalendarModel.add(ni);
			int colorPos = NewsColors.getColorPosition(ni.getContentColor());
			if(colorPos >-1) {
				counts[colorPos]++;
			}
		}
		for(int j=0; j<3; j++) {
			Integer count = new Integer(counts[j]);
			_simplePieModel.setValue(NewsColors._type[j], count);
		}
	}
	public  SimpleCalendarModel getSimpleCalendarModel() {
		return _simpleCalendarModel;
	}

	public SimplePieModel getSimplePieModel() {
		return _simplePieModel;
	}
}
