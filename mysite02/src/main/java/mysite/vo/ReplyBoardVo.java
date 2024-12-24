package mysite.vo;

public class ReplyBoardVo {

	private int gNo;
	private int oNo;
	private int depth;
	private String title;
	private String contents;
	private Long userId;
	
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
