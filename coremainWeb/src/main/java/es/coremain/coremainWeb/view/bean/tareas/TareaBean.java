package es.coremain.coremainWeb.view.bean.tareas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.model.DualListModel;

import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainUserService.service.UserService;
import es.coremain.coremainWeb.view.bean.LoginBean;
import es.coremain.coremainWeb.view.bean.project.ActualProjectBean;
import es.coremain.coremainWeb.view.util.FacesUtils;
import es.uvigo.esei.pfc.eseiPfcProjectService.dto.TareaDto;
import es.uvigo.esei.pfc.eseiPfcProjectService.service.ProjectService;
import es.uvigo.esei.pfc.eseiPfcProjectService.util.Validaciones;


@ManagedBean(name = "tareaBean")
@ViewScoped
public class TareaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TareaDto selectedTarea; 
	private List<SelectItem> estados;
	private String estado;
	private List<TareaDto> tareas;
	private List<TareaDto> tareasAsignadasUsuario;
	private boolean mostrarEstado;
	private DualListModel<UserDto> usersTarea;
	private String horasImputadas;
	//Lista de usuarios asignados a la tarea
	private List<UserDto> users;
	//private List<UserDto> usersInProjectNotInTarea;
	//private boolean hayUsuarioAsignados;
	
	private UserDto nuevoUsuarioAsignadoATarea;
		

	/*public List<UserDto> getUsersInProjectNotInTarea() {
		System.out.println("*********************COGIENDO USUARIOS*********************");
		
		
		UserService userService = FacesUtils.getService("userService", UserService.class);
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		
		System.out.println("*********************Actual Project Id:"+actualProjectBean.getProyectoActual().getProjectId()+"*************");
		System.out.println("*********************Selected Tarea Id:"+ this.selectedTarea.getTareaId()+"*************");
		usersInProjectNotInTarea = userService.getUsersNotParticipateIntoTarea(actualProjectBean.getProyectoActual().getProjectId(),selectedTarea.getTareaId());
		
		if (usersInProjectNotInTarea == null){
			System.out.println("*********************NO HAY USUARIOS EN PROYECTO*********************");
			
		}
		return usersInProjectNotInTarea;
	}*/

	/*public void setUsersInProjectNotInTarea(List<UserDto> usersInProjectNotInTarea) {
		this.usersInProjectNotInTarea = usersInProjectNotInTarea;
	}*/

	
	public UserDto getNuevoUsuarioAsignadoATarea() {
		return nuevoUsuarioAsignadoATarea;
	}

	public void setNuevoUsuarioAsignadoATarea(UserDto nuevoUsuarioAsignadoATarea) {
		this.nuevoUsuarioAsignadoATarea = nuevoUsuarioAsignadoATarea;
	}
	
	/*public boolean isHayUsuarioAsignados() {
		
		UserService userService = FacesUtils.getService("userService", UserService.class);
		 
		if (selectedTarea != null){
		
		     if (userService.getUsersByTareaId(selectedTarea.getTareaId()) == null){
			
			       hayUsuarioAsignados = false;
			
		     }else{
			        
		    	   hayUsuarioAsignados = true;
		     }
		     
		}else{
			       hayUsuarioAsignados = false;
		}     
		
		return hayUsuarioAsignados;
	}

	public void setHayUsuarioAsignados(boolean hayUsuarioAsignados) {
		this.hayUsuarioAsignados = hayUsuarioAsignados;
	}*/

	//No funciona bien pq selectedTarea apunta a null?
	public List<UserDto> getUsers() {
		
		UserService userService = FacesUtils.getService("userService", UserService.class);
		
		selectedTarea.setUsers(userService.getUsersByTareaId(selectedTarea.getTareaId()));
		
		if (selectedTarea.getUsers() == null){
			selectedTarea.setUsers(new ArrayList<UserDto>());
		}
		
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	//***************** CONSRUCTOR ******************************************
	public TareaBean(){
		     estados = new ArrayList<SelectItem>();
		     estado = new String();
		
		     estados.add(new SelectItem("Asignada"));
		     estados.add(new SelectItem("En curso"));
		     estados.add(new SelectItem("Finalizada"));
		     
		     selectedTarea = (TareaDto) FacesUtils.flashScope().get("selectedTarea");
		     	
	}
	
	//****************** GETTERS Y SETTERS ******************************
	
	public String getHorasImputadas() {
		return horasImputadas;
	}

	public void setHorasImputadas(String horasImputadas) {
		this.horasImputadas = horasImputadas;
	}
	
	public List<TareaDto> getTareasAsignadasUsuario() {
		
		ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
		LoginBean loginBean = FacesUtils.getManagedBean("loginBean", LoginBean.class);
		
		tareasAsignadasUsuario = projectService.getTareasByUserLoggedIn(loginBean.getUsername(),loginBean.getPassword(),"En curso");
		
		return tareasAsignadasUsuario;
	}

	public void setTareasAsignadasUsuario(List<TareaDto> tareasAsignadasUsuario) {
		this.tareasAsignadasUsuario = tareasAsignadasUsuario;
	}
	
	public DualListModel<UserDto> getUsersTarea() {
		
		UserService userService = FacesUtils.getService("userService", UserService.class);
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		
		//List<UserDto> usersInProject = userService.getUsersByProjectId(actualProjectBean.getProyectoActual().getProjectId());
		
		List<UserDto> usersInProject = userService.getUsersNotParticipateIntoTarea(actualProjectBean.getProyectoActual().getProjectId(),selectedTarea.getTareaId());
		
		selectedTarea.setUsers(userService.getUsersByTareaId(selectedTarea.getTareaId()));
		
		if (usersInProject == null){usersInProject = new ArrayList<UserDto>();}
		if (selectedTarea.getUsers() == null){
			selectedTarea.setUsers(new ArrayList<UserDto>());
		}
		
		usersTarea = new DualListModel<UserDto>(usersInProject,selectedTarea.getUsers());
		
		return usersTarea;
	}

	public void setUsersTarea(DualListModel<UserDto> usersTarea) {
		
		selectedTarea.setUsers(usersTarea.getTarget());
		
		this.usersTarea = usersTarea;
	}
	
	public boolean isMostrarEstado() {
		if (selectedTarea.getEstado().equals("Sin asignar") || selectedTarea.getEstado() == null){
			 mostrarEstado = false;
		}else{
			 mostrarEstado = true;
		}
		
		return mostrarEstado;
	}

	public void setMostrarEstado(boolean mostrarEstado) {
		this.mostrarEstado = mostrarEstado;
	}

	public TareaDto getSelectedTarea() {
		if (selectedTarea == null){selectedTarea = new TareaDto();}
		return selectedTarea;
	}

	public void setSelectedTarea(TareaDto selectedTarea) {
		this.selectedTarea = selectedTarea;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public String getEstado() {
		estado = selectedTarea.getEstado();
		return estado;
	}

	/*public void setEstado(String estado) {
		this.estado = estado;
	}*/
	
	public List<TareaDto> getTareas() {
		
		ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		
		tareas = projectService.getAllTareas(actualProjectBean.getProyectoActual().getProjectId());
		
		return tareas;
	}

	public void setTareas(List<TareaDto> tareas) {
		this.tareas = tareas;
	}
	
	//************ LISTA DE METODOS DE LA CLASE ***********************

	public void guardarTarea(ActionEvent event){
		
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		selectedTarea.setProyecto(actualProjectBean.getProyectoActual());
		
		if (!Validaciones.validaNombreTarea(selectedTarea).equals("") || 
				!Validaciones.validaFechasTarea(selectedTarea).equals("")){
			
			if (!Validaciones.validaNombreTarea(selectedTarea).equals("")){
				
				 FacesUtils.setErrorMessage(null, Validaciones.validaNombreTarea(selectedTarea), null, (Object[])null);
			}
			if (!Validaciones.validaFechasTarea(selectedTarea).equals("")){
				
				FacesUtils.setErrorMessage(null, Validaciones.validaFechasTarea(selectedTarea), null, (Object[])null);
			}
			
		}else{
		
		      ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
		      selectedTarea.setEstado("Sin asignar"); 
		
		      selectedTarea = projectService.insertTarea(selectedTarea);
		
		      FacesUtils.setInfoMessage(null, "detalleProjecto_tareaInsertada", null);
		}
	}
	
	public String goNuevaTarea(){
	
		return "nuevaTarea";
	}
	
	public String goListaTareas(){
		
		return "listaTareas";
		//FacesUtils.flashScope().put("tareas", this.tareas);
		//return "tareas";
	}
	
	public String goTareaDetail(){
		
		FacesUtils.flashScope().put("selectedTarea", this.selectedTarea);
		
		return "detalleTarea";
	}
	
	public String goTareaAssign(){
		
		FacesUtils.flashScope().put("selectedTarea", this.selectedTarea);
		
		return "asignarTarea";
	}
	
	public String goImputarHoras(){
		
		return "imputarHoras";
	}
	
	public void deleteTarea(ActionEvent event){
		
		ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
		projectService.deleteTareaById(selectedTarea.getTareaId());
		
		FacesUtils.setInfoMessage(null, "detalleProjecto_tareaEliminada", null);
		
	}
	
	public void updateTarea(ActionEvent event){
		
		if (!Validaciones.validaNombreTarea(selectedTarea).equals("") || 
				!Validaciones.validaFechasTarea(selectedTarea).equals("")){
			
			if (!Validaciones.validaNombreTarea(selectedTarea).equals("")){
				
				 FacesUtils.setErrorMessage(null, Validaciones.validaNombreTarea(selectedTarea), null, (Object[])null);
			}
			if (!Validaciones.validaFechasTarea(selectedTarea).equals("")){
				
				FacesUtils.setErrorMessage(null, Validaciones.validaFechasTarea(selectedTarea), null, (Object[])null);
			}
		}else{
			
		       ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
		       projectService.updateTarea(selectedTarea);
		
		       FacesUtils.setInfoMessage(null, "detalleProjecto_projectoActualizado", null);
		}
	}
	
	public void asignarTarea(ActionEvent event){
		
		if (usersTarea.getTarget() != null){
			
			if (usersTarea.getTarget().isEmpty()){
				
				selectedTarea.setEstado("Sin asignar");
			}else{
				
				selectedTarea.setEstado("Asignada");
			}	
		}
		this.updateTarea(event);
	}
	
	
	public void imputarHoras(ActionEvent event){
		
		if (selectedTarea == null){
			
			FacesUtils.setErrorMessage(null, "detalleProyecto_TareaNoSeleccionada", null, (Object[])null);
		}else{
			
			     Date fechaInicio = selectedTarea.getFechaInicioTarea();
		 	     Date fechaFin = selectedTarea.getFechaFinTarea();
			
			     Calendar calendar = new GregorianCalendar();
			     calendar.setTimeInMillis(fechaFin.getTime()-fechaInicio.getTime());
			     int dias = calendar.get(Calendar.DAY_OF_YEAR);
			     int numHorasMaximo = 8 * dias;//Esto habria que multiplicarlo por el numero de 
			                                   //personas asginado a la tarea
			
			
		         int horas = Integer.valueOf(selectedTarea.getHorasImputadas());
		
		         int horasAImputar = Integer.valueOf(horasImputadas);
		
		         horasAImputar += horas;
		         
		         if (numHorasMaximo > horasAImputar){
		
		                  selectedTarea.setHorasImputadas(String.valueOf(horasAImputar));
		
		                  updateTarea(event);
		         }else{
		        	 
		        	 
		        	 FacesUtils.setErrorMessage(null, "detalleProyecto_horasAImputarExcedidas", null, (Object[])null);
		         }
		        	 
		                  
		     }    	
		
	}
	
	
	
	//Intentar conseguir la tarea seleccionada a través de la pestaña seleccionada
	
	public void onTabChange(TabChangeEvent event){
		
		//event.getTab().getTitle();
		
		TareaDto tarea = (TareaDto)event.getData();
		
		//String tareaSeleccionada = event.getTab().getId();
		
		String activeIndex = ((AccordionPanel) event.getComponent()).getActiveIndex();
		
		System.out.print("*******************************ACTIVE INDEX:"+activeIndex);
		
		this.selectedTarea = tarea;
		
		
		//FacesUtils.flashScope().put("selectedTarea", this.selectedTarea);
		
		
		
		//Tab selectedTarea = (Tab)event.getComponent();
		
		//System.out.println("************************Tarea Seleccionada:"+ tarea.getNombreTarea());
		
	}
	
	public void onTabClose(TabCloseEvent event){
		
		
		
		UserDto user = (UserDto) event.getData();
		
		List<UserDto> users = this.selectedTarea.getUsers();
		
	    for (UserDto userDto : users){
	    	
	    	if (userDto.getName().equals(user.getName())){
	    		
	    		users.remove(user);
	    		this.selectedTarea.setUsers(users);
	    	}
	    }
		
	    
        System.out.println("ACtualizando usuarios asignados.....................");
	    
	    
	    ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
	    projectService.updateTarea(selectedTarea);
	
	    FacesUtils.setInfoMessage(null, "detalleProjecto_projectoActualizado", null);
		
	}
	
	
	public void asignarUsuarioATarea(AjaxBehaviorEvent event){
		
		System.out.println("*********************************ELEMENTO SELECCIKONADO **********************************");
	
	    System.out.println("Tarea: "+ selectedTarea.getNombreTarea());
	    
	    
	    if (selectedTarea.getUsers() != null){
	            List<UserDto> usuariosTarea = this.selectedTarea.getUsers();
	            
	            if (usuariosTarea.contains(nuevoUsuarioAsignadoATarea)){
	                   
	                    //No se hace nada,el usuario ya está añadido
	            	
	            }else{
	            	
	            	usuariosTarea.add(nuevoUsuarioAsignadoATarea);
	                this.selectedTarea.setUsers(usuariosTarea);	
	            }
	    }else{
	    	
	    	 List<UserDto> usuariosTarea = new ArrayList<UserDto>();
	    	 usuariosTarea.add(nuevoUsuarioAsignadoATarea);
	    	 this.selectedTarea.setUsers(usuariosTarea);
	    }
	    
	    System.out.println("ACtualizando tarea.....................");
	    
	    
	    ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		
	    projectService.updateTarea(selectedTarea);
	
	    FacesUtils.setInfoMessage(null, "detalleProjecto_projectoActualizado", null);
	    
	}
	
	public void seleccionarTarea(ActionEvent event){
		
		CommandLink selectedTarea = (CommandLink) event.getComponent();
		
		ProjectService projectService = FacesUtils.getService("projectService", ProjectService.class);
		ActualProjectBean actualProjectBean = FacesUtils.getManagedBean("actualProjectBean", ActualProjectBean.class);
		
		
		List<TareaDto> tareas = projectService.getAllTareas(actualProjectBean.getProyectoActual().getProjectId());
		
		
		for (TareaDto tarea : tareas){
			
			if(tarea.getNombreTarea().equals(selectedTarea.getValue())){
		              
				FacesUtils.flashScope().put("selectedTarea", tarea);
				
			}
		}
		
		
		
	}
	
	/**
     * El listener asigna el nuevo estado del select a la variable estado del bean
     *
     * @param event indica el cambio de valor del estado de la tarea.
     */
	/*public void stateChangeListener(ValueChangeEvent event) {
           estado = (String)event.getNewValue();
    }*/
	
	
}