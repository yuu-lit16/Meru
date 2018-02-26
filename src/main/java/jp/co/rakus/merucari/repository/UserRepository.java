package jp.co.rakus.merucari.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jp.co.rakus.merucari.domain.User;

@Repository
@Transactional
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedjdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** User型のRowMapper */
	private static final RowMapper<User> RowMapper = (rs, i) -> {
		
		User user = new User();
		
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("authority"));
		user.setMailAddress(rs.getString("mail_address"));
		
		return user;
	};
	
	/** ログイン用
	 *   mailAddressでuserを取得
	 *   
	 *   @param String mailAddress
	 *   @return User user
	 *  */
	public User findByMaillAddress(String mailAddress) {

		String sql = "select id,name,password,authority,mail_address from users where mail_address = :mailAddress";

		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);

		User user = namedjdbcTemplate.queryForObject(sql, param, RowMapper);

		return user;

	}
	
	
	
	/**
	 * 引数のusersを追加(idとauthorityはメソッド内で追加 id = maxId +1, authority = 0)
	 * 
	 * @param User user
	 */
	public void save(User user) {

		String sql = "insert into users (id,name,password,authority,mail_address)"
				+ " values(:id,:name,:password,:authority,:mail_address)";

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", user.getId())
				.addValue("name", user.getName())
				.addValue("password", user.getPassword())
				.addValue("authority", user.getAuthority())
				.addValue("mail_address",user.getMailAddress());				

		namedjdbcTemplate.update(sql, param);

	}
	

	/** 新規登録用 
	 *   引数のuserをinsertする
	 *  
	 *  @return int maxId
	 * 
	 * */
	public Integer getMaxId() {
		SqlParameterSource param = new MapSqlParameterSource();
		Integer maxId = namedjdbcTemplate.queryForObject("SELECT max(id) from users", param, Integer.class);
		return maxId;
	}
	
}
