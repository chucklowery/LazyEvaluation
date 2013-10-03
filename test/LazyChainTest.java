/*
 * Copyright (C) 2013 Payment Alliance International. All Rights Reserved.
 * 
 * This software is the proprietary information of Payment Alliance International.
 * Use is subject to license terms.
 * 
 * Name: LazyChainTest.java 
 * Created: Oct 3, 2013 3:20:32 PM
 * Author: Chuck Lowery <chuck.lowery @ gopai.com>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gopai.com>
 */
public class LazyChainTest {

    @Test
    @Ignore
    public void testIterate_single() {
        List<String> strings = Arrays.asList(new String[]{"A", "B", "C"});

        LazyChain<String> chain = new LazyChain<String>(strings);

        for (String string : chain) {
            System.out.println(string);
        }
    }

    @Test
    public void testIterate_triple() {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        List<Iterable> level0 = new ArrayList<Iterable>();
        List<Iterable> level1_0 = new ArrayList<Iterable>();
        List<Iterable> level1_1 = new ArrayList<Iterable>();
        List<Iterable> level1_2 = new ArrayList<Iterable>();

        level0.add(level1_0);
        level0.add(level1_1);
        level0.add(level1_2);

        level1_0.add(Arrays.asList(new String[]{"A", "B", "C"}));
        level1_0.add(Arrays.asList(new String[]{"D", "E", "F"}));
        level1_0.add(Arrays.asList(new String[]{"G", "H", "I"}));

        level1_1.add(Arrays.asList(new String[]{"J", "K", "L"}));
        level1_1.add(Arrays.asList(new String[]{"M", "N", "O"}));
        level1_1.add(Arrays.asList(new String[]{"P", "Q", "R"}));

        level1_2.add(Arrays.asList(new String[]{"S", "T", "U"}));
        level1_2.add(Arrays.asList(new String[]{"V", "W", "X"}));
        level1_2.add(Arrays.asList(new Object[]{"Y", "Z", 
            Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"})}));

        LazyChain<String> chain = new LazyChain<String>(level0);

        int count = 0;
        for (String string : chain) {
            Assert.assertEquals(alphabet[count++], string);
        }
    }
}