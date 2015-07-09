package com.waygo;


import com.waygo.utils.SimpleTestCase;
import com.waygo.utilskt.Option;
import com.waygo.utilskt.Result;

import java.io.Serializable;

public class TestResult extends SimpleTestCase {


    public void testOfObjSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj(str);

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testOfObjFailure() throws Exception {

        final String str = null;
        Result<String> re = Result.ofObj(str);

        assertFalse(re.getIsSuccess());
    }

    public void testMapSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj("")
                .map(__ -> str);

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testMapFailure() throws Exception {

        Result<String> re = Result.ofObj((String) null)
                .map(__ -> "");

        assertFalse(re.getIsSuccess());
    }


    public void testFilterSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj(str)
                .filter(val -> val.equals(str), "");

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testFilterSomeFailed() throws Exception {

        final String str = "Something";
        final String error = "error";
        Result<String> re = Result.ofObj(str)
                .filter(val -> val.equals(""), error);

        assertFalse(re.getIsSuccess());
        assertEquals(error, re.getMessage());
    }

    public void testFilterFailure() throws Exception {

        Result<String> re = Result.ofObj((String) null)
                .filter(val -> val.equals(""), "Error");

        assertFalse(re.getIsSuccess());
    }

    public void testFlatMapSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj("")
                .flatMap(val -> Result.ofObj(str));

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testFlatMapSomeFailed() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj(str)
                .flatMap(val -> Result.ofObj((String) null));

        assertFalse(re.getIsSuccess());
    }

    public void testFlatMapFailure() throws Exception {

        Result<String> re = Result.ofObj((String) null)
                .flatMap(val -> Result.ofObj(""));

        assertFalse(re.getIsSuccess());
    }

    public void testOrOptionSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj(str)
                .orResult(() -> Result.ofObj(""));

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }


    public void testOrOptionFailure() throws Exception {
        final String str = "Something";
        Result<String> re = Result.ofObj((String) null)
                .orResult(() -> Result.ofObj(str));

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testOrDefaultSuccess() throws Exception {

        final String str = "Something";
        String s = Result.ofObj(str)
                .orDefault(() -> "");

        assertEquals(str, s);
    }


    public void testOrDefaultFailure() throws Exception {
        final String str = "Something";
        String s = Result.ofObj((String) null)
                .orDefault(() -> str);

        assertEquals(str, s);
    }

    public void testTryAsOptionSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.tryAsOption(() -> str);

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }


    public void testTryAsOptionFailure() throws Exception {
        final Integer str = null;
        Result<String> re = Result.tryAsOption(() -> str.toString());

        assertFalse(re.getIsSuccess());
    }

    public void testOfTypeSuccess() throws Exception {

        final String str = "Something";
        Result<String> re = Result.ofObj((Serializable)str)
                .ofType(String.class);

        assertTrue(re.getIsSuccess());
        assertEquals(str, re.getUnsafe());
    }

    public void testOfTypeSomeFailed() throws Exception {

        Result<String> re = Result.ofObj(1)
                .ofType(String.class);

        assertFalse(re.getIsSuccess());
    }

    public void testOfTypeFailure() throws Exception {

        Result<String> re = Result.ofObj((String) null)
                .ofType(String.class);

        assertFalse(re.getIsSuccess());
    }

    public void testToOptionSuccess() throws Exception {

        final String str = "Something";
        final Option<String> op = Result.ofObj(str)
                .toOption();

        assertTrue(op.getIsSome());
        assertEquals(str, op.getUnsafe());
    }

    public void testToOptionFailure() throws Exception {
        final Option<String> re = Result.ofObj((String) null)
                .toOption();

        assertFalse(re.getIsSome());
    }
}
