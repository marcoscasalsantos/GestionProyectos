package es.coremain.coremainWeb.view.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "menuBarBean")
@SessionScoped
public class MenuBarBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String goUsuarios() {
		return "usuarios";
	}
	
	public String goTerritorio() {
		return "territorio";
	}
	
}
