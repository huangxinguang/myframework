package com.starlight;

import com.starlight.bean.Data;
import com.starlight.bean.Handler;
import com.starlight.bean.Param;
import com.starlight.bean.View;
import com.starlight.helper.BeanHelper;
import com.starlight.helper.ConfigHelper;
import com.starlight.helper.ControllerHelper;
import com.starlight.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/30 下午4:47.
 * 请求分发
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关的Helper类
        HelperLoader.init();

        //获取ServletContext对象（用于注册Servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        //注册Servlet
        registerServlet(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ServletHelper.init(request, response);
        try {
            //获取请求方法与请求路径
            String requestMethod = request.getMethod().toLowerCase();
            String requestPath = request.getPathInfo();
            //获取Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

                if (handler != null) {
                //获取Controller类机器Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                //创建请求参数对象
                Map<String, Object> paramMap = new HashMap<>();
                Enumeration<String> paramNames = request.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String paramValue = request.getParameter(paramName);
                    paramMap.put(paramName, paramValue);
                }
                String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
                if (StringUtil.isNotEmpty(body)) {
                    String[] params = body.split("&");
                    if (ArrayUtil.isNotEmpty(params)) {
                        for (String param : params) {
                            String[] array = param.split("=");
                            if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                String paramName = array[0];
                                String paramValue = array[1];
                                paramMap.put(paramName, paramValue);
                            }
                        }
                    }
                }
                Param param = new Param(paramMap);


                //调用Action方法
                Object result;
                Method actionMethod = handler.getActionMethod();
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

                //处理Action返回值
                if (result instanceof View) {
                    handleViewResult((View) result, request, response);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, response);
                }
            }
        }catch (Exception e) {
            LOGGER.error("request dispacher failure",e);
            throw new RuntimeException(e);
        }
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                LOGGER.info(ConfigHelper.getAppJspPath()+path);
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
