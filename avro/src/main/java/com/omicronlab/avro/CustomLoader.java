package main.java.com.omicronlab.avro;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.smartapps.avro.phonetic.Data;
import com.smartapps.avro.phonetic.Match;
import com.smartapps.avro.phonetic.Pattern;
import com.smartapps.avro.phonetic.Rule;

public class CustomLoader implements PhoneticLoader {
	private InputStream is;

	// private String xmlString;

	public CustomLoader() {

		// Data.class.getResource("file:///android_asset/phonetic.xml");
		//

		try {
			is = new In;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// this.xmlString = getXml("phonetic.xml");

	}

	// public Data getData()
	// {
	// return null;
	// }

	public Data getData() {
		// XStream xstream = new XStream(new DomDriver("UTF-8"));
		// // Data data=new Data();
		// xstream.alias("data", com.smartapps.avro.phonetic.Data.class);
		// // InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		// Data data = (Data) xstream.fromXML(xmlString);
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			// xpp.setInput(new StringReader(xmlString));
			xpp.setInput(is, "UTF-8");
			int eventType = xpp.getEventType();
			Data data = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					data = new Data();
				} else if (eventType == XmlPullParser.START_TAG) {
					String tagName = xpp.getName();
					if (tagName.equalsIgnoreCase("vowel")) {
						String vowel = xpp.nextText();
						data.setVowel(vowel);
					} else if (tagName.equalsIgnoreCase("consonant")) {
						String consonant = xpp.nextText();
						data.setConsonant(consonant);
					} else if (tagName.equalsIgnoreCase("casesensitive")) {
						String casesensitive = xpp.nextText();
						data.setConsonant(casesensitive);
					} else if (eventType == XmlPullParser.START_TAG
							&& tagName.equalsIgnoreCase("patterns")) {
						eventType = xpp.next();
						while (eventType != XmlPullParser.END_TAG
								|| 0 != "patterns".compareTo(xpp.getName())) {
							// Pattern pattern=new Pattern();
							eventType = xpp.getEventType();

							if (eventType == XmlPullParser.START_TAG
									&& 0 == "pattern".compareTo(xpp.getName())) {

								data.addPattern(getPattern(xpp));

							}
							eventType = xpp.next();
						}

					}

				}
				eventType = xpp.next();

			}

			return data;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private Pattern getPattern(XmlPullParser xpp) {

		Pattern pattern = new Pattern();
		int eventType;
		try {
			eventType = xpp.nextTag();
			if (eventType == XmlPullParser.START_TAG
					&& 0 == "find".compareTo(xpp.getName())) {
				String find = xpp.nextText();
				pattern.setFind(find);
			}
			xpp.nextTag();
			if (eventType == XmlPullParser.START_TAG
					&& 0 == "replace".compareTo(xpp.getName())) {
				String replace = xpp.nextText();
				pattern.setReplace(replace);
			}

			xpp.nextTag();
			// Log.e("ERRROR", "-----------------------" + xpp.getName()
			// + "----------------");
			if (eventType == XmlPullParser.START_TAG
					&& 0 == "rules".compareTo(xpp.getName())) {
				// eventType = xpp.next();
				while (eventType != XmlPullParser.END_TAG
						|| 0 != "rules".compareTo(xpp.getName())) {
					if (eventType == XmlPullParser.START_TAG
							&& 0 == "rule".compareTo(xpp.getName())) {

						pattern.addRule(loadRule(xpp));
					}
					eventType = xpp.next();
				}
			}
			return pattern;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private Rule loadRule(XmlPullParser xpp) {
		Rule rule = new Rule();
		int eventType;
		try {
			eventType = xpp.nextTag();
			// Log.e("ERRROR", xpp.getName() + "----------------X");
			if (eventType == XmlPullParser.START_TAG
					&& 0 == "find".compareTo(xpp.getName())) {
				// eventType = xpp.next();

				while ((eventType != XmlPullParser.END_TAG || 0 != "find"
						.compareTo(xpp.getName()))) {
					if (eventType == XmlPullParser.START_TAG
							&& 0 == "match".compareTo(xpp.getName())) {
						String type = xpp.getAttributeValue(null, "type");
						String scope = xpp.getAttributeValue(null, "scope");
						String value = xpp.nextText();
						Match match = new Match();
						match.setScope(scope);
						match.setType(type);
						match.setValue(value);
						rule.addMatch(match);
					}

					eventType = xpp.next();
				}

			} else if (eventType == XmlPullParser.START_TAG
					&& 0 != "replace".compareTo(xpp.getName())) {
				rule.setReplace(xpp.nextText());
			}
			return rule;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getXml(String path) {

		String xmlString = null;
		AssetManager am = context.getAssets();
		try {
			InputStream is = am.open(path);

			int length = is.available();
			byte[] data = new byte[length];
			is.read(data);
			xmlString = new String(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return xmlString;
	}
}
