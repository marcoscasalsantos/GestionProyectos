package es.coremain.coremainWeb.view.bean.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DualListModel;

import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainUserService.service.UserService;
import es.coremain.coremainWeb.view.util.FacesUtils;
import es.uvigo.esei.pfc.eseiPfcProjectService.dto.ProjectDto;


@ManagedBean(name="actualProjectBean")
@SessionScoped
public class ActualProjectBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProjectDto proyectoActual;
	
	private boolean hayUnProyectoSeleccionado;
	
	private DualListModel<UserDto> usersNotInProject;

	public DualListModel<UserDto> getUsersNotInProject() {
		if (hayUnProyectoSeleccionado && proyectoActual != null){
			
			UserService userService = FacesUtils.getService("userService", UserService.class);
			
			List<UserDto> usersNoProject = userService.getUsersNotParticipateIntoProject(proyectoActual.getProjectId());
			
			proyectoActual.setUsers(userService.getUsersByProjectId(proyectoActual.getProjectId()));
			
			if (usersNoProject == null){usersNoProject = new ArrayList<UserDto>();}
			if (proyectoActual.getUsers() == null){
				proyectoActual.setUsers(new ArrayList<UserDto>());
			}
			
			usersNotInProject = new DualListModel<UserDto>(usersNoProject,proyectoActual.getUsers());
			
			//this.setUsersNotInProject(usersNotInProject);		
		}
		
		return usersNotInProject;
	}

	public void setUsersNotInProject(DualListModel<UserDto> usersNotInProject) {
		
         proyectoActual.setUsers(usersNotInProject.getTarget()); 
	
		 this.usersNotInProject = usersNotInProject;
	}
	

	public boolean isHayUnProyectoSeleccionado() {
		return hayUnProyectoSeleccionado;
	}

	public void setHayUnProyectoSeleccionado(boolean hayUnProyectoSeleccionado) {
		this.hayUnProyectoSeleccionado = hayUnProyectoSeleccionado;
	}
	
	public ProjectDto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(ProjectDto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}
	
	public void selectActualProject(){
		
		//proyectoActual = (ProjectDto) FacesUtils.flashScope().get("proyectoActual");
			
		this.setHayUnProyectoSeleccionado(true);
		
		//Al seleccionar el un proyecto al proyecto actual hay que asginarle la lista de usuarios
	    //que participan en el proyecto seleccionado.Esta lista se ira actualizando desde el menu
	    //Proyecto/participantes
	   
		UserService userService = FacesUtils.getService("userService", UserService.class);
	    proyectoActual.setUsers(userService.getUsersByProjectId(proyectoActual.getProjectId()));
	    
	    FacesUtils.flashScope().put("proyectoActual", this.proyectoActual);		
	}
	
	public String goSalirProyecto(){
		
		this.setHayUnProyectoSeleccionado(false);
		this.setProyectoActual(null);
		this.usersNotInProject=null;
		ProjectBean projectBean = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
		projectBean.setInfo("SE HA SALIDO CORRECTAMENTE DEL PROYECTO");
		FacesUtils.flashScope().put("info", projectBean.getInfo());
		
		return "menuPrincipal";
	}
	
	public void goUpdateProject(){
		
		ProjectBean projectBean = FacesUtils.getManagedBean("projectBean", ProjectBean.class);
		projectBean.setSelectedProject(proyectoActual);
		projectBean.updateProject();
		
	}
	
	
}