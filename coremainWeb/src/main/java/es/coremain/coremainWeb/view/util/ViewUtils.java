package es.coremain.coremainWeb.view.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import es.coremain.coremainBaseService.util.comparator.DateComparator;
import es.coremain.coremainUserService.dto.UserDto;

public class ViewUtils {

	

	public static Collection<SelectItem> getUsersSelectItems(Collection<UserDto> users) {
		ArrayList<SelectItem> selectItems = null;
		if (users != null && !users.isEmpty()) {

			selectItems = new ArrayList<SelectItem>();
			
			for (UserDto user: users){
				if (user != null) {
					String label = user.getFullName();
					String value = user.getUserId().toString();

					SelectItem selectItem = new SelectItem(value, label);
					selectItems.add(selectItem);
				}								
			}			
		}
		return selectItems;
	}	
		
	
	/**
	 * 
	 * @param fechas
	 * @return Una coleccion de selectItems a partir de una coleccion de fechas
	 */
	public static Collection<SelectItem> getDateSelectItems(Collection<Date> fechas, boolean ordenadas) {
		Collection<SelectItem> selectItems = null;
		if (fechas != null && !fechas.isEmpty()) {

			if (ordenadas) {

				if (fechas instanceof java.util.HashSet) {

					Object[] array = fechas.toArray();
					ArrayList<Date> listaFechas = new ArrayList<Date>();
					for (int i = 0; i < array.length; i++) {
						listaFechas.add((Date) array[i]);
					}
					fechas = listaFechas;
				}

				// Ordena las fechas de antiguas a modernas
				Comparator<Date> dateComparator = new DateComparator();
				Collections.sort((List<Date>) fechas, dateComparator);
			}

			selectItems = new ArrayList<SelectItem>();
			SimpleDateFormat formateadorFechaSimple = new SimpleDateFormat("dd/MM/yyyy");

			Iterator<Date> iterFechas = fechas.iterator();
			while (iterFechas.hasNext()) {
				Date fecha = (Date) iterFechas.next();
				if (fecha != null) {
					String label = formateadorFechaSimple.format(fecha);
					SelectItem selectItem = new SelectItem(fecha, label);
					selectItems.add(selectItem);
				}
			}
		} else {
			return new ArrayList<SelectItem>();
		}

		return selectItems;
	}

	/*
	public static Collection<SelectItem> getProvinciasSelectItems(Collection<ProvinciaDto> provincias) {
		Collection<SelectItem> selectItems = null;
		if (provincias != null && !provincias.isEmpty()) {

			selectItems = new ArrayList<SelectItem>();

			Iterator<ProvinciaDto> iterProvincias = provincias.iterator();
			while (iterProvincias.hasNext()) {
				ProvinciaDto provincia = (ProvinciaDto) iterProvincias.next();
				if (provincia != null) {
					String label = provincia.getNombre();
					Long value = provincia.getIdProvincia();

					SelectItem selectItem = new SelectItem(value, label);
					selectItems.add(selectItem);
				}
			}
		}
		return selectItems;
	}
	*/

}
