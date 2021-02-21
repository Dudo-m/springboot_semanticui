package com.ll.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ll.NotFoundException;
import com.ll.entity.Blog;
import com.ll.entity.Type;
import com.ll.mapper.BlogRepository;
import com.ll.util.BlogAddDescriptionUtils;
import com.ll.util.MarkdownUtils;
import com.ll.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        //增加查看次数
        BeanUtil.copyProperties(blog,b, CopyOptions.create().setIgnoreNullValue(true));
        b.setViews(b.getViews()+1);
        blogRepository.save(b);
        //转换html并处理
        String content = b.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {

        Page<Blog> blogPage = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (blogQuery.getTitle() != null && !"".equals(blogQuery.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blogQuery.getTitle() + "%"));
                }
                if (blogQuery.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                if (blogQuery.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return BlogAddDescriptionUtils.BlogAddDescription(blogPage);
    }

    /**
     * 查询同时增加description
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return  BlogAddDescriptionUtils.BlogAddDescription(blogRepository.findAll(pageable));
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        Page<Blog> blogPage = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
        return BlogAddDescriptionUtils.BlogAddDescription(blogPage);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return  BlogAddDescriptionUtils.BlogAddDescription(blogRepository.findByQuery(query,pageable));
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if (b == null){
            throw new NotFoundException("该博客不存在");
        }
        //忽略空值注入
        BeanUtil.copyProperties(blog,b, CopyOptions.create().setIgnoreNullValue(true));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
