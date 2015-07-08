package com.waygo;


import com.waygo.utils.SimpleTestCase;
import com.waygo.utilskt.option.Option;

public class TestOption extends SimpleTestCase {


    public void testOfObjSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.Companion.ofObj(str);

        assertTrue(op.getIsSome());
        assertEquals(str, op.getValue());
    }

    public void testOfObjNone() throws Exception {

        final String str = null;
        Option<String> op = Option.Companion.ofObj(str);

        assertFalse(op.getIsSome());
    }

}
