package com.board.dtos;

public class AccountDto {
   private int money;
   private String fintech_use_num;
   
   public AccountDto() {
      super();
      // TODO Auto-generated constructor stub
   }
   public AccountDto(int money, String fintech_use_num) {
      super();
      this.money = money;
      this.fintech_use_num = fintech_use_num;
   }
   @Override
   public String toString() {
      return "AccountDto [money=" + money + ", fintech_use_num=" + fintech_use_num + "]";
   }
   public int getMoney() {
      return money;
   }
   public void setMoney(int money) {
      this.money = money;
   }
   public String getFintech_use_num() {
      return fintech_use_num;
   }
   public void setFintech_use_num(String fintech_use_num) {
      this.fintech_use_num = fintech_use_num;
   }
}