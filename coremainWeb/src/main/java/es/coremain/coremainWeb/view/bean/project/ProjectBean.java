package es.coremain.coremainWeb.view.bean.project;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.MenuModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import es.coremain.coremainUserService.service.UserService;
import es.coremain.coremainWeb.view.bean.LoginBean;
import es.coremain.coremainWeb.view.bean.MenuBean;
import es.coremain.coremainWeb.view.bean.tareas.TareaBean;
import es.coremain.coremainWeb.view.util.FacesUtils;
import es.uvigo.esei.pfc.eseiPfcProjectService.dto.ProjectDto;
import es.uvigo.esei.pfc.eseiPfcProjectService.dto.TareaDto;
import es.uvigo.esei.pfc.eseiPfcProjectService.service.ProjectService;
import es.uvigo.esei.pfc.eseiPfcProjectService.util.Operaciones;
import es.uvigo.esei.pfc.eseiPfcProjectService.util.Validaciones;

@ManagedBean(name="projectBean")
@ViewScoped
public class ProjectBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String error;
	private String info;
	private ProjectDto proyectoActual;
	private ProjectDto selectedProject;
	private List<ProjectDto> projects;
	private Date fechaActual;
	private String numeroParticipantes;
	private String salariosTotales;
	private String salariosConsumidos;
	private String tareasTotales;
	private String tareasSinAsignar;
	private String tareasAsignadas;
	private String tareasEnCurso;
	private String tareasFinalizadas;
	private CartesianChartModel evolucionSalarios;
	
	//****************** CONSTRUCTOR **********************************************
    
	public ProjectBean(){
		//error="";
		selectedProject = (ProjectDto) FacesUtils.flashScope().get("selectedProject");	
		proyectoActual = (ProjectDto) FacesUtils.flashScope().get("proyectoActual");
		error = (String) FacesUtils.flashScope().get("error");
		info = (String) FacesUtils.flashScope().get("info");
	}

	//*******************************GETTERS Y SETTERS*****************************
	
	public CartesianChartModel getEvolucionSalarios() {
		construirGrafico();
        return evolucionSalarios;
    }
	
	public String getTareasFinalizadas() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getTareasByEstado(actualProjectBean.getProyectoActual().getProjectId(),"Finalizada");
    	int numTareas;
    	if (tareas != null){
    		numTareas = Operaciones.calcularNumeroTareas(tareas);
    	}else{
    		numTareas = 0;
    	}
    	this.tareasFinalizadas = String.valueOf(numTareas);
		return tareasFinalizadas;
	}

	public void setTareasFinalizadas(String tareasFinalizadas) {
		this.tareasFinalizadas = tareasFinalizadas;
	}
	
	public String getTareasEnCurso() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getTareasByEstado(actualProjectBean.getProyectoActual().getProjectId(),"En curso");
    	int numTareas;
    	if (tareas != null){
    		numTareas = Operaciones.calcularNumeroTareas(tareas);
    	}else{
    		numTareas = 0;
    	}
    	this.tareasEnCurso = String.valueOf(numTareas);
		return tareasEnCurso;
	}

	public void setTareasEnCurso(String tareasEnCurso) {
		this.tareasEnCurso = tareasEnCurso;
	}
	
	public String getTareasAsignadas() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getTareasByEstado(actualProjectBean.getProyectoActual().getProjectId(),"Asignada");
    	int numTareas;
    	if (tareas != null){
    		numTareas = Operaciones.calcularNumeroTareas(tareas);
    	}else{
    		numTareas = 0;
    	}
    	this.tareasAsignadas = String.valueOf(numTareas);
		return tareasAsignadas;
	}

	public void setTareasAsignadas(String tareasAsignadas) {
		this.tareasAsignadas = tareasAsignadas;
	}
	
	public String getTareasSinAsignar() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getTareasByEstado(actualProjectBean.getProyectoActual().getProjectId(),"Sin asignar");
    	int numTareas;
    	if (tareas != null){
    		numTareas = Operaciones.calcularNumeroTareas(tareas);
    	}else{
    		numTareas = 0;
    	}
    	
    	this.tareasSinAsignar = String.valueOf(numTareas);
		return this.tareasSinAsignar;
	}

	public void setTareasSinAsignar(String tareasSinAsignar) {
		this.tareasSinAsignar = tareasSinAsignar;
	}
	
	public String getTareasTotales() {
		TareaBean tareaBean = FacesUtils.getManagedBean("tareaBean", TareaBean.class);
		List<TareaDto> tareas = tareaBean.getTareas();
		int numTareas;
		if (tareas != null){
			numTareas = Operaciones.calcularNumeroTareas(tareas);
		}else{
			numTareas = 0;
		}
		this.tareasTotales = String.valueOf(numTareas);
		return this.tareasTotales;
	}

	public void setTareasTotales(String tareasTotales) {
		this.tareasTotales = tareasTotales;
	}
	
	public String getSalariosConsumidos() {
        ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	double salariosConsumidos = Operaciones.calcularSalariosConsumidos(actualProjectBean.getProyectoActual());
    	DecimalFormat redondeo = new DecimalFormat("########.##");
    	this.salariosConsumidos = redondeo.format(salariosConsumidos);
		return this.salariosConsumidos;
	}

	public void setSalariosConsumidos(String salariosConsumidos) {
		this.salariosConsumidos = salariosConsumidos;
	}
	
	public String getSalariosTotales() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		double salariosTotales = Operaciones.calcularSalariosTotales(actualProjectBean.getProyectoActual());
		DecimalFormat redondeo = new DecimalFormat("########.##");
    	this.salariosTotales = redondeo.format(salariosTotales);
		return this.salariosTotales;
	}

	public void setSalariosTotales(String salariosTotales) {
		this.salariosTotales = salariosTotales;
	}
	
	public String getNumeroParticipantes() {
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class); 	
    	int numeroParticipantes = Operaciones.calcularNumeroParticipantes(actualProjectBean.getProyectoActual());
    	this.numeroParticipantes = String.valueOf(numeroParticipantes);
    	return this.numeroParticipantes;
	}

	public void setNumeroParticipantes(String numeroParticipantes) {
		this.numeroParticipantes = numeroParticipantes;
	}
	
	public Date getFechaActual() {
		Calendar calendario = Calendar.getInstance();
		fechaActual = calendario.getTime();
		return  fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ProjectDto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(ProjectDto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public ProjectDto getSelectedProject() {
		if (selectedProject == null){ selectedProject = new ProjectDto();}
		return selectedProject;
	}

	public void setSelectedProject(ProjectDto selectedProject) {
		this.selectedProject = selectedProject;
	}
	
	public List<ProjectDto> getProjects() {
		ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		projects = projectService.getAllProjects();
		
		//LoginBean loginBean = FacesUtils.getManagedBean("loginBean", LoginBean.class);
		//projects = projectService.getProjectsByUserLoggedIn(loginBean.getUsername(),loginBean.getPassword());
		return projects;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}
	
	
	
	
	//***********************************LISTA DE METODOS****************************************
	

	/**
	 * Método que inserta un nuevo proyecto en la base de datos
	 */
    public void insertProject(ActionEvent event){
		
    	if ((!Validaciones.validaCodigo(selectedProject).equals("") || !Validaciones.validaNombre(selectedProject).equals("")) || 
    		      !Validaciones.validaFechasProyecto(selectedProject).equals("")){
    		
    		  if (!Validaciones.validaCodigo(selectedProject).equals("")){
    			    
    			  FacesUtils.setErrorMessage(null, Validaciones.validaCodigo(selectedProject), null, (Object[])null);
    		  }
    		  if (!Validaciones.validaNombre(selectedProject).equals("")){
    			  
    			  FacesUtils.setErrorMessage(null, Validaciones.validaNombre(selectedProject), null, (Object[])null);	
    		  }
    		  if (!Validaciones.validaFechasProyecto(selectedProject).equals("")){
    			  
    			  FacesUtils.setErrorMessage(null, Validaciones.validaFechasProyecto(selectedProject), null, (Object[])null);
    		  }
    	}else{
	    	   
			   ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
			 
			   selectedProject = projectService.insertProject(selectedProject);
			   //this.getTareas();
			   //tareas.add(selectedProject);
			   
			   MenuBean menu = FacesUtils.getManagedBean("menuBean", MenuBean.class);
			   //if userRoles = admin or directivo (jefeProyecto?)
			   menu.añadirSubMenu(selectedProject.getNombreProyecto());
			   //menu.getModel();
			   
			   FacesUtils.setInfoMessage(null, "detalleProjecto_projectoInsertado", null);
	    	 }
 
	}
    
    /**
     * Metodo que actualiza la lista de tareas del proyecto por si añadieron nuevas tareas y devuelve
     * una cadena para ir a la pagina de detalle del proyecto
     */
    public String goProjectDetail(){
    	
        UserService userService = FacesUtils.getService("userService", UserService.class);
        selectedProject.setUsers(userService.getUsersByProjectId(selectedProject.getProjectId()));
    	
    	FacesUtils.flashScope().put("selectedProject", this.selectedProject);
    	FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);
    	
    	return "detalleProyecto";
    }
    
    
    /**
     * Metodo que actualiza los datos de un proyecto ya existente
     */
    public void updateProject(){
    	
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getAllTareas(selectedProject.getProjectId());
    	selectedProject.setTareas(tareas);
    	
    	
    	
    if ((!Validaciones.validaCodigo(selectedProject).equals("") || !Validaciones.validaNombre(selectedProject).equals("")) || 
    		(!Validaciones.validaFechasProyecto(selectedProject).equals(""))){
    		
  		  if (!Validaciones.validaCodigo(selectedProject).equals("")){
  			    
  			  FacesUtils.setErrorMessage(null, Validaciones.validaCodigo(selectedProject), null, (Object[])null);
  		  }
  		  if (!Validaciones.validaNombre(selectedProject).equals("")){
  			  
  			  FacesUtils.setErrorMessage(null, Validaciones.validaNombre(selectedProject), null, (Object[])null);	
  		  }
  		  if (!Validaciones.validaFechasProyecto(selectedProject).equals("")){
  			  
  			  FacesUtils.setErrorMessage(null, Validaciones.validaFechasProyecto(selectedProject), null, (Object[])null);
  		  }
  	}else{
  		
  		 if (!Validaciones.validaFechasProyectoTareas(selectedProject).equals("")){  			  
  			  
  			FacesUtils.setErrorMessage(null, Validaciones.validaFechasProyectoTareas(selectedProject), null, (Object[])null);
  			  
  		  }else{
	    	   	 
			   projectService.updateProject(selectedProject);
			   
			   FacesUtils.setInfoMessage(null, "detalleProjecto_projectoActualizado", null);
			   
			   /*ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
			   
			   if (actualProjectBean.getProyectoActual().getProjectId().equals(selectedProject.getProjectId())){
				   actualProjectBean.setProyectoActual(selectedProject);
			   }*/
			   
  		  }//End-Else ValidarFechasProyectoTareas
  		 
  	}//End-Else Validaciones
    	
    }//End metodo
    
    /**
     * Metodo que elimina un proyecto y todas sus tareas de la base de datos
     * Tb se elimina la asignacion de la gente al proyecto y sus tareas
     */
    public void deleteProject(ActionEvent event){
    	
    	/*ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	
    	if (actualProjectBean.getProyectoActual() != null && 
    			Validaciones.proyectosSonIguales(actualProjectBean.getProyectoActual(), selectedProject)){
    		
    		FacesUtils.setErrorMessage(null, "detalleProyecto_eliminarProyectoSeleccionado", null, (Object[])null);
    	
    	}else{*/
         	
    	ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
    	List<TareaDto> tareas = projectService.getAllTareas(selectedProject.getProjectId());
    	selectedProject.setTareas(tareas);
    	
    	projectService.deleteProjectById(selectedProject.getProjectId());
    	
    	MenuBean menu = FacesUtils.getManagedBean("menuBean", MenuBean.class);
		menu.borrarSubMenu(selectedProject.getNombreProyecto());   
    	 	
    	FacesUtils.setInfoMessage(null, "detalleProjecto_projectoEliminado", null);
      //}
    	
    }
    
    /**
     * Seleccionar uno de los proyectos de la lista y asignarlo como proyecto seleccionado para
     * trabajar con el
     */
    public void selectProject(ActionEvent event){
    	
        if (selectedProject != null){
    	
    	       //proyectoActual = new ProjectDto();
    	
     	       //proyectoActual = selectedProject;
    	
    	       //FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);
    	       
    	       //Creo una instancia del managed Bean "actualProjectBean" para poder llamar a sus metodos
    	       ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	       //actualProjectBean.selectActualProject();
    	       actualProjectBean.setProyectoActual(selectedProject);
    	       actualProjectBean.selectActualProject();
    	       
    	       FacesUtils.setInfoMessage(null, "detalleProjecto_projectoSeleccionado", null);
    	       
        }else{
        	
        	 FacesUtils.setInfoMessage(null, "detalleProjecto_projectoNoSeleccionado", null);	
        }       
    	
    }
    
    
    public String goEquipoProyecto(){
    	
    	FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);
    	
    	ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	
    	if (actualProjectBean.isHayUnProyectoSeleccionado())
    	{
    		
    		FacesUtils.setInfoMessage(null, "detalleProjecto_projectoSeleccionado", null);
    		return "participantes";
    		
    	}else{
    		
    		FacesUtils.setInfoMessage(null, "detalleProjecto_projectoEliminado", null);
    		this.setError("ERROR:PARA PODER VER LOS PARTICIPANTES PRIMERO DEBE SELECCIONAR UN PROYECTO DE LA LISTA DE PROYECTOS");
    		FacesUtils.flashScope().put("error", this.error);
    		return "menuPrincipal";
    	}
    	
    	
    }
    
    public String goNewProject(){
    	
    	FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);
    	
    	//selectedProject = null;
    	return "nuevoProyecto";
    }
    
    
    public String goListaProyectos(){
    	
    	FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);
    	
    	return "listaProyectos";
    }
    
    /**
     * Construye el grafico relativo a los presupuestos y salarios 
     */
    private void construirGrafico(){
    	ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
    	
    	
	    evolucionSalarios = new CartesianChartModel();
		final ChartSeries presupuesto  = new ChartSeries("Presupuesto");
		final ChartSeries salariosTotales  = new ChartSeries("SalariosTotales");
		final ChartSeries salariosConsumidos  = new ChartSeries("SalariosConsumidos");
		 
	    Date fechaInicioProyecto = actualProjectBean.getProyectoActual().getFechaInicioProyecto();
	    Date fechaFinProyecto = actualProjectBean.getProyectoActual().getFechaFinProyecto();
	    Date fechaActual = new Date();
	    fechaActual.getTime();
	    
	    Calendar fechaInicio = new GregorianCalendar();
	    Calendar fechaFin = new GregorianCalendar();
	    Calendar fechaHoy = new GregorianCalendar();
	    
	    fechaInicio.setTime(fechaInicioProyecto);
	    fechaFin.setTime(fechaFinProyecto);
	    fechaHoy.setTime(fechaActual);
	    String clave;
	    
	    double salarios = Operaciones.calcularSalariosTotales(actualProjectBean.getProyectoActual());
	    double salariosParciales;
	    double cantidadInicialParcial;
	    
	     //Presupuesto y salarios totales 
	     if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 1){
		    //Proyectos de un mes,se muestran en tres tramos presupuesto y salarios totales
	    	double dias = Operaciones.calcularNumeroDiasProyecto(actualProjectBean.getProyectoActual());
	    	salariosParciales = salarios / ((dias / 10 ) + 1);
	    	cantidadInicialParcial = salariosParciales;
		    while (fechaInicio.before(fechaFin)){
		       clave = String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaInicio.get(Calendar.MONTH)+1);
		       presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
   		       salariosTotales.set(clave, salariosParciales);
	    	   fechaInicio.add(Calendar.DATE, 10);
	    	   salariosParciales = salariosParciales + cantidadInicialParcial;
		    }
		    clave = String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaFin.get(Calendar.MONTH)+1);  
		    presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    salariosTotales.set(clave, salarios);		
	    	
	      }
	    
	     if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 2){
	    	 double dias = Operaciones.calcularNumeroDiasProyecto(actualProjectBean.getProyectoActual());
	    	 salariosParciales = salarios / ((dias / 15 )+ 1);
	    	 cantidadInicialParcial = salariosParciales;
	    	 while(fechaInicio.before(fechaFin)){
	    		clave = String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaInicio.get(Calendar.MONTH)+1);    
			    presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			    salariosTotales.set(clave, salariosParciales);
			    fechaInicio.add(Calendar.DATE, 15);  
			    salariosParciales = salariosParciales + cantidadInicialParcial;
		     }
	    	 clave = String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaFin.get(Calendar.MONTH)+1);  
			 presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			 salariosTotales.set(clave, salarios);		
	     }
	     
	     if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 3){
	    	 
	    	 double meses = Operaciones.calcularNumeroDiasProyecto(actualProjectBean.getProyectoActual());
	    	 salariosParciales = salarios / meses;
	    	 cantidadInicialParcial = salariosParciales;
	    	 
	    	 System.out.println("*************************cantidadInicialParcial********************"+cantidadInicialParcial);
	    	 System.out.println("*************************meses********************"+meses);
	    	 
	    	 
	    	 while(fechaInicio.before(fechaFin)){
	    		clave = String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaInicio.get(Calendar.MONTH)+1);       
			    presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			    salariosTotales.set(clave, salariosParciales);
		    	fechaInicio.add(Calendar.MONTH, 1); 
		    	salariosParciales = salariosParciales + cantidadInicialParcial;
		      }
	    	 clave = String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaFin.get(Calendar.MONTH)+1);  
			 presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			 salariosTotales.set(clave, salarios);
	     }
	    
	    
	    
	  //Salarios consumidos denpenderia de si el proyecto esta finalizado o no
	   /* if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 1){
	    	
	    	if (fechaHoy.after(fechaFin)){
	    		
	    		   while(fechaInicio.before(fechaFin)){
	    			clave = String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaInicio.get(Calendar.MONTH)+1);
			    	presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    		salariosTotales.set(clave, salarios);
			    	fechaInicio.add(Calendar.DATE, 10);   	
		    	   }
	    		 clave = String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaFin.get(Calendar.MONTH)+1);  
	    		 presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	// presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	 salariosTotales.set(clave, salarios);
	   	    	 //salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
	   	    	 
	    	}else{
	    		
	    		while(fechaInicio.before(fechaFin)){
	    			clave = String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaInicio.get(Calendar.MONTH)+1);
			    	presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			    	salariosTotales.set(clave, salarios);
			    	fechaInicio.add(Calendar.DATE, 10);   	
		    	}
	    		clave = String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaFin.get(Calendar.MONTH)+1);  
		    	//presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	presupuesto.set(clave, Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	//salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
		    	salariosTotales.set(clave, salarios);
	    	}
	    	
	    	
	    }
	    
	    if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 2){
	    	
	    	//double cantidad = salarios / 2;
	    	
	    	if (fechaHoy.after(fechaFin)){
	    		
	    		   while(fechaInicio.before(fechaFin)){
			    	presupuesto.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			    	salariosTotales.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), salarios);
			    	fechaInicio.add(Calendar.DATE, 15);   	
			    	//cantidad = cantidad + cantidad;
		    	   }
	    		 presupuesto.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	 //presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	 salariosTotales.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), salarios);
	   	    	 //salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
	    	}else{
	    		
	    		while(fechaInicio.before(fechaFin)){
			    	presupuesto.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
			    	salariosTotales.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), salarios);
			    	fechaInicio.add(Calendar.DATE, 15);   	
		    	}
		    	
		    	//presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	presupuesto.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	//salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
		    	salariosTotales.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), salarios);
	    	}
	    }
	    
        if (Operaciones.calcularDuracionProyecto(actualProjectBean.getProyectoActual(), true) == 3){
	    	
	    	if (fechaHoy.after(fechaFin)){
	    		
	    		   while(fechaInicio.before(fechaFin)){
			    	presupuesto.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    		fechaInicio.add(Calendar.MONTH, 1);   	
		    	   }
	    		 presupuesto.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	 //presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
	   	    	 salariosTotales.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), salarios);
	   	    	 //salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
	    	}else{
	    		
	    		while(fechaInicio.before(fechaFin)){
			    	presupuesto.set(String.valueOf(fechaInicio.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    		fechaInicio.add(Calendar.MONTH, 15);   	
		    	}
		    	
		    	//presupuesto.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	presupuesto.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), Double.valueOf(actualProjectBean.getProyectoActual().getPresupuesto()));
		    	//salariosTotales.set(String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH)), salarios);
		    	salariosTotales.set(String.valueOf(fechaFin.get(Calendar.DAY_OF_MONTH)), salarios);
	    	}
	    }*/
        
        
        
	   
	         
	        evolucionSalarios.addSeries(presupuesto);
	        evolucionSalarios.addSeries(salariosTotales);
	        
    	
    	
    }
         
	
}