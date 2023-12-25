package com.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.board.dtos.UserDto;

@Mapper
public interface UserMapper {
   public int addUser(UserDto dto);
   public UserDto loginUser(UserDto dto);
   public UserDto userInfo(String email);
   
   
   // 정보 수정
      public boolean updateUser(UserDto dto);
}