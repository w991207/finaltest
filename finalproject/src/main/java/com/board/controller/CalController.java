package com.board.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.board.command.InsertCalCommand;
import com.board.dtos.CalDto;
import com.board.service.CalService;
import com.board.utils.Util;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/cal")
public class CalController {
   
   @Autowired 
   CalService calService;
   
    @GetMapping(value="/calendar")
       public String calendar(Model model, HttpServletRequest request, String ykiho) {
          System.out.println("병원의 예약 현황 보기");
          System.out.println(ykiho);
          //달력에서 일일별 일정목록 구하기
          String year=request.getParameter("year");
          String month=request.getParameter("month");
          
          if(year==null||month==null) {
             Calendar cal=Calendar.getInstance();
             year=cal.get(Calendar.YEAR)+"";
             month=(cal.get(Calendar.MONTH)+1)+"";
          }
          System.out.println("year:"+year);
          System.out.println("month:"+month);
          //달력만들기위한 값 구하기
          Map<String, Integer>map=calService.makeCalendar(request);
          model.addAttribute("calMap", map);
          model.addAttribute("ykiho", ykiho);
          String yyyyMM=year+Util.isTwo(month);//202311 6자리변환
          List<CalDto>clist=calService.calViewList(yyyyMM, ykiho);
          model.addAttribute("clist", clist);
          
          return "cal/Calendar";
       }
       
       @ResponseBody
       @GetMapping(value = "/cal", produces = "application/json")
       public ResponseEntity<List<Map<String,String>>> cal(String sgguCd, String dgsbjtCd, String ykiho) throws IOException, ParserConfigurationException {
           System.out.println("검색목록 보기");
           HttpURLConnection conn = null;
           List<Map<String, String>> resultList = new ArrayList<>();
           
           try {
               URL url = new URL("https://apis.data.go.kr/B551182/hospAsmInfoService/getHospAsmInfo?"
                       + "pageNo=1"
                       + "&numOfRows=10"
                       + "&ServiceKey=%2BDfDMIhG5kDUTz24ii%2FSw6DK5tVQExSNQmQQy7eyHU2tebzs14UXPh%2FWhQEluGOQ97nb3NBaelLCkgHRfvVgSQ%3D%3D"
                       + "&ykiho=" + ykiho);

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
//                       String ykiho = itemElement.getElementsByTagName("ykiho").item(0).getTextContent();
                       map.put("yadmNm",yadmNm);
//                       map.put("ykiho",ykiho);
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
       
       
       @GetMapping(value = "/addCalBoardForm")
       public String addCalBoardForm(Model model, InsertCalCommand insertCalCommand, String ykiho) {
    	   System.out.println("일정추가 폼 이동");
    	   System.out.println(ykiho);
          System.out.println(insertCalCommand);
          //addCalBoardfForm 페이지에서 유효값 처리를 위해 insertCalCommand 받고 있기때문에
          model.addAttribute("insertCalCommand", insertCalCommand);
          model.addAttribute("ykiho",ykiho);
          return "cal/addCalBoardForm";
       }
       
       @PostMapping(value = "/addCalBoard")
       public String addCalBoard(@Validated InsertCalCommand insertCalCommand,
                           BindingResult result, String ykiho) throws Exception {
          System.out.println("일정추가하기");
          System.out.println(insertCalCommand);
          if(result.hasErrors()) {
             System.out.println("글을 모두 입력해야 함");
             return "cal/addCalBoardForm";
          }
          calService.insertCalBoard(insertCalCommand, ykiho);
          return "redirect:/cal/calendar?year="+insertCalCommand.getYear()
                                  +"&month="+insertCalCommand.getMonth() + "&ykiho="+ykiho;
       }
       @ResponseBody
       @GetMapping(value="/calCountAjax")
       public Map<String,Integer> calCountAjax(String yyyyMMdd){

          Map<String, Integer>map=new HashMap<>();
          int count=calService.calBoardCount(yyyyMMdd);
          map.put("count", count);
          return map;
       }
}