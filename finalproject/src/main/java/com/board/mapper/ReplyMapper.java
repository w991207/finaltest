package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.dtos.ReviewDto;

@Mapper
public interface ReplyMapper {
	   public int insertReplyBoard(ReviewDto dto) throws Exception;
	   
	   public List<ReviewDto> showReplyBoard(int seq) throws Exception;
}
