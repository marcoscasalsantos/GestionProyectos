package es.coremain.coremainWeb.view.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import org.apache.myfaces.view.facelets.el.MethodExpressionMethodExpression;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import es.coremain.coremainWeb.view.bean.project.ActualProjectBean;
import es.coremain.coremainWeb.view.bean.project.ProjectBean;
import es.coremain.coremainWeb.view.bean.tareas.TareaBean;
import es.coremain.coremainWeb.view.util.FacesUtils;
import es.uvigo.esei.pfc.eseiPfcProjectService.dto.ProjectDto;
import es.uvigo.esei.pfc.eseiPfcProjectService.service.ProjectService;


@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int tabIndex;
    
    private MenuModel model;
    
    private Submenu firstSubMenu;
    
    private String opcionSeleccionada;
    
    private String idComponente;
    
    public String getOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	public void setOpcionSeleccionada(String opcionSeleccionada) {
		this.opcionSeleccionada = opcionSeleccionada;
	}

	//El menu será dinamico y según los roles de cada usuario se mostrarán unas opciones u otras
    List<ProjectDto> projects;
    
    //***************IMPORTANTE*****************************************
    //Para quitar actualPRojectBean podria usar una propieda asi:
    //ProjectDto proyectoSeleccionado;
    //Y trabajaria con ella en todos lados pq menuBean es SessionScoped
    //Es tonteria tener tantos sessionScoped y que hagan lo mismo
    
    public List<ProjectDto> getProjects() {
    	
    	ProjectBean proyecto = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
    	projects = proyecto.getProjects();
    	
    	return projects;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}

	public MenuBean(){
    	tabIndex = 100; //set the initial tab index to 100 so all tabs are closed when page loads
    	
    	construirMenu();
    	
    	//this.idComponente = "1";
    	
    	/*model = new DefaultMenuModel();
    	
    	
    	FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = facesCtx.getELContext();
    	ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();
    	
    	MethodExpression methodExpressionTarea = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTarea}", null, new Class[]{ActionEvent.class});
    	MethodExpression methodExpressionProyecto = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarProyecto}", null, new Class[]{ActionEvent.class});
    	//Devuelve un String y le paso 0 argumentos de parametro
    	MethodExpression methodExpressionTareaAction = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTareaAction}", String.class, new Class[0]);
    	    	
    	
    	MethodExpressionActionListener actionListenerSeleccionarTarea = new MethodExpressionActionListener(methodExpressionTarea);
    	MethodExpressionActionListener actionListenerSeleccionarProyecto = new MethodExpressionActionListener(methodExpressionProyecto);
    	
    	
    	
    	//LoginBean loginBean = FacesUtils.getManagedBean("loginBean", LoginBean.class);
    	
    	//ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	//List<ProjectDto> projects = projectService.getProjectsByUserLoggedIn(loginBean.getUsername(), loginBean.getPassword());
    	
    	
    	Submenu firstSubMenu = new Submenu();
    	firstSubMenu.setLabel("PROYECTOS");
    	firstSubMenu.setId("proyectos_id");
    	model.addSubmenu(firstSubMenu);
    	
    	MenuItem nuevoProyecto = new MenuItem();
    	nuevoProyecto.setValue("+ Nuevo");
    	nuevoProyecto.setStyle("font-weight:bold; font-style:italic; float:right");
    	nuevoProyecto.setTitle("Crear un nuevo proyecto");
    	nuevoProyecto.setUrl("/pages/proyecto/nuevoProyecto.xhtml");
    	nuevoProyecto.setUpdate(":contenidoCentro");
    	firstSubMenu.getChildren().add(nuevoProyecto);
    	
    	
    	
    	ProjectBean proyecto = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
    	projects = proyecto.getProjects();
    	for(ProjectDto project : projects){
    		
    		Submenu subMenu = new Submenu();
            subMenu.setLabel(project.getNombreProyecto());
            firstSubMenu.getChildren().add(subMenu);
            
            MenuItem tareas = new MenuItem();
            tareas.setValue("Tareas");
            tareas.setTitle(project.getNombreProyecto()+">Tareas");
            tareas.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
            tareas.addActionListener(actionListenerSeleccionarTarea);
            tareas.setActionExpression(methodExpressionTareaAction);
            tareas.setUpdate(":contenidoCentro"); //layoutUnit id: The component you wanna update with the ajax request
            tareas.setUpdate(":norte");
            subMenu.getChildren().add(tareas);
            
            MenuItem notas = new MenuItem();
            notas.setValue("Notas");
            notas.setUrl("/pages/tareas/tareas.xhtml");
            subMenu.getChildren().add(notas);                        
            
            MenuItem conversaciones = new MenuItem();
            conversaciones.setValue("Conversaciones");
            subMenu.getChildren().add(conversaciones);
            
            MenuItem gente = new MenuItem();
            gente.setValue("Gente y permisos");
            subMenu.getChildren().add(gente);
            
            MenuItem configuracion =  new MenuItem();
            configuracion.setValue("Configuracion");
            subMenu.getChildren().add(configuracion);
            
            MenuItem control = new MenuItem();
            control.setValue("Control del proyecto");
            subMenu.getChildren().add(control);
    		
    	}*/
     	
    }
	
	public int getTabIndex(){
		return tabIndex;
	}
	
	public void setTabIndex(int tabIndex){
		this.tabIndex = tabIndex;
	}
	
	public String nuevoUsuario(){
		//Habria que asignar selectedUSer = null para que al hacer click en nuevo Usuario
		//no se muestren los datos del ultimo usuario seleccionado
		//Con flash Scope esto estaria solucionado pq User Bean tendría ambito de vista y asi al hacer click
		//en nuevo USuario ya no se cargarían los datos del usuario,solo en las paginas que nosotros queramos.
		
		return "newUser";
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public String seleccionarTareaAction(){
		
		//System.out.println("***********************PULSADO************ ACTION **************");
		return "tareas";
	}
	
	public String irConfiguracion(){
		
		//System.out.println("*********************CONFIGURACION:"+event.toString()+"**********************");
		return "detalleProyecto";
	}
	
	public void seleccionarConfiguracion(ActionEvent event){
		
		System.out.println("*********************CONFIGURACION:"+((MenuItem)event.getComponent()).getValue()+"**********************");
		
		System.out.println("*********************Source:"+((Submenu)event.getComponent().getParent()).getLabel()+"**********************");
		
		String nombreProyecto = ((Submenu)event.getComponent().getParent()).getLabel();
		
		this.projects = this.getProjects();
		
		for (ProjectDto project : projects){
			
			if (project.getNombreProyecto().equals(nombreProyecto)){
				
				//ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
				//actualProjectBean.setProyectoActual(project);
	    	    //actualProjectBean.selectActualProject();
				//ProjectBean projectBean = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
				//projectBean.setSelectedProject(project);
				
				//Subo al flashScope como proyectoSeleccionado project,que es el proyecto seleccionado al pusar
				//configuracion--> al iniciar projectBean, se recupera del flashScope selectedProject 
				//del constructor
				FacesUtils.flashScope().put("selectedProject", project);
			}
			
		}
		
	}
	
	
	
	public void seleccionarTarea(ActionEvent event){
	//public String seleccionarTarea(){
		
		System.out.println("*****************PULSADO Listener****************************************************");
		
		MenuItem selectedMenuItem = (MenuItem) event.getComponent();
		//String idtoidentify = selectedMenuItem.getAttributes().get("idobject").toString() ;
		String idtoidentify = selectedMenuItem.getTitle();
		
		int index = idtoidentify.indexOf(">");
		String nombreProyecto = idtoidentify.substring(0, index);
		
		
		for (ProjectDto proyecto : projects){
			
			if (proyecto.getNombreProyecto().equals(nombreProyecto)){
	
				//ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
				//proyecto.setTareas(projectService.getAllTareas(proyecto.getProjectId()));
				//proyecto.setTareas(tareas.getTareas());
				
				ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
				actualProjectBean.setProyectoActual(proyecto);
	    	    actualProjectBean.selectActualProject();
	    	    this.setOpcionSeleccionada(idtoidentify);
	    	    System.out.println("PRoyecto actual actualizado"+" Id:"+proyecto.getProjectId());
	    	    //System.out.println("TAREAS"+ proyecto.getTareas());
			}
			
		}
		
		//Tendria que hacer una consulta y traerme el proyecto ProjectDto que concuerda con ese nombre de proyecto
		//Usar StringBuilder pàra las concatenaciones-->es mas eficaz que el "+",no consume tantos recursos
		    
		
		//Submenu selectedSubMenu = (Submenu) event.getComponent();
		//idtoidentify += " "+ selectedSubMenu.getLabel();
		System.out.println("*****************PULSADOO**"+idtoidentify +"********************"+nombreProyecto+"****");
	
		//return "listaTareas";
	}
	
	
	public void construirMenu(){
		
		model = new DefaultMenuModel();
		
		FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = facesCtx.getELContext();
    	ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();
    	
    	//Creo un MethodExpressio que devuelve un String y le paso 0 argumentos de parametro
    	MethodExpression methodExpressionConfiguracionAction = expFact.createMethodExpression(elCtx, "#{menuBean.irConfiguracion}", String.class, new Class[0]);
    	MethodExpression methodExpressionTareaAction = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTareaAction}", String.class, new Class[0]);
    	MethodExpression methodExpressionTarea = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTarea}", null, new Class[]{ActionEvent.class});
    	MethodExpression methodExpressionConfiguracion = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarConfiguracion}", null, new Class[]{ActionEvent.class});
    	
    	MethodExpressionActionListener actionListenerSeleccionarTarea = new MethodExpressionActionListener(methodExpressionTarea);
    	MethodExpressionActionListener actionListenerConfiguracion = new MethodExpressionActionListener(methodExpressionConfiguracion);
    	
    	firstSubMenu = new Submenu();
    	firstSubMenu.setLabel("PROYECTOS");
    	firstSubMenu.setId("proyectos_id");
    	model.addSubmenu(firstSubMenu);
    	 	
    	MenuItem nuevoProyecto = new MenuItem();
    	nuevoProyecto.setValue("+ Nuevo");
    	nuevoProyecto.setStyle("font-weight:bold; font-style:italic; float:right");
    	nuevoProyecto.setTitle("Crear un nuevo proyecto");
    	nuevoProyecto.setUrl("/pages/proyecto/nuevoProyecto.xhtml");
    	//nuevoProyecto.setUpdate(":contenidoCentro");
    	firstSubMenu.getChildren().add(nuevoProyecto);
    	
    	
    	//ProjectBean proyecto = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
    	//projects = proyecto.getProjects();
    	this.projects = this.getProjects();
    	for(ProjectDto project : projects){
    		
    		this.asignarIdComponente();
    		
    		Submenu subMenu = new Submenu();
            subMenu.setLabel(project.getNombreProyecto());
            subMenu.setId("proyecto_"+this.idComponente);
            //subMenu.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
            firstSubMenu.getChildren().add(subMenu);
            
            MenuItem tareas = new MenuItem();
            tareas.setValue("Tareas");
            tareas.setTitle(project.getNombreProyecto()+">Tareas");
            tareas.setId("tareas_"+this.idComponente);
            //tareas.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
            tareas.addActionListener(actionListenerSeleccionarTarea);
            tareas.setActionExpression(methodExpressionTareaAction);
            //tareas.setUpdate(":contenidoCentro"); //layoutUnit id: The component you wanna update with the ajax request
            //tareas.setUpdate(":norte");
            //tareas.setUpdate(":cabeceraForm:texto");
            subMenu.getChildren().add(tareas);
            
            MenuItem notas = new MenuItem();
            notas.setValue("Notas");
            notas.setId("notas_"+this.idComponente);
            notas.setUrl("/pages/tareas/tareas.xhtml");
            subMenu.getChildren().add(notas);                        
            
            MenuItem conversaciones = new MenuItem();
            conversaciones.setValue("Conversaciones");
            conversaciones.setId("conversaciones_"+this.idComponente);
            subMenu.getChildren().add(conversaciones);
            
            MenuItem gente = new MenuItem();
            gente.setValue("Gente y permisos");
            gente.setId("gente_"+this.idComponente);
            subMenu.getChildren().add(gente);
            
            MenuItem configuracion =  new MenuItem();
            configuracion.setValue("Configuracion");
            configuracion.setTitle("Editar los datos del proyecto");
            configuracion.setId("configuracion_"+this.idComponente);
            //configuracion.setId(project.getNombreProyecto()+"_Configuracion");
            configuracion.addActionListener(actionListenerConfiguracion);
            configuracion.setActionExpression(methodExpressionConfiguracionAction);
            //configuracion.setUrl("/pages/proyecto/detalleProyecto.xhtml");
            subMenu.getChildren().add(configuracion);
            
            MenuItem control = new MenuItem();
            control.setValue("Control del proyecto");
            control.setId("control_"+this.idComponente);
            subMenu.getChildren().add(control);
            
            
    	}
		
	}
	
	
	public void añadirSubMenu(String nombre){
		
		FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = facesCtx.getELContext();
    	ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();
    	
    	//Creo un MethodExpressio que devuelve un String y le paso 0 argumentos de parametro
    	MethodExpression methodExpressionTareaAction = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTareaAction}", String.class, new Class[0]);
    	MethodExpression methodExpressionTarea = expFact.createMethodExpression(elCtx, "#{menuBean.seleccionarTarea}", null, new Class[]{ActionEvent.class});
    	
    	MethodExpressionActionListener actionListenerSeleccionarTarea = new MethodExpressionActionListener(methodExpressionTarea);
		
		this.asignarIdComponente();
    	
    	Submenu nuevo = new Submenu();
		nuevo.setLabel(nombre);
		nuevo.setId("proyecto_"+this.idComponente);
		//nuevo.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
		firstSubMenu.getChildren().add(nuevo);
		
		MenuItem tareas = new MenuItem();
        tareas.setValue("Tareas");
        tareas.setTitle(nombre+">Tareas");
        tareas.setId("tareas_"+this.idComponente);
        //tareas.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
        tareas.addActionListener(actionListenerSeleccionarTarea);
        tareas.setActionExpression(methodExpressionTareaAction);
        tareas.setUpdate(":contenidoCentro"); //layoutUnit id: The component you wanna update with the ajax request
        tareas.setUpdate(":norte");
        nuevo.getChildren().add(tareas);
        
        MenuItem notas = new MenuItem();
        notas.setValue("Notas");
        notas.setId("notas_"+this.idComponente);
        notas.setUrl("/pages/tareas/tareas.xhtml");
        nuevo.getChildren().add(notas);                        
        
        MenuItem conversaciones = new MenuItem();
        conversaciones.setValue("Conversaciones");
        conversaciones.setId("conversaciones_"+this.idComponente);
        nuevo.getChildren().add(conversaciones);
        
        MenuItem gente = new MenuItem();
        gente.setValue("Gente y permisos");
        gente.setId("gente_"+this.idComponente);
        nuevo.getChildren().add(gente);
        
        MenuItem configuracion =  new MenuItem();
        configuracion.setValue("Configuracion");
        configuracion.setId("configuracion_"+this.idComponente);
        nuevo.getChildren().add(configuracion);
        
        MenuItem control = new MenuItem();
        control.setValue("Control del proyecto");
        control.setId("control_"+this.idComponente);
        nuevo.getChildren().add(control);
		
	}
	
	public void borrarSubMenu(String nombre){
		
		Iterator<UIComponent> iteratorSubMenu = firstSubMenu.getChildren().iterator();

		while (iteratorSubMenu.hasNext()) {
			
			try{
			    Submenu subMenu = (Submenu) iteratorSubMenu.next();
			
			    if (subMenu.getLabel().equals(nombre)){
			    	Iterator<UIComponent> iteratorItem = subMenu.getChildren().iterator();
			    	
			    	while (iteratorItem.hasNext()){
			    		
			    		MenuItem item = (MenuItem) iteratorItem.next();
			    		if ((item.getParent()).equals(subMenu)){
			    	        	iteratorItem.remove();
			    		}    	
			    	}
			    	
				    iteratorSubMenu.remove();
				    break;
			    }
			}catch(ClassCastException ex){}	    
		}
		
	}
	
	private void asignarIdComponente(){
		
		//id´s serán tareas_1 configuracion 1 etc...
		
		int numeroProyectos = firstSubMenu.getChildCount();
		
		numeroProyectos++;
		
		this.idComponente = String.valueOf(numeroProyectos);
	}
	
	
}