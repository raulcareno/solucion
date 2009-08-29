 package org.zkoss.zknewsfeed.database;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jcinform.persistencia.Tblnews;
import jcinform.procesos.Administrador;
import org.zkoss.zknewsfeed.models.NewsItem;


public class NewsDAO {
 
       	Administrador adm;
	public NewsDAO() {
         adm = new Administrador();
	}
	
	public List selectAll() {
		List allObjects = new ArrayList();
			List<Tblnews> fechas = adm.query("Select o from Tblnews as o ");
            NewsItem ni;
            for (Iterator<Tblnews> it = fechas.iterator(); it.hasNext();) {
                Tblnews item = it.next();
                 ni = new NewsItem();
				Date dt = new Date();
				ni.setNews_item(item.getNewsItem());
				dt.setTime(item.getDateBegin());
				ni.setBeginDate((Date)dt.clone());
				dt.setTime(item.getDateEnd());
				ni.setEndDate((Date)dt.clone());

				ni.setTitle(item.getTitle());
				ni.setContent(item.getContent());
				ni.setHeaderColor(item.getHeaderColor());
				ni.setContentColor(item.getContentColor());
				ni.setLocked(item.getIsLocked());
				allObjects.add(ni);
            }
		return allObjects;
	}



	public boolean updateNewsItem(NewsItem ni) {
		boolean result = false;
            Tblnews t = new Tblnews();
            t.setContent(ni.getContent());
            t.setContentColor(ni.getContentColor());
            t.setDateBegin(ni.getBeginDate().getTime());
            t.setDateEnd(ni.getEndDate().getTime());
            t.setHeaderColor(ni.getHeaderColor());
            t.setIsLocked(false);
            t.setTitle(ni.getTitle());
            t.setNewsItem(ni.getNews_item());
            adm.actualizar(t);
			result = true;

		return result;		
	}
	
	public boolean insertNewsItem(NewsItem ni) {
            boolean result = false;
            Tblnews t = new Tblnews();
            t.setContent(ni.getContent());
            t.setContentColor(ni.getContentColor());
            t.setDateBegin(ni.getBeginDate().getTime());
            t.setDateEnd(ni.getEndDate().getTime());
            t.setHeaderColor(ni.getHeaderColor());
            t.setIsLocked(false);
            t.setTitle(ni.getTitle());
            t.setNewsItem(adm.getNuevaClave("Tblnews", "newsItem"));
            adm.guardar(t);
			result = true;
		return result;
	}
	
	public boolean deleteNewsItem(NewsItem ni) {
		boolean result = false;
			Administrador adm = new Administrador();
            adm.eliminarObjeto(Tblnews.class, ni.getNews_item());
		return result;
		
	}
}
