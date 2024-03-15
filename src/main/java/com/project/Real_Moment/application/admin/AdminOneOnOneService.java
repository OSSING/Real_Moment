package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Comment;
import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.CommentRepository;
import com.project.Real_Moment.domain.repository.OneOnOneRepository;
import com.project.Real_Moment.presentation.dto.CommentDto;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OneOnOneDto;
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
public class AdminOneOnOneService {

    private final OneOnOneRepository oneOnOneRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public OneOnOneDto.OneOnOneWrapper getOneOnOneList(CondDto.AdminOneOnOneListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);

        Page<OneOnOne> oneOnOnePaging = oneOnOneRepository.findOneOnOneListByPaging_admin(pageable, dto);

        List<OneOnOneDto.OneOnOneList> oneOnOneDto = oneOnOnePaging.stream()
                .map(OneOnOneDto.OneOnOneList::new)
                .toList();

        for (OneOnOneDto.OneOnOneList oneOnOneList : oneOnOneDto) {
            Comment comment = commentRepository.findById(oneOnOneList.getOneOnOneId()).orElse(null);

            CommentDto.CommentResponse commentDto = null;

            if (comment != null) {
                commentDto = new CommentDto.CommentResponse(comment);
            }

            oneOnOneList.setComment(commentDto);
        }

        return new OneOnOneDto.OneOnOneWrapper(oneOnOneDto, oneOnOnePaging.getTotalPages(), dto.getNowPage());
    }
}
