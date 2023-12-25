package com.board.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import com.board.command.InsertReviewCommand;
import com.board.dtos.ReviewDto;

import com.board.mapper.ReviewMapper;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class ReviewService {

   @Autowired
   private ReviewMapper reviewMapper;

//   @Autowired
//   private NewsBoardReplyMapper newsBoardReplyMapper;
   
   public List<ReviewDto> getAllList(String pnum){
	      Map<String,String>map=new HashMap<>();
	      map.put("pnum", pnum);
	      return reviewMapper.getAllList(map);
   }
   
   //글 추가, 파일 업로드 및 파일 정보 추가
      public void insertReview(InsertReviewCommand insertReviewCommand, MultipartRequest multipartRequest, HttpServletRequest request) throws IllegalStateException, IOException {
         
         // command -> dto 데이터 옮겨담기
    	  ReviewDto reviewDto = new ReviewDto();
    	  reviewDto.setEmail(insertReviewCommand.getEmail());
    	  reviewDto.setHos_name(insertReviewCommand.getHos_name());
    	  reviewDto.setTitle(insertReviewCommand.getTitle());
    	  reviewDto.setContent(insertReviewCommand.getContent());
    	  reviewDto.setDelflag(insertReviewCommand.getDelflag());
         
         //글을 추가할 때 파라미터로 전달된 boardDto 객체에 자동으로 증가된 board_seq값이 저장
         reviewMapper.insertReview(reviewDto); // 새글 추가

         
      }
//      
      public ReviewDto getBoard(int board_seq) {
         return reviewMapper.getBoard(board_seq);
      }
//      
//      // 수정하기
//      public boolean updateBoard(NewsUpdateBoardCommand updateBoardCommand) {
//               //command:UI --> DTO:DB
//               NewsBoardDto dto = new NewsBoardDto();
//               dto.setBoard_seq(updateBoardCommand.getBoard_seq());
//               dto.setTitle(updateBoardCommand.getTitle());
//               dto.setContent(updateBoardCommand.getContent());
//               return newsBoardMapper.updateBoard(dto);
//      }
//      
//      public boolean mulDel(String[] seqs) {
//         return newsBoardMapper.mulDel(seqs);
//      }
//      
      public boolean readCount(int seq) {
         
         return reviewMapper.readCount(seq);
      }
//      public boolean insertReply(InsertReplyCommand insertCommand) throws Exception {
//          
//          NewsBoardDto dto=new NewsBoardDto();
//          dto.setBoard_seq(insertCommand.getBoard_seq());
//          dto.setId(insertCommand.getId());
//          dto.setContent(insertCommand.getContent());
//          
//          int count=newsBoardReplyMapper.insertReplyBoard(dto);
//         
//          return count>0?true:false;
//       }
//   
//    public List<NewsBoardDto> showReply(int seq) throws Exception{    
//        return newsBoardReplyMapper.showReplyBoard(seq);
//    }
//
   public int getPCount() {
      return reviewMapper.getPCount();
   }
}












