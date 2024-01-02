package com.board.command;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public class InsertReviewCommand {
	   private int board_seq;
	   private String email;
	   @NotBlank(message="제목을 입력해주세요")
	   private String title;
	   @NotBlank(message="병원명을 입력해주세요")
	   private String hos_name;
	   private String content;
	   private Date regdate;
	   private int refer;
	   private String readCount;
	   private String delflag;
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHos_name() {
		return hos_name;
	}
	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getRefer() {
		return refer;
	}
	public void setRefer(int refer) {
		this.refer = refer;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	@Override
	public String toString() {
		return "InsertReviewCommnad [board_seq=" + board_seq + ", email=" + email + ", title=" + title + ", hos_name="
				+ hos_name + ", content=" + content + ", regdate=" + regdate + ", refer=" + refer + ", readCount="
				+ readCount + ", delflag=" + delflag + "]";
	}
	public InsertReviewCommand(int board_seq, String email, @NotBlank(message = "제목을 입력해주세요") String title,
			@NotBlank(message = "병원명을 입력해주세요") String hos_name, String content, Date regdate, int refer,
			String readCount, String delflag) {
		super();
		this.board_seq = board_seq;
		this.email = email;
		this.title = title;
		this.hos_name = hos_name;
		this.content = content;
		this.regdate = regdate;
		this.refer = refer;
		this.readCount = readCount;
		this.delflag = delflag;
	}
	public InsertReviewCommand() {
		super();
		// TODO Auto-generated constructor stub
	}
	   
	   
}