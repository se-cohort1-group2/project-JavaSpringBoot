package sg.edu.ntu.m3project.m3project.interceptor;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import sg.edu.ntu.m3project.m3project.service.UserService;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, user-id, X-Requested-With, Content-Type, Accept, token");
        // response.setHeader("Access-Control-Allow-Headers", "Content-Type, user-id,
        // X-Requested-With");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        String token = request.getHeader("token");
        String userId_str = request.getHeader("user-id");
        if (token == null || userId_str == null) {
            throw new AccessDeniedException("There is no user token and/or userid.");
        }

        try {
            int userId = Integer.parseInt(userId_str);
            System.out.println("[preHandle][" + request + "]" + "[" + request.getMethod()
                    + "]" + request.getRequestURI() + " token: " + token + " userId: " + userId);

            userService.checkToken(token, userId);

        } catch (NumberFormatException e) {
            throw new NumberFormatException("user-id is not numeric.");
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

}
