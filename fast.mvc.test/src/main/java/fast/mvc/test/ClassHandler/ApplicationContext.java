package fast.mvc.test.ClassHandler;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by lmm on 2015/11/9.
 * 应用程序容器
 */
@WebListener
public class ApplicationContext implements ServletContextListener {

    /**
     * context 初始化时执行
     * Author:刘明明
     * CreateTime:2015年11月9日20:39:58
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        fast.mvc.context.ApplicationContext.init("fast.mvc.test.controller");
    }

    /**
     * 初始化配置文件
     * Author:刘明明
     * CreateTime:2015年11月10日10:36:50
     */
    private void InitConfig() {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}