package com.ll.service;

import com.ll.NotFoundException;
import com.ll.entity.Tag;
import com.ll.mapper.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository TagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag Tag) {
        return TagRepository.save(Tag);
    }

    @Override
    public Tag getTag(Long id) {
        return TagRepository.findById(id).get();
    }

    @Override
    public Tag getTagByName(String name) {
        return TagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return TagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return TagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return TagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {//1,2,3
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(Long.valueOf(split[i]));
            }
        }
        return TagRepository.findAllById(list);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag Tag) {
        Tag t = TagRepository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(Tag,t);
        return TagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        TagRepository.deleteById(id);
    }
}
