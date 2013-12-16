/**
 * 
 */
package de.thwildau.asa.rest;

import org.xml.sax.Attributes;

/**
 * @author z0036w8h
 * 
 */
public class OSLCContentHandler extends AdvancedContentHandler {

	private OSLCDataSet _dataSet;

	private OSLCEntry entry;

	public OSLCContentHandler(OSLCDataSet dataSet) {
		_dataSet = dataSet;
	}

	@Override
	public void startTag(String uri, String localName, String qName,
			Attributes atts, String path) {
		
		if (qName.equals("entry")) {
			entry = _dataSet.createNewEntry();
		}
	}

	@Override
	public void endTag(String uri, String localName, String qName, String path,
			String innerText) {
		
		if(innerText != null && entry != null && innerText != ""){
			_dataSet.addPath(path+localName);
			entry.setProperty(path+localName, innerText);
		}

	}

}
