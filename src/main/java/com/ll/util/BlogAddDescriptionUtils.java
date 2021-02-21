package com.ll.util;

import cn.hutool.core.text.StrSpliter;
import com.ll.entity.Blog;
import org.springframework.data.domain.Page;
/**
 * 封装的方法
 * blog增加description简略
 */
public class BlogAddDescriptionUtils {
    public static Page<Blog> BlogAddDescription(Page<Blog> blogPage){
        String content;
        int size = blogPage.getContent().size();
        for (int i = 0; i < size; i++) {
            content = blogPage.getContent().get(i).getContent();
            //markdown转text
            String contentText = MarkdownUtils.markdownTOText(content);
            blogPage.getContent().get(i).setDescription(StrSpliter.splitByLength(contentText,200)[0]);
        }
        return blogPage;
    }
}
