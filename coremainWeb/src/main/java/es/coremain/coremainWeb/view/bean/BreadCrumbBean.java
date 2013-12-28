package es.coremain.coremainWeb.view.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;


@ManagedBean(name = "breadCrumbBean")
@ViewScoped
public class BreadCrumbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private MenuModel model;

		
	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public void addItem(String value, String url, String id) {
		if (model == null)
			model = new DefaultMenuModel();
		
		MenuItem item = new MenuItem();

		item.setValue(value);
		item.setUrl(url);
		item.setId(id);

		this.model.addMenuItem(item);
	}

	public void addItem(MenuItem item) {
		if (model == null)
			model = new DefaultMenuModel();
		
		this.model.addMenuItem(item);
	}	
	
	
	public String goMainMenu() {				
		return "menuPrincipal";
	}		
	
}
