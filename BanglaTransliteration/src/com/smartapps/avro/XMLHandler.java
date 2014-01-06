package com.smartapps.avro;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.smartapps.avro.phonetic.Data;

public class XMLHandler extends DefaultHandler {

	private Data data;

	public Data getData() {
		return this.data;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		Log.e("StartElement", localName+"----------"+qName);
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Log.e("StartElement", localName + "----------" + qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

	}
}
