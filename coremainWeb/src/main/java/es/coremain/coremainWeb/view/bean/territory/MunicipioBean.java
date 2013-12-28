package es.coremain.coremainWeb.view.bean.territory;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import es.coremain.coremainTerritoryService.dto.MunicipioCdto;
import es.coremain.coremainTerritoryService.service.TerritoryService;
import es.coremain.coremainTerritoryService.util.ConstantesTerritoryService;
import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "municipioBean")
@ViewScoped
public class MunicipioBean implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private List<MunicipioCdto> municipios;
	
	private UserDto selectedMunicipio; // Al anhadir un nuevo municipio el selectedMunicipio sera el nueva municipio
		
	
	public List<MunicipioCdto> getMunicipios() {
		
		if (municipios == null) {
			TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
			
			municipios = territoryService.getMunicipiosByIdPais(ConstantesTerritoryService.ID_PAIS_ESPANHA);
		}
		
		return municipios;
	}

	public void setMunicipios(List<MunicipioCdto> municipios) {
		this.municipios = municipios;
	}		

	public UserDto getSelectedMunicipio() {
		return selectedMunicipio;
	}

	public void setSelectedMunicipio(UserDto selectedMunicipio) {
		this.selectedMunicipio = selectedMunicipio;
	}
		
	
	public String goMunicipioDetail() {
		// TODO: indicar de alguna forma el selectedMunicipio para tenerlo en el nuevo municipioBean que se crea al 
		// cambiar de pagina ya que este ManagedBean tiene ViewScope
		return "detalleMunicipio";
	}
	
	public void updateMunicipio(ActionEvent event){
		
		//TerritoryService territoryService = FacesUtils.getService("territoryService", TerritoryService.class);
		
		//territoryService.updateMunicipio(selectedMunicipio);		
		
		FacesUtils.setInfoMessage(null, "detalleMunicipio_municipioActualizada", null);
	}
	
}
