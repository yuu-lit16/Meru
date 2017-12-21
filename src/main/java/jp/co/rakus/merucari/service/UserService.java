package jp.co.rakus.merucari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.rakus.merucari.domain.User;
import jp.co.rakus.merucari.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	/** ログイン用
	 *   mailAddressでuserを取得
	 *   
	 *   @param String mailAddress
	 *   @return User user
	 *  */
	public User findByMaillAddress(String mailAddress) {
		return repository.findByMaillAddress(mailAddress);
	}
	
	
	
	/**
	 * 引数のusersを追加(idとauthorityとnameはメソッドで追加 id = maxId +1, authority = 0,name = root)
	 * また、passwordを暗号化する
	 * 
	 * @param User user
	 */
	public void save(User user) {

		// password エンコード処理
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword =  encoder.encode(user.getPassword());
		
		user.setName("root");
		user.setId(getMaxId() + 1);
		user.setAuthority(0);
		user.setPassword(encodedPassword);
		
		repository.save(user);
	}
	

	/** 新規登録用 
	 *   引数のuserをinsertする
	 *  
	 *  @return int maxId
	 * 
	 * */
	public Integer getMaxId() {
		return repository.getMaxId();
	}

}
