package fruit.servlets;

import basedao.ViewBaseServlet;
import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/del.do")
public class DelServlt extends ViewBaseServlet {
    private FruitDAO fruitDAO=new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr=request.getParameter("fid");
        if(fidStr!=null&&!"".equals(fidStr)){
            int fid=Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            //不能服务器内部转发，需要重定位
            response.sendRedirect("index");
        }
    }
}
