package es.coremain.coremainWeb.view.bean.territory;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import es.coremain.coremainTerritoryService.dto.ComunidadDto;
import es.coremain.coremainTerritoryService.service.TerritoryService;
import es.coremain.coremainTerritoryService.util.ConstantesTerritoryService;
import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "comunidadBean")
@ViewScoped
public class ComunidadBean implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private List<ComunidadDto> comunidades;
	
	private UserDto selectedComunidad; // Al anhadir una nueva comunidad la selectedComunidad sera la nueva comunidad
		
	
	public List<ComunidadDto> getComunidades() {
		
		if (comunidades == null) {
			TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
			
			comunidades = territoryService.getComunidadesByIdPais(ConstantesTerritoryService.ID_PAIS_ESPANHA);
		}
		
		return comunidades;
	}

	public void setComunidades(List<ComunidadDto> comunidades) {
		this.comunidades = comunidades;
	}		

	public UserDto getSelectedComunidad() {
		return selectedComunidad;
	}

	public void setSelectedComunidad(UserDto selectedComunidad) {
		this.selectedComunidad = selectedComunidad;
	}
		
	
	public String goComunidadDetail() {
		// TODO: indicar de alguna forma la selectedComunidad para tenerla en el nuevo comunidadBean que se crea al 
		// cambiar de pagina ya que este ManagedBean tiene ViewScope
		return "detalleComunidad";
	}
	
	public void updateComunidad(ActionEvent event){
		
		//TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
		
		//territoryService.updateComunidad(selectedComunidad);		
		
		FacesUtils.setInfoMessage(null, "detalleComunidad_comunidadActualizada", null);
	}
	
}
