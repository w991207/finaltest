package com.board.command;

import jakarta.validation.constraints.NotBlank;

public class UpdateReviewCommand {
	private int board_seq;
	@NotBlank(message = "병원명을 입력해주세요")   
	private String hos_name;
	
	   @NotBlank(message = "제목을 입력해주세요")
	   private String title;
	   @NotBlank(message = "내용을 입력해주세요")
	   private String content;
	public UpdateReviewCommand() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateReviewCommand(int board_seq, @NotBlank(message = "병원명을 입력해주세요") String hos_name,
			@NotBlank(message = "제목을 입력해주세요") String title, @NotBlank(message = "내용을 입력해주세요") String content) {
		super();
		this.board_seq = board_seq;
		this.hos_name = hos_name;
		this.title = title;
		this.content = content;
	}
	@Override
	public String toString() {
		return "UpdateReviewCommand [board_seq=" + board_seq + ", hos_name=" + hos_name + ", title=" + title
				+ ", content=" + content + "]";
	}
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public String getHos_name() {
		return hos_name;
	}
	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	   

}