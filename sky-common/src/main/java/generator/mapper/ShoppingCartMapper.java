package generator.mapper;

import com.sky.entity.ShoppingCart;

/**
* @author O.Dentist
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2023-04-27 17:06:52
* @Entity generator.domain.ShoppingCart
*/
public interface ShoppingCartMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);

}
