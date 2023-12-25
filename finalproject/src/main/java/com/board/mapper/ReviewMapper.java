package com.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.board.dtos.ReviewDto;


@Mapper
public interface ReviewMapper {

   
	  // 글 목록
      public List<ReviewDto> getAllList(Map<String,String>map);
      
      // 글 상세 조회
      public ReviewDto getBoard(int board_seq);
      
      // 글 추가
      public boolean insertReview(ReviewDto dto);
      
      // 글 수정
      public boolean updateBoard(ReviewDto dto);
      
      // 글 삭제
      public boolean mulDel(String[] seqs);
      
      // 조회수
      public boolean readCount(int seq);
      
      //페이징
      public int getPCount();
}