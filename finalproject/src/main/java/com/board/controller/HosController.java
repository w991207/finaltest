package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.board.dtos.CalDto;
import com.board.service.CalService;
import com.board.utils.Util;

import jakarta.servlet.http.HttpServletRequest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/hos")
public class HosController {
   
   @Autowired 
   CalService calService;

    @GetMapping(value = "/boardList")
    public String boardList(Model model) {
        System.out.println("병원목록 보기");
        return "hos/boardList";
    }

    @ResponseBody
    @GetMapping(value = "/selectList", produces = "application/json")
    public ResponseEntity<List<Map<String,String>>> selectList(String sgguCd, String dgsbjtCd) throws IOException, ParserConfigurationException {
        System.out.println("검색목록 보기");
        HttpURLConnection conn = null;
        List<Map<String, String>> resultList = new ArrayList<>();
        
        try {
            URL url = new URL("https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?"
                    + "serviceKey=%2BDfDMIhG5kDUTz24ii%2FSw6DK5tVQExSNQmQQy7eyHU2tebzs14UXPh%2FWhQEluGOQ97nb3NBaelLCkgHRfvVgSQ%3D%3D"
                    + "&pageNo=1"
                    + "&numOfRows=10"
                    + "&sgguCd=" + sgguCd
                    + "&dgsbjtCd=" + dgsbjtCd);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);

            // Parse the XML response
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            // Extract data from XML
            NodeList itemList = doc.getElementsByTagName("item");
            
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                Map<String, String>map = new HashMap<>();   
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    String yadmNm = itemElement.getElementsByTagName("yadmNm").item(0).getTextContent();
                    String ykiho = itemElement.getElementsByTagName("ykiho").item(0).getTextContent();
                    map.put("yadmNm",yadmNm);
                    map.put("ykiho",ykiho);
                    resultList.add(map);
                }
            }

            // Set the Content-Type header to application/json
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(resultList, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
















