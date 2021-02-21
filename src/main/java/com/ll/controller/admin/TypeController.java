package com.ll.controller.admin;

import com.ll.entity.Type;
import com.ll.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        //防止前端没有type报错
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }


    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id , Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    //新增
    @PostMapping("/types")
    public String post(Type type , RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        Type t = null;

        if(typeByName == null) {
            t  = typeService.saveType(type);
        }

        if(t == null) {
            if (typeByName != null){
                attributes.addFlashAttribute("message","添加失败：重复的分类");
            }else {
                attributes.addFlashAttribute("message", "操作失败");
            }
        }
        else {
            //保存成功
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@PathVariable Long id,Type type , RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        Type t = null;

        if(typeByName == null) {
            t  = typeService.updateType(id,type);
        }
        if(t == null) {
            if (typeByName != null){
                attributes.addFlashAttribute("message","更新失败：重复的分类");
            }else {
                attributes.addFlashAttribute("message", "更新操作失败");
            }
        }
        else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
