package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    SetmealVO queryById(Long id);

    void saveSetMeal(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateSetmeal(SetmealDTO setmealDTO);

    void delete(List<Long> ids);

    void updateSetmealStatus(int status, Long id);
}
