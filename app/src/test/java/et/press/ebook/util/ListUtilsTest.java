package et.press.ebook.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import et.press.ebook.models.Chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtilsTest {
    private final List<Chapter> items = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 20; i++) {
            Chapter item = new Chapter(String.valueOf(i));
            item.chapterUuid = i;
            item.chapterName = String.format("item_%d", i);
            items.add(item);
        }
    }

    @Test
    public void searchByName_ShouldReturnEmptyForNoResults() {
        assertTrue(ListUtils.searchByName("item_21", items).isEmpty());
        assertTrue(ListUtils.searchByName("item_21", new ArrayList<>()).isEmpty());
    }

    @Test
    public void searchByName_ShouldReturnMatchedResults() {
        Assert.assertEquals(20, ListUtils.searchByName("item", items).size());
        Assert.assertEquals(11, ListUtils.searchByName("item_1", items).size());
        Assert.assertEquals(1, ListUtils.searchByName("19", items).size());
    }

    @Test
    public void sortById_ShouldReturnValidResult() {
        List<Chapter> reversed = new ArrayList<>(items);
        Collections.reverse(reversed);
        Assert.assertEquals(items, ListUtils.sortById(reversed));
    }
}