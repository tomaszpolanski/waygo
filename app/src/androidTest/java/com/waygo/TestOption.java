package com.waygo;


import com.waygo.utils.SimpleTestCase;
import com.waygo.utilskt.option.Option;

import java.io.Serializable;
import java.util.List;

public class TestOption extends SimpleTestCase {


    public void testOfObjSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj(str);

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testOfObjNone() throws Exception {

        final String str = null;
        Option<String> op = Option.ofObj(str);

        assertFalse(op.getIsSome());
    }

    public void testMapSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj("")
                .map(__ -> str);

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testMapNone() throws Exception {

        Option<String> op = Option.ofObj((String) null)
                .map(__ -> "");

        assertFalse(op.getIsSome());
    }


    public void testFilterSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj(str)
                .filter(val -> val.equals(str));

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testFilterSomeFailed() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj(str)
                .filter(val -> val.equals(""));

        assertFalse(op.getIsSome());
    }

    public void testFilterNone() throws Exception {

        Option<String> op = Option.ofObj((String) null)
                .filter(val -> val.equals(""));

        assertFalse(op.getIsSome());
    }

    public void testFlatMapSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj("")
                .flatMap(val -> Option.ofObj(str));

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testFlatMapSomeFailed() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj(str)
                .flatMap(val -> Option.ofObj((String) null));

        assertFalse(op.getIsSome());
    }

    public void testFlatMapNone() throws Exception {

        Option<String> op = Option.ofObj((String) null)
                .flatMap(val -> Option.ofObj(""));

        assertFalse(op.getIsSome());
    }

    public void testOrOptionSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj(str)
                .orOption(() -> Option.ofObj(""));

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }


    public void testOrOptionNone() throws Exception {
        final String str = "Something";
        Option<String> op = Option.ofObj((String) null)
                .orOption(() -> Option.ofObj(str));

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testOrDefaultSome() throws Exception {

        final String str = "Something";
        String s = Option.ofObj(str)
                .orDefault(() -> "");

        assertEquals(str, s);
    }


    public void testOrDefaultNone() throws Exception {
        final String str = "Something";
        String s = Option.ofObj((String) null)
                .orDefault(() -> str);

        assertEquals(str, s);
    }

    public void testToListSome() throws Exception {

        final String str = "Something";
        List<String> list = Option.ofObj(str)
                .toList();

        assertEquals(1, list.size());
        assertEquals(str, list.get(0));
    }


    public void testToListNone() throws Exception {
        List<String> list = Option.ofObj((String) null)
                .toList();

        assertEquals(0, list.size());
    }

    public void testTryAsOptionSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.tryAsOption(() -> str);

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }


    public void testTryAsOptionNone() throws Exception {
        final Integer str = null;
        Option<String> op = Option.tryAsOption(() -> str.toString());

        assertFalse(op.getIsSome());
    }

    public void testOfTypeSome() throws Exception {

        final String str = "Something";
        Option<String> op = Option.ofObj((Serializable)str)
                                  .ofType(String.class);

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testOfTypeSomeFailed() throws Exception {

        Option<String> op = Option.ofObj(1)
                .ofType(String.class);

        assertFalse(op.getIsSome());
    }

    public void testOfTypeNone() throws Exception {

        Option<String> op = Option.ofObj((String) null)
                .ofType(String.class);

        assertFalse(op.getIsSome());
    }
}
