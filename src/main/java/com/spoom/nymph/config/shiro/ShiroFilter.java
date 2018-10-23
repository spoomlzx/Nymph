package com.spoom.nymph.config.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoom.nymph.utils.BaseResult;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.spoom.nymph.config.shiro
 *
 * @author spoomlan
 * @date 2018/10/24
 */
public class ShiroFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new ShiroToken(token);
    }

    /**
     * 为了跨域，如果是OPTIONS请求直接放行
     * 不是则进入{@link #onAccessDenied(ServletRequest, ServletResponse)}进行认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());

    }

    /**
     * {@link AuthenticatingFilter#executeLogin(ServletRequest, ServletResponse)}中调用{@link #createToken(ServletRequest, ServletResponse)}
     * 获取到AuthenticationToken对象，然后对subject进行认证，会调用到{@link ShiroRealm#doGetAuthenticationInfo(AuthenticationToken)}进行认证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));

        if (StringUtils.isBlank(token)) {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(httpResponse.getWriter(), BaseResult.error().put("message", "token is valid"));
            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * {@link ShiroRealm#doGetAuthenticationInfo(AuthenticationToken)}失败时调用，返回exception的报错信息
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        try {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(httpResponse.getWriter(), BaseResult.error().put("error", e.getMessage()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }

        return token;
    }
}
