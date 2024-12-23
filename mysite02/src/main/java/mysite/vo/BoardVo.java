package mysite.vo;

public class BoardVo {
	
	private Long id;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int gNo;
	private int oNo;
	private int depth;
	private String userName;
	private Long userId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public int getHit() {
		return hit;
	}
	
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
