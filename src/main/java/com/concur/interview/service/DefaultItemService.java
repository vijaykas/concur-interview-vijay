package com.concur.interview.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import com.concur.interview.dto.ItemDto;
import com.concur.interview.entity.Item;

@Component
public class DefaultItemService implements ItemService {

    private Deque<Item> itemStore = new ConcurrentLinkedDeque<>();
    private Map<Long, Long> expiryMap = new ConcurrentHashMap<>();
    private static ZoneId zoneId;

    public boolean createItem(ItemDto itemDto) {

        if (!itemDto.validate()) {
            return false;
        }

        Item item = itemDto.getItem();

        /*
         * If the number of items stored is more than 100, we can remove the
         * oldest item from the store while saving so that, get operation will
         * have less data to deal with and be faster in returning item list
         */
        if (itemStore.size() > 100) {
            Item oldItem = itemStore.peekFirst();
            if (isExpired(expiryMap.get(oldItem.getId()),
                    ZonedDateTime.now().toInstant().toEpochMilli())) {
                itemStore.removeFirst();
                expiryMap.remove(oldItem.getId());
            }
        }

        /*
         * Insert item to the end of deque so that oldest item is available at
         * the front of deque
         */
        itemStore.addLast(item);

        ZonedDateTime zdt = ZonedDateTime.parse(item.getTimestamp());
        if (zoneId == null) {
            zoneId = zdt.getZone();
        }

        expiryMap.put(item.getId(), zdt.toInstant().toEpochMilli());

        return true;
    }

    /*
     * Return items stored in the last 2 seconds or the list of last 100 saved
     * items, whichever is greater.
     * 
     */
    public Collection<ItemDto> getItems() {
        List<ItemDto> items = null;

        cleanupItemStore();
        
        if (itemStore.size() > 100) {
            Item item = itemStore.peekFirst();
            if (!isExpired(expiryMap.get(item.getId()),
                    ZonedDateTime.now().toInstant().toEpochMilli())) {
                items = getItemDtos();
            } else {
                while (itemStore.size() >= 100) {
                    Item oldItem = itemStore.removeFirst();
                    expiryMap.remove(oldItem.getId());
                }
                items = getItemDtos();
            }
        } else {
            items = getItemDtos();
        }

        return items;
    }

    private List<ItemDto> getItemDtos() {
        List<ItemDto> items = new ArrayList<>();
        Iterator<Item> iterator = itemStore.iterator();
        while (iterator.hasNext()) {
            items.add(new ItemDto(iterator.next()));
        }

        return items;
    }

    /*
     * This method check if an item is expired by checking if difference between
     * current time and item timestamp is under 2 seconds
     */
    private boolean isExpired(Long itemExpiry, long now) {

        if (itemExpiry != null) {
            long itemExpirationTime = itemExpiry.longValue();

            if ((now - itemExpirationTime) <= 2000) {
                return false;
            }
        }

        return true;
    }

    /*
     * This method will remove the expired item from the deque, but maintains a
     * size of 100 items even if items have expired so that we could return last
     * 100 posted items.
     */
    private void cleanupItemStore() {

        while (itemStore.size() > 100) {
            Iterator<Item> iterator = itemStore.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (isExpired(expiryMap.get(item.getId()),
                        ZonedDateTime.now().toInstant().toEpochMilli())) {
                    expiryMap.remove(item.getId());
                    iterator.remove();
                } else {
                    return;
                }
            }
        }
    }

}
