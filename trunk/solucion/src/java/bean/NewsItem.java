/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import org.zkoss.calendar.impl.SimpleCalendarEvent;

/**
 *
 * @author geovanny
 */
public class NewsItem extends SimpleCalendarEvent {
//org.zkoss.zknewsfeed.controllers.NewsController
    //NewsController a;
	private int news_item;

	public int getNews_item() {
		return news_item;
	}

	public void setNews_item(int id) {
		news_item = id;
	}

	public NewsItem() {

	}
}