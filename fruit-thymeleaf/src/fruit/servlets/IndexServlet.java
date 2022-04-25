package fruit.servlets;

import basedao.ViewBaseServlet;
import fruit.Fruit;
import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        int pageNo=1;
        HttpSession session=request.getSession();

        String oper=request.getParameter("oper");
        String keyword=null;
        if(oper!=null&&!"".equals(oper)&&oper.equals("search")){
            //说明是点击表单发送过来的请求
            //pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo=1;
            keyword=request.getParameter("keyword");
            if(keyword==null||keyword.equals("")){
                keyword="";
            }
            session.setAttribute("keyword",keyword);

        }else{
            //说明不是点击表单发送过来的请求（上一页下一页）
            //此时应该从session作用域获取
            String pageNoStr=request.getParameter("pageNo");
            if(pageNoStr!=null&&!"".equals(pageNoStr)){
                pageNo=Integer.parseInt(pageNoStr);
            }
            Object keywordobj=session.getAttribute("keyword");
            if(keywordobj!=null){
                keyword=(String) keywordobj;
            }else{
                keyword="";
            }


        }





        FruitDAO fruitDAO=new FruitDAOImpl();
        List<Fruit> fruitList=fruitDAO.getFruitList(keyword,pageNo);
        //保存到session作用域

        session.setAttribute("pageNo",pageNo);
        session.setAttribute("fruitList",fruitList);
        int fruitCount=fruitDAO.getFruitCount(keyword);
        int pageCount=(fruitCount+5-1)/5;
        session.setAttribute("pageCount",pageCount);




        super.processTemplate("index",request,response);
    }
}
