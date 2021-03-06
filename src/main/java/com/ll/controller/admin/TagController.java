package com.ll.controller.admin;

import com.ll.entity.Tag;
import com.ll.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        //防止前端没有Tag报错
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }


    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id , Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //新增
    @PostMapping("/tags")
    public String post(Tag Tag , RedirectAttributes attributes){
        Tag TagByName = tagService.getTagByName(Tag.getName());
        Tag t = null;

        if(TagByName == null) {
            t  = tagService.saveTag(Tag);
        }

        if(t == null) {
            if (TagByName != null){
                attributes.addFlashAttribute("message","添加失败：重复的分类");
            }else {
                attributes.addFlashAttribute("message", "操作失败");
            }
        }
        else {
            //保存成功
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@PathVariable Long id,Tag Tag , RedirectAttributes attributes){
        Tag TagByName = tagService.getTagByName(Tag.getName());
        Tag t = null;

        if(TagByName == null) {
            t  = tagService.updateTag(id,Tag);
        }
        if(t == null) {
            if (TagByName != null){
                attributes.addFlashAttribute("message","更新失败：重复的分类");
            }else {
                attributes.addFlashAttribute("message", "更新操作失败");
            }
        }
        else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

}
