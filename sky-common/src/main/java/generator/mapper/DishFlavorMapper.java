package generator.mapper;


import com.sky.entity.DishFlavor;

/**
* @author O.Dentist
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2023-04-20 11:12:38
* @Entity generator.domain.DishFlavor
*/
public interface DishFlavorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DishFlavor record);

    int insertSelective(DishFlavor record);

    DishFlavor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DishFlavor record);

    int updateByPrimaryKey(DishFlavor record);

}
