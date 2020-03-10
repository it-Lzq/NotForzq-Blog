package cn.itlzq.controller;

import cn.itlzq.bean.Type;
import cn.itlzq.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:58
 * @email 邮箱:905866484@qq.com
 * @description 描述：类别
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService service;

    /**
     * 跳转到类别管理
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",service.listType(pageable));
        return "admin/types";
    }

    /**
     * 跳转到增加类别
     */
    @GetMapping("/types/input")
    public String insert(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    @GetMapping("/type/{id}/input")
    public String updateType(@PathVariable Long id, Model model){
        model.addAttribute("type",service.getType(id));
        return "admin/type-input";
    }

    /**
     * 删除类别
     * @param id 类别id
     * @return 页面
     */
    @GetMapping("/type/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        service.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

    /**
     * 增加类别
     * @param type 类别参数
     * @param result 错误结果
     * @param attributes 重定向参数
     * @return 页面
     */
    @PostMapping("/types") @SuppressWarnings("all")
    public String insert(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if(service.getTypeByName(type.getName()) != null){
            result.rejectValue("name","nameError","该分类名称已经存在");
            return "admin/type-input";
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }

        Type t = service.insertType(type);
        if(t != null){
            attributes.addFlashAttribute("message","添加成功");
        }else{
            attributes.addFlashAttribute("message","添加分类失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改类别
     * @param type 类别参数
     * @param result 错误结果
     * @param attributes 重定向参数
     * @return 页面
     */
    @PostMapping("/types/{id}") @SuppressWarnings("all")
    public String update(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        if(service.getTypeByName(type.getName()) != null){
            result.rejectValue("name","nameError","该分类名称已经存在");
            return "admin/type-input";
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }

        Type t = service.updateType(id,type);
        if(t != null){
            attributes.addFlashAttribute("message","更新成功");
        }else{
            attributes.addFlashAttribute("message","更新分类失败");
        }
        return "redirect:/admin/types";
    }


}
