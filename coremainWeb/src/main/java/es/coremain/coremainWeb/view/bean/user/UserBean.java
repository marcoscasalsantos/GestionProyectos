package es.coremain.coremainWeb.view.bean.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;

import es.coremain.coremainUserService.dto.RoleDto;
import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainUserService.service.UserService;
import es.coremain.coremainUserService.util.ValidacionesUsuario;
import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

	//TODO este bean deberia tener ViewScoped, pero falta pasar el selectedUsuario entre las vistas de usuario y detalleUsuario 
	private static final long serialVersionUID = 1L;

	private List<UserDto> users;
	
	private UserDto selectedUser; // Al a�adir un nuevo usuario el selectedUser ser� el nuevo usuario
	
	private DualListModel<RoleDto> avaliableAndSelectedRoles;

	private String repeatedPassword; //Password que debe repetir el usuario para evitar que lo introzca mal sin darse cuenta

    public UserBean(){
    	
    	selectedUser = (UserDto) FacesUtils.flashScope().get("selectedUser");
    	repeatedPassword = (String) FacesUtils.flashScope().get("repeatedPassword");
    }
	
	public List<UserDto> getUsers() {
		//Creo que la lista de usuarios hay que obtenerla siempre,independientemente de si users = null
		//o no (seria la primera vez que se coge) ya que se puede añadir un nuevo usuario y sería necesario
		//actualizar la lista de usuarios para que este siempre actualizada
		
		//if (users == null) {
			UserService userService = FacesUtils.getService("userService", UserService.class);
			
			users = userService.getAllUsers();
		//}
		
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
	
	public UserDto getSelectedUser() {	
		if (selectedUser == null) {selectedUser = new UserDto();}
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public String goUserDetail() {
		
		//Resetea el atributo avaliableAndSelectedRoles para que se recalcule para el selectedUser
		//Recalcula la lista de roles del usuario actual por si se modificase.Creo que con ponerlo
		//en el metodo goUserManagement llegaria, se podría quitar de goUserDetail pq la gestion de roles
		//se va a poner en gestionUsuario.xhtml y se va a quitar de detalleUsuario.xhtml, con lo que solo
		//haria falta en el metodo goUserManagement pq se usa en el action para llegar a la pagina
		//de gestion de los roles de los usuarios 
		avaliableAndSelectedRoles = null;
		this.repeatedPassword = selectedUser.getPassword();
		
		FacesUtils.flashScope().put("selectedUser", this.selectedUser);
		
		
		return "detalleUsuario";
	}
	
	public String goUserManagement(){
		
		//Resetea el atributo avaliableAndSelectedRoles para que se recalcule para el selectedUser
		avaliableAndSelectedRoles = null;
		this.repeatedPassword = selectedUser.getPassword();
		
		FacesUtils.flashScope().put("selectedUser", this.selectedUser);
		FacesUtils.flashScope().put("repeatedPassword", this.repeatedPassword);
		
		return "gestionUsuario";
	}
	
		
	public DualListModel<RoleDto> getAvaliableAndSelectedRoles() {
		
		if (selectedUser != null && avaliableAndSelectedRoles == null) {
						
			UserService userService = FacesUtils.getService("userService", UserService.class);
			
			List<RoleDto> avaliableAndNotGrantedRoles = userService.getAvaliableAndNotGrantedRoles(selectedUser.getUserId());
			
			selectedUser.setRoles(userService.getRolesByUserId(selectedUser.getUserId()));
			
			avaliableAndSelectedRoles = new DualListModel<RoleDto>(avaliableAndNotGrantedRoles, selectedUser.getRoles());					
		}

		return avaliableAndSelectedRoles;
	}
	
	public void setAvaliableAndSelectedRoles(DualListModel<RoleDto> avaliableAndSelectedRoles) {
		
		selectedUser.setRoles(avaliableAndSelectedRoles.getTarget());
		
		this.avaliableAndSelectedRoles = avaliableAndSelectedRoles;
	}
	
	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}	
	
	public void updateUser(ActionEvent event){
		
		if (!ValidacionesUsuario.validaNombre(selectedUser).equals("") || !ValidacionesUsuario.validaApellidos(selectedUser).equals("") ||
				!ValidacionesUsuario.validaFechaNacimiento(selectedUser).equals("") ||
				!ValidacionesUsuario.validaNif(selectedUser).equals("")){
			
			if (!ValidacionesUsuario.validaNombre(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaNombre(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaApellidos(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaApellidos(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaFechaNacimiento(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaFechaNacimiento(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaNif(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaNif(selectedUser), null, (Object[])null);
			}
			
		}else{
			
		        UserService userService = FacesUtils.getService("userService", UserService.class);
		
		        userService.updateUser(selectedUser);
		
		        FacesUtils.setInfoMessage(null, "detalleUsurio_usuarioActualizado", null);
		}
	}
	
	public void manageUser(ActionEvent event){
		
		if (!ValidacionesUsuario.validaUserName(selectedUser).equals("") || 
				!ValidacionesUsuario.validaPassword(selectedUser, repeatedPassword).equals("")){
			
			    if (!ValidacionesUsuario.validaUserName(selectedUser).equals("")){
			    	
			    	FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaUserName(selectedUser), null, (Object[])null);
			    }
			    if (!ValidacionesUsuario.validaPassword(selectedUser, repeatedPassword).equals("")){
			    	FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaPassword(selectedUser, repeatedPassword), null, (Object[])null);
			    }
			
		}else{
			
			    UserService userService = FacesUtils.getService("userService", UserService.class);
			    userService.updateUser(selectedUser);
			    FacesUtils.setInfoMessage(null, "detalleUsurio_usuarioActualizado", null);
		}
	}
	
	
	public String goMainMenu() {				
		return "menuPrincipal";
	}	
	
	
	public void deleteUser(ActionEvent event){
		
		UserService userService = FacesUtils.getService("userService", UserService.class);
		
		userService.deleteUserById(selectedUser.getUserId());
		users.remove(selectedUser);
		
		FacesUtils.setInfoMessage(null, "detalleUsurio_usuarioEliminado", null);
		
	}	
	
	public String goNewUser() {
		
		selectedUser = new UserDto();		
		selectedUser.setRoles(new ArrayList<RoleDto>());

		UserService userService = FacesUtils.getService("userService", UserService.class);		
		avaliableAndSelectedRoles = new DualListModel<RoleDto>(userService.getAllRoles(), selectedUser.getRoles());
				
		return "nuevoUsuario";
	}
	
	public void insertUser(ActionEvent event){
		
		if (!ValidacionesUsuario.validaNombre(selectedUser).equals("") || !ValidacionesUsuario.validaApellidos(selectedUser).equals("") ||
				!ValidacionesUsuario.validaFechaNacimiento(selectedUser).equals("") ||
				!ValidacionesUsuario.validaNif(selectedUser).equals("")){
			
			if (!ValidacionesUsuario.validaNombre(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaNombre(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaApellidos(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaApellidos(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaFechaNacimiento(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaFechaNacimiento(selectedUser), null, (Object[])null);
			}
			if (!ValidacionesUsuario.validaNif(selectedUser).equals("")){
				FacesUtils.setErrorMessage(null, ValidacionesUsuario.validaNif(selectedUser), null, (Object[])null);
			}
			
		}else{
		
		        UserService userService = FacesUtils.getService("userService", UserService.class);
		
		        selectedUser = userService.insertUser(selectedUser);
		        this.getUsers();
		        users.add(selectedUser);
		
		        FacesUtils.setInfoMessage(null, "detalleUsurio_usuarioInsertado", null);
		}
		
	}
	
	
}
