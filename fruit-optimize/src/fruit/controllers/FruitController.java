package fruit.controllers;

import fruit.Fruit;
import fruit.biz.FruitService;
import fruit.biz.impl.FruitServiceImpl;
import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import java.util.List;


public class FruitController{
    private FruitService fruitService=null;

    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark){

        fruitService.UpdateFruit(new Fruit(fid,fname,price,fcount,remark));
        //super.processTemplate("index",request,response);
        //需要重定向，目的是重新给IndexServlet发请求，重新获取fruitList,然后覆盖到session中，这样index.html页面显示的数据才是最新的数据
        //response.sendRedirect("fruit.do");

        return "redirect:fruit.do";


    }
        private String edit( HttpServletRequest request,Integer fid)  {
        if (fid!=null){
            Fruit fruit= fruitService.getFruitByFid(fid);
            request.setAttribute("fruit",fruit);



            //super.processTemplate("edit",request,response);
            return"edit";



        }
        return "error";
    }
    private String del(Integer fid) {
        if(fid!=null){

            fruitService.delFruit(fid);

            //不能服务器内部转发，需要重定位

            //response.sendRedirect("fruit.do");
            return "redirect:fruit.do";



        }
        return "error";
    }
    private String add(String fname,Integer price,Integer fcount,String remark) {

        Fruit fruit=new Fruit(0,fname,price,fcount,remark);
        fruitService.addFruit(fruit);

        //response.sendRedirect("fruit.do");
        return "redirect:fruit.do";


    }
    private String index(String oper,String keyword,Integer pageNo,HttpServletRequest request) {

        HttpSession session=request.getSession();
        if(pageNo==null){
            pageNo=1;
        }
        if(oper!=null&&!"".equals(oper)&&oper.equals("search")){
            //说明是点击表单发送过来的请求
            //pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo=1;
            if(keyword==null||keyword.equals("")){
                keyword="";
            }
            session.setAttribute("keyword",keyword);

        }else{
            //说明不是点击表单发送过来的请求（上一页下一页）
            //此时应该从session作用域获取

            Object keywordobj=session.getAttribute("keyword");
            if(keywordobj!=null){
                keyword=(String) keywordobj;
            }else{
                keyword="";
            }


        }
        List<Fruit> fruitList=fruitService.getFruitList(keyword,pageNo);
        //保存到session作用域

        session.setAttribute("pageNo",pageNo);
        session.setAttribute("fruitList",fruitList);
        int pageCount=fruitService.getPageCount(keyword);
        session.setAttribute("pageCount",pageCount);
        //super.processTemplate("index",request,response);
        return "index";

    }
}
