package com.board.command;

import java.util.Arrays;

import jakarta.validation.constraints.NotEmpty;

public class DelReviewCommand {

   @NotEmpty(message = "삭제하려면 최소 하나 이상 체크하세요")
      private String[] seq;
      
      public DelReviewCommand() {
         super();
         // TODO Auto-generated constructor stub
      }

      public DelReviewCommand(@NotEmpty(message = "삭제하려면 최소 하나 이상 체크하세요") String[] seq) {
         super();
         this.seq = seq;
      }


      public String[] getSeq() {
		return seq;
	}

	public void setSeq(String[] seq) {
		this.seq = seq;
	}

	@Override
      public String toString() {
         return "DelBoardCommand [board_seq=" + Arrays.toString(seq) + "]";
      }
}