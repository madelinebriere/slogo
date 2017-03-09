package xml;

import java.util.Arrays;
import java.util.List;


import main.Defaults;

public class XMLParser extends XML{
	
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "background", "pen", "image", "numTurtles", "language"});

	private String background;
	private String pen;
	private String image;
	private String numTurtles;
	private String language;
	
	public XMLParser() {
		super();
		background = XML.ROOT.getElementsByTagName(DATA_FIELDS.get(0)).item(0).getTextContent();
		pen = XML.ROOT.getElementsByTagName(DATA_FIELDS.get(1)).item(0).getTextContent();
		image = XML.ROOT.getElementsByTagName(DATA_FIELDS.get(2)).item(0).getTextContent();
		numTurtles = XML.ROOT.getElementsByTagName(DATA_FIELDS.get(3)).item(0).getTextContent();
		language = XML.ROOT.getElementsByTagName(DATA_FIELDS.get(4)).item(0).getTextContent();

	}

	public Defaults setDefaults()
	{
		return new Defaults(background, pen, image, Integer.parseInt(numTurtles), language);
	}

	
}