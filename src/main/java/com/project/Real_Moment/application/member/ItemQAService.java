package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.ItemQA;
import com.project.Real_Moment.domain.entity.QAComment;
import com.project.Real_Moment.domain.repository.ItemQARepository;
import com.project.Real_Moment.domain.repository.QACommentRepository;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemQADto;
import com.project.Real_Moment.presentation.dto.QACommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemQAService {

    private final ItemQARepository itemQARepository;
    private final QACommentRepository qaCommentRepository;

    @Transactional
    public ItemQADto.ItemQAListPage getItemQAList(CondDto.QAListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 5);

        Page<ItemQA> itemQAList = itemQARepository.findQAListByCond(pageable, dto);

        List<ItemQADto.ItemQAList> itemQAListDto = itemQAList.stream()
                .map(ItemQADto.ItemQAList::new)
                .toList();

        for (ItemQADto.ItemQAList itemQA : itemQAListDto) {
            QAComment qaComment = qaCommentRepository.findById(itemQA.getItemQAId()).orElse(null);

            QACommentDto.QAComment qaCommentDto = null;

            if (qaComment != null) {
                qaCommentDto = new QACommentDto.QAComment(qaComment);
            }

            itemQA.setQaComment(qaCommentDto);
        }

        ItemQADto.ItemQAListPage itemQAPage = new ItemQADto.ItemQAListPage(itemQAListDto, itemQAList.getTotalPages(), dto.getNowPage());

        return itemQAPage;
    }
}
