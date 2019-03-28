package com.douzone.mysite.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentVo {
	private long no;
	@NotEmpty
	private String contents;
	private String writeDate;
	private long boardNo;
	private long userNo;
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	private int gNo;
	private int oNo;
	private int depth;
	
	public int getgNo() {
		return gNo;
	}
	public void setgNo(int gNo) {
		this.gNo = gNo;
	}
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(long boardNo) {
		this.boardNo = boardNo;
	}
	public long getUserNo() {
		return userNo;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", contents=" + contents + ", writeDate=" + writeDate + ", boardNo=" + boardNo
				+ ", userNo=" + userNo + ", name=" + name + ", password=" + password + ", gNo=" + gNo + ", oNo=" + oNo
				+ ", depth=" + depth + "]";
	}

	
}
