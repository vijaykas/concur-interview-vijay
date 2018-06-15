package com.concur.interview.dto;

import org.springframework.util.StringUtils;

import com.concur.interview.entity.Item;

public class ItemDto {

    private Item item;

    public ItemDto() {

    }

    public ItemDto(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean validate() {
        boolean isValid = true;
        if (item == null) {
            isValid = false;
        } else if (StringUtils.isEmpty(item.getTimestamp())) {
            isValid = false;
        }
        return isValid;
    }
}
