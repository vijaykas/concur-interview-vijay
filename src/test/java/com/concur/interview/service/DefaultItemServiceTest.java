package com.concur.interview.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concur.interview.dto.ItemDto;
import com.concur.interview.entity.Item;

public class DefaultItemServiceTest {

    ItemService itemService;

    @Before
    public void setup() {
        itemService = new DefaultItemService();
    }

    @Test
    public void createItem() {
        boolean created = itemService.createItem(getTestItemDto());
        assertTrue(created);

        List<ItemDto> items = (List<ItemDto>) itemService.getItems();
        assertNotNull(items);
        assertTrue(items.size() == 1);
    }

    @Test
    public void expiredItems() {

        for (int i = 120; i >= 0; i--) {
            Item item = new Item(i, ZonedDateTime.now().toInstant()
                    .minusMillis(i * i).toString());
            itemService.createItem(new ItemDto(item));
        }

        List<ItemDto> items = (List<ItemDto>) itemService.getItems();
        assertNotNull(items);
        assertTrue(items.size() < 50);
    }

    @Test
    public void unexpiredItems() {

        for (int i = 120; i >= 0; i--) {
            Item item = new Item(i,
                    ZonedDateTime.now().toInstant().minusMillis(i).toString());
            itemService.createItem(new ItemDto(item));
        }

        List<ItemDto> items = (List<ItemDto>) itemService.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= 100);


    }

    private ItemDto getTestItemDto() {
        Item item = new Item(111, ZonedDateTime.now().toString());
        ItemDto itemDto = new ItemDto(item);
        return itemDto;
    }

}
