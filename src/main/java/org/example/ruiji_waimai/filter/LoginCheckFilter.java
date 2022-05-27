//package org.example.ruiji_waimai.filter;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.example.ruiji_waimai.common.Result;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//// filterName : filter的名称, urlPaterns : 表示拦截那些内容
//@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
//public class LoginCheckFilter implements Filter {
//
//    // 路径适配器
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        // 获取请求的URL
//        String requestURL = request.getRequestURI();
//
//        // 定义不需要被处理的URL
//        String[] urls = new String[]{
//                "/employee/login", // 登录接口
//                "/employee/logout", // 登出接口
//                "/backend/**", // 静态资源
//                "/front/**" // 静态资源
//        };
//        //判断请求的url是否需要进行拦截
//        boolean check = check(urls,requestURL);
//
//        if (check){ //本次请求不需要处理
//            log.info("本次请求不需要进行处理");
//            filterChain.doFilter(request,response);
//            return;
//        }
//        // 判断用户是否登录
//        if (null != request.getSession().getAttribute("employee")) {
//            // 用户已经登录放行
//            log.info("用户已经登录，用户id：" + request.getSession().getAttribute("employee"));
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        log.info("用户未登录");
//        //如果用户未登录，通过输出流的方式想客户端响应数据
//        response.getWriter().write(JSON.toJSONString(Result.error("NOLOGIN")));
//        return;
//    }
//
//    // true 表示不需要进行拦截
//    public Boolean check(String[] urls, String requestURL) {
//        for (String url : urls) {
//            boolean bool = PATH_MATCHER.match(url, requestURL);
//            if (bool) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
