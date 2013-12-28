/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package es.coremain.coremainWeb.view.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.primefaces.component.menuitem.MenuItem;
import org.springframework.security.core.context.SecurityContextHolder;

import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Podra inyectarse los servicios con el autowire de spring si la clase estuviese anotada como @component
	// o @Controller
	// en lugar de como @managedBean, creo que es preferible hacerlo utilizando la clase ServiceUtils ya que resulta 
	// mas comodo usar las ventajas de anotar como managedBean y ademas el managedBean podria utilizar varios servicios, por
	// lo que anhadirlos todos como atributos autowired "ensuciaria" mas el codigo
	// UserService userService = ServiceUtils.getService("userService", UserService.class);	
	
	private String username;
	
	private String password;
	
	private String index;
	
	private int tabIndex;
	
	public LoginBean(){
		tabIndex = 100;
		
	}
	

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public int getTabIndex(){
		return tabIndex;
	}
	
	public void setTabIndex(int tabIndex){
		this.tabIndex = tabIndex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public boolean isUserLoggedIn() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) return true;
				 return false;
	}
	
	public UserDto getLoggedUser() {		
		UserDto loggedUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

		return loggedUser;
	}

    // public String doLoginAction() throws IOException {
	
	public void executeLogin(ActionEvent e) throws ServletException, IOException {
				
		/*
		 * El siguiente codigo es necesario para que el filtro de Spring-Security intercepte
		 * la request del login ya que no funciona poner action="\j_spring_security_check" en la request del form
		 * como hariamos si fuese una pagina jsp en lugar de una xhtml de facelets.
		 * 
		 * La ventaja es que la pagina de login no esta condicionada por spring-security
		 */
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();             
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check?j_username=" + username + "&j_password=" + password);        
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse()); 
        FacesContext.getCurrentInstance().responseComplete();

        
        // Anhade el item "Menu principal" al breadCrumb.
        BreadCrumbBean breadCrumBean = FacesUtils.getManagedBean("breadCrumbBean", BreadCrumbBean.class);
        
		MenuItem item = new MenuItem();
				
		//MethodExpression methodExpression = FacesUtils.createMethodExpression("#{breadCrumbBean.goMenuPrincipal}"+"?faces-redirect=true", null, new Class<?>[0]);
		
		item.setId("menuPrincipalMenuItem");
		item.setValue("Men� principal");
		item.setUrl("/pages/menuPrincipal.xhtml");
		
		//item.setActionExpression(methodExpression);
	
		breadCrumBean.addItem(item);
    		     
		
	}

	public String doCerrarSesionAction() {

		FacesUtils.invalidateSession();

		return "login";
	}
	
	
	

}