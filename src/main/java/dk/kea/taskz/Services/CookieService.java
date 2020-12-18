package dk.kea.taskz.Services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {

	/**
	 * - OVO
	 * Gets an array of cookies and looks to see if one has the name of "id"
	 *
	 * @param request
	 * @return int
	 */
	public int getActiveUserId(HttpServletRequest request) {
		try {

			Cookie cookie[] = request.getCookies();

			Cookie cookieId = new Cookie("id", "");

			for (Cookie cookie1 : cookie) {
				if (cookie1.getName().equals("id")) {
					cookieId.setValue(cookie1.getValue());
				}
			}

			return Integer.valueOf(cookieId.getValue());

		} catch (Exception e) {
			
			System.out.println("cookiesServivce, Error: " + e.getMessage());
		}

		return -1;
	}
}
