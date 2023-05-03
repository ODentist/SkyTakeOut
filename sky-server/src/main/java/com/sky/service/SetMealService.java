package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    SetmealVO queryById(Long id);

    void saveSetMeal(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateSetmeal(SetmealDTO setmealDTO);

    void delete(List<Long> ids);

    void updateSetmealStatus(int status, Long id);

        /**
         * 条件查询
         * @param setmeal
         * @return
         */
    List<Setmeal> list(Setmeal setmeal);

        /**
         * 根据id查询菜品选项
         * @param id
         * @return
         */
    List<DishItemVO> getDishItemById(Long id);

}
