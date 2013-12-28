package es.coremain.coremainWeb.view.bean.territory;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import es.coremain.coremainTerritoryService.dto.ProvinciaDto;
import es.coremain.coremainTerritoryService.service.TerritoryService;
import es.coremain.coremainTerritoryService.util.ConstantesTerritoryService;
import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "provinciaBean")
@ViewScoped
public class ProvinciaBean implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private List<ProvinciaDto> provincias;
	
	private UserDto selectedProvincia; // Al anhadir una nueva provincia la selectedProvincia sera la nueva provincia
		
	
	public List<ProvinciaDto> getProvincias() {
		
		if (provincias == null) {
			TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
			
			provincias = territoryService.getProvinciasByIdPais(ConstantesTerritoryService.ID_PAIS_ESPANHA);
		}
		
		return provincias;
	}

	public void setProvincias(List<ProvinciaDto> provincias) {
		this.provincias = provincias;
	}		

	public UserDto getSelectedProvincia() {
		return selectedProvincia;
	}

	public void setSelectedProvincia(UserDto selectedProvincia) {
		this.selectedProvincia = selectedProvincia;
	}
		
	
	public String goProvinciaDetail() {
		// TODO: indicar de alguna manera la selectedProvincia para tenerla en el nuevo provinciaBean que se crea al 
		// cambiar de pagina ya que este ManagedBean tiene ViewScope
		return "detalleProvincia";
	}
	
	public void updateProvincia(ActionEvent event){
		
		//TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
		
		//territoryService.updateProvincia(selectedProvincia);		
		
		FacesUtils.setInfoMessage(null, "detalleProvincia_provinciaActualizada", null);
	}
	
}
