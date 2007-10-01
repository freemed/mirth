package com.webreach.mirth.model.converters;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.dcm4che2.data.*;
import org.dcm4che2.io.*;
import org.dcm4che2.tool.dcm2xml.Dcm2Xml;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

import javax.xml.parsers.*;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import com.webreach.mirth.model.dicom.DICOMReference;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;


/**
 * Created by IntelliJ IDEA.
 * User: dans
 * Date: Aug 6, 2007
 * Time: 11:27:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class DICOMSerializer implements IXMLSerializer<String> {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean includePixelData = false;
    public boolean validationError = false;
    private boolean includeGroupLength = false;
    public String temp_dir = "DICOM_TEMP";
    public String files_dir = "DICOM_FILES";

    public DICOMSerializer(Map DICOMProperties){
        if (DICOMProperties == null) { 
			return;
		}
		if (DICOMProperties.get("includePixelData") != null) {
            String pixelData = convertNonPrintableCharacters((String) DICOMProperties.get("includePixelData"));
            if(pixelData.equals("false")){
                this.includePixelData = false;
            }
            else {
                this.includePixelData = true;
            }
        }
		if (DICOMProperties.get("includeGroupLength") != null) {
            String groupLength = convertNonPrintableCharacters((String) DICOMProperties.get("includeGroupLength"));
            if(groupLength.equals("false")){
                this.includeGroupLength = false;
            }
            else {
                this.includeGroupLength = true;
            }
        }        
        new File(temp_dir).mkdir();
        new File(files_dir).mkdir();
    }

	private String convertNonPrintableCharacters(String delimiter) {
		return delimiter.replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");
	}

	public DICOMSerializer() {
	}

	public String fromXML(String source) throws SerializerException {
        if(source == null || source.equals("")){
            return "";
        }
        try {
            // 1. reparse the xml to Mirth format
            DicomObject dicomObject = new BasicDicomObject();
            SAXParserFactory f = SAXParserFactory.newInstance();
            SAXParser p = f.newSAXParser();
            ContentHandlerAdapter ch = new ContentHandlerAdapter(dicomObject);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(source)));
                Element element = document.getDocumentElement();
                Node node = element.getChildNodes().item(0);
                // change back to <attr> tag for all tags under <dicom> tag
                while(node != null){
                    NamedNodeMap attr = node.getAttributes();
                    if(attr == null) {
                        node = node.getNextSibling();
                        continue;
                    }
                    Node tagAttr = attr.getNamedItem("tag");
                    //System.out.println("tag (value): " + tagAttr.getNodeValue());
                    if(tagAttr != null) {
                        String tag = tagAttr.getNodeValue();
                        String tagDesc = DICOMReference.getInstance().getDescription(tag,null);
                        tagDesc = removeInvalidCharXML(tagDesc);
                        //System.out.println("tag: " + tagDesc);
                        try {
                            if(!tagDesc.equals("?"))  {
                                if(node.getNodeName().equals(tagDesc)){
                                    document.renameNode(node,null,"attr");
                                }
                            }
                        }
                        catch(DOMException e){
                            e.printStackTrace();
                        }
                    }
                    node = node.getNextSibling();
                }
                NodeList items = document.getElementsByTagName("item");
                // change back to <attr> tag for all tags under <item> tags
                if(items != null){
                    for(int i=0;i<items.getLength();i++){
                        Node itemNode = items.item(i);
                        if(itemNode.getChildNodes() != null){
                            NodeList itemNodes = itemNode.getChildNodes();
                            for(int j=0;j<itemNodes.getLength();j++){
                                Node nodeItem = itemNodes.item(j);
                                NamedNodeMap attr = nodeItem.getAttributes();
                                if(attr == null) {
                                    continue;
                                }
                                Node tagAttr = attr.getNamedItem("tag");
                                //System.out.println("tag (value): " + tagAttr.getNodeValue());
                                if(tagAttr != null) {
                                    String tag = tagAttr.getNodeValue();
                                    String tagDesc = DICOMReference.getInstance().getDescription(tag,null);
                                    tagDesc = removeInvalidCharXML(tagDesc);
                                    //System.out.println("tag: " + tagDesc);
                                    try {
                                        if(!tagDesc.equals("?"))  {
                                            if(nodeItem.getNodeName().equals(tagDesc)){
                                                document.renameNode(nodeItem,null,"attr");
                                            }
                                        }
                                    }
                                    catch(DOMException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                // 2. Create temp XML file
                File tempXML = new File(temp_dir+"/tempXMLIn.xml");
                FileOutputStream fos = new FileOutputStream(tempXML);
                //fos.write(source.getBytes());
                String test = new DocumentSerializer().toXML(document);
                fos.write(new DocumentSerializer().toXML(document).getBytes());
                fos.close();                    
                if(includePixelData){
                    p.parse(tempXML,ch);
                }
                else {
                    p.parse(new ByteArrayInputStream(new DocumentSerializer().toXML(document).getBytes()),ch,"file:" + new File(files_dir, "STDIN").getAbsolutePath());    
                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                File tempDcmOut = new File(temp_dir+"/tempDcmOut.dcm");//File.createTempFile("temp",".dcm");
                DicomOutputStream dos = new DicomOutputStream(new BufferedOutputStream(new FileOutputStream(tempDcmOut)));                        
                // include group length. Option g
                if(includeGroupLength){
                    dos.setIncludeGroupLength(true);
                }
                // option u
               // dos.setExplicitItemLengthIfZero(true);
                // option U
               // dos.setExplicitSequenceLengthIfZero(true);
                // option e
//                dos.setExplicitItemLength(true);
                // option E
//                dos.setExplicitSequenceLength(true);
                //DicomOutputStream dos = new DicomOutputStream(bos);
                try {
                    dos.writeDicomFile(dicomObject);
                }
                // If missing Transfer Syntax UID Tag, try writing DicomObject instead
                catch(IllegalArgumentException e){
                    //dicomObject.initFileMetaInformation(TransferSyntax.ExplicitVRLittleEndian.uid());
                    dos = new DicomOutputStream(bos);
                    if(includeGroupLength){
                        dos.setIncludeGroupLength(true);
                    }
                    dos.writeDicomObject(dicomObject);
                    FileOutputStream fos2 = new FileOutputStream(tempDcmOut);
                    fos2.write(bos.toByteArray());
                    fos2.close();
//                    dicomObject.putString(Tag.TransferSyntaxUID, VR.UI, TransferSyntax.ExplicitVRLittleEndian.uid());        
//                    dos.writeDicomFile(dicomObject);
                }
                dos.close();
                byte[] temp = getBytesFromFile(tempDcmOut);
                String preEncoding = temp.toString();
                BASE64Encoder encoder = new BASE64Encoder();
                String encodedMessage = encoder.encode(temp);
                return encodedMessage;
            }
            catch(ParserConfigurationException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializerException(e.getMessage());
		}
        return new String();
    }

	public String toXML(String source)   {        
        try {
            // 1. Decode source
            byte[] temp = null;
            BASE64Decoder decoder = new BASE64Decoder();
            temp = decoder.decodeBuffer(source);
            // 2. put data into a temp file
            File tempDcmFile = new File(temp_dir+"/tempDcmIn.dcm");//File.createTempFile("temp",".dcm");//
            FileOutputStream fos = new FileOutputStream(tempDcmFile);
            fos.write(temp);
            fos.close();
            // 3. create temp xml output file
            File xmlOutput = new File(temp_dir+"/tempXMLOut.xml");//File.createTempFile("temp",".xml");//new File("tempXMLOut.xml");  
            // 4. Call conversion method
            Dcm2Xml dcm2xml = new Dcm2Xml();
            if(!includePixelData) {
                dcm2xml.setExclude(new int[] {Tag.PixelData});
                dcm2xml.setBaseDir(new File(files_dir));                  
            }
            //dcm2xml.setIndent(false);
            dcm2xml.convert(tempDcmFile,xmlOutput);
            return decodeTagNames(new String(getBytesFromFile(xmlOutput)));
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new String();
	}
    public String toXML(File tempDCMFile) throws SerializerException {
        try {
            // Encode it before transforming it
            BASE64Encoder encoder = new BASE64Encoder();
            return toXML(encoder.encode(getBytesFromFile(tempDCMFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }
    
    private Map<String, String> getMetadata(String sourceMessage) throws SerializerException {
		DocumentSerializer docSerializer = new DocumentSerializer();
		docSerializer.setPreserveSpace(true);
		Document document = docSerializer.fromXML(sourceMessage);
		return getMetadataFromDocument(document);
	}

	public Map<String, String> getMetadataFromDocument(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		String sendingFacility = "dicom";
		String event = "DICOM";
		String version = "";
		map.put("version", version);
		map.put("type", event);
		map.put("source", sendingFacility);
		return map;
	}

	public Map<String, String> getMetadataFromEncoded(String source) throws SerializerException {
		String DICOMXML = fromXML(source);
		return getMetadata(DICOMXML);
	}

	public Map<String, String> getMetadataFromXML(String xmlSource) throws SerializerException {
		return getMetadata(xmlSource);
	}
    private String decodeTagNames(String input){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document;
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(new InputSource(new StringReader(input)));
                NodeList nodeList = document.getElementsByTagName("attr");
                for(int i = 0; i < nodeList.getLength();i++){
                    Node node = nodeList.item(i);
                    if(node.getNodeName().equals("attr")){
                        Node tagAttr = node.getAttributes().getNamedItem("tag");
                        String tag = tagAttr.getNodeValue();
                        String tagDesc = DICOMReference.getInstance().getDescription(tag,null);
                        tagDesc = removeInvalidCharXML(tagDesc);
                        try {
                            if(!tagDesc.equals("?")) 
                                document.renameNode(node,null,tagDesc);  
                        }
                        catch(DOMException e){
                            e.printStackTrace();
                        }
                    }
                }
                return new DocumentSerializer().toXML(document);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        return new String();
    }

    private String convertToXML(DicomInputStream dis){
        StringWriter xmlOutput = new StringWriter();
        try{
            try {
                SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory.newInstance();
                TransformerHandler th = tf.newTransformerHandler();
                th.getTransformer().setOutputProperty(OutputKeys.INDENT, "no");
                th.setResult(new StreamResult(xmlOutput));
                final SAXWriter writer = new SAXWriter(th, null);
                if(!includePixelData) {
                    writer.setExclude(new int[]{Tag.PixelData});
                    writer.setBaseDir(new File(files_dir));
                }
                dis.setHandler(writer);
                dis.readDicomObject(new BasicDicomObject(), -1);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                dis.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //return xmlOutput.toString();
        return decodeTagNames(xmlOutput.toString());
    }
    public String removeInvalidCharXML(String tag){
        tag = tag.replaceAll(" ", "");
        tag = tag.replaceAll("'", "");
        tag = tag.replaceAll("\\(","");
        tag = tag.replaceAll("\\)","");
        tag = tag.replaceAll("/","");
        tag = tag.replaceAll("&","");
        return tag;
    }
	// Returns the contents of the file in a byte array.
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
}
