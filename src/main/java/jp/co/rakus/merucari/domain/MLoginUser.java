package jp.co.rakus.merucari.domain;


//@Entity
public class MLoginUser implements java.io.Serializable {

    private String loginUserId;
    public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;

    public MLoginUser() {
    }
}