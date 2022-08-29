//package com.saehan.shop.service.items;
//
//import com.saehan.shop.domain.item.*;
//import com.saehan.shop.web.dto.ItemFormDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//@Transactional
//class ItemServiceTest {
//
//    @Autowired
//    ItemService itemService;
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Autowired
//    ItemImgRepository itemImgRepository;
//
//    List<MultipartFile> createMultipartFiles() throws Exception{
//        List<MultipartFile> multipartFileList = new ArrayList<>();
//
//        for(int i = 0; i < 5; i++){
//            String path = "C:/shop/item";
//            String imageName = "image" + i + ".jpg";
//            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
//            multipartFileList.add(multipartFile);
//        }
//        return multipartFileList;
//    }
//
//    @Test
//    @DisplayName("상품 등록 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    void saveItem() throws Exception{
//
//        String itemNm = "테스트 상품";
//        ItemSellStatus itemSellStatus = ItemSellStatus.SELL;
//        String itemDetail = "테스트 상품";
//        int price = 10000;
//        int stockNumber = 10000;
//        ItemCategory itemCategory = ItemCategory.Hand;
//
//        ItemFormDto itemFormDto = ItemFormDto.builder()
//                .itemNm(itemNm)
//                .itemSellStatus(itemSellStatus)
//                .itemDetail(itemDetail)
//                .price(price)
//                .stockNumber(stockNumber)
//                .itemCategory(itemCategory)
//                .build();
//
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
//
//        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//
//        assertEquals(itemNm, item.getItemNm());
//        assertEquals(itemCategory, item.getItemCategory());
//        assertEquals(itemDetail, item.getItemDetail());
//        assertEquals(price, item.getPrice());
//        assertEquals(stockNumber, item.getStockNumber());
//        assertEquals(itemSellStatus, item.getItemSellStatus());
//        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
//    }
//
//}