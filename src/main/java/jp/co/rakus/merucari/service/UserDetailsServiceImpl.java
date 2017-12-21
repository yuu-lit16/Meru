package jp.co.rakus.merucari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.rakus.merucari.domain.LoginUser;
import jp.co.rakus.merucari.domain.MLoginUser;
import jp.co.rakus.merucari.domain.User;
import jp.co.rakus.merucari.repository.UserRepository;

/**
 * UserDetailsServiceの実装クラス
 * Spring Securityでのユーザー認証に使用する
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	
	
    /* (非 Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String mail_address)
            throws UsernameNotFoundException {

        // 認証を行うユーザー情報を格納する
        //MLoginUser user = null;
    	MLoginUser user = new MLoginUser();
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
            // 処理内容は省略
        	
        	// ここでuserをmailaddress でとってきて、user　に詰めて返してあげればよい
        	User selectedUser = repository.findByMaillAddress(mail_address);
        	user.setLoginUserId(selectedUser.getMailAddress());
        	user.setPassword(selectedUser.getPassword());
        
        } catch (Exception e) {
            // 取得時にExceptionが発生した場合
        	e.printStackTrace();
            throw new UsernameNotFoundException("It can not be acquired User");
        }

        // ユーザー情報を取得できなかった場合
        if(user == null){
            throw new UsernameNotFoundException("User not found for login id: " + mail_address);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return new LoginUser(user);
    }

}
