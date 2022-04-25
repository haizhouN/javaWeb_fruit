package io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathxmlApplicationContext implements BeanFactory{
    private Map<String,Object> beanMap=new HashMap<>();
    public  ClassPathxmlApplicationContext(){
        try {
            InputStream inputStream=getClass().getClassLoader().getResourceAsStream("applicationsContext.xml");
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            //3创建doucument对象
            Document document=documentBuilder.parse(inputStream);
            //4获取所有的bean节点
            NodeList nodeList= document.getElementsByTagName("bean");
            for (int i=0;i<nodeList.getLength();i++){
                Node beanNode=nodeList.item(i);
                if (beanNode.getNodeType()==Node.ELEMENT_NODE){
                    Element beanElement=(Element) beanNode;
                    String beanId= beanElement.getAttribute("id");
                    String className=beanElement.getAttribute("class");
                    Class beanClass=Class.forName(className);
                    //创建bean实例
                    Object beanObj=beanClass.newInstance();
                    //将bean实例保存到map容器中
                    beanMap.put(beanId,beanObj);
                    //目前位置bean与bean之间的依赖关系并没有设置

                }
            }
            //5.组装bean之间的依赖关系
            for (int i=0;i<nodeList.getLength();i++){
                Node beanNode=nodeList.item(i);
                if (beanNode.getNodeType()==Node.ELEMENT_NODE){
                    Element beanElement=(Element) beanNode;
                    String beanId= beanElement.getAttribute("id");
                    NodeList beanChildNodeList=beanElement.getChildNodes();
                    for (int j = 0; j <beanChildNodeList.getLength() ; j++) {
                        Node childNode=beanChildNodeList.item(j);
                        if(childNode.getNodeType()==Node.ELEMENT_NODE&&"property".equals(childNode.getNodeName())){
                            Element propertyElement=(Element) childNode;
                            String propertyName= propertyElement.getAttribute("name");
                            String propertyRef=propertyElement.getAttribute("ref");
                            //1找到propertyRef对应的实例
                            Object refObj=beanMap.get(propertyRef);
                            //2将refObj设置到当前bean对应的实例的property属性上
                            Object beanObj=beanMap.get(beanId);
                            Class beanClass=beanObj.getClass();
                            Field propertyField=beanClass.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);

                        }
                    }





                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
