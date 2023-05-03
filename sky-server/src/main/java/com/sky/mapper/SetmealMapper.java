package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    SetmealVO queryById(Long id);

    Setmeal querySetMealById(Long id);

    void saveSetmealDish(SetmealDish setmealDish);
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    @AutoFill(OperationType.UPDATE)
    void updateSetmealDishes(SetmealDish setmealDish);

    void deleteSetmealDishesById(Long id);
    @AutoFill(OperationType.INSERT)
    void insertSetmealDishes(SetmealDish setmealDish);

    void delete(List<Long> ids);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    @Select("select count(status) from setmeal where status=1")
    Integer queryStatus_ON();

    @Select("select count(status) from setmeal where status=0")
    Integer queryStatus_OFF();
}
