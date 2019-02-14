package mesin;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainApp {
	
	public static void main(String[] args) {
		//���۽ð�
		long startTime = System.currentTimeMillis();
		System.out.println("startTime :" + startTime);
		
		PracticeXML prXML = new PracticeXML();
		
		//T_BASEFILE_TB.xml��������
		Document baseDocument = prXML.fileParsing("T_BASEFILE_TB.xml");
		
		//baseFile�� FILE_ID tag�� node�� nodeList�� ����
		Map<String, NodeList> baseFileMap = prXML.getNodeLists(baseDocument, "FILE_ID");

		//T_BASEFILE_TB.xml�� {FILE_ID}���� ����
		for(int temp1=0; temp1<baseFileMap.get("FILE_ID").getLength(); temp1++) {	
			
			String fileId = baseFileMap.get("FILE_ID").item(temp1).getTextContent();
			
			//F_{FILE_ID}_TB.xml ��������
			Document fDocument = prXML.fileParsing("F_" + fileId + "_TB.xml");			
			//fFile�� SIMILAR_RATE, P_ID, COMMENT tag�� nodeList�� Map�� ����
			Map<String, NodeList> fFileMap = prXML.getNodeLists(fDocument, "SIMILAR_RATE", "P_ID", "COMMENT");
			
			//P_{FILE_ID}_TB.xml ��������
			Document pDocument = prXML.fileParsing("P_" + fileId + "_TB.xml");			
			//pFile�� P_ID, LICENSE_ID tag�� nodeList�� Map�� ����
			Map<String,NodeList> pFileMap = prXML.getNodeLists(pDocument, "P_ID", "LICENSE_ID");
			
			
			//tFile�� ����
			File tFile = new File("C:\\Users\\meta\\Desktop\\1.XML ���� �м�/"+"T_"+fileId+"_TB.xml");
			
			//F_{FILE_ID}_TB.xml�� SIMILAR_RATE���� ����
			for(int temp2=0; temp2<fFileMap.get("SIMILAR_RATE").getLength(); temp2++) {
				//fFile�� SIMILAR_RATE�� 100���� �������� 15���� ū ���
				String similarRate = fFileMap.get("SIMILAR_RATE").item(temp2).getTextContent();
							
				if(Integer.parseInt(similarRate)/100>15) {					
					//�ش� fFile�� P_ID���� pIdFromF������ ����
					String pIdFromF = fFileMap.get("P_ID").item(temp2).getTextContent();
					
					//pIdFromF���� ���� pFile�� P_ID�� LICENSE_ID�� ���� ����
					Node licenseIdNode = prXML.searchNodeList(pIdFromF, pFileMap.get("P_ID"), pFileMap.get("LICENSE_ID"));
					
					//fFile�� COMMENT�� ���� licenseId������ ����
					if(!licenseIdNode.getTextContent().equals("")) {
						fFileMap.get("COMMENT").item(temp2).setTextContent(licenseIdNode.getTextContent());
					}
				}
			}
			//�����fDcoument�� tFile�� ����
			prXML.saveXMLFile(fDocument, tFile);
		}//for�� ��

		//����ð�
		long endTime = System.currentTimeMillis();
		System.out.println("endTime :" + endTime);
		//�ɸ��ð�
		long duration = endTime-startTime;
		System.out.println("duration :" + duration);		
	}//main�Լ� ��
}
