package com.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.servlet.http.Cookie;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.board.command.DelReviewCommand;
import com.board.command.InsertReplyCommand;
import com.board.command.InsertReviewCommand;
import com.board.command.UpdateReviewCommand;
import com.board.dtos.ReviewDto;
import com.board.service.ReviewService;
import com.board.utils.Paging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/review")
public class ReviewController {

   @Autowired
   private ReviewService reviewService;

   
   @GetMapping(value = "/reviewList")
   public String NewsBoardList(Model model,HttpServletRequest request,HttpServletResponse response, String pnum) {
      System.out.println("리뷰목록 보기");
     HttpSession session=request.getSession();
      if(pnum==null) {
         pnum=(String)session.getAttribute("pnum");//현재 조회중인 페이지번호
      }else {
         //새로 페이지를 요청할 경우 세션에 저장
         session.setAttribute("pnum", pnum);
      }
     //페이지 수 구하기 
      int pcount=reviewService.getPCount();
     model.addAttribute("pcount", pcount);
     System.out.println("pcount : " +pcount);
     System.out.println("pnum : " +pnum);
            
     //---페이지에 페이징 처리 기능 추가
     //필요한 값: 페이지수, 페이지번호, 페이지범위(페이지수)
     Map<String, Integer>map=Paging.pagingValue(pcount, pnum, 10);
     model.addAttribute("pMap", map);
      
      List<ReviewDto> list = reviewService.getAllList(pnum);
      System.out.println(list);
      model.addAttribute("list", list);
//      model.addAttribute("delBoardCommand", new NewsDelBoardCommand());
      
      return "review/reviewList";
   }
   
   @GetMapping(value = "/reviewInsert")
   public String boardInsertForm(Model model) {
      System.out.println("글추가폼 이동");
      model.addAttribute("insertReviewCommand", new InsertReviewCommand());
      
      return "review/reviewInsertForm";
   }
   
   @PostMapping(value = "/reviewInsert")
   public String boardInsert(@Validated InsertReviewCommand insertReviewCommand,
                           BindingResult result, MultipartRequest multipartRequest,
                           HttpServletRequest request,
                           Model model) throws IllegalStateException, IOException {
      
      if(result.hasErrors()) {
         System.out.println("글을 모두 입력해주세요");
         return "review/reviewInsertForm";
      }
      
      reviewService.insertReview(insertReviewCommand, multipartRequest, request);
      
      return "redirect:/review/reviewList";
   }
   
   @GetMapping(value = "/reviewDetail")
   public String boardDetail(int board_seq, Model model, UpdateReviewCommand updateReviewCommand) {
	   ReviewDto dto = reviewService.getBoard(board_seq);
      model.addAttribute("updateReviewCommand", new UpdateReviewCommand());
      model.addAttribute("dto",dto);
      int seq = updateReviewCommand.getBoard_seq();
      reviewService.readCount(seq);//조회수 증가

      return "review/reviewDetail";
   }
//   
   @PostMapping(value = "/reviewUpdate")
   public String reviewUpdate(@Validated UpdateReviewCommand updateReviewCommand
                              ,BindingResult result) {
      System.out.println("수정시작");
      if(result.hasErrors()) {
         System.out.println("수정내용을 모두 입력해주세요");
         return "review/reviewUpdate";
      }
      reviewService.updateBoard(updateReviewCommand);
      
      return "redirect:/review/reviewDetail?board_seq="+ updateReviewCommand.getBoard_seq();
      
   }

  
   @RequestMapping(value="mulDel",method = {RequestMethod.GET, RequestMethod.POST})
   public String mulDel(@Validated DelReviewCommand delReviewCommand
                   ,BindingResult result
                      , Model model,String pnum) {
      if(result.hasErrors()) {
         System.out.println("최소하나 체크하기");
         List<ReviewDto> list=reviewService.getAllList(pnum);
         model.addAttribute("list", list);
         return "review/reviewList";
      }
      reviewService.mulDel(delReviewCommand.getSeq());
      System.out.println("글삭제함");
      return "redirect:/review/reviewList";
   }
   
   @ResponseBody
   @GetMapping(value = "/addReplyBoard")
   public String addCalReply(@Validated InsertReplyCommand insertCommand,
                       BindingResult result) throws Exception {
      System.out.println("댓글추가");
      if(result.hasErrors()) {
         System.out.println("글을 모두 입력해야 합니다");
         return "review/reviewDetail";
      }
      System.out.println(insertCommand);
      reviewService.insertReply(insertCommand);
      
      return "";
   }

   @ResponseBody
   @GetMapping(value = "/showReplyBoard")
   public Map<String, List<ReviewDto>> NewsBoardList(@Validated InsertReplyCommand insertCommand, BindingResult result,  Model model, int seq) throws Exception {
      System.out.println("댓글 보여주기");
      List<ReviewDto> list = reviewService.showReply(seq);
      Map<String, List<ReviewDto>> map = new HashMap<>();
      map.put("list", list);
      System.out.println(list);
      model.addAttribute("insertCommand", new InsertReplyCommand());
      
      return map;
   }
}