package sg.edu.ntu.m3project.m3project.interceptor;

import java.nio.file.AccessDeniedException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        }

        // try {
        // String token = request.getHeader("token");
        // System.out.println("[preHandle][" + request + "]" + "[" + request.getMethod()
        // + "]" + request.getRequestURI() + " token: " + token);
        // } catch (Exception e) {
        // // TODO: handle exception
        // throw new AccessDeniedException("There is no user token.");
        // }

        return true;
        // HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub
        // HandlerInterceptor.super.postHandle(request, response, handler,
        // modelAndView);
        System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        // HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        System.out.println("Request and Response is completed");
    }

}
