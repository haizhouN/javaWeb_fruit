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

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO=new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidstr=request.getParameter("fid");
        if (fidstr!=null&&!"".equals(fidstr)){
            int fid=Integer.parseInt(fidstr);
            Fruit fruit= fruitDAO.getFruitByFid(fid);
            request.setAttribute("fruit",fruit);
            super.processTemplate("edit",request,response);

        }
    }
}
