package generator.mapper;


import com.sky.entity.Dish;

/**
* @author O.Dentist
* @description 针对表【dish(菜品)】的数据库操作Mapper
* @createDate 2023-04-19 14:14:41
* @Entity generator.domain.Dish
*/
public interface DishMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Dish record);

    int insertSelective(Dish record);

    Dish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKey(Dish record);

}
