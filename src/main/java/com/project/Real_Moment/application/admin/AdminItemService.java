package com.project.Real_Moment.application.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.Real_Moment.domain.entity.*;
import com.project.Real_Moment.domain.repository.*;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.ItemFileDto;
import com.project.Real_Moment.presentation.dto.S3FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final S3FileRepository s3FileRepository;
    private final CategoryRepository categoryRepository;
    private final ItemFileRepository itemFileRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional(readOnly = true)
    public ItemDto.AdminItemListWrapper getItemList(CondDto.ItemListCond dto) {
        int pageNumber = (dto.getNowPage() != null && dto.getNowPage() > 0) ? dto.getNowPage() : 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 9);

        Page<Item> itemListPaging = itemRepository.findItemListByCond(pageable, dto);

        List<ItemDto.AdminItemList> itemList = itemListPaging.stream()
                .map(ItemDto.AdminItemList::new)
                .toList();

        for (ItemDto.AdminItemList item : itemList) {
            Item findItem = itemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            int totalSales = getTotalSales(findItem);

            item.setTotalSales(totalSales);

            // item 객체에 맞는 fileUrl을 추출
            List<ItemDto.MainImgListResponse> fileUrl = s3FileRepository.findMainImg_UrlByItemId(findItem);
            item.setMainImg(fileUrl);
        }

        return new ItemDto.AdminItemListWrapper(itemList, itemListPaging.getTotalPages(), pageNumber);
    }

    @Transactional(readOnly = true)
    public ItemDto.AdminItemDef getItemDef(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        ItemDto.AdminItemDef itemDefDto = new ItemDto.AdminItemDef(item);

        int totalSales = getTotalSales(item);
        itemDefDto.setTotalSales(totalSales);

        List<ItemDto.MainImgListResponse> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(item);
        itemDefDto.setMainImg(mainImgUrl);

        List<ItemDto.SubImaListResponse> subImgUrl = s3FileRepository.findSubImg_UrlByItemId(item);
        itemDefDto.setSubImg(subImgUrl);

        return itemDefDto;
    }

    // orderDetail에 저장된 데이터로 item의 총 매출을 계산

    private int getTotalSales(Item item) {
        List<OrderDetail> OrderedItem = orderDetailRepository.findByItemId(item);

        int totalSales = 0;
        for (OrderDetail orderDetail : OrderedItem) {
            totalSales += orderDetail.getTotalPrice();
        }

        return totalSales;
    }

    @Transactional
    public void saveItem(ItemDto.SaveItem dto, List<MultipartFile> mainImgList, List<MultipartFile> subImgList) throws IOException {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 카테고리가 아닙니다."));

        /**
         * 1. Dto에 담긴 요청 데이터로 item객체를 생성해 저장한다.
         * 2. Dto에서 mainImg와 subImg 리스트의 요소를 추출한다.
         * 3. 각 이미지를 AWS S3에 업로드하고, fileName과 fileUrl을 S3File 엔티티에 저장한다.
         * 4. 저장된 이미지와 관련된 itemId, s3FileId, 이미지의 구분(main, sub)을 ItemFile 엔티티에 저장한다.
         */

        Item savedItem = itemRepository.save(dto.toEntity(category));

        for (MultipartFile mainImg : mainImgList) {
            uploadImg(mainImg, savedItem, "main");
        }

        for (MultipartFile subImg : subImgList) {
            uploadImg(subImg, savedItem, "serve");
        }
    }

    public void uploadImg(MultipartFile file, Item savedItem, String imgType) throws IOException {

        String fileName = file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType((file.getContentType()));
        metadata.setContentLength(file.getSize());

        // AWS S3에 이미지 업로드
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

        // 업로드 후 받아온 S3의 Url
        String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        // S3에 저장한 파일 정보 DB에 저장
        S3FileDto.SaveS3File s3FileDto = new S3FileDto.SaveS3File(fileName, fileUrl);
        S3File s3File = s3FileRepository.save(s3FileDto.toEntity());

        // Item 객체와 S3File 객체 정보 ItemFile에 저장
        ItemFileDto.SaveItemFile itemFileDto = new ItemFileDto.SaveItemFile(s3File, savedItem, imgType);
        itemFileRepository.save(itemFileDto.toEntity());
    }

    @Transactional(readOnly = true)
    public ItemDto.EditItemClick editItemClick(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 상품이 아닙니다."));

        ItemDto.EditItemClick editItemClick = new ItemDto.EditItemClick(item);

        List<ItemFile> itemFileList = itemFileRepository.findByItemId(item);

        List<S3FileDto.GetS3File> mainImgList = itemFileList.stream()
                .filter(itemFile -> "main".equals(itemFile.getMainOrServe()))
                .map(itemFile -> {
                    return new S3FileDto.GetS3File(itemFile.getS3FileId());
                })
                .toList();

        List<S3FileDto.GetS3File> subImgList = itemFileList.stream()
                .filter(itemFile -> "serve".equals(itemFile.getMainOrServe()))
                .map(itemFile -> {
                    return new S3FileDto.GetS3File(itemFile.getS3FileId());
                })
                .toList();

        editItemClick.setMainImgDataList(mainImgList);
        editItemClick.setServeImgDataList(subImgList);

        return editItemClick;
    }

    @Transactional
    public void editItem(ItemDto.EditItem dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 카테고리가 아닙니다."));

        itemRepository.updateItemByDto(dto, category);
    }
}
