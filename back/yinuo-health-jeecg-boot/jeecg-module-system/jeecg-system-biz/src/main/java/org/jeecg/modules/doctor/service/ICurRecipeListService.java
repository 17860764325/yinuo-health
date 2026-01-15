package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CurRecipeList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: cur_recipe_list
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
public interface ICurRecipeListService extends IService<CurRecipeList> {

    Result boilMedicineBack(String recipeNo);
}
