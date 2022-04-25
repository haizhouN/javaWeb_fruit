package fruit.biz.impl;

import fruit.Fruit;
import fruit.biz.FruitService;
import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;

import java.util.List;

public class FruitServiceImpl implements FruitService {
private FruitDAO fruitDAO=null;

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDAO.getFruitList(keyword,pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitByFid(fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        int count= fruitDAO.getFruitCount(keyword);

        return (count+5-1)/5;
    }

    @Override
    public void UpdateFruit(Fruit fruit) {
        fruitDAO.UpdateFruit(fruit);
    }

}
