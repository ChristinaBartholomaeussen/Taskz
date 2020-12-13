package dk.kea.taskz.Services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {
	
	public int getActiveUserId(HttpServletRequest request) {
		
		Cookie[] ck = request.getCookies();
		for (Cookie cookie : ck) {
			if (cookie.getName().equals("id")) {
				return Integer.parseInt(cookie.getValue());
			}
		}
		return -1;
	}
}
