package dk.kea.taskz.Services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {
	
	public int getActiveUserId(HttpServletRequest request) {

		Cookie cookie[] = request.getCookies();

		Cookie cookieId = new Cookie("id", "");

		for (Cookie cookie1 : cookie) {
			if (cookie1.getName().equals("id")) {
				cookieId.setValue(cookie1.getValue());
			}
		}

		return Integer.parseInt(cookieId.getValue());
	}
}
