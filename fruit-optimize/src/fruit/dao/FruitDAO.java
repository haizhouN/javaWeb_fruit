package fruit.dao;

import fruit.Fruit;

import java.util.List;

public interface FruitDAO {
//    //获取所有的库列表信息
//    List<Fruit> getFruitList();
    //获取指定页面列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);
    //根据fid获取特定的水果库存信息
    Fruit getFruitByFid(Integer fid);
    //修改指定的库存记录
    void UpdateFruit(Fruit fruit);
    //删除指定的信息
    void delFruit(int fid);
    //添加信息
    void addFruit(Fruit fruit);
    //查询总记录的条数
    int getFruitCount(String keyword);
}
