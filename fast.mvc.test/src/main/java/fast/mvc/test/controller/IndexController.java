package fast.mvc.test.controller;

import fast.mvc.annotation.Controller;
import fast.mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lmm on 2016/6/17.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public void index(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.getWriter().write("welcome home");
    }

    @RequestMapping("/aaa")
    public void aaa(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.getWriter().write("welcome home2");
    }
}