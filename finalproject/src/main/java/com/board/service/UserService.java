package com.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.command.UserUpdateCommand;
import com.board.dtos.UserDto;
import com.board.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
   
   @Autowired
   private UserMapper userMapper;
   
   public boolean addUser(UserDto dto) {
      int count = userMapper.addUser(dto);
      return count>0?true:false;
   }
   
   public UserDto loginUser(UserDto dto) {
      return userMapper.loginUser(dto);
   }
   
   public UserDto userInfo(HttpServletRequest request) {
         UserDto udto = (UserDto)request.getSession().getAttribute("ldto");
         String email = udto.getEmail();
         System.out.println(email);
         return userMapper.userInfo(email);
      }
   
   // 수정하기
   public boolean updateUser(UserUpdateCommand userUpdateCommand) {
      UserDto dto = new UserDto();
      dto.setEmail(userUpdateCommand.getEmail());
      dto.setName(userUpdateCommand.getName());
      dto.setAddress(userUpdateCommand.getAddress());
      dto.setBirth(userUpdateCommand.getBirth());
      dto.setPhone(userUpdateCommand.getPhone());
      return userMapper.updateUser(dto);
   }
}



















