package jp.co.rakus.merucari.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UseToApiController {

	/** APIで通信する */
	@RequestMapping("/connection_by_api")
	public String displayApiConnectionPage() {
		return "list_by_api";
	}

	// APIによるアクセスはこれ
	// http://172.16.100.30:8080/merucariAPI/merucariAPI/detail?id=3

}
