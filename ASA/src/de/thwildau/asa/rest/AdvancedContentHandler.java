/**
 * 
 */
package de.thwildau.asa.rest;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author z0036w8h
 *
 */
public abstract class AdvancedContentHandler implements ContentHandler {

	private StringBuilder innerText = new StringBuilder();
	
	private LinkedList<String> list = new LinkedList<String>();
	
	public abstract void startTag(String uri, String localName, String qName, Attributes atts, String path);
	
	public abstract void endTag(String uri, String localName, String qName, String path, String innerText);
	
	
	
	protected String getPath(){
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
			sb.append("#");
		}
		
		return sb.toString().replaceAll("feed#entry#", "");
	}
	
	/**
	 *  DO NOT OVERRIDE IN SUBCLASSES!
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		innerText.append(new String(ch, start, length));
	}

	/**
	 * DO NOT OVERRIDE IN SUBCLASSES!
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		innerText.setLength(0);
		startTag(uri, localName, qName, atts, getPath());
		list.addLast(qName);
	}
	
	/**
	 * DO NOT OVERRIDE IN SUBCLASSES!
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		list.removeLast();
		endTag(uri, localName, qName, getPath(), innerText.toString());
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}
	

}
