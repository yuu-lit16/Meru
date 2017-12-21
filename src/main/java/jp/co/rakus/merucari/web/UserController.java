package jp.co.rakus.merucari.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.merucari.domain.User;
import jp.co.rakus.merucari.repository.UserRepository;

@Controller
@EnableAutoConfiguration
public class UserController {
	
	@Autowired
	private UserRepository repository;

	/** ログインページを表示する */
	@RequestMapping("/displayLoginPage")
	public String displayLogin() {
		return "login";
	}
	
	/** 新規登録ページを表示する */
	@RequestMapping("/displayRegister")
	public String displayRegister() {
		return "register";
	}
	
	/** 新規登録処理を行う */
	@RequestMapping("/excuteRegister")
	public String excuteRegister(String mail_address, String password) {

		User user = new User();
		user.setName("test");
		user.setMailAddress(mail_address);
		user.setPassword(password);
		repository.save(user);
		
		return "login";
	}
	
	/** ログアウト処理を行う */
	@RequestMapping("/logout")
	public String logout() {
		return "/logout";
	}

	
}
