package com.ulplanet.trip.filter;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.LocalHelper;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.common.utils.TokenUtils;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.util.LocalContext;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 请求过滤器.
 *
 * @author zhangxd
 *         Date 2015年7月13日
 */
public class RequestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    private Pattern[] eps;

    private long requestCount = 0;

    private synchronized String nextThreadId() {
        return "Request-" + ++requestCount;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //无需过滤的URI
        String excludingPattern = config.getInitParameter("excludingPattern");

        if (!StringHelper.isEmpty(excludingPattern)) {
            String[] es = excludingPattern.split("[,;]");
            List<Pattern> ls = new ArrayList<>();
            for (String e : es) {
                ls.add(Pattern.compile(e.trim()));
            }
            eps = new Pattern[ls.size()];
            ls.toArray(eps);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        if (noCheckUrl(uri)) {
            chain.doFilter(req, resp);
            return;
        }

        Thread.currentThread().setName(nextThreadId());


        String token = request.getHeader(Constants.HEADER_TOKEN);
        String imei = request.getHeader(Constants.HEADER_IMEI);

        if (StringHelper.isEmpty(imei) || StringHelper.isEmpty(token)) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
            errorMap.put(Constants.RETURN_FIELD_MESSAGE, "未获取IMEI或Token");
            String errorMsg = new Gson().toJson(errorMap);

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(errorMsg);
            return;
        }

        boolean invalid = false;
        if (TokenUtils.validToken(token, imei)) {
            User user = new Gson().fromJson(JedisUtils.get(token), User.class);
            if (user != null) {
                if (token.equals(JedisUtils.get(user.getId()))) {
                    LocalContext.setUser(user);
                } else {
                    invalid = true;
                }
            } else {
                invalid = true;
            }
        } else {
            invalid = true;
        }

        if (invalid) {
            Map<String, Object> errorMap = new HashMap<>();
            logger.error("Return Invalid Status. Token:" + token + ",IMEI:" + imei + ",Uri:" + uri);
            errorMap.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_INVALID);
            String errorMsg = new Gson().toJson(errorMap);

            response.getWriter().write(errorMsg);
            return;
        }

        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LocalHelper.clearContexts();
        }
    }

    private boolean noCheckUrl(String url) {
        if (eps == null || eps.length == 0) {
            return false;
        }

        for (Pattern p : eps) {
            if (p.matcher(url).find()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void destroy() {
    }
}