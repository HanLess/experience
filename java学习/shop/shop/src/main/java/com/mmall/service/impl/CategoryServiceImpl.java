package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import net.sf.jsqlparser.schema.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ServerResponse addCategory(int parentId, String name, int status) {
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(name);
        category.setStatus(status);

        int result = categoryMapper.insert(category);
        if(result  < 1){
            return ServerResponse.createByErrorMessage("新增种类失败");
        }

        return ServerResponse.createBySuccess(category,"添加成功");
    }

    @Override
    public ServerResponse rename(Integer id, String name) {
        if(id == null){
            return ServerResponse.createByErrorMessage("品类id为空");
        }

        Category category = new Category();
        category.setName(name);
        category.setId(id);

        int result = categoryMapper.updateByPrimaryKeySelective(category);
        if(result < 1){
            return ServerResponse.createByErrorMessage("修改种类名失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categories = categoryMapper.getChildrenParallelCategory(categoryId);
        if(CollectionUtils.isEmpty(categories)){
            logger.info("没有找到子品类");
        }
        return ServerResponse.createBySuccess(categories);
    }

    @Override
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategory(Integer categoryId) {
        Set<Category> categorySet = new HashSet();
        this.findChildCategory(categorySet,categoryId);
        List<Integer> categories = new ArrayList<>();
        if(categoryId == null){
            return ServerResponse.createByErrorMessage("品类id为空");
        }
        for(Category categoryItem : categorySet){
            categories.add(categoryItem.getId());
        }

        return ServerResponse.createBySuccess(categories);
    }

    private Set<Category> findChildCategory(Set<Category> categories,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categories.add(category);
        }

//        查找自节点，如果为空，退出遍历
        List<Category> childrenCategories = categoryMapper.getChildrenParallelCategory(categoryId);
        for(Category categoryItem : childrenCategories){
            findChildCategory(categories,categoryItem.getId());
        }
        return categories;
    }
}
