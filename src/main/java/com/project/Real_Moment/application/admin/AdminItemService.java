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
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 9);

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
            String fileUrl = s3FileRepository.findMainImg_UrlByItemId(findItem);
            item.setMainImg(fileUrl);
        }

        return new ItemDto.AdminItemListWrapper(itemList, itemListPaging.getTotalPages(), dto.getNowPage());
    }

    @Transactional(readOnly = true)
    public ItemDto.AdminItemDef getItemDef(Long itemId) {
        Item item = getItem(itemId);

        ItemDto.AdminItemDef itemDefDto = new ItemDto.AdminItemDef(item);

        int totalSales = getTotalSales(item);
        itemDefDto.setTotalSales(totalSales);

        List<S3FileDto.GetS3File> mainImgUrl = s3FileRepository.findMainImgList_UrlByItemId(item);
        itemDefDto.setMainImg(mainImgUrl);

        List<S3FileDto.GetS3File> subImgUrl = s3FileRepository.findSubImgList_UrlByItemId(item);
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
            uploadImg(subImg, savedItem, "sub");
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
        Item item = getItem(itemId);

        ItemDto.EditItemClick editItemClick = new ItemDto.EditItemClick(item);

        List<ItemFile> itemFileList = itemFileRepository.findByItemId(item);

        List<S3FileDto.GetS3File> mainImgList = itemFileList.stream()
                .filter(itemFile -> "main".equals(itemFile.getMainOrSub()))
                .map(itemFile -> {
                    return new S3FileDto.GetS3File(itemFile.getS3FileId());
                })
                .toList();

        List<S3FileDto.GetS3File> subImgList = itemFileList.stream()
                .filter(itemFile -> "sub".equals(itemFile.getMainOrSub()))
                .map(itemFile -> {
                    return new S3FileDto.GetS3File(itemFile.getS3FileId());
                })
                .toList();

        editItemClick.setMainImgDataList(mainImgList);
        editItemClick.setSubImgDataList(subImgList);

        return editItemClick;
    }

    @Transactional
    public void editItem(ItemDto.EditItem dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 카테고리가 아닙니다."));

        itemRepository.updateItemByDto(dto, category);
    }

    @Transactional
    public void editItemMainImg(ItemDto.ItemIdRequestPart itemId, List<MultipartFile> mainImgList, S3FileDto.s3FileIdRequestPart s3FileIdList) {

        /**
         * 요청 데이터 : 이미지 수정될 상품 ID, 수정될 S3FileId, 수정될 이미지
         * 1. 요청 받은 itemId로 item 객체를 생성한다.
         * 2. 요청 받은 s3FileId를 통해 fileUrl에 담긴 Url에 해당하는 AWS S3의 파일을 삭제한다.
         * 3. 요청 받은 mainImgList에 담긴 이미지를 AWS S3에 등록한다.
         * 4. 새로 저장된 AWS S3 이미지의 Url과 fileName을 s3File에 update한다.
         */

        Item item = getItem(itemId.getItemId());

        for (int i = 0; i < s3FileIdList.getS3FileId().size(); i++) {

            Long s3FileId = s3FileIdList.getS3FileId().get(i);

            S3File s3File = s3FileRepository
                    .findById(s3FileId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));

            MultipartFile mainImg = mainImgList.get(i);

            // 이미지 업로드
            String fileName = mainImg.getOriginalFilename();
            String fileUrl = uploadImageToS3(fileName, mainImg);

            updateS3File(s3FileId, fileName, fileUrl);
            deleteImgFromS3(s3File);
        }
    }

    @Transactional
    public void editItemSubImg(ItemDto.ItemIdRequestPart itemId, List<MultipartFile> subImgList, S3FileDto.s3FileIdRequestPart s3FileIdList) {

        Item item = getItem(itemId.getItemId());

        for (int i = 0; i < s3FileIdList.getS3FileId().size(); i++) {

            Long s3FileId = s3FileIdList.getS3FileId().get(i);

            S3File s3File = s3FileRepository
                    .findById(s3FileId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));

            MultipartFile subImg = subImgList.get(i);

            // 이미지 업로드
            String fileName = subImg.getOriginalFilename();
            String fileUrl = uploadImageToS3(fileName, subImg);

            updateS3File(s3FileId, fileName, fileUrl);
            deleteImgFromS3(s3File);
        }
    }

    @Transactional
    public void deleteItem(Long itemId) {

        Item item = getItem(itemId);

        /**
         * 1. 삭제할 item에 대한 itemFile 객체를 찾는다.
         * 2. itemFile가 참조한 S3File 객체를 찾는다.
         * 3. S3File에 저장된 이미지들을 AWS S3에서 삭제한다.
         * 4. 상품에 대한 이미지를 모두 삭제한 뒤 마지막으로 Item 객체를 isDelete = true 처리한다.
         */

        // 상품 삭제 처리 (isDelete = true)
        itemRepository.deleteItem(itemId);

        List<ItemFile> itemFileList = itemFileRepository.findByItemId(item);

        for (ItemFile itemFile : itemFileList) {
            S3File s3File = itemFile.getS3FileId();

            deleteImgFromS3(s3File);

            // 상품 이미지 삭제 후 데이터 삭제
            s3FileRepository.delete(s3File);
            itemFileRepository.delete(itemFile);
        }
    }

    @Transactional
    public void replaceImg(ItemDto.ReplaceImg dto, String imgType) {

        S3File s3File = s3FileRepository.findById(dto.getS3FileId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));

        ItemFile itemFile = itemFileRepository.findByS3FileId(s3File);

        if (!itemFile.getMainOrSub().equalsIgnoreCase(imgType)) {
            throw new IllegalArgumentException(imgType + " 이미지가 아닙니다.");
        }

        // 기존 이미지 삭제
        deleteImgFromS3(s3File);

        // 새로운 이미지 S3에 업로드
        String fileName = dto.getImgFile().getOriginalFilename();
        String fileUrl = uploadImageToS3(fileName, dto.getImgFile());

        // 새로운 이미지 ItemFile에 갱신
        s3FileRepository.updateImg(s3File.getId(), fileName, fileUrl);
    }

    @Transactional
    public void addImg(ItemDto.AddImg dto, String imgType) {

        Item item = getItem(dto.getItemId());

        // 요청된 number와 같거나 큰 순서를 가진 객체 List 추출
        List<ItemFile> itemFileList = itemFileRepository.findImgListByGoeNumber(item, dto.getNumber(), imgType);

        // 객체들의 순서를 +1
        for (ItemFile itemFile : itemFileList) {
            itemFileRepository.updateGoeNumberPlus(itemFile);
        }

        // 이미지 S3에 업로드
        String fileName = dto.getImgFile().getOriginalFilename();
        String fileUrl = uploadImageToS3(fileName, dto.getImgFile());

        // 새로 추가한 이미지 데이터 저장
        S3File savedS3File = s3FileRepository.save(dto.toEntity(fileName, fileUrl));

        itemFileRepository.save(dto.toEntity(item, savedS3File, dto.getNumber(), imgType));
    }

    @Transactional
    public void numberChangeImg(ItemDto.NumberChangeImg dto, String imgType) {
        Item item = getItem(dto.getItemId());

        itemFileRepository.updateChangeNumber(item, dto.getNumber1(), dto.getNumber2(), imgType);
    }

    @Transactional
    public void deleteImg(Long itemId, Long s3FileId, String imgType) {
        Item item = getItem(itemId);

        S3File s3File = s3FileRepository.findById(s3FileId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));

        ItemFile findItemFile = itemFileRepository.findByS3FileId(s3File);

        // 메인 이미지 중 대표 이미지 (number = 0)은 삭제할 수 없다.
        if (imgType.equalsIgnoreCase("main") && findItemFile.getNumber() == 0) {
            throw new RuntimeException("대표 이미지는 삭제할 수 없습니다.");
        }

        // S3에서 이미지 삭제
        deleteImgFromS3(s3File);

        // 이미지 순서 재구성
        List<ItemFile> itemFileList = itemFileRepository.findImgListByGoeNumber(item, findItemFile.getNumber(), imgType);
        for (ItemFile itemFile : itemFileList) {
            itemFileRepository.updateGoeNumberMinus(itemFile);
        }

        // 이미지 데이터 삭제
        s3FileRepository.delete(s3File);
        itemFileRepository.delete(findItemFile);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private String uploadImageToS3(String fileName, MultipartFile img) {
        try {
            // AWS S3에 이미지 업로드
            amazonS3Client.putObject(bucket, fileName, img.getInputStream(), new ObjectMetadata());

            // 업로드 후 받아온 Url
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }

    private void updateS3File(Long s3FileId, String fileName, String fileUrl) {

        // 새로 저장한 이미지에 대한 정보 S3File에 update
        s3FileRepository.updateImg(s3FileId, fileName, fileUrl);
    }

    private void deleteImgFromS3(S3File s3File) {

        // 이전 이미지 삭제
        amazonS3Client.deleteObject(bucket, s3File.getFileName());
    }
}
