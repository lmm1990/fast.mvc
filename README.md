# fast.mvc

速度快、轻量级的java mvc框架

##      	目录

*	[特性](#特性)
*	[快速上手](#快速上手)

##	特性

1.	速度快：首次启动时，扫描controller类文件，请求时直接调用。
2.	轻量级：jar包大小仅93.2KB
2.	上手快：只要用过spring mvc简单配置即可上手

## 快速上手

1.	web项目需实现：ServletContextListener contextInitialized方法中调用：fast.mvc.context.ApplicationContext.init("fast.mvc.test.controller");
    fast.mvc.test.controller为需要扫描的controller包名

2.	controller编写
	
	@Controller
	@RequestMapping("/")
	public class IndexController {
	
	    @RequestMapping("/")
	    public void index(HttpServletRequest request,HttpServletResponse response) throws IOException {
	        response.getWriter().write("welcome home");
	    }
	 }
