package es.coremain.coremainWeb.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "menuProyectoBean")
@SessionScoped
public class MenuProyectoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public class MenuList implements Serializable{

		private static final long serialVersionUID = 1L;
			private String id;
	        private String name;

	        public String getId() {
	            return id;
	        }

	        public void setId(String id) {
	            this.id = id;
	        }

	        public String getName() {
	            return name;
	        }

	        public void setName(String name) {
	            this.name = name;
	        }

	 }
	
    public class MenuTab implements Serializable {

		private static final long serialVersionUID = 1L;
		private String id;
        private String title;
        private List<MenuList> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MenuList> getList() {
            return list;
        }

        public void setList(List<MenuList> list) {
            this.list = list;
        }
    }
    
    private List<MenuTab> menus = new ArrayList<MenuTab>();

    public List<MenuTab> getMenus() {
        return menus;
    }
    
    @SuppressWarnings("unused")
	@PostConstruct
    private void initModel() {

        List<MenuList> list;
        MenuTab tab;
        MenuList item;
        
        list = new ArrayList<MenuList>();
        tab = new MenuTab();
        tab.setId("tab_proyectos");
        tab.setTitle("Proyectos");
        
        //Añadiendo opciones de menu a la pestaña
        item = new MenuList();
        item.setId("selec_project");
        item.setName("Seleccionar Proyecto");
        list.add(item);
        
        item = new MenuList();
        item.setId("new_project");
        item.setName("Crear Proyecto");
        list.add(item);
        
        item = new MenuList();
        item.setId("modify_project");
        item.setName("Modificar Proyecto");
        list.add(item);
        
        item = new MenuList();
        item.setId("team_project");
        item.setName("Equipo Proyecto");
        list.add(item);
         
        item = new MenuList();
        item.setId("seguimiento_project");
        item.setName("Seguimiento");
        list.add(item);
        
        tab.setList(list);
        menus.add(tab);
        
        
       /* for(int i=1; i< 4; i++){
                list = new ArrayList<MenuList>();
                tab = new MenuTab();        
                tab.setId("tab" + i);
                tab.setTitle("Menu "+i);

                for(int j=1; j<5; j++){
                      item = new MenuList();
                      item.setId(id);
                      item.setName("Submenu "+i+" - "+j);
        
                      list.add(item);
               }

               tab.setList(list);
               menus.add(tab);

       }*/

    }
	
	
}