package com.board.command;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

public class InsertReplyCommand {
	 private String email;
	   
	 private int board_seq;
	   
	 private int comment_seq;
	   
	   
	 @NotBlank(message = "내용을 입력하세요") //문자열만 가능
	 private String content;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getBoard_seq() {
		return board_seq;
	}


	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}


	public int getComment_seq() {
		return comment_seq;
	}


	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "InsertReplyCommand [email=" + email + ", board_seq=" + board_seq + ", comment_seq=" + comment_seq
				+ ", content=" + content + "]";
	}


	public InsertReplyCommand(String email, int board_seq, int comment_seq,
			@NotBlank(message = "내용을 입력하세요") String content) {
		super();
		this.email = email;
		this.board_seq = board_seq;
		this.comment_seq = comment_seq;
		this.content = content;
	}


	public InsertReplyCommand() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	
}
