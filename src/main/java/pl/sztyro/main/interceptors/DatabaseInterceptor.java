package pl.sztyro.main.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.sztyro.main.enums.Permission;
import pl.sztyro.main.exceptions.NoModuleException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.ModuleService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class DatabaseInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ModuleService moduleService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NoModuleException {

        User user = null;
        try {
            String mail = authService.getLoggedUserMail(request);
            user = userService.getUser(mail);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Company company = user.getSelectedCompany();

        if (!moduleService.hasAccess(Permission.DatabaseModule, company))
            throw new NoModuleException("toasts.modules.forbidden");

        return true;
    }


}