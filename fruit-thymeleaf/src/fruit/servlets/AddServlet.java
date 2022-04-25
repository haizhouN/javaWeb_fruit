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
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO=new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest requst, HttpServletResponse response) throws ServletException, IOException {
        requst.setCharacterEncoding("utf-8");
        String fname=requst.getParameter("fname");
        int price=Integer.parseInt(requst.getParameter("price"));
        int fcount=Integer.parseInt(requst.getParameter("fcount"));
        String remark=requst.getParameter("remark");
        Fruit fruit=new Fruit(0,fname,price,fcount,remark);


        fruitDAO.addFruit(fruit);




        response.sendRedirect("index");
    }
}
