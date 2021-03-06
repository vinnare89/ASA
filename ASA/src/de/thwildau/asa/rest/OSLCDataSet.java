/**
 * 
 */
package de.thwildau.asa.rest;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.siemens.railautomation.oslc.http.request.RequestContext;
import net.siemens.railautomation.oslc.http.request.Requester;
import net.siemens.railautomation.oslc.http.response.ResponseContext;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import android.util.Log;

/**
 * @author z0036w8h
 *
 */
public class OSLCDataSet implements Iterable<OSLCEntry>{

	private HashMap<String, Integer> _properties;
	
	private List<OSLCEntry> _entries;
	
	private String _url;
	
	private Requester _requester;
	

	public OSLCDataSet(String url,String user, String password, boolean loadData) {
		_properties = new HashMap<String, Integer>();
		_url = url;
		_entries = new LinkedList<OSLCEntry>();
		_requester = new Requester(user, password);
		if(loadData) loadData();
	}
	
	public int getIndex(String path){
		Integer i = _properties.get(path);
		if(i == null) return -1;
		return i;
	}
	
	public void addPath(String path){
		if(_properties.containsKey(path)) return;
		int index = _properties.keySet().size();
		//System.out.println(index);
		_properties.put(path, index);
	}
	
	public Set<String> getAllPaths(){
		return _properties.keySet();
	}
	
	public void loadData(){

		RequestContext req = _requester.createRequestContext(null, _url);
		ResponseContext res = _requester.doRequest(req);
		//System.out.println(res.getResponseBody());

		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			OSLCContentHandler srh = new OSLCContentHandler(this);
			reader.setContentHandler(srh);
			reader.parse(new InputSource(new StringReader(res.getResponseBody())));
			System.out.println(res.getResponseBody());
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OSLCEntry createNewEntry(){
		OSLCEntry e = new OSLCEntry(this);
		_entries.add(e);
		return e;
	}

	public Iterator<OSLCEntry> iterator() {
		return _entries.iterator();
	}
	
}
