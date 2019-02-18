package mesin;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainApp {
	
	public static void main(String[] args) {
		//시작시간
		long startTime = System.currentTimeMillis();
		System.out.println("startTime :" + startTime);
		
		PracticeXML prXML = new PracticeXML();
		
		//T_BASEFILE_TB.xml가져오기
		Document baseDocument = prXML.fileParsing("T_BASEFILE_TB.xml");
		
		//baseFile의 FILE_ID tag의 node를 nodeList로 저장
		Map<String, NodeList> baseFileMap = prXML.getNodeLists(baseDocument, "FILE_ID");

		//T_BASEFILE_TB.xml의 {FILE_ID}별로 진행
		for(int temp1=0; temp1<baseFileMap.get("FILE_ID").getLength(); temp1++) {	
			
			String fileId = baseFileMap.get("FILE_ID").item(temp1).getTextContent();
			
			//F_{FILE_ID}_TB.xml 가져오기
			Document fDocument = prXML.fileParsing("F_" + fileId + "_TB.xml");			
			//fFile의 SIMILAR_RATE, P_ID, COMMENT tag의 nodeList를 Map에 저장
			Map<String, NodeList> fFileMap = prXML.getNodeLists(fDocument, "SIMILAR_RATE", "P_ID", "COMMENT");
			
			//P_{FILE_ID}_TB.xml 가져오기
			Document pDocument = prXML.fileParsing("P_" + fileId + "_TB.xml");			
			//pFile의 P_ID, LICENSE_ID tag의 nodeList를 Map에 저장
			Map<String,NodeList> pFileMap = prXML.getNodeLists(pDocument, "P_ID", "LICENSE_ID");
			
			
			//tFile을 생성
			File tFile = new File("C:\\Users\\meta\\Desktop\\1.XML 파일 분석/"+"T_"+fileId+"_TB.xml");
			
			//F_{FILE_ID}_TB.xml의 SIMILAR_RATE별로 진행
			for(int temp2=0; temp2<fFileMap.get("SIMILAR_RATE").getLength(); temp2++) {
				//fFile의 SIMILAR_RATE를 100으로 나눈값이 15보다 큰 경우
				String similarRate = fFileMap.get("SIMILAR_RATE").item(temp2).getTextContent();
							
				if(Integer.parseInt(similarRate)/100>15) {					
					//해당 fFile의 P_ID값을 pIdFromF변수에 저장
					String pIdFromF = fFileMap.get("P_ID").item(temp2).getTextContent();
					
					//pIdFromF값과 같은 pFile의 P_ID의 LICENSE_ID의 값을 저장
					Node licenseIdNode = prXML.searchNodeList(pIdFromF, pFileMap.get("P_ID"), pFileMap.get("LICENSE_ID"));
					
					//fFile의 COMMENT의 값을 licenseId값으로 변경
					if(!licenseIdNode.getTextContent().equals("")) {
						fFileMap.get("COMMENT").item(temp2).setTextContent(licenseIdNode.getTextContent());
					}
				}
			}
			//변경된fDcoument를 tFile로 저장
			prXML.saveXMLFile(fDocument, tFile);
		}//for문 끝

		//종료시간
		long endTime = System.currentTimeMillis();
		System.out.println("endTime :" + endTime);
		//걸린시간
		long duration = endTime-startTime;
		System.out.println("duration :" + duration);		
	}//main함수 끝
}
