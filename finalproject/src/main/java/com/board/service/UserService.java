package com.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.command.UserUpdateCommand;
import com.board.dtos.AccountDto;
import com.board.dtos.CalDto;
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
   
   public List<CalDto> userReserve(String email){
      return userMapper.userReserve(email);
   }
   
   public boolean addAccount(String money, String fintech_use_num, String bank_name, int userseqno) {
         Map<String, Object>map=new HashMap<>();
         map.put("money", money);
         map.put("fintech_use_num", fintech_use_num);
         map.put("bank_name", bank_name);
         map.put("userseqno", userseqno);
         int count = userMapper.addAccount(map);
         return count>0?true:false;
      }
   
   
   public int totalMoney(HttpServletRequest requset) {
         UserDto udto = (UserDto)requset.getSession().getAttribute("ldto");
         int userseqno = udto.getUserseqno();
         return userMapper.totalMoney(userseqno);
      }

   
   public List<AccountDto> getMyAccount(int userseqno){
      System.out.println("service까지"+userseqno);
      return userMapper.getMyAccount(userseqno);
   }
   
   public String CheckAccount(String fintech_use_num) {
         return userMapper.CheckAccount(fintech_use_num);
      }
   
   
   
   
}