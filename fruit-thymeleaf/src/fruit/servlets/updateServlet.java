package fruit.servlets;

import basedao.ViewBaseServlet;
import fruit.Fruit;
import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update.do")
public class updateServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO=new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");


        int fid=Integer.parseInt(request.getParameter("fid"));
        String fname= request.getParameter("fname");
        int price=Integer.parseInt(request.getParameter("price"));
        int fcout=Integer.parseInt(request.getParameter("fcount"));
        String remark=request.getParameter("remark");

        fruitDAO.UpdateFruit(new Fruit(fid,fname,price,fcout,remark));
        //super.processTemplate("index",request,response);
        //需要重定向，目的是重新给IndexServlet发请求，重新获取fruitList,然后覆盖到session中，这样index.html页面显示的数据才是最新的数据
        response.sendRedirect("index");
    }
}
