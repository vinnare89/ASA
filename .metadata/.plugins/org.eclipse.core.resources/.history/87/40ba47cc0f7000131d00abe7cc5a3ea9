/**
 * 
 */
package de.thwildau.asa.rest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author z0036w8h
 *
 */
public class OSLCEntry {

	OSLCDataSet _dataSet;
	
	ArrayList<Object> _data;
	
	public OSLCEntry(OSLCDataSet dataSet) {
		_dataSet = dataSet;
		_data = new ArrayList<Object>();
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean setProperty(String path, String value){
		try {
			//get the index in the list
			int index = _dataSet.getIndex(path);
			
			if (index >= 0) {
				//try to get already existing value
				Object tmp = getProperty(path);
				//if a value already exists
				if (tmp != null) {
					//create a new sublist or add the value to the sublist if it already exists
					if (tmp instanceof List) {
						((List<String>) tmp).add(value);
					} else {
						List<String> list = new LinkedList<String>();
						_data.add(index, list);
						list.add((String) tmp);
						list.add(value);
					}
				} else {
					//if value not already exists add it to the data list
					_data.add(index, value);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Object getProperty(String path){
		int index = _dataSet.getIndex(path);
		if(index >= _data.size() || index < 0) return null;
		
		return _data.get(index);
	}	
	
}
